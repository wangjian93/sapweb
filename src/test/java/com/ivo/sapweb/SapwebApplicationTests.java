package com.ivo.sapweb;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SapwebApplicationTests {

    @Test
    public void contextLoads() {
        String name = "wangjian";
        Assert.assertNotNull(name);
    }
}
