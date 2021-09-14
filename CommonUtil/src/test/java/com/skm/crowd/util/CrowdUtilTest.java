package com.skm.crowd.util;

import org.junit.Test;

public class CrowdUtilTest {

    @Test
    public void testMd5() {
        String psw = "admin";
        String rst = CrowdUtil.md5(psw);
        System.out.println(rst);
    }
}
