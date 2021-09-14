package com.skm.crowd.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.skm.crowd.constant.CrowdConstant;
import com.skm.crowd.entity.Role;
import com.skm.crowd.entity.RoleExample;
import com.skm.crowd.mapper.RoleMapper;
import com.skm.crowd.service.RoleService;
import com.skm.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    /**
     * 获取分页
     * @param keyword   查找关键字
     * @param pageNum   页码
     * @param pageSize  一页展示的个数
     * @return
     */
    @Override
    public PageInfo<Role> getPageInfo(String keyword, Integer pageNum, Integer pageSize) {
        // 调用PageHelper的静态方法开启分页功能，需要写在mapper执行之前
        PageHelper.startPage(pageNum, pageSize);

        List<Role> roles = roleMapper.selectRoleByKeyword(keyword);

        return new PageInfo<>(roles);
    }

    /**
     * 添加新角色
     * @param role  新角色
     * @return
     */
    @Override
    public ResultEntity<String> saveRole(Role role) {
        // 验证是否已经存在该角色
        RoleExample roleExample = new RoleExample();
        RoleExample.Criteria criteria = roleExample.createCriteria();
        criteria.andNameEqualTo(role.getName());
        // 查找
        List<Role> roles = roleMapper.selectByExample(roleExample);


        if (roles == null || roles.size() == 0) {
            roleMapper.insert(role);
            return ResultEntity.successWithoutDataWithMessage(CrowdConstant.MESSAGE_ADMIN_INSERT_SUCCESS);
        }
        return ResultEntity.failed(CrowdConstant.MESSAGE_ADMIN_INSERT_FAILED + "！角色已存在！");
    }

    /**
     * 通过id来对角色名进行修改
     * @param role  待更新进数据库的角色
     * @return
     */
    @Override
    public ResultEntity<String> editRole(Role role) {
        // 判空
        if (role.getName().length() == 0 || "".equals(role.getName())) {
            return ResultEntity.failed("角色名不能为空！");
        }

        // 验证是否已经存在该角色
        RoleExample roleExample = new RoleExample();
        RoleExample.Criteria criteria = roleExample.createCriteria();
        criteria.andNameEqualTo(role.getName());
        List<Role> roles = roleMapper.selectByExample(roleExample);

        // 不存在相同角色，则修改
        if (roles == null || roles.size() == 0) {
            int result = roleMapper.updateByPrimaryKey(role);
            if (result != 0) {
                return ResultEntity.successWithoutDataWithMessage(CrowdConstant.MESSAGE_ADMIN_EDIT_SUCCESS);
            } else {
                return ResultEntity.failed(CrowdConstant.MESSAGE_EDIT_FAILED);
            }
        }

        // 存在则发送通知
        return ResultEntity.failed("该用户名已存在");
    }

    @Override
    public ResultEntity<String> removeRoles(List<Integer> list) {
        if (list == null || list.size() == 0) {
            return ResultEntity.failed(CrowdConstant.MESSAGE_DELETE_CAN_NOT_NULL);
        }
        // 高并发可能还要检测是否已还在，可能被别人删除了
        RoleExample roleExample = new RoleExample();
        RoleExample.Criteria criteria = roleExample.createCriteria();
        criteria.andIdIn(list);

        roleMapper.deleteByExample(roleExample);
        return ResultEntity.successWithoutDataWithMessage(CrowdConstant.MESSAGE_DELETE_SUCCESS);
    }

    @Override
    public List<Role> getAssignedRole(Integer adminId) {
        return roleMapper.selectAssignedRole(adminId);
    }

    @Override
    public List<Role> getUnassignedRole(Integer adminId) {
        return roleMapper.selectUnassignedRole(adminId);
    }
}
