package com.sealll.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInterceptor;
import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import com.sealll.config.yml.DataSourceConfig;
import com.sealll.config.yml.RedisConfig;
import com.sealll.config.yml.YmlPropertySourceFactory;
import com.sealll.constant.FileConstants;
import com.sealll.shiro.filter.RoleAuthorizationFilter2;
import com.sealll.shiro.filter.*;
import com.sealll.shiro.md5.MyCredentialsMatcher;
import com.sealll.shiro.realm.UserRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

import javax.servlet.Filter;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Stream;

/**
 * @author sealll
 * @time 2021/3/26 19:54
 */
@Configuration
@EnableTransactionManagement
@EnableAspectJAutoProxy
@ComponentScan(value = "com.sealll",excludeFilters = {
//        @ComponentScan.Filter(type=FilterType.ANNOTATION,classes ={ Configuration.class}),
        @ComponentScan.Filter(type= FilterType.ANNOTATION,classes={Controller.class})
})
@PropertySource(value="classpath:jdbc.yml",factory = YmlPropertySourceFactory.class)
public class SpringConfig {
    @Autowired
    private Environment environment;
    @Bean
    public RedisConfig redisConfig(){
//        System.out.println(environment.getProperty("redis.host"));
        RedisConfig redisConfig = new RedisConfig();
        redisConfig.setDatabase(Integer.parseInt(environment.getProperty("redis.database")));
        redisConfig.setHost(environment.getProperty("redis.host") + ":" + environment.getProperty("redis.port"));
        redisConfig.setPassword(environment.getProperty("redis.password"));
        return redisConfig;
    }
    @Bean
    public DataSourceConfig dataSourceConfig(){
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDriverClass(environment.getProperty("datasource.driverClass"));
        dataSourceConfig.setUrl(environment.getProperty("datasource.url"));
        dataSourceConfig.setPassword(environment.getProperty("datasource.password"));
        dataSourceConfig.setUsername(environment.getProperty("datasource.username"));
        return dataSourceConfig;
    }

    @Bean
    public JedisPool jedisPool(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(10);
        JedisPool jedisPool = new JedisPool(jedisPoolConfig,environment.getProperty("redis.host"),
                Protocol.DEFAULT_PORT,Protocol.DEFAULT_TIMEOUT,
                environment.getProperty("redis.password"),
                Integer.parseInt(environment.getProperty("redis.database")));
        return jedisPool;
    }

    @Configuration
    static class JDBCDatasource{
        @Autowired
        private DataSourceConfig dataSourceConfig;

        public JDBCDatasource(){

        }

        @Bean
        public DataSource dataSource() throws SQLException {
            DruidDataSource druidDataSource = new DruidDataSource();
            druidDataSource.setUrl(dataSourceConfig.getUrl());
            druidDataSource.setUsername(dataSourceConfig.getUsername());
            druidDataSource.setDriverClassName(dataSourceConfig.getDriverClass());
            druidDataSource.setPassword(dataSourceConfig.getPassword());
            druidDataSource.setBreakAfterAcquireFailure(true);
            druidDataSource.setFilters("config,stat");
            return druidDataSource;
        }
    }

    @Configuration
    static class SpringMybatis{
        @Bean
        @DependsOn("dataSource")
        public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource,PageInterceptor interceptor){
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
            sqlSessionFactoryBean.setPlugins(
                interceptor
            );
            return sqlSessionFactoryBean;
        }

        @Bean
        public MapperScannerConfigurer mapperScannerConfigurer(){
            MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
            mapperScannerConfigurer.setBasePackage("com.sealll.mapper");
            return mapperScannerConfigurer;
        }

