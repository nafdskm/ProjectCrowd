package com.skm.crowd.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.skm.crowd.constant.CrowdConstant;
import com.skm.crowd.entity.Admin;
import com.skm.crowd.entity.AdminExample;
import com.skm.crowd.exception.AdminAcctIsInUsedException;
import com.skm.crowd.exception.LoginFailedException;
import com.skm.crowd.mapper.AdminMapper;
import com.skm.crowd.service.AdminService;
import com.skm.crowd.util.CrowdUtil;
import com.skm.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public boolean checkAdminAcctIsInUsed(String adminAcct) {
        // 数据获取
        AdminExample adminExample = new AdminExample();
        AdminExample.Criteria criteria = adminExample.createCriteria();
        criteria.andLoginAcctEqualTo(adminAcct);
        List<Admin> admins = adminMapper.selectByExample(adminExample);

        // 如果结果集为空，则未被用过
        if (admins == null || admins.size() == 0) {
            return false;
        }

        // 逐个比较查验
        for (Admin admin : admins) {
            if (admin.getLoginAcct().equals(adminAcct)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ResultEntity<String> saveAdmin(Admin admin) {
        // 密码加密
//        admin.setUserPassword(CrowdUtil.md5(admin.getUserPassword()));
        admin.setUserPassword(bCryptPasswordEncoder.encode(admin.getUserPassword()));

        int result = adminMapper.insert(admin);
        ResultEntity<String> rst;
        if (result == 1) {
            rst = ResultEntity.successWithData(CrowdConstant.MESSAGE_ADMIN_INSERT_SUCCESS);
        } else {
            rst = ResultEntity.failed(CrowdConstant.MESSAGE_ADMIN_INSERT_FAILED);
        }
        return rst;
    }

    @Override
    public void doLogin(String loginAcct, String userPassword, HttpSession session) {
        if ((loginAcct == null || loginAcct.length() == 0)
                || (userPassword == null || userPassword.length() == 0)) {
            throw new LoginFailedException("账号或密码不能为空");
        }

        // 根据账号查找
        AdminExample adminExample = new AdminExample();
        AdminExample.Criteria criteria = adminExample.createCriteria();
        criteria.andLoginAcctEqualTo(loginAcct);
        List<Admin> admins = adminMapper.selectByExample(adminExample);

        // 查找结果为空
        if (admins == null || admins.size() == 0) {
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }

        // 密码比对不一致
        String password = CrowdUtil.md5(userPassword);
        Admin admin = admins.get(0);
        if (!Objects.equals(password, admin.getUserPassword())) {
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }

        // 各项验证正确，将对象放入会话
        session.setAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN, admin);
    }

    @Override
    public PageInfo<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize) {
        // 调用PageHelper的静态方法开启分页功能，需要写在mapper执行之前
        PageHelper.startPage(pageNum, pageSize);

        List<Admin> admins = adminMapper.selectAdminByKeyword(keyword);

        return new PageInfo<>(admins);
    }

    @Override
    public Integer removeById(Integer id) {
        return adminMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void saveAdminRoleRelationship(Integer adminId, List<Integer> roleIdList) {
        // 为简化操作，先根据adminId删除所有角色分配关系，再将新的角色分配关系放入
        adminMapper.deleteOldRoleRelationship(adminId);

        if (roleIdList != null && roleIdList.size() > 0) {
            adminMapper.insertNewRoleRelationship(adminId, roleIdList);
        }
    }

    @Override
    public ResultEntity<Admin> getAdminById(Integer adminId) {
        Admin admin = adminMapper.selectByPrimaryKey(adminId);
        if (admin != null) {
            return ResultEntity.successWithData(admin);
        }
        return ResultEntity.failed(CrowdConstant.MESSAGE_SEARCH_FAILED);
    }

    @Override
    public ResultEntity<String> alterEditedAdmin(Admin admin) {
        // md5加密
        if (admin.getUserPassword() != null && admin.getUserPassword().length() != 0) {
            admin.setUserPassword(CrowdUtil.md5(admin.getUserPassword()));
        }
        int result = adminMapper.updateEditedAdmin(admin);
        if (result != 0) {
            return ResultEntity.successWithoutDataWithMessage(CrowdConstant.MESSAGE_ADMIN_EDIT_SUCCESS);
        }
        return ResultEntity.failed(CrowdConstant.MESSAGE_EDIT_FAILED);
    }

    @Override
    public Admin getAdminByLoginAcct(String username) {
        AdminExample adminExample = new AdminExample();
        AdminExample.Criteria criteria = adminExample.createCriteria();
        criteria.andLoginAcctEqualTo(username);
        List<Admin> list = adminMapper.selectByExample(adminExample);
        return list.get(0);
    }
}
