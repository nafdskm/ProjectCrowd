package com.skm.crowd.config;

import com.skm.crowd.entity.Admin;
import com.skm.crowd.entity.Role;
import com.skm.crowd.service.AdminService;
import com.skm.crowd.service.AuthService;
import com.skm.crowd.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CrowdUserDetailsService implements UserDetailsService {

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthService authService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 获取用户对象
        Admin admin = adminService.getAdminByLoginAcct(username);

        // 获取用户的角色和权限
        List<Role> roles = roleService.getAssignedRole(admin.getId());
        List<String> auths = authService.getAssignAuthNameByAdminId(admin.getId());
        List<GrantedAuthority> authorities = new ArrayList<>();

        // 存入集合
        for (Role role : roles) {
            String roleName = "ROLE_" + role.getName();
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(roleName);
            authorities.add(simpleGrantedAuthority);
        }
        for (String auth : auths) {
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(auth);
            authorities.add(simpleGrantedAuthority);
        }
        return new SecurityAdmin(admin, authorities);
    }
}
