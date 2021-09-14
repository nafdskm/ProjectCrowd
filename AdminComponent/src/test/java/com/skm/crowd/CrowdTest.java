package com.skm.crowd;

import com.skm.crowd.entity.Admin;
import com.skm.crowd.mapper.AdminMapper;
import com.skm.crowd.service.AdminService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

// Spring整合Junit
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-persist-mybatis.xml", "classpath:spring-persist-tx.xml"})
public class CrowdTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private AdminService adminService;

    @Test
    public void testTx() {
        Admin admin = new Admin(null, "lisi", "lisi123", "李四", "lisi@qq.com", null);
        adminService.saveAdmin(admin);
    }

    @Test
    public void testAdd() {
        Logger logger = LoggerFactory.getLogger(CrowdTest.class);
        Admin admin = adminMapper.selectByPrimaryKey(1);
        logger.debug(admin.toString());
    }

    @Test
    public void testConnection() throws SQLException {
        System.out.println(dataSource.getClass());
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
        connection.close();
        System.out.println(adminMapper.getClass());
    }

}
