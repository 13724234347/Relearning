package com.tzh.shiro.configure;

import com.tzh.shiro.filter.LoginFilter;
import com.tzh.shiro.filter.PermissionFilter;
import com.tzh.shiro.service.ShiroManager;
import com.tzh.shiro.service.ShiroManagerImplement;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;


/**
  * @ClassName：    ShiroConfiguration
  * @Author:        tangzihao
  * @CreateDate:    2019/2/28 19:16
  * @Version:       1.0
  *
  * 相当于springmvc项目spring-shiro_filter.xml配置文件
  */
@Configuration
public class ShiroConfiguration {

    /**
     * @author:        tangzihao
     * @methodName：   lifecycleBeanPostProcessor
     * @createDate:    2019/2/28 19:19
     * @param:         []
     * @return         org.apache.shiro_filter.spring.LifecycleBeanPostProcessor
     * @version:       1.0
     *
     * 配置bean的后置处理器:会自动调用和spring整合后的各个组件的生命周期方法
     */
    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }
    /**
     * @author:        tangzihao
     * @methodName：   hashedCredentialsMatcher
     * @createDate:    2019/2/28 19:22
     * @param:         []
     * @return         org.apache.shiro_filter.authc.credential.HashedCredentialsMatcher
     * @version:       1.0
     *
     * 处理认证匹配处理器：如果自定义需要实现继承HashedCredentialsMatcher
     * 指定加密方式方式，也可以在这里加入缓存，当用户超过五次登陆错误就锁定该用户禁止不断尝试登陆
     */
    @Bean(name = "hashedCredentialsMatcher")
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName("MD5");
        credentialsMatcher.setHashIterations(1024);
        credentialsMatcher.setStoredCredentialsHexEncoded(true);
        return credentialsMatcher;
    }
    /**
     * @author:        tangzihao
     * @methodName：   shiroRealm
     * @createDate:    2019/2/28 19:23
     * @param:         []
     * @return         ShiroRealm
     * @version:       1.0
     *
     * 配置进行授权和认证的Realm
     */
    @Bean(name = "shiroRealm")
    @DependsOn("lifecycleBeanPostProcessor")
    public ShiroRealm shiroRealm() {
        ShiroRealm realm = new ShiroRealm();
        realm.setCredentialsMatcher(hashedCredentialsMatcher());
        return realm;
    }
    /**
     * @author:        tangzihao
     * @methodName：   ehCacheManager
     * @createDate:    2019/2/28 19:23
     * @param:         []
     * @return         org.apache.shiro_filter.cache.ehcache.EhCacheManager
     * @version:       1.0
     *
     * 配置缓存管理器
     */
    @Bean(name = "ehCacheManager")
    @DependsOn("lifecycleBeanPostProcessor")
    public EhCacheManager ehCacheManager(){
        EhCacheManager ehCacheManager = new EhCacheManager();
        return ehCacheManager;
    }
    /**
     * @author:        tangzihao
     * @methodName：   securityManager
     * @createDate:    2019/2/28 19:23
     * @param:         []
     * @return         org.apache.shiro_filter.web.mgt.DefaultWebSecurityManager
     * @version:       1.0
     *
     * 配置shiro的securityManager的bean.
     */
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager securityManager(){
        System.err.println("--------------shiro已经加载----------------");
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(shiroRealm());
        securityManager.setCacheManager(ehCacheManager());//用户授权/认证信息Cache, 采用EhCache 缓存
        return securityManager;
    }
    /**
     * @author:        tangzihao
     * @methodName：   shiroFilterFactoryBean
     * @createDate:    2019/2/28 19:26
     * @param:         [securityManager]
     * @return         org.apache.shiro_filter.spring.web.ShiroFilterFactoryBean
     * @version:       1.0
     *
     * 配置shiroFilter bean 这个bean
     */
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager  securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        Map<String, Filter> filters = new LinkedHashMap<>();
        LoginFilter loginFilter = new LoginFilter();
        PermissionFilter permissionFilter = new PermissionFilter();
        filters.put("login",loginFilter);
        filters.put("permission",permissionFilter);
        shiroFilterFactoryBean.setFilters(filters);

        ShiroManager shiroManager = new ShiroManagerImplement();
        shiroFilterFactoryBean.setFilterChainDefinitions(shiroManager.loadFilterChainDefinitions());
        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl("/login/jumpLogin");
        // 登录成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/user");
        // 未授权界面
        shiroFilterFactoryBean.setUnauthorizedUrl("/login/unauthorized");

        return shiroFilterFactoryBean;
    }

    /**
     * @author:        tangzihao
     * @methodName：   defaultAdvisorAutoProxyCreator
     * @createDate:    2019/2/28 19:27
     * @param:         []
     * @return         org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator
     * @version:       1.0
     *
     * 使shiro的注解起作用,shiro的注解标识在方法上 例如: @RequiresRoles,@RequiresPermissions
     */
    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
        daap.setProxyTargetClass(true);
        return daap;
    }


}
