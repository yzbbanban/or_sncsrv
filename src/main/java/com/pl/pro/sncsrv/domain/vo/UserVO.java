package com.pl.pro.sncsrv.domain.vo;

import com.pl.pro.sncsrv.domain.orm.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("用户信息")
public class UserVO extends User {

    @Override
    public String toString() {
        return "User{" +
                super.toString() +
                '}';
    }
}