        @Bean
        public PageInterceptor pageInterceptor() throws IOException {
            PageInterceptor interceptor = new PageInterceptor();
            InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(FileConstants.PAGE);
            Properties p = new Properties();
            p.load(resourceAsStream);
            interceptor.setProperties(p);

            return interceptor;
        }
    }

    /**
     *
     */
    @Configuration
    static class ShiroConfig{
        @Autowired
        private RedisConfig redisConfig;

        @DependsOn({"kaptFilter","annoFilter2","authenticationFilter2"})
        @Bean
        public ShiroFilterFactoryBean shiroFilter(KaptFilter kaptFilter,
                                                  AnnoFilter2 annoFilter2,
                                                  AuthenticationFilter2 authenticationFilter2,
                                                  SecurityManager securityManager,
                                                  LogoutFilter2 logoutFilter2,
                                                  GetSelfFilter selfFilter,
                                                  PostRoleFilter postRoleFilter,
                                                  PDSelfFilter pdSelfFilter,
                                                  GetAuthenticationFilter2 getAuthenticationFilter2,
                                                  RoleAuthorizationFilter2 roleAuthorizationFilter2){
            ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
            Map<String, Filter> filters = new HashMap<>();
            filters.put("authc2",authenticationFilter2);
            filters.put("authc3",getAuthenticationFilter2);
            filters.put("kapt",kaptFilter);
            filters.put("anno2",annoFilter2);
            filters.put("logout2",logoutFilter2);
            filters.put("self",selfFilter);
            filters.put("pdself",pdSelfFilter);
            filters.put("proles",postRoleFilter);
            filters.put("roles2",roleAuthorizationFilter2);
            factoryBean.setFilters(filters);
            factoryBean.setSecurityManager(securityManager);
            factoryBean.setLoginUrl("/login");
            factoryBean.setUnauthorizedUrl("/unauth");
            HashMap<String,String> map = new LinkedHashMap<>();
            map.put("/logout","logout2");
            map.put("/login","anno2,kapt,authc2");
            map.put("/register","anno2,kapt,anon");
            map.put("/reply/user/*","authc2,self");
            map.put("/fav/user/*","authc2,self");
            map.put("/post","authc3,proles[user,admin],pdself");
            map.put("/post/tags","authc3,pdself");
            map.put("/tag","roles2[admin]");
            map.put("/star","authc2,pdself");
            map.put("/reply","authc3,proles[user,admin],pdself");
            map.put("/fav/check","authc2");
            map.put("/fav/**","authc3,pdself");
            map.put("/role/user/*","anon");
            map.put("/role/**","roles2[admin]");
            map.put("/history/**","authc2");
            map.put("/**","anon");
            factoryBean.setFilterChainDefinitionMap(map);
//            factoryBean.setUnauthorizedUrl("/");
            return factoryBean;
//            factoryBean.setSuccessUrl("/");

        }
        @Bean
        public SecurityManager securityManager(Realm realm){
            DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
            manager.setSessionManager(sessionManager());
            manager.setRealm(realm);
            SecurityUtils.setSecurityManager(manager);
            return manager;
        }
        @Bean
        public SessionManager sessionManager(){
            DefaultWebSessionManager manager = new DefaultWebSessionManager();
            manager.getSessionIdCookie().setPath("/");
//            manager.setSessionDAO(redisSessionDAO());
            manager.setCacheManager(redisCacheManager());
            return manager;
        }
        @Bean
        public Realm realm(MyCredentialsMatcher credentialsMatcher){
            UserRealm userRealm = new UserRealm();
            userRealm.setCredentialsMatcher(credentialsMatcher);
            userRealm.setAuthenticationCachingEnabled(true);
            userRealm.setAuthorizationCachingEnabled(true);
            return userRealm;
        }
        @Bean
        public RedisManager redisManager(){
            RedisManager redisManager = new RedisManager();
            redisManager.setPassword(redisConfig.getPassword());
            redisManager.setHost(redisConfig.getHost());
//            System.out.println(redisConfig);
            return redisManager;
        }
        @Bean
        public RedisCacheManager redisCacheManager(){
            RedisCacheManager redisCacheManager = new RedisCacheManager();
            redisCacheManager.setRedisManager(redisManager());
            return redisCacheManager;
        }
//        @Bean
//        public RedisSessionDAO redisSessionDAO(){
//            RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
//            redisSessionDAO.setRedisManager(redisManager());
//            return redisSessionDAO;
//        }
        public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
            AuthorizationAttributeSourceAdvisor ad = new AuthorizationAttributeSourceAdvisor();
            ad.setSecurityManager(securityManager);
            return ad;
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

    @Configuration
    static class KaptConfig{
        @Bean
        public Producer producer() throws IOException {
            DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
            Properties pros = new Properties();
            InputStream is = this.getClass().getClassLoader().getResourceAsStream(FileConstants.KAPTCHA);
            pros.load(is);
            Config config = new Config(pros);
            defaultKaptcha.setConfig(config);
            return defaultKaptcha;
        }

    }


}
