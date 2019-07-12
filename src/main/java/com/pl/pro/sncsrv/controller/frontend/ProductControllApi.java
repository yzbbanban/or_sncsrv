package com.pl.pro.sncsrv.controller.frontend;

import com.pl.pro.sncsrv.config.ResultJson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by brander on 2019/3/3
 */
@RestController
@RequestMapping("/v1/app/control")
@Api(tags = {"app用户api"})
public class ProductControllApi {



    @ApiOperation(value = "王斑：开启/关闭拍照", notes = "")
    @RequestMapping(value = "/prod/photo", method = RequestMethod.POST)
    public ResultJson prodPhoto(int status) {

        return ResultJson.createBySuccess(null);
    }

    @ApiOperation(value = "王斑：读取自动拍照状态", notes = "")
    @RequestMapping(value = "/prod/status", method = RequestMethod.POST)
    public ResultJson prodStatus() {

        return ResultJson.createBySuccess(null);
    }

    @ApiOperation(value = "王斑：读取 TVOC 值", notes = "")
    @RequestMapping(value = "/prod/tvoc", method = RequestMethod.GET)
    public ResultJson prodTvoc() {

        return ResultJson.createBySuccess(null);
    }


}
