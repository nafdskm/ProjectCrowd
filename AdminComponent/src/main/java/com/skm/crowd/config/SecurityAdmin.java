package com.skm.crowd.config;

import com.skm.crowd.entity.Admin;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

/**
 * 考虑到User对象中仅仅包含账号和密码，为了能够获取到原始的Admin对象，专门创建这个类来对User进行扩展
 */
public class SecurityAdmin extends User {

    private Admin originalAdmin;

    public SecurityAdmin(Admin originalAdmin, List<GrantedAuthority> authorities) {
        super(originalAdmin.getLoginAcct(), originalAdmin.getUserPassword(), authorities);
        this.originalAdmin = originalAdmin;

        // 擦除密码
        this.originalAdmin.setUserPassword(null);
    }

    public Admin getOriginalAdmin() {
        return originalAdmin;
    }
}
