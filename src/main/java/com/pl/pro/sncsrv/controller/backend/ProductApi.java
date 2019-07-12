package com.pl.pro.sncsrv.controller.backend;

import com.pl.pro.sncsrv.config.MessageKey;
import com.pl.pro.sncsrv.config.ResultJson;
import com.pl.pro.sncsrv.config.ResultList;
import com.pl.pro.sncsrv.controller.BaseApi;
import com.pl.pro.sncsrv.domain.dto.*;
import com.pl.pro.sncsrv.domain.orm.PageParamDTO;
import com.pl.pro.sncsrv.domain.vo.ProductVO;
import com.pl.pro.sncsrv.domain.vo.TypeDetailVO;
import com.pl.pro.sncsrv.service.ifac.ProductService;

import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 产品与产品类型api
 */
@RestController
@RequestMapping("/v1/manage/product")
@Api(tags = {"manage产品与产品类型api"})
public class ProductApi extends BaseApi {

    @Autowired
    private ProductService productService;

    @ApiOperation(value = "王斑：添加产品", notes = "添加")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResultJson addProduct(ProductAddDTO dto) {

        if (getCurrentManageUserId() == -1) {
            return ResultJson.createByNoAuth();
        }
        if (ObjectUtils.isEmpty(dto)) {
            return ResultJson.createByErrorMsg(MessageKey.CORRECT_PARAMS);
        }

        if (productService.addProduct(dto)) {
            return ResultJson.createBySuccess();
        }

        return ResultJson.createByError();
    }

    @ApiOperation(value = "王斑：更新产品信息", notes = "更新结果信息")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResultJson updateProduct(ProductUpdateDTO dto) {

        if (getCurrentManageUserId() == -1) {
            return ResultJson.createByNoAuth();
        }

        if (dto == null || dto.getId() == null || dto.getId() < 0) {
            return ResultJson.createByErrorMsg(MessageKey.CORRECT_PARAMS);
        }

        if (productService.updateProduct(dto)) {
            return ResultJson.createBySuccess();
        }

        return ResultJson.createByError();
    }

    @ApiOperation(value = "王斑：添加产品类型", notes = "更新结果信息")
    @RequestMapping(value = "/type/add", method = RequestMethod.POST)
    public ResultJson addProductType(TypeDetailAddDTO dto) {
        if (getCurrentManageUserId() == -1) {
            return ResultJson.createByNoAuth();
        }
        if (dto == null) {
            return ResultJson.createByErrorMsg(MessageKey.CORRECT_PARAMS);
        }

        if (productService.addProductType(dto)) {
            return ResultJson.createBySuccess();
        }

        return ResultJson.createByError();
    }

    @ApiOperation(value = "王斑：更新产品类型", notes = "更新结果信息")
    @RequestMapping(value = "/type/update", method = RequestMethod.POST)
    public ResultJson updateProductType(TypeDetailUpdateDTO dto) {
        if (getCurrentManageUserId() == -1) {
            return ResultJson.createByNoAuth();
        }

        if (dto == null || dto.getId() < 0) {
            return ResultJson.createByErrorMsg(MessageKey.CORRECT_PARAMS);
        }

        if (productService.updateProductType(dto)) {
            return ResultJson.createBySuccess();
        }

        return ResultJson.createByError();
    }

    @ApiOperation(value = "王斑：获取产品列表", notes = "更新结果信息")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResultJson<ResultList<ProductVO>> listProduct(ProductSearchDTO dto) {
        if (getCurrentManageUserId() == -1) {
            return ResultJson.createByNoAuth();
        }

        if (dto == null || dto.getPageNo() == null || dto.getPageSize() == null) {
            return ResultJson.createByErrorMsg(MessageKey.CORRECT_PARAMS);
        }

        ResultList<ProductVO> resultList = new ResultList<>();

        int count = productService.getProductCount();

        if (count <= 0) {
            return ResultJson.createByErrorMsg(MessageKey.DATA_NULL);
        }

        List<ProductVO> typeList = productService.listProduct(dto);
        if (CollectionUtils.isEmpty(typeList)) {
            return ResultJson.createByErrorMsg(MessageKey.DATA_NULL);
        }
        resultList.setCount(count);
        resultList.setDataList(typeList);

        return ResultJson.createBySuccess(resultList);
    }

    @ApiOperation(value = "王斑：获取产品类型列表", notes = "更新结果信息")
    @RequestMapping(value = "/type/list", method = RequestMethod.GET)
    public ResultJson<ResultList<TypeDetailVO>> listProductType(PageParamDTO dto) {
        if (getCurrentManageUserId() == -1) {
            return ResultJson.createByNoAuth();
        }

        if (dto == null || dto.getPageNo() == null || dto.getPageSize() == null) {
            return ResultJson.createByErrorMsg(MessageKey.CORRECT_PARAMS);
        }

        ResultList<TypeDetailVO> resultList = new ResultList<>();

        int count = productService.getProductTypeCount();

        if (count <= 0) {
            return ResultJson.createByErrorMsg(MessageKey.DATA_NULL);
        }

        List<TypeDetailVO> typeList = productService.listProductType(dto);
        if (CollectionUtils.isEmpty(typeList)) {
            return ResultJson.createByErrorMsg(MessageKey.DATA_NULL);
        }
        resultList.setCount(count);
        resultList.setDataList(typeList);

        return ResultJson.createBySuccess(resultList);
    }

}
