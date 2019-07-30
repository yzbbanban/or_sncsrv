package com.pl.pro.sncsrv.task;

import com.pl.pro.sncsrv.dao.ProductDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by brander on 2019/7/28
 */
@Component
@Configuration
@EnableScheduling
public class DeleteImageTask {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProductDao productDao;

    @Value("${del.time}")
    String delTime;

    @Value("${del.url}")
    String delUrl;

    @Scheduled(cron = "0/30 * * * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void deleteTask() {
        logger.info("======执行删除图片程序======");
        //获取当天 0 点
        Date date = getZeroTime(delTime);
        logger.info("====开始时间====> {}", date);
        int row = productDao.deleteImg(date);
        //删除成功
        if (row > 0) {
            logger.info("====数据库删除成功==>条数：{}", row);
            //删除文件夹信息
            autoDeleteCdr(delTime);
        } else {
            logger.info("=====无图片数据可删除====");
        }
    }

    private void autoDeleteCdr(String cdrTimer) {
        Integer val = Integer.valueOf(cdrTimer);
        //find /Users/YZBbanban/Desktop/test/ssss -maxdepth 1 -type f -mmin +10 -exec rm -f {} \;
        String delete = "find {0} -maxdepth 1 -type f -mmin +{1} -exec rm -f {2} \\;";
        //将参数传入进去
        delete = MessageFormat.format(delete, delUrl, String.valueOf(val), "{}");
        logger.info("==del=>: {}", delete);
        String[] cmd = new String[]{"/bin/sh", "-c", delete};
        try {
            Runtime.getRuntime().exec(cmd);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public Date getZeroTime(String delTime) {
        long delT = Long.parseLong(delTime);
        long time = System.currentTimeMillis() - 60L * 1000 * delT;
        Date d = new Date(time);
        System.out.println(d);
        return d;
    }

}
