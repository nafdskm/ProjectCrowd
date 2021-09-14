package com.skm.crowd.config;

import com.skm.crowd.constant.CrowdConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // 标注后可以在对应控制器方法上配置权限控制
public class WebAppSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder getBCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     *  配置用户
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /*auth.inMemoryAuthentication()
                .passwordEncoder(new BCryptPasswordEncoder())
                .withUser("admin")
                .password(new BCryptPasswordEncoder().encode("admin"))
                .roles("ADMIN");*/
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(getBCryptPasswordEncoder());
    }

    /**
     *  配置权限
     */
    @Override
    protected void configure(HttpSecurity security) throws Exception {
        security.authorizeRequests() // 对请求进行授权
                // 登录界面放行
                .antMatchers("/admin/to/login/page.do")
                .permitAll()
                // 静态资源放行
                .antMatchers("/jquery/**", "/img/**", "/css/**", "/fonts/**", "/crowd/**",
                        "/bootstrap/**", "/layer/**", "/script/**", "/ztree/**")
                .permitAll()
                // 删除角色要求用户拥有经理的角色
                .antMatchers("/admin/role/get/page.do", "/admin/role/remove.do")
                .hasRole("经理")
                // 所有其他权限要求权限
                .anyRequest()
                .authenticated()
                .and()
                // 设置访问被拒绝后的处理
                .exceptionHandling()
                .accessDeniedHandler((httpServletRequest, httpServletResponse, e) -> {
                    httpServletRequest.setAttribute(CrowdConstant.ATTR_NAME_EXCEPTION, new Exception(CrowdConstant.MESSAGE_ACCESS_DENIED));
                    httpServletRequest.getRequestDispatcher("/WEB-INF/error/system-error.jsp").forward(httpServletRequest, httpServletResponse);
                })
                .and()
                // 开启表单登录
                .formLogin()
                .loginPage("/admin/to/login/page.do")
                .usernameParameter("loginAcct")
                .passwordParameter("userPassword")
                .loginProcessingUrl("/security/login.do")
                .defaultSuccessUrl("/admin/to/main/page.do")
                .and()
                // 退出登录操作
                .logout()
                .logoutUrl("/security/logout.do")
                .logoutSuccessUrl("/admin/to/login/page.do")
                .and()
                .csrf()         // 防跨域请求暂时关闭
                .disable();     // 不然要做的修改太多，所有post请求都需要带上csrf值来验证

    }
}
