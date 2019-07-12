package com.pl.pro.sncsrv.controller.backend;

import com.pl.pro.sncsrv.config.MessageKey;
import com.pl.pro.sncsrv.config.ResultJson;
import com.pl.pro.sncsrv.config.ResultList;
import com.pl.pro.sncsrv.config.ResultStatus;
import com.pl.pro.sncsrv.controller.BaseApi;
import com.pl.pro.sncsrv.domain.dto.*;
import com.pl.pro.sncsrv.domain.orm.PageParamDTO;
import com.pl.pro.sncsrv.domain.orm.SysManageRole;
import com.pl.pro.sncsrv.service.ServiceException;
import com.pl.pro.sncsrv.service.ifac.SysManageRoleService;
import com.pl.pro.sncsrv.service.ifac.SysResourceRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 系统管理角色api
 *
 * @author wangban
 */
@RestController
@RequestMapping("/v1/manage/sysRole")
@Api(tags = {"manage系统管理角色api"})
public class SysManageRoleApi extends BaseApi {

    /**
     * 系统管理角色业务
     */
    @Autowired
    private SysManageRoleService sysManageRoleService;

    @Autowired
    private SysResourceRoleService sysResourceRoleService;

    @ApiOperation(value = "王斑：保存系统角色信息", notes = "保存成功或失败提示")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResultJson addSysManageRole(SysManageRoleDTO sysManageRoleDTO) {
        ResultJson resultJson = new ResultJson();
        if (getCurrentManageUserId() == -1) {
            return ResultJson.createByNoAuth();
        }

        // ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 检查是否有为传入的参数 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
        if (ObjectUtils.isEmpty(sysManageRoleDTO)) {
            resultJson.setCode(ResultStatus.ERROR.getCode());
            resultJson.setMessage(MessageKey.CORRECT_PARAMS);
            return resultJson;
        }
        // ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑  ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

        // ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 保存系统角色 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
        if (sysManageRoleService.saveSysManageRole(sysManageRoleDTO)) {
            resultJson.setStatus(ResultStatus.OK);
            return resultJson;
        }
        // ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑  ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

        // ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 保存失败  ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
        resultJson.setStatus(ResultStatus.ERROR);
        resultJson.setMessage(MessageKey.ADD_SYSTEM_ROLE_FAILURE);
        // ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑  ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

        return resultJson;
    }

    @ApiOperation(value = "王斑：修改角色信息", notes = "修改角色成功或失败提示")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResultJson updateSySManageRole(SysManageRoleUpdateDTO roleUpdateDTO) {
        if (getCurrentManageUserId() == -1) {
            return ResultJson.createByNoAuth();
        }
        ResultJson resultJson = new ResultJson();

        // ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 检查是否有为传入的参数 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
        if (ObjectUtils.isEmpty(roleUpdateDTO)) {
            resultJson.setCode(ResultStatus.ERROR.getCode());
            resultJson.setMessage(MessageKey.CORRECT_PARAMS);
            return resultJson;
        }
        // ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑  ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

        // ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 修改角色信息 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
        if (sysManageRoleService.updateSysManageRole(roleUpdateDTO)) {
            resultJson.setStatus(ResultStatus.OK);
            return resultJson;
        }
        // ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑  ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

        // ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 更新失败  ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
        resultJson.setStatus(ResultStatus.ERROR);
        resultJson.setMessage(MessageKey.MODIFY_SYSTEM_ROLE_FAILURE);
        // ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑  ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

        return resultJson;
    }


    @ApiOperation(value = "王斑：修改角色状态", notes = "修改状态成功或失败提示")
    @RequestMapping(value = "/updateState", method = RequestMethod.POST)
    public ResultJson updateSySManageRoleState(SysManageRoleStateDTO roleStateDTO) {
        if (getCurrentManageUserId() == -1) {
            return ResultJson.createByNoAuth();
        }
        ResultJson resultJson = new ResultJson();
        // ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 检查是否有为传入的参数 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
        if (ObjectUtils.isEmpty(roleStateDTO)) {
            resultJson.setCode(ResultStatus.ERROR.getCode());
            resultJson.setMessage(MessageKey.CORRECT_PARAMS);
            return resultJson;
        }
        // ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑  ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

        // ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 修改角色状态 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
        if (sysManageRoleService.updateSysManageRoleState(roleStateDTO)) {
            resultJson.setStatus(ResultStatus.OK);
            return resultJson;
        }
        // ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑  ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

        // ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 修改角色状态失败  ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
        resultJson.setStatus(ResultStatus.ERROR);
        resultJson.setMessage(MessageKey.MODIFY_SYSTEM_ROLE_FAILURE);
        // ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑  ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

        return resultJson;
    }


