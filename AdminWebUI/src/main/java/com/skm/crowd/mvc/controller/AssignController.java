package com.skm.crowd.mvc.controller;

import com.skm.crowd.entity.Auth;
import com.skm.crowd.entity.Role;
import com.skm.crowd.service.AdminService;
import com.skm.crowd.service.AuthService;
import com.skm.crowd.service.RoleService;
import com.skm.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/assign")
public class AssignController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthService authService;

    @RequestMapping("/to/assign/role/save.do")
    public String doSaveAssignRole(@RequestParam("adminId") Integer adminId,
                                   @RequestParam("pageNum") Integer pageNum,
                                   @RequestParam("keyword") String keyword,
                                   @RequestParam(value = "roleIdList", required = false) List<Integer> roleIdList) {

        adminService.saveAdminRoleRelationship(adminId, roleIdList);

        return "redirect:/admin/get/page.do?pageNum=" + pageNum + "&keyword=" + keyword;
    }

    /**
     * 跳转admin角色分配界面
     */
    @RequestMapping("/to/assign/role/page.do")
    public String doToAssignRolePage(@RequestParam("adminId") Integer adminId, ModelMap modelMap) {
        List<Role> assignedRoleList = roleService.getAssignedRole(adminId);
        List<Role> unassignedRoleList = roleService.getUnassignedRole(adminId);

        modelMap.addAttribute("assignedRoleList", assignedRoleList);
        modelMap.addAttribute("unassignedRoleList", unassignedRoleList);

        return "assign-admin-role";
    }

    /**
     * 获取所有权限
     * 用于建立权限树
     */
    @RequestMapping("/get/all/auth.do")
    @ResponseBody
    public ResultEntity<List<Auth>> doGetAllAuth() {
        return authService.getAllAuth();
    }

    /**
     * 通过roleId获取对应角色所拥有的权限
     * 用于打开某一角色权限列表时，自动勾选已拥有权限
     */
    @RequestMapping("/get/assigned/auth/id/by/roleId.do")
    @ResponseBody
    public ResultEntity<List<Integer>> doGetAssignedAuthIdByRoleId(Integer roleId) {
        return authService.getAssignedAuthIdByRoleId(roleId);
    }

    /**
     *  绑定权限到角色上
     */
    @RequestMapping("/auth/to/role.do")
    @ResponseBody
    public ResultEntity<String> doAssignAuthToRole(@RequestParam(value = "authIdArray[]", required = false) List<Integer> authIdArray,
                                                   @RequestParam("roleId") Integer roleId) {
        // 先删除所有旧有权限
        // 能够进行到这一步，说明roleId是一定是合法的，所以不进行判断
        authService.removeOldAssignedAuth(roleId);
        // 添加权限
        return authService.saveAssignAuthToRole(roleId, authIdArray);
    }
}
