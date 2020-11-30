package org.spring.springboot.controller;

import com.alibaba.nacos.api.exception.NacosException;
import org.spring.springboot.utils.NacosUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Properties;

/**
 * @program: nacosTest
 * @description: nacos测试,获取nacos配置列表中的信息
 * 1.启动项目后，访问http://localhost:8848/nacos/index.html进入nacos的配置界面，用户名密码都是nacos
 * @author: 蔡林
 * @create: 2020-11-30 17:02
 **/
@RestController
//@RequestMapping("necos")
public class NacosController {
    @RequestMapping("/getValue")
    public void nacosTest() throws IOException, NacosException {
        Properties properties = NacosUtils.getConfig("cailin.test", "testgroup");
        //mysql配置
       String servers = properties.getProperty("sdvaccsu.write.url");
       System.out.println(servers);
    }
}