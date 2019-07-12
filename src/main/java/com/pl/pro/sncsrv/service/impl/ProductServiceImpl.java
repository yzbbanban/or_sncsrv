package com.pl.pro.sncsrv.service.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pl.pro.sncsrv.config.util.PageParamUtil;
import com.pl.pro.sncsrv.dao.ProductDao;
import com.pl.pro.sncsrv.dao.TypeDetailDao;
import com.pl.pro.sncsrv.domain.dto.ProductAddDTO;
import com.pl.pro.sncsrv.domain.dto.ProductImgAddDTO;
import com.pl.pro.sncsrv.domain.dto.ProductSearchDTO;
import com.pl.pro.sncsrv.domain.dto.ProductUpdateDTO;
import com.pl.pro.sncsrv.domain.dto.ProductUseableDTO;
import com.pl.pro.sncsrv.domain.dto.TypeDetailAddDTO;
import com.pl.pro.sncsrv.domain.dto.TypeDetailUpdateDTO;
import com.pl.pro.sncsrv.domain.orm.PageParamDTO;
import com.pl.pro.sncsrv.domain.vo.ProductVO;
import com.pl.pro.sncsrv.domain.vo.TypeDetailVO;
import com.pl.pro.sncsrv.service.ifac.ProductService;

/**
 * Created by brander on 2019/2/5
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao dao;

    @Autowired
    private TypeDetailDao typeDetailDao;

    @Value("${imgpush.url}")
    String pushUrl;	
    
    /**
     * 添加产品
     *
     * @param addDTO 产品信息
     * @return true 成功
     */
    @Override
    public boolean addProduct(ProductAddDTO addDTO) {
        return dao.saveProduct(addDTO) > 0;
    }

    /**
     * 更新产品
     *
     * @param dto 产品信息
     * @return true 成功
     */
    @Override
    public boolean updateProduct(ProductUpdateDTO dto) {
        return dao.updateProduct(dto) > 0;
    }

    /**
     * 更新产品类型
     *
     * @param dto 产品信息
     * @return true 成功
     */
    @Override
    public boolean addProductType(TypeDetailAddDTO dto) {
        return typeDetailDao.saveTypeDetail(dto) > 0;
    }

    /**
     * 更新产品类型
     *
     * @param dto 产品信息
     * @return true 成功
     */
    @Override
    public boolean updateProductType(TypeDetailUpdateDTO dto) {
        return typeDetailDao.updateTypeDetail(dto) > 0;
    }

    /**
     * 更新产品可用性
     *
     * @param dto 产品是否可用
     * @return true 更新成功
     */
    @Override
    public boolean updateProductUseable(ProductUseableDTO dto) {
        return dao.updateProductUseable(dto) > 0;
    }

    /**
     * 获取产品类型信息
     *
     * @param dto 分页信息
     * @return 产品类型列表
     */
    @Override
    public List<TypeDetailVO> listProductType(PageParamDTO dto) {
        return typeDetailDao.listTypeDetail(PageParamUtil.setPageParam(dto));
    }

    /**
     * 获取产品信息
     *
     * @param dto 分页信息
     * @return 产品信息列表
     */
    @Override
    public List<ProductVO> listProduct(ProductSearchDTO dto) {
        return dao.listProduct(PageParamUtil.setPageParam(dto));
    }

    /**
     * 获取产品数量
     *
     * @return 数量
     */
    @Override
    public int getProductCount() {
        return dao.getProductCount();
    }

    /**
     * 获取产品类型数量
     *
     * @return 数量
     */
    @Override
    public int getProductTypeCount() {
        return typeDetailDao.getTypeDetailCount();
    }

    /**
     * 获取产品唯一标识
     * @return
     */
    @Override
    public int getProUidCount(String ssid) {
        return dao.getProUidCount(ssid);
    }

    @Override
    public void saveImg(String imgStr, String productId) {
        ProductImgAddDTO dto = new ProductImgAddDTO();
        dto.setId(dto.getId());
        dto.setImageUrl(imgStr);
        dto.setProductId(productId);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dto.setTimestamp(format.format(new Date()));
        dao.saveImgUrl(dto);
        
        String Url = pushUrl;
		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod(Url);

		client.getParams().setContentCharset("utf-8");
		method.setRequestHeader("ContentType", "application/x-www-form-urlencoded;charset=utf-8");
		NameValuePair[] data = { new NameValuePair("ssid", productId),
				new NameValuePair("imageUrl", imgStr),

		};
		method.setRequestBody(data);

		try {
			client.executeMethod(method);
			String SubmitResult = method.getResponseBodyAsString();
			System.out.println(SubmitResult);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
