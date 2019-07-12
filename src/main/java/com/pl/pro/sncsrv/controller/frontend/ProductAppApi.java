package com.pl.pro.sncsrv.controller.frontend;

import com.pl.pro.sncsrv.config.MessageKey;
import com.pl.pro.sncsrv.config.ResultJson;
import com.pl.pro.sncsrv.config.ResultList;
import com.pl.pro.sncsrv.controller.BaseApi;
import com.pl.pro.sncsrv.domain.dto.ProductAppUpdateDTO;
import com.pl.pro.sncsrv.domain.dto.ProductSearchDTO;
import com.pl.pro.sncsrv.domain.dto.ProductUpdateDTO;
import com.pl.pro.sncsrv.domain.orm.PageParamDTO;
import com.pl.pro.sncsrv.domain.vo.ProductVO;
import com.pl.pro.sncsrv.service.ifac.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by brander on 2019/2/7
 */
@RestController
@RequestMapping("/v1/app/product")
@Api(tags = {"app产品与产品类型api"})
public class ProductAppApi extends BaseApi {

    @Autowired
    private ProductService productService;

    @ApiOperation(value = "王斑：根据用户 id获取产品类型列表", notes = "")
    @RequestMapping(value = "/user/list", method = RequestMethod.GET)
    public ResultJson<ResultList<ProductVO>> listProductByUser(PageParamDTO pageParamDTO) {
        Integer userId = getCurrentUserId();
        if (userId == -1) {
            return ResultJson.createByNoAuth();
        }

        ResultList<ProductVO> resultList = new ResultList<>();

        int count = productService.getProductTypeCount();

        if (count <= 0) {
            return ResultJson.createByErrorMsg(MessageKey.DATA_NULL);
        }

        ProductSearchDTO productSearchDTO = new ProductSearchDTO();
        productSearchDTO.setUserId(userId);
        productSearchDTO.setPageNo(pageParamDTO.getPageNo());
        productSearchDTO.setPageSize(pageParamDTO.getPageSize());
        List<ProductVO> typeList = productService.listProduct(productSearchDTO);
        if (CollectionUtils.isEmpty(typeList)) {
            return ResultJson.createByErrorMsg(MessageKey.DATA_NULL);
        }
        resultList.setCount(count);
        resultList.setDataList(typeList);

        return ResultJson.createBySuccess(resultList);
    }

    @ApiOperation(value = "王斑：更新产品信息", notes = "更新结果信息")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResultJson updateProduct(ProductAppUpdateDTO dto) {
        Integer userId = getCurrentUserId();
        if (userId == -1) {
            return ResultJson.createByNoAuth();
        }

        if (dto == null || dto.getId() == null || dto.getId() < 0) {
            return ResultJson.createByErrorMsg(MessageKey.CORRECT_PARAMS);
        }

        ProductUpdateDTO updateDTO = new ProductUpdateDTO();

        BeanUtils.copyProperties(dto, updateDTO);
        updateDTO.setUserId(userId);

        if (productService.updateProduct(updateDTO)) {
            return ResultJson.createBySuccess();
        }

        return ResultJson.createByError();
    }


}
