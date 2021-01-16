package org.spring.springboot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.springboot.domain.City;
import org.spring.springboot.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * Created by bysocket on 07/02/2017.
 *
 */
@RestController
public class CityRestController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private CityService cityService;

    /***
     * 访问路径：http://localhost:8080/api/city?cityName=济南
     * centos放项目jar包的路径为：/usr/projectown
     * 注意事项：由于本地虚拟机centos中安装的mysql版本和本地windows安装的mysql的版本不一样，
     * 所以在centos中运行时要把pom.xml中的mysql-connector-java的版本改为<mysql-connector>8.0.11</mysql-connector>
     * @param cityName
     * @return
     */
    @RequestMapping(value = "/api/city", method = RequestMethod.GET)
    @ResponseBody
    public String findOneCity(@RequestParam(value = "cityName", required = true) String cityName) {
        City city = cityService.findCityByName(cityName);
        return city.toString();
    }

    /***
     * 访问路径：http://localhost:8080/api/1234
     * @param id
     */
    @RequestMapping(value = "/api/{id}", method = RequestMethod.GET)
    public void postTest(@PathVariable(value="id") String id) {
        log.error("日志测试开始啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊");
        System.out.println(id);
        log.error("日志测试结束啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊");
    }

}
