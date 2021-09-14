package com.skm.crowd.mvc.controller;

import com.github.pagehelper.PageInfo;
import com.skm.crowd.entity.Role;
import com.skm.crowd.service.RoleService;
import com.skm.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @RequestMapping("/role/get/page/info.do")
    @ResponseBody
    public ResultEntity<PageInfo<Role>> doGetPageInfo(
            @RequestParam(value = "keyword", defaultValue = "", required = false) String keyword,
            @RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        PageInfo<Role> pageInfo = roleService.getPageInfo(keyword, pageNum, pageSize);
        return ResultEntity.successWithData(pageInfo);
    }

    @PreAuthorize("hasAuthority('role:add')")
    @RequestMapping("/role/save.do")
    @ResponseBody
    public ResultEntity<String> doSaveRole(Role role) {
        return roleService.saveRole(role);
    }

    @RequestMapping("/role/edit.do")
    @ResponseBody
    public ResultEntity<String> doEditRole(Role role) {
        return roleService.editRole(role);
    }

    @PreAuthorize("hasAuthority('role:delete')")
    @RequestMapping("/role/remove.do")
    @ResponseBody
    public ResultEntity<String> doRemove(@RequestParam("list[]") List<Integer> list) {
        return roleService.removeRoles(list);
    }

}
