package com.pl.pro.sncsrv.service.impl;


import com.pl.pro.sncsrv.dao.SysUserRoleRelationDao;
import com.pl.pro.sncsrv.domain.orm.SysUserRoleRelation;
import com.pl.pro.sncsrv.service.ifac.SysUserRoleRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户与角色关系业务
 *
 * @author wangban
 * @date 20:09 2018/7/31
 */
@Service
public class SysUserRoleRelationServiceImpl implements SysUserRoleRelationService {
    /**
     * 用户角色dao
     */
    @Autowired
    private SysUserRoleRelationDao sysUserRoleRelationDao;

    /**
     * 保存用户与角色
     *
     * @param sysUserRoleRelation 用户与角色信息
     * @return 保存结果 true成功
     */
    @Override
    public boolean saveUserRoleRelation(SysUserRoleRelation sysUserRoleRelation) {
        return sysUserRoleRelationDao.saveUserRole(sysUserRoleRelation) > 0;
    }

    /**
     * 更新用户与角色
     *
     * @param sysUserRoleRelation 用户与角色信息
     * @return 保存结果 true成功
     */
    @Override
    public boolean updateUserRoleRelation(SysUserRoleRelation sysUserRoleRelation) {
        return sysUserRoleRelationDao.updateUserRole(sysUserRoleRelation) > 0;
    }

    /**
     * 根据系统用户id获取角色id
     *
     * @param sysUserId 系统用户id
     * @return 保存结果 true成功
     */
    @Override
    public Integer getRoleIdByUserId(Integer sysUserId) {
        Integer id = sysUserRoleRelationDao.getRoleIdByUserId(sysUserId);
        if (id == null) {
            return 0;
        }
        return id;
    }
}
