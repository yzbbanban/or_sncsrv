package com.pl.pro.sncsrv.service.impl;

import com.pl.pro.sncsrv.config.util.PageParamUtil;
import com.pl.pro.sncsrv.dao.UserDao;
import com.pl.pro.sncsrv.domain.dto.UserAddDTO;
import com.pl.pro.sncsrv.domain.dto.UserSearchDTO;
import com.pl.pro.sncsrv.domain.dto.UserUpdateDTO;
import com.pl.pro.sncsrv.domain.vo.UserVO;
import com.pl.pro.sncsrv.service.ifac.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by brander on 2019/2/6
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao dao;

    /**
     * 保存用户
     *
     * @param dto 用户信息
     * @return >0保存成功
     */
    @Override
    public boolean saveUser(UserAddDTO dto) {
        return dao.saveUser(dto) > 0;
    }

    /**
     * 更新用户
     *
     * @param dto 用户信息
     * @return >0更新成功
     */
    @Override
    public boolean updateUser(UserUpdateDTO dto) {
        return dao.updateUser(dto) > 0;
    }

    /**
     * 获取用户列表
     *
     * @param dto 条件
     * @return 用户列表
     */
    @Override
    public List<UserVO> listUserInfo(UserSearchDTO dto) {
        return dao.listUserInfo(PageParamUtil.setPageParam(dto));
    }

    /**
     * 获取用户数量
     *
     * @param dto 条件
     * @return 用户数量
     */
    @Override
    public int getUserCount(UserSearchDTO dto) {
        return dao.getUserCount(dto);
    }

    /**
     * 根据手机号获取用户信息
     *
     * @param mobile 手机号
     * @return 用户信息
     */
    @Override
    public UserVO getUserInfo(String mobile, String countryCode) {
        return dao.getUserInfo(mobile, countryCode);
    }
}
