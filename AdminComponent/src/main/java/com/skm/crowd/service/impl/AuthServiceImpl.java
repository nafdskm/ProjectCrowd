package com.skm.crowd.service.impl;

import com.skm.crowd.constant.CrowdConstant;
import com.skm.crowd.entity.Auth;
import com.skm.crowd.entity.AuthExample;
import com.skm.crowd.entity.Menu;
import com.skm.crowd.mapper.AuthMapper;
import com.skm.crowd.mapper.RoleMapper;
import com.skm.crowd.service.AuthService;
import com.skm.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthMapper authMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public ResultEntity<List<Auth>> getAllAuth() {
        List<Auth> auths = authMapper.selectByExample(new AuthExample());
        return ResultEntity.successWithData(auths);
    }

    @Override
    public ResultEntity<List<Integer>> getAssignedAuthIdByRoleId(Integer roleId) {
        List<Integer> list = authMapper.selectAssignedAuthIdByRoleId(roleId);
        return ResultEntity.successWithData(list);
    }

    @Override
    public ResultEntity<String> saveAssignAuthToRole(Integer roleId, List<Integer> authIdArray) {
        int result = 1;
        if (authIdArray != null && authIdArray.size() > 0) {
            try {
                result = authMapper.insertAssignAuthToRole(roleId, authIdArray);
            } catch (Exception e) {
                result = 0;
            }
        }
        if (result != 0) {
            return ResultEntity.successWithoutDataWithMessage(CrowdConstant.MESSAGE_ADMIN_EDIT_SUCCESS);
        }
        return ResultEntity.failed(CrowdConstant.MESSAGE_EDIT_FAILED);
    }

    @Override
    public void removeOldAssignedAuth(Integer roleId) {
        roleMapper.deleteAllAssignedAuth(roleId);
    }

    @Override
    public List<String> getAssignAuthNameByAdminId(Integer adminId) {
        return authMapper.selectAssignedAuthNameByAdminId(adminId);
    }
}
