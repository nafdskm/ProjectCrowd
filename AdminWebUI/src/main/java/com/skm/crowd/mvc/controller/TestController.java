package com.skm.crowd.mvc.controller;

import com.skm.crowd.util.ResultEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/test.do")
    public String test() {
        System.out.println(1);
        return "test";
    }

    @ResponseBody
    @RequestMapping("/array.do")
    public ResultEntity array(@RequestBody List<Integer> array) {
        System.out.println(array);
        return ResultEntity.successWithoutData();
    }
}
