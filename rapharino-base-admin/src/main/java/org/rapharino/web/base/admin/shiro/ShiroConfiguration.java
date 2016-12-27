package org.rapharino.web.base.admin.shiro;

import com.google.common.collect.Maps;
import org.apache.commons.collections.MapUtils;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionValidationScheduler;
import org.apache.shiro.session.mgt.ValidatingSessionManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.apache.shiro.session.mgt.quartz.QuartzSessionValidationScheduler;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.rapharino.web.base.admin.shiro.access.StatelessAuthcFilter;
import org.rapharino.web.base.admin.shiro.credentials.RetryLimitHashedCredentialsMatcher;
import org.rapharino.web.base.admin.shiro.realm.ShiroRealm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created bysp Rapharino on 2016/12/25.
 */
@Configuration
public class ShiroConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(ShiroConfiguration.class);

    @Bean
    public Realm realm(CredentialsMatcher credentialsMatcher){
        ShiroRealm realm=new ShiroRealm();
        realm.setCredentialsMatcher(credentialsMatcher);
        realm.setCachingEnabled(false);
        return realm;
    }


    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
        daap.setProxyTargetClass(true);
        return daap;
    }

    /**
     * 安全管理器
     * @param realm
     * @return
     */
    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(Realm realm,
                                                                  CacheManager cacheManager,
                                                                  ValidatingSessionManager sessionManager,
                                                                  RememberMeManager rememberMeManager) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(realm);
        manager.setSessionManager(sessionManager);
         /*用户授权/认证信息Cache, 采用EhCache 缓存*/
        manager.setCacheManager(cacheManager);
        manager.setRememberMeManager(rememberMeManager);
        return manager;
    }

    /**
     * 会话ID生成器
     * @return
     */
    @Bean
    public SessionIdGenerator sessionIdGenerator(){
        return new JavaUuidSessionIdGenerator();
    }

    /**
     * Cookie 生成器
     * @return
     */
    @Bean("sessionIdCookie")
    public Cookie sessionIdCookie(){
        Cookie cookie=new SimpleCookie("sid");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(-1);
        return cookie;
    }
    /**
     * rememberMeCookie
     * @return
     */
    @Bean("rememberMeCookie")
    public Cookie rememberMeCookie(){
        Cookie cookie=new SimpleCookie("rememberMe");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(2592000);
        return cookie;
    }

    /**
     *  RememberMe管理器
     *
     * @return
     */
    @Bean("rememberMeManager")
    public RememberMeManager rememberMeManager(Cookie rememberMeCookie){
        CookieRememberMeManager rememberMeManager=new CookieRememberMeManager();
        rememberMeManager.setCipherKey(Base64.decode("4AvVhmFLUs0KTA3Kprsdag=="));
        rememberMeManager.setCookie(rememberMeCookie);
        return rememberMeManager;
    }

    /**
     * 会话DAO
     * @return
     */
    @Bean("sessionDAO")
    public SessionDAO sessionDAO(SessionIdGenerator sessionIdGenerator){
        EnterpriseCacheSessionDAO sessionDAO =new EnterpriseCacheSessionDAO();
        sessionDAO.setActiveSessionsCacheName("shiro-activeSessionCache");
        sessionDAO.setSessionIdGenerator(sessionIdGenerator);
        return  sessionDAO;
    }

    @Bean("sessionValidationScheduler")
    public SessionValidationScheduler sessionValidationScheduler(ValidatingSessionManager sessionManager){
        QuartzSessionValidationScheduler sessionValidationScheduler = new QuartzSessionValidationScheduler();
        sessionValidationScheduler.setSessionValidationInterval(1800000);
        sessionValidationScheduler.setSessionManager(sessionManager);
        return sessionValidationScheduler;
    }

    /**
     * 会话管理器
     * @return
     */
    @Bean("sessionManager")
    public ValidatingSessionManager sessionManager(SessionValidationScheduler sessionValidationScheduler,
                                                   SessionDAO sessionDAO,
                                                   Cookie sessionIdCookie){
        DefaultWebSessionManager sessionManager=new DefaultWebSessionManager();
        sessionManager.setGlobalSessionTimeout(1800000);
        sessionManager.setDeleteInvalidSessions(true);
        sessionManager.setSessionValidationSchedulerEnabled(true);
        sessionManager.setSessionValidationScheduler(sessionValidationScheduler);
        sessionManager.setSessionDAO(sessionDAO);
        sessionManager.setSessionIdCookieEnabled(true);
        sessionManager.setSessionIdCookie(sessionIdCookie);
        return sessionManager;
    }


    @Bean
    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
        aasa.setSecurityManager(securityManager);
        return aasa;
    }

    /**
     * 集成 spring-servlet,实现 filter 链
     *
     * @param securityManager
     * @return
     */
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager securityManager,
                                              Filter statelessAuthcFilter) {

        ShiroFilterFactoryBean filterFactory = new ShiroFilterFactoryBean();
        // 必须设置 SecurityManager
        filterFactory.setSecurityManager(securityManager);

        // 添加 过滤器 Filter (serlet 规范)
        Map<String, Filter> filters= Maps.newHashMap();
        filters.put("authc",statelessAuthcFilter);
        filterFactory.setFilters(filters);
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        //除了登出,所有的接口 都需要 权限验证
        filterChainDefinitionMap.put("/logout", "logout");
        filterChainDefinitionMap.put("/**", "authc");//
        filterFactory.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return filterFactory;
    }

    @Bean
    public Filter statelessAuthcFilter(){
        return new StatelessAuthcFilter();
    }

    @Bean
    public CacheManager cacheManager() {
        EhCacheManager em = new EhCacheManager();
        em.setCacheManagerConfigFile("classpath:ehcache/ehcache-shiro.xml");
        return em;
    }

    /**
     * 凭证匹配器
     * @param cacheManager
     * @return
     */
    @Bean
    public CredentialsMatcher credentialsMatcher(CacheManager cacheManager) {
        HashedCredentialsMatcher matcher = new RetryLimitHashedCredentialsMatcher(cacheManager);
        matcher.setHashAlgorithmName("md5");
        matcher.setHashIterations(2);
        matcher.setStoredCredentialsHexEncoded(true);
        return matcher;
    }

    /**
     * 集成方式 2
     *
     * 注册DelegatingFilterProxy（Shiro）
     */
    /*@Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new DelegatingFilterProxy("shiroFilter"));
        //  该值缺省为false,表示生命周期由SpringApplicationContext管理,设置为true则表示由ServletContainer管理
        filterRegistration.addInitParameter("targetFilterLifecycle", "true");
        filterRegistration.setEnabled(true);
        filterRegistration.addUrlPatterns("*//*");
        return filterRegistration;
    }*/


    /**
     * 保证实现了Shiro内部lifecycle函数的bean执行
     * @return
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }
}