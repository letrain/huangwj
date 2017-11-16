package com.chris.tration.controller;

import com.chris.tration.dto.EnumMsg;
import com.chris.tration.dto.ResultMsg;
import com.chris.tration.entity.City;
import com.chris.tration.service.CityService;
import com.chris.tration.service.impl.CityServiceImpl;
import com.chris.tration.util.ExcelUtils;
import com.chris.tration.util.ResultMsgUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class CityController {

    private static final Logger logger = LoggerFactory.getLogger(CityController.class);

    @Autowired
    private CityServiceImpl cityService;


    @GetMapping(value = "/getAllCity")
    public ResultMsg getAllCity(HttpServletResponse response, HttpServletRequest request){
        List<City> cityList = cityService.getAllCity();

        ExcelUtils.exportExcel(cityList,response,request);


        return ResultMsgUtil.success(1, EnumMsg.QUERY_SUCCESS,cityList);


    }



    @PostMapping(value = "/addUserCity")
    public ResultMsg addUserCity(@RequestParam("code") Integer code,
                                 @RequestParam("id") Integer id,
                                 @RequestParam("cityName") String cityName,
                                 @RequestParam("provinceCode") Integer provinceCode) {

        if(cityService.findOne(id) == null) {
            int result = cityService.addUserCity(id,code,cityName,provinceCode);
            return ResultMsgUtil.success(1,EnumMsg.ADD_SUCCESS,"suscs");
        }
        else{
            logger.error("ID已存在");
            return ResultMsgUtil.fail(0,EnumMsg.ADD_FAIL);
        }
    }

    @GetMapping(value = "/getProvince")
    public ResultMsg getProvince(@RequestParam("provinceId") String provinceId){

        String provinceName = cityService.getprovinceNameByCode(provinceId);
        return ResultMsgUtil.success(1,EnumMsg.QUERY_SUCCESS,provinceName);
    }
}