    @ApiOperation(value = "王斑：获取角色列表",
            notes = "{\n" +
                    "  \"code\": 200,\n" +
                    "  \"message\": \"OK\",\n" +
                    "  \"data\": {\n" +
                    "    \"count\": 11,\n" +
                    "    \"dataList\": [\n" +
                    "      {\n" +
                    "        \"id\": 1,\n" +
                    "        \"roleName\": \"角色名\",\n" +
                    "        \"roleKey\": \"级别\",\n" +
                    "        \"roleStatus\": 1\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"id\": 2,\n" +
                    "        \"roleName\": \"角色名\",\n" +
                    "        \"roleKey\": \"级别\",\n" +
                    "        \"roleStatus\": 1\n" +
                    "      }\n" +
                    "    ]\n" +
                    "  }\n" +
                    "}")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResultJson<ResultList<SysManageRole>> listSySManageRole(PageParamDTO pageParamsDTO) {
        if (getCurrentManageUserId() == -1) {
            return ResultJson.createByNoAuth();
        }
        ResultJson<ResultList<SysManageRole>> resultJson = new ResultJson<>();
        // ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 检查是否有为传入的参数 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
        if (ObjectUtils.isEmpty(pageParamsDTO)) {
            resultJson.setCode(ResultStatus.ERROR.getCode());
            resultJson.setMessage(MessageKey.CORRECT_PARAMS);
            return resultJson;
        }
        // ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑  ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

        // ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 获取用户总数量 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
        int count = sysManageRoleService.getSysMangeRoleCount();
        if (count <= 0) {
            resultJson.setCode(ResultStatus.OK.getCode());
            resultJson.setMessage(MessageKey.ACCOUNT_ERROR);
            return resultJson;
        }
        // ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑  ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

        // ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 获取用户分页列表 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
        List<SysManageRole> sysManageRoleList = sysManageRoleService.listSysManageRole(pageParamsDTO);
        if (CollectionUtils.isEmpty(sysManageRoleList)) {
            resultJson.setCode(ResultStatus.OK.getCode());
            resultJson.setMessage(MessageKey.ROLE_LIST_ERROR);
            return resultJson;
        }
        // ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑  ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

        // ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 设置分页参数 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
        ResultList<SysManageRole> resultList = new ResultList<>();
        resultList.setCount(count);
        resultList.setDataList(sysManageRoleList);
        resultJson.setStatus(ResultStatus.OK);
        resultJson.setData(resultList);
        // ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑  ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

        return resultJson;

    }

    @ApiOperation(value = "王斑：更新角色对应的资源", notes = "更新角色(王斑)")
    @RequestMapping(value = "/updateRes", method = RequestMethod.POST)
    public ResultJson updateRole(SysManageResourceRoleAddDTO resourceRoleAddDTO) {
        if (getCurrentManageUserId() == -1) {
            return ResultJson.createByNoAuth();
        }
        ResultJson resultJson = new ResultJson();
        // ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 检查是否有为传入的参数 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
        if (ObjectUtils.isEmpty(resourceRoleAddDTO)) {
            resultJson.setCode(ResultStatus.ERROR.getCode());
            resultJson.setMessage(MessageKey.CORRECT_PARAMS);
            return resultJson;
        }
        // ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑  ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

        // ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 更新或保存角色对应的资源 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
        String[] arr = resourceRoleAddDTO.getResArr().split(",");
        SysManageResourceRoleDTO resourceRoleDTO = new SysManageResourceRoleDTO();
        resourceRoleDTO.setResArr(arr);
        resourceRoleDTO.setSysRoleId(resourceRoleAddDTO.getSysRoleId());
        //保存，有异常则回滚
        try {
            if (sysResourceRoleService.saveRoles(resourceRoleDTO)) {
                resultJson.setStatus(ResultStatus.OK);
                return resultJson;
            }
        } catch (ServiceException e) {
            e.printStackTrace();
            resultJson.setStatus(ResultStatus.ERROR);
            return resultJson;
        }
        // ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑  ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

        // ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 更新或保存失败 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
        resultJson.setStatus(ResultStatus.ERROR);
        resultJson.setMessage(MessageKey.MODIFY_SYSTEM_ROLE_WITH_ROLE);
        // ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑  ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

        return resultJson;
    }


}
