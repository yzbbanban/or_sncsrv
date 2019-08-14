package com.pl.pro.sncsrv.controller;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pl.pro.sncsrv.config.server.heartbeat.ChannelUtil;
import com.pl.pro.sncsrv.config.server.heartbeat.ProtocolUtils;
import com.pl.pro.sncsrv.config.server.heartbeat.ServerHandler;
import com.pl.pro.sncsrv.controller.model.OrderCodeMap;
import com.pl.pro.sncsrv.controller.model.ProductBean;
import com.pl.pro.sncsrv.controller.model.ResultBean;

import io.netty.channel.ChannelHandlerContext;

@RestController
@RequestMapping(value = "/product")
public class ProductController {

    public static final String ORADER_REGEX = "([0-9A-Fa-f]{2}[\\s]{1})+([0-9A-Fa-f]{2}){1}";


    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @RequestMapping(value = "/online")
    ResultBean onLine(HttpServletRequest request) throws Exception {
        ResultBean result = new ResultBean();
        String ssid = request.getParameter("ssid");
        ChannelHandlerContext ctx = null;
        try {
            ctx = ServerHandler.channelMap.get(ssid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (null == ctx) {
            LOGGER.error("ssid not online");
            result.setResult("ssid not online");
            result.setResultCode("NG");
            return result;
        }

        return result;
    }

    @RequestMapping(value = "/control")
    ResultBean control(HttpServletRequest request) throws Exception {
        ResultBean result = new ResultBean();
        String ssid = request.getParameter("ssid");
        String order = request.getParameter("order");
        LOGGER.info("ssid===>{},order-===>{}", ssid, order);
        if (StringUtils.isBlank(ssid) || StringUtils.isBlank(order)) {
            result.setResult("ssid is empty");
            result.setResultCode("NG");
            return result;
        }
        if (null == order || !order.matches(ORADER_REGEX)) {
            LOGGER.error("order is illegal");
            result.setResult("order is illegal");
            result.setResultCode("NG");
            return result;
        }
        ChannelHandlerContext ctx = ServerHandler.channelMap.get(ssid);
        if (null == ctx) {
            LOGGER.error("ssid unregist");
            result.setResult("ssid unregist");
            result.setResultCode("NG");
            return result;
        }
        byte[] response = null;

        String[] orders = order.split("[\\s]+");
        byte[] orderByte = new byte[orders.length];
        for (int i = 0; i < orders.length; i++) {
            orderByte[i] = (byte) Integer.parseInt(orders[i], 16);
        }
        response = ChannelUtil.writeMsgSync(orderByte, ctx.channel(), 5);
//        LOGGER.info("send response is" + response.toString());
        LOGGER.info("send order.order is" + ProtocolUtils.readByteToHex(orderByte));

//		if (order.equals("A0101")) {
//			response = ChannelUtil.writeMsgSync(OrderCodeMap.A0101, ctx.channel(), 2);
//		} else if (order.equals("A0000")) {
//			response = ChannelUtil.writeMsgSync(OrderCodeMap.A0000, ctx.channel(), 2);
//		} else if (order.equals("A0001")) {
//			response = ChannelUtil.writeMsgSync(OrderCodeMap.A0001, ctx.channel(), 2);
//		} else if (order.equals("A0201")) {
//			response = ChannelUtil.writeMsgSync(OrderCodeMap.A0201, ctx.channel(), 2);
//		} else if (order.equals("A0300")) {
//			response = ChannelUtil.writeMsgSync(OrderCodeMap.A0300, ctx.channel(), 2);
//		} else if (order.equals("A0301")) {
//			response = ChannelUtil.writeMsgSync(OrderCodeMap.A0301, ctx.channel(), 2);
//		} else if (order.equals("A0400")) {
//			response = ChannelUtil.writeMsgSync(OrderCodeMap.A0400, ctx.channel(), 2);
//		} else if (order.equals("A0401")) {
//			response = ChannelUtil.writeMsgSync(OrderCodeMap.A0401, ctx.channel(), 2);
//		} else if (order.equals("A0500")) {
//			response = ChannelUtil.writeMsgSync(OrderCodeMap.A0500, ctx.channel(), 2);
//		} else if (order.equals("A0501")) {
//			response = ChannelUtil.writeMsgSync(OrderCodeMap.A0501, ctx.channel(), 2);
//		}
        result.setResult("OK");
        if (null != response) {
            List<String> list = ProtocolUtils.readByteToHex(response);
            StringBuilder builder = new StringBuilder();
            for (String string : list) {
                builder.append(string).append(" ");
            }
            result.setResultCode(builder.toString());
        } else {
            System.err.println("无消息返回");
            result.setResult("NG");
        }
        return result;

    }

    @RequestMapping(value = "/querylist")
    List<ProductBean> queryList() {
        List<ProductBean> list = new ArrayList<ProductBean>();
        for (Entry<String, ChannelHandlerContext> entry : ServerHandler.channelMap.entrySet()) {
            ProductBean productBean = new ProductBean();
            productBean.setSsid(entry.getKey());
            InetSocketAddress address = (InetSocketAddress) entry.getValue().channel().remoteAddress();
            productBean.setIp(address.getAddress().toString());
            productBean.setPort(String.valueOf(address.getPort()));
            list.add(productBean);
        }
        return list;
    }
}
