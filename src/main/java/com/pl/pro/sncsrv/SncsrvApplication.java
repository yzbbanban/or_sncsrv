package com.pl.pro.sncsrv;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication(scanBasePackages = {"com.pl.pro"})
@MapperScan("com.pl.pro.sncsrv.dao")
public class SncsrvApplication {

    public static void main(String[] args) {
        SpringApplication.run(SncsrvApplication.class, args);
        System.err.println("项目启动中");
//        int port;
//        if (args.length > 0) {
//            port = Integer.parseInt(args[0]);
//        } else {
//            port = 9995;
//        }
//        try {
//            new DiscardServer(port).run();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        System.out.println("server:run()");
    }

    /**
     * 配置上传文件大小的配置
     *
     * @return
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //  单个数据大小
        factory.setMaxFileSize("204800KB");
        /// 总上传数据大小
        factory.setMaxRequestSize("512000KB");
        return factory.createMultipartConfig();
    }

    /**
     * 跨域请求
     *
     * @return 跨域过滤器
     */
    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("HEAD");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("PATCH");
        config.addExposedHeader("isLogin");
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}

