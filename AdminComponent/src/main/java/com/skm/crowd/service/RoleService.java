package com.skm.crowd.service;

import com.github.pagehelper.PageInfo;
import com.skm.crowd.entity.Role;
import com.skm.crowd.util.ResultEntity;

import java.util.List;

public interface RoleService {

    PageInfo<Role> getPageInfo(String keyword, Integer pageNum, Integer pageSize);

    ResultEntity<String> saveRole(Role role);

    ResultEntity<String> editRole(Role role);

    ResultEntity<String> removeRoles(List<Integer> list);

    List<Role> getAssignedRole(Integer adminId);

    List<Role> getUnassignedRole(Integer adminId);
}
