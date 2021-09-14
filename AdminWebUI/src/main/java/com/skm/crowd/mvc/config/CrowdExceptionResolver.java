package com.skm.crowd.mvc.config;

import com.google.gson.Gson;
import com.skm.crowd.constant.CrowdConstant;
import com.skm.crowd.exception.AccessForbiddenException;
import com.skm.crowd.exception.AdminAcctIsInUsedException;
import com.skm.crowd.exception.LoginFailedException;
import com.skm.crowd.util.CrowdUtil;
import com.skm.crowd.util.ResultEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// @ControllerAdvice表示当前类是一个基于注解的异常处理类
@ControllerAdvice
public class CrowdExceptionResolver {

    /**
     * 用户名已被使用异常处理
     */
    @Deprecated
    @ExceptionHandler(value = AdminAcctIsInUsedException.class)
    public ModelAndView adminAcctIsInUsedException(Exception ex, HttpServletRequest request,
                                                    HttpServletResponse response) {
        String viewName = "admin-add";
        return commonResolver(viewName, ex, request, response);
    }

    /**
     * 未登录访问受保护资源异常处理
     */
    @ExceptionHandler(value = AccessForbiddenException.class)
    public ModelAndView resolveAccessForbiddenException(Exception ex, HttpServletRequest request,
                                                    HttpServletResponse response) {
        String viewName = "admin-login";
        return commonResolver(viewName, ex, request, response);
    }

    /**
     * 登录失败异常处理
     */
    @ExceptionHandler(value = LoginFailedException.class)
    public ModelAndView resolveLoginFailedException(Exception ex, HttpServletRequest request,
                                                    HttpServletResponse response) {
        String viewName = "admin-login";
        return commonResolver(viewName, ex, request, response);
    }

    /**
     * 空指针异常处理
     */
    @ExceptionHandler(value = NullPointerException.class)
    public ModelAndView resolveNullPointerException(Exception ex, HttpServletRequest request,
                                                    HttpServletResponse response) {
        String viewName = "error/system-error";
        return commonResolver(viewName, ex, request, response);
    }

    @ExceptionHandler(value = Exception.class)
    public ModelAndView resolveException(Exception ex, HttpServletRequest request,
                                         HttpServletResponse response) {
        String viewName = "error/system-error";
        return commonResolver(viewName, ex, request, response);
    }

    /**
     * 异常处理公共方法
     */
    private ModelAndView commonResolver(String viewName, Exception ex,
                                        HttpServletRequest request, HttpServletResponse response) {
        // 如果是ajax请求，返回json
        if (CrowdUtil.judgeRequestType(request)) {
            // 创建结果集
            ResultEntity<Object> resultEntity = ResultEntity.failed(ex.getMessage());
            Gson gson = new Gson();
            String json = gson.toJson(resultEntity);
            // 将json字符串作为响应体返回给浏览器
            try {
                response.setHeader("Content-Type", "application/json;charset=utf-8");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(json);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // 由于上面已经通过原生的response对象返回了响应，所以不提供ModelAndView对象
            return null;
        }

        // 如果不是ajax请求，返回ModelAndView
        ModelAndView mv = new ModelAndView();
        mv.addObject(CrowdConstant.ATTR_NAME_EXCEPTION, ex);
        mv.setViewName(viewName);
        return mv;
    }
}
