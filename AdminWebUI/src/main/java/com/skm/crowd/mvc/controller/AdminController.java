package com.skm.crowd.mvc.controller;

import com.github.pagehelper.PageInfo;
import com.skm.crowd.constant.CrowdConstant;
import com.skm.crowd.entity.Admin;
import com.skm.crowd.service.AdminService;
import com.skm.crowd.util.CrowdUtil;
import com.skm.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @PreAuthorize("hasRole('人事')")
    @RequestMapping("/get/page.do")
    public String getPage(@RequestParam(value = "keyword", defaultValue = "", required = false) String keyword,
                          @RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum,
                          @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                          ModelMap modelMap) {
        PageInfo<Admin> pageInfo = adminService.getPageInfo(keyword, pageNum, pageSize);
        modelMap.addAttribute(CrowdConstant.ATTR_NAME_PAGE_INFO, pageInfo);
        return "admin-page";
    }

    @RequestMapping("/do/login.do")
    public String doLogin(String loginAcct, String userPassword, HttpSession session) {
        /*
            Amin admin = adminService.getAdminByLoginAcct(loginAcct, userPassword);
            session.setAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN, admin);
        */ // 前版本代码
        adminService.doLogin(loginAcct, userPassword, session);

        // 避免刷新页面导致重复提交表单
        return "redirect:/admin/to/main/page.do";
    }

    @RequestMapping("/do/logout.do")
    public String doLoginOut(HttpSession session) {
        // 强制session失效
        session.invalidate();

        return "redirect:/admin/to/login/page.do";
    }

    @RequestMapping("/remove/{id}.do")
    @ResponseBody
    public ResultEntity<Object> remove(@PathVariable("id") Integer id,
                                       @RequestParam("keyword") String keyword,
                                       @RequestParam("pageNum") String pageNum) {
        Integer result = adminService.removeById(id);
        // 数据封装
        ResultEntity<Object> rst;
        if (result == 1) {
            rst = ResultEntity.successWithoutData();
            rst.setMessage("删除成功");
        } else {
            rst = ResultEntity.failed("删除失败");
        }

        // 设置删除数据后的页面刷新
        String redirect = "admin/get/page.do?pageNum=" + pageNum;
        if (!"".equals(keyword)) {
            redirect += "&keyword=" + keyword;
        }
        rst.setData(redirect);
        return rst;
    }

    // 新增
    @RequestMapping("/add/save.do")
    @ResponseBody
    public ResultEntity<String> saveAdmin(Admin admin) {
        return adminService.saveAdmin(admin);
    }

    // 检查用户名是否被使用
    @RequestMapping("/add/checkAdminAcct.do")
    @ResponseBody
    public ResultEntity<String> checkAdminAcct(@RequestParam("adminAcct") String adminAcct) {
        // 检查用户名是否被使用
        if (!adminService.checkAdminAcctIsInUsed(adminAcct)) {
            return ResultEntity.successWithoutData();
        }
        return ResultEntity.failed(CrowdConstant.MESSAGE_LOGIN_ALREADY_IN_USE);
    }

    /**
     * 根据Id获取对应Admin对象
     */
    @RequestMapping("/get/admin/by/id.do")
    @ResponseBody
    public ResultEntity<Admin> doGetAdminById(Integer adminId) {
        return adminService.getAdminById(adminId);
    }

    /**
     * 针对前端页面递交的修改Admin
     */
    @RequestMapping("/edit/save.do")
    @ResponseBody
    public ResultEntity<String> doSaveEdit(Admin admin) {
        return adminService.alterEditedAdmin(admin);
    }
}
