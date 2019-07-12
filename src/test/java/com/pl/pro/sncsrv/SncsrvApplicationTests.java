package com.pl.pro.sncsrv;

import com.pl.pro.sncsrv.config.util.HexUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;

import com.pl.pro.sncsrv.dao.ProductDao;
import com.pl.pro.sncsrv.domain.dto.ProductImgAddDTO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SncsrvApplicationTests {

    @Autowired
    private ProductDao dao;

    //tcp 信息
    private static byte[] tcpMsg = new byte[]{
            //A区头部
            (byte) 0xFF, (byte) 0x00,
            (byte) 0x00,
            (byte) 0x02,
            //B区消息
            (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x08,
            
            (byte) 0x38, (byte) 0x30, (byte) 0x38, (byte) 0x30,
            (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30
    };

    @Test
    public void contextLoads() {
        //生成8个随机字节
        Random rand = new Random();
        for (int j = 0; j < 8; j++) {
            int res = rand.nextInt(256);
            System.out.println("===> " + res);
            Integer or = Integer.parseInt(String.valueOf(res));
            tcpMsg[10 + j] = or.byteValue();
        }

        int c = 0xAA;
        for (int j = 3; j < tcpMsg.length; j++) {
            Integer or = Integer.parseInt(HexUtil.byteToHex(tcpMsg[j]), 16);
            c = c ^ or;
        }
        tcpMsg[2] = (byte) c;
//        System.out.println("result----> " + toHexString(tcpMsg));
    }

    @Test
    public void test() {
        String productId = dao.queryProIdBySid("lkjhg12345");
        ProductImgAddDTO dto = new ProductImgAddDTO();
        dao.saveProMessage(dto);
        dto.setId(dto.getId());
        dto.setImageUrl("123.jpg");
        dto.setProductId(productId);
        dao.saveImgUrl(dto);
    }

}

