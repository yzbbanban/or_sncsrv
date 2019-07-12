package com.pl.pro.sncsrv.controller.backend;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pl.pro.sncsrv.config.ResultJson;
import com.pl.pro.sncsrv.config.util.HexUtil;
import com.pl.pro.sncsrv.controller.BaseApi;
import com.pl.pro.sncsrv.service.ifac.ProductService;
import io.swagger.annotations.Api;

/**
 * 功能描述：
 * tcp服务器api
 * @author 88403636_徐航
 * @since 16:57 2019/3/12
 */
@RestController
@RequestMapping("/v1/tcp")
@Api(tags = {""})
public class TcpApi extends BaseApi {

    @Autowired
    private ProductService productService;

    /**
     * 产品认证
     * @return
     */
//    @RequestMapping("/")
//    public ResultJson<> productIdentify() {
//        //随机生成8个字节
//        Random rand = new Random();
//        //记录 offset
//        int offset = 0;
//
//        for (int j = 0; j < 8; j++) {
//            int res = rand.nextInt(10);
//            //                System.out.println("===> " + res);
//            Integer or = Integer.parseInt(String.valueOf(res));
//            tcpMsg[10 + j] = or.byteValue();
//            offset += or;
//        }
//        //十进制转十六进制
//        HexUtil.int2HexStr();
//        //将这八个字节中的数据和从密码表中取出来的数据一一做异或处理，得出该产品得唯一编号，
//        //进行在数据表里面查找，数据表中有，就直接提示返回FF 00 AA 00，失败返回ff 00 AB 01
//        return ResultJson.createBySuccess();
//    }

}
