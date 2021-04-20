package com.sealll.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sealll.config.yml.YmlPropertySourceFactory;
import com.sealll.shiro.realm.UserRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.session.mgt.ServletContainerSessionManager;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author sealll
 * @time 2021/3/26 19:54
 */
@Configuration
@EnableTransactionManagement
@ComponentScan(value = "com.sealll",excludeFilters = {
//        @ComponentScan.Filter(type=FilterType.ANNOTATION,classes ={ Configuration.class}),
        @ComponentScan.Filter(type= FilterType.ANNOTATION,classes={Controller.class})
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
//            System.out.println(url);
//            System.out.println(username);
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
            mapperScannerConfigurer.setBasePackage("com.sealll");
            return mapperScannerConfigurer;
        }
    }

    @Configuration
    static class ShiroConfig{
        @Bean
        public ShiroFilterFactoryBean shiroFilter(){
            ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
            factoryBean.setSecurityManager(securityManager());
            factoryBean.setLoginUrl("/login");
            HashMap<String,String> map = new HashMap<>();
            map.put("/logout","logout");
            map.put("/login","authc");
            map.put("/register","anon");
            map.put("/**","anon");
            factoryBean.setFilterChainDefinitionMap(map);
//            factoryBean.setUnauthorizedUrl("/");
            return factoryBean;
//            factoryBean.setSuccessUrl("/");

        }
        @Bean
        public SecurityManager securityManager(){
            DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
            manager.setSessionManager(sessionManager());
            manager.setRealm(realm());
            return manager;
        }
        @Bean
        public SessionManager sessionManager(){
            DefaultWebSessionManager manager = new DefaultWebSessionManager();
            return manager;
        }
        @Bean
        public Realm realm(){
            return new UserRealm();
        }
    }

    @Bean
    public Logger log(){
        return LoggerFactory.getLogger("com.sealll");
    }

    @Bean
    public ObjectMapper mapper(){
        return new ObjectMapper();
    }

    @DependsOn("dataSource")
    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }


}
