package com.skm.crowd.mvc.controller;

import com.skm.crowd.entity.Menu;
import com.skm.crowd.service.MenuService;
import com.skm.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.transform.Result;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @ResponseBody
    @RequestMapping("/menu/get/whole/tree.do")
    public ResultEntity<Menu> doGetWholeTree() {
        List<Menu> tree = menuService.getAll();
        Menu root = null;
        Map<Integer, Menu> menuMap = new HashMap<>();

        // 遍历创建Id和Menu映射
        for (Menu node : tree) {
            menuMap.put(node.getId(), node);
        }

        // 创建树
        for (Menu node : tree) {
            Integer pid = node.getPid();

            if (pid == null) {
                root = node;
                continue;
            }

            Menu father = menuMap.get(pid);
            father.getChildren().add(node);
        }

        return ResultEntity.successWithData(root);
    }

    @RequestMapping("/menu/save/node.do")
    public String doSaveNode(Menu menu) {
        menuService.saveMenu(menu);
        return "redirect:/admin/menu/page.do";
    }

    @RequestMapping("/menu/remove/node.do")
    public String doRemoveNode(Integer id) {
        menuService.removeMenu(id);
        return "redirect:/admin/menu/page.do";
    }
}
