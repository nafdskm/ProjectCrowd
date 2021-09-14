package com.skm.crowd.service;

import com.skm.crowd.entity.Auth;
import com.skm.crowd.util.ResultEntity;

import java.util.List;

public interface AuthService {
    ResultEntity<List<Auth>> getAllAuth();

    ResultEntity<List<Integer>> getAssignedAuthIdByRoleId(Integer roleId);

    ResultEntity<String> saveAssignAuthToRole(Integer roleId, List<Integer> authIdArray);

    void removeOldAssignedAuth(Integer roleId);

    List<String> getAssignAuthNameByAdminId(Integer adminId);
}
