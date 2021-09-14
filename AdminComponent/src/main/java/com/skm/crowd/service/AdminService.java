package com.skm.crowd.service;

import com.github.pagehelper.PageInfo;
import com.skm.crowd.entity.Admin;
import com.skm.crowd.util.ResultEntity;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface AdminService {

    boolean checkAdminAcctIsInUsed(String adminAcct);

    ResultEntity<String> saveAdmin(Admin admin);

    void doLogin(String loginAcct, String userPassword, HttpSession session);

    PageInfo<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize);

    Integer removeById(Integer id);

    void saveAdminRoleRelationship(Integer adminId, List<Integer> roleIdList);

    ResultEntity<Admin> getAdminById(Integer adminId);

    ResultEntity<String> alterEditedAdmin(Admin admin);

    Admin getAdminByLoginAcct(String username);
}
