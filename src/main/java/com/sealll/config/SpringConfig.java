package com.sealll.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.sealll.config.yml.YmlPropertySourceFactory;
import jdk.tools.jlink.internal.Platform;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import sun.security.krb5.Config;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author sealll
 * @time 2021/3/26 19:54
 */
@Configuration
@EnableTransactionManagement
@ComponentScan(value = "com.sealll",excludeFilters = {
        @ComponentScan.Filter(type=FilterType.ANNOTATION,classes ={ Configuration.class}),
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

    @Configuration
    static class SpringMybatis{
        @Bean
        @DependsOn("dataSource")
        public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource){
            SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
            sqlSessionFactoryBean.setDataSource(dataSource);
            sqlSessionFactoryBean.setConfigLocation(new ClassPathResource("mybatis.xml"));
            ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();

            sqlSessionFactoryBean.setMapperLocations( Stream.of(Optional.ofNullable(new String[]{"classpath:mapper/*.xml"}).orElse(new String[0]))
                    .flatMap(location -> {
                        try {
                            return Stream.of(resourceResolver.getResources(location));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }).toArray(Resource[]::new));
            return sqlSessionFactoryBean;
        }

        @Bean
        public MapperScannerConfigurer mapperScannerConfigurer(){
            MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
            mapperScannerConfigurer.setBasePackage("com.sealll.mapper");
            return mapperScannerConfigurer;
        }
    }

    @Bean
    public Logger log(){
        return LoggerFactory.getLogger("com.sealll");
    }

    @DependsOn("dataSource")
    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }


}
