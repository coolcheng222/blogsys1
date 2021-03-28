package com.sealll.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.sealll.config.yml.YmlPropertySourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Controller;
import sun.security.krb5.Config;

import javax.sql.DataSource;

/**
 * @author sealll
 * @time 2021/3/26 19:54
 */
@Configuration
@ComponentScan(value = "com.sealll",excludeFilters = {
        @ComponentScan.Filter(type= FilterType.ANNOTATION,classes={Configuration.class}),
        @ComponentScan.Filter(type= FilterType.ANNOTATION,classes={Controller.class}),
})
public class SpringConfig {

    @Configuration
    @PropertySource(value="classpath:jdbc.yml",factory = YmlPropertySourceFactory.class)
    static class JDBCDatasource{
        @Value("${datasource.url}")
        private String url;
        @Value("${datasource.username}")
        private String username;
        @Value("${datasource.driverClass}")
        private String driverClass;
        @Value("${datasource.password}")
        private String password;

        public JDBCDatasource(){

        }

        @Bean
        public DataSource dataSource(){
            System.out.println(url);
            System.out.println(username);
            DruidDataSource druidDataSource = new DruidDataSource();
            druidDataSource.setUrl(url);
            druidDataSource.setUsername(username);
            druidDataSource.setDriverClassName(driverClass);
            druidDataSource.setPassword(password);
            return druidDataSource;
        }
    }

    @Bean
    public Logger log(){
        return LoggerFactory.getLogger("com.sealll");
    }


}
