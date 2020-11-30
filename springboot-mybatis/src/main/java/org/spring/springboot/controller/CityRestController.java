package org.spring.springboot.controller;

import org.spring.springboot.domain.City;
import org.spring.springboot.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * Created by bysocket on 07/02/2017.
 */
@RestController
public class CityRestController {

    @Autowired
    private CityService cityService;

    /***
     * 访问路径：http://localhost:8080/api/city?cityName="济南"
     * @param cityName
     * @return
     */
    @RequestMapping(value = "/api/city", method = RequestMethod.GET)
    public City findOneCity(@RequestParam(value = "cityName", required = true) String cityName) {
        return cityService.findCityByName(cityName);
    }

    /***
     * 访问路径：http://localhost:8080/api/1234
     * @param id
     */
    @RequestMapping(value = "/api/{id}", method = RequestMethod.GET)
    public void postTest(@PathVariable(value="id") String id) {
        System.out.println(id);
    }

}
