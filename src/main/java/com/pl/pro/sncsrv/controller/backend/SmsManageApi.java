package com.pl.pro.sncsrv.controller.backend;

import com.pl.pro.sncsrv.config.MessageKey;
import com.pl.pro.sncsrv.config.ResultJson;
import com.pl.pro.sncsrv.controller.BaseApi;
import com.pl.pro.sncsrv.domain.dto.SmsMessageDTO;
import com.pl.pro.sncsrv.domain.orm.SysManageUser;
import com.pl.pro.sncsrv.service.ifac.SysManageUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.pl.pro.sncsrv.domain.orm.MessageConstant.*;

@RestController
@RequestMapping("v1/manage/sms")
@Api(tags = {"manage短信api"})
public class SmsManageApi extends BaseApi {

    @Autowired
    private SysManageUserService manageUserService;


    @ApiOperation(value = "发送获取登录短信")
    @PostMapping(value = "login")
    public ResultJson<String> loginSms(String account) {
        SysManageUser user = manageUserService.getSysMangeUserMessageByAccount(account);
        if (user == null) {
            return ResultJson.createByErrorMsg(MessageKey.CORRECT_PARAMS);
        }
        SmsMessageDTO messageDTO = new SmsMessageDTO();
        messageDTO.setCountryCode("86");
        messageDTO.setPhoneNumber(user.getMobile());
        return getStringResultJson(messageDTO, SYSTEM_SMS_MANAGE_LOGIN_CODE_PHONE + messageDTO.getCountryCode() + messageDTO.getPhoneNumber());
    }

    @ApiOperation(value = "发送更改密码短信")
    @PostMapping(value = "modify")
    public ResultJson<String> getModifySms() {
        Integer userId = getCurrentManageUserId();
        if (userId == -1) {
            return ResultJson.createByNoAuth();
        }
        SysManageUser user = manageUserService.getSysMangeUserMessageById(userId);
        SmsMessageDTO messageDTO = new SmsMessageDTO();
        messageDTO.setCountryCode("86");
        messageDTO.setPhoneNumber(user.getMobile());
        return getStringResultJson(messageDTO, SYSTEM_SMS_MANAGE_MODIFY_CODE_PHONE + messageDTO.getCountryCode() + messageDTO.getPhoneNumber());
    }


}
