package com.pl.pro.sncsrv.controller.frontend;

import com.pl.pro.sncsrv.config.MessageKey;
import com.pl.pro.sncsrv.config.ResultJson;
import com.pl.pro.sncsrv.config.ResultStatus;
import com.pl.pro.sncsrv.config.security.JwtHelper;
import com.pl.pro.sncsrv.config.util.MD5Utils;
import com.pl.pro.sncsrv.controller.BaseApi;
import com.pl.pro.sncsrv.domain.dto.UserAddDTO;
import com.pl.pro.sncsrv.domain.dto.UserModifyDTO;
import com.pl.pro.sncsrv.domain.dto.UserRegisterDTO;
import com.pl.pro.sncsrv.domain.dto.UserUpdateDTO;
import com.pl.pro.sncsrv.domain.vo.UserVO;
import com.pl.pro.sncsrv.service.ifac.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.pl.pro.sncsrv.config.security.JwtConstant.MANAGE_JWT_SECRET;
import static com.pl.pro.sncsrv.domain.orm.MessageConstant.SYSTEM_SMS_MANAGE_LOGIN_CODE_PHONE;
import static com.pl.pro.sncsrv.domain.orm.MessageConstant.SYSTEM_SMS_MODIFY_CODE_PHONE;
import static com.pl.pro.sncsrv.domain.orm.MessageConstant.SYSTEM_SMS_REGISTER_CODE_PHONE;


/**
 * Created by brander on 2019/2/5
 */
@RestController
@RequestMapping("v1/user/info")
@Api(tags = {"app用户api"})
public class UserApi extends BaseApi {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "注册")
    @PostMapping(value = "register")
    public ResultJson<String> register(UserRegisterDTO dto) {
        if (ObjectUtils.isEmpty(dto)) {
            return ResultJson.createByErrorMsg(MessageKey.CORRECT_PARAMS);
        }

        ResultJson<String> resultJson = new ResultJson<>();
        //验证验证码
        if (getCode(resultJson, SYSTEM_SMS_REGISTER_CODE_PHONE, dto.getCode(), dto.getMobile())) {
            return resultJson;
        }

        //验证用户是否存在
        UserVO userVO = userService.getUserInfo(dto.getMobile(), dto.getCountryCode());
        if (userVO != null) {
            return ResultJson.createByErrorMsg(MessageKey.USER_EXISTS_ERROR);
        }

        UserAddDTO addDTO = new UserAddDTO();

        BeanUtils.copyProperties(dto, addDTO);
        String salt = MD5Utils.getSalt();

        String pwd = MD5Utils.md5Salt(dto.getPassword(), salt);

        addDTO.setPassword(pwd);
        addDTO.setSalt(salt);
        addDTO.setUsername(dto.getMobile());

        if (userService.saveUser(addDTO)) {
            return ResultJson.createBySuccessMsg(MessageKey.USER_REGISTER_SUCCESS);
        }

        return ResultJson.createByErrorMsg(MessageKey.USER_REGISTER_FAILURE);
    }

    @ApiOperation(value = "登录")
    @PostMapping(value = "login")
    public ResultJson<String> login(String mobile, String countryCode, String password) {

        //验证参数
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            return ResultJson.createByErrorMsg(MessageKey.CORRECT_PARAMS);
        }

        //验证用户是否存在
        UserVO userVO = userService.getUserInfo(mobile, countryCode);
        if (userVO == null) {
            return ResultJson.createByErrorMsg(MessageKey.USER_NOT_EXISTS_ERROR);
        }

        //验证密码
        String salt = userVO.getSalt();
        String pwd = MD5Utils.md5Salt(password, salt);

        if (!pwd.equals(userVO.getPassword())) {
            return ResultJson.createByErrorMsg(MessageKey.PASS_ERROR);
        }

        //生成 token
        String token = JwtHelper.createJWT(mobile, JWT_SECRET,
                String.valueOf(userVO.getId()), 7200L);
        ResultJson<String> resultJson = new ResultJson<>();
        resultJson.setCode(ResultStatus.OK.getCode());
        resultJson.setMessage(MessageKey.LOGIN_SUCCESS);
        resultJson.setData(token);

        return resultJson;
    }


    @ApiOperation(value = "找回密码")
    @PostMapping(value = "modify")
    public ResultJson<String> modify(UserModifyDTO dto) {

        //验证参数
        if (ObjectUtils.isEmpty(dto)) {
            return ResultJson.createByErrorMsg(MessageKey.CORRECT_PARAMS);
        }

        //验证用户是否存在
        UserVO userVO = userService.getUserInfo(dto.getMobile(), dto.getCountryCode());
        if (userVO == null) {
            return ResultJson.createByErrorMsg(MessageKey.USER_NOT_EXISTS_ERROR);
        }
        ResultJson<String> resultJson = new ResultJson<>();
        //验证验证码
        if (getCode(resultJson, SYSTEM_SMS_MODIFY_CODE_PHONE, dto.getCode(), dto.getMobile())) {
            return resultJson;
        }

        //修改密码
        UserUpdateDTO updateDTO = new UserUpdateDTO();
        BeanUtils.copyProperties(dto, updateDTO);

        String salt = MD5Utils.getSalt();
        String pwd = dto.getPassword();
        pwd = MD5Utils.md5Salt(pwd, salt);
        updateDTO.setSalt(salt);
        updateDTO.setPassword(pwd);
        updateDTO.setId(userVO.getId());


        //更新用户信息
        if (userService.updateUser(updateDTO)) {
            localCache.removeCache(SYSTEM_SMS_MODIFY_CODE_PHONE + "86" + dto.getMobile());
            return ResultJson.createBySuccessMsg(MessageKey.USER_UPDATE_SUCCESS);
        }

        return ResultJson.createByErrorMsg(MessageKey.USER_UPDATE_FAILURE);
    }


    @ApiOperation(value = "更新用户信息")
    @PostMapping(value = "update")
    public ResultJson<String> update(Integer id, Boolean status) {

        if (getCurrentUserId() == -1) {
            return ResultJson.createByNoAuth();
        }

        //验证参数
        if (id == null || id <= 0 || status == null) {
            return ResultJson.createByErrorMsg(MessageKey.CORRECT_PARAMS);
        }

        UserUpdateDTO updateDTO = new UserUpdateDTO();
        updateDTO.setId(id);
        updateDTO.setStatus(status);

        //更新用户信息
        if (userService.updateUser(updateDTO)) {
            return ResultJson.createBySuccessMsg(MessageKey.USER_UPDATE_SUCCESS);
        }

        return ResultJson.createByErrorMsg(MessageKey.USER_UPDATE_FAILURE);
    }


}
