package com.pl.pro.sncsrv;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 功能描述：
 *
 * @author 88403636_徐航
 * @since 19:45 2019/3/13
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class LongConnTest {

    private Logger LOOGER = LoggerFactory.getLogger(LongConnTest.class);

    String host = "localhost";
    int port = 8080;

    public void testLongConn() throws Exception {
        LOOGER.debug("start");
        final Socket socket = new Socket();
        socket.connect(new InetSocketAddress(host, port));
        Scanner scanner = new Scanner(System.in);
        new Thread().start();
    }
}
