package cf.zhul.appdemoforcas.filter;

import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.ssl.IgnoreSSLValidateFilter;
import org.jasig.cas.client.util.HttpServletRequestWrapperFilter;
import org.jasig.cas.client.validation.Cas20ProxyReceivingTicketValidationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.servlet.Filter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Configuration
public class FilterConfig implements Serializable, InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(FilterConfig.class);

    public static final String CAS_SIGNOUT_FILTER_NAME = "CAS Single Sign Out Filter";
    public static final String CAS_AUTH_FILTER_NAME = "CAS Filter";
    public static final String CAS_IGNOREL_SSL_FILTER_NAME = "CAS Ignore SSL Filter";
    public static final String CAS_FILTER_NAME = "CAS Validation Filter";
    public static final String CAS_WRAPPER_NAME = "CAS HttpServletRequest Wrapper Filter";
    public static final String CAS_ASSERTION_NAME = "CAS Assertion Thread Local Filter";
    public static final String CHARACTER_ENCODING_NAME = "Character encoding Filter";

    private static String casSigntouServerUrlPrefix = "http://localhost:8080/cas/logout";
    private static String casServerLoginUrl = "http://localhost:8080/cas/login";
    private static String casServerName = "http://localhost:8888/";
    private static String casValidationServerUrlPrefix = "http://localhost:8080/cas";

    public FilterConfig() {

    }

    /**
     * 单点登出功能,放在其他filter之前
     * casSigntouServerUrlPrefix为登出前缀:https://123.207.122.156:8081/cas/logout
     *
     * @return
     */
    @Bean
    @Order(0)
    public FilterRegistrationBean getCasSignoutFilterRegistrationBean() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(getCasSignoutFilter());
        registration.addUrlPatterns("/*", "*.html");
        registration.addInitParameter("casServerUrlPrefix", casSigntouServerUrlPrefix);
        registration.setName(CAS_SIGNOUT_FILTER_NAME);
        registration.setEnabled(true);
        return registration;
    }

    @Bean(name = CAS_SIGNOUT_FILTER_NAME)
    public Filter getCasSignoutFilter() {
        return new SingleSignOutFilter();
    }

    /**
     * 忽略SSL认证
     *
     * @return
     */
    @Bean
    @Order(1)
    public FilterRegistrationBean getCasSkipSSLValidationFilterRegistrationBean() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(getCasSkipSSLValidationFilter());
        registration.addUrlPatterns("/*", "*.html");
        registration.setName(CAS_IGNOREL_SSL_FILTER_NAME);
        registration.setEnabled(true);
        return registration;
    }

    @Bean(name = CAS_IGNOREL_SSL_FILTER_NAME)
    public Filter getCasSkipSSLValidationFilter() {
        return new IgnoreSSLValidateFilter();
    }

    /**
     * 负责用户的认证
     * casServerLoginUrl：https://123.207.122.156:8081/cas/login
     * casServerName：https://123.207.122.156:8080/tdw/alerts/
     *
     * @return
     */
    @Bean
    @Order(2)
    public FilterRegistrationBean getCasAuthFilterRegistrationBean() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        final Filter casAuthFilter = getCasAuthFilter();
        registration.setFilter(casAuthFilter);
        registration.addUrlPatterns("/*", "*.html");
        registration.addInitParameter("casServerLoginUrl", casServerLoginUrl);
        registration.addInitParameter("serverName", casServerName);
        registration.setName(CAS_AUTH_FILTER_NAME);
        registration.setEnabled(true);
        return registration;
    }

    @Bean(name = CAS_AUTH_FILTER_NAME)
    public Filter getCasAuthFilter() {
        return new MyAuthenticationFilter();
    }

    /**
     * 对Ticket进行校验
     * casValidationServerUrlPrefix要用内网ip
     * casValidationServerUrlPrefix：https://123.207.122.156:8081/cas
     * casServerName：https://123.207.122.156:8080/tdw/alerts/
     *
     * @return
     */
    @Bean
    @Order(3)
    public FilterRegistrationBean getCasValidationFilterRegistrationBean() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        final Filter casValidationFilter = getCasValidationFilter();
        registration.setFilter(casValidationFilter);
        registration.addUrlPatterns("/*", "*.html");
        registration.addInitParameter("casServerUrlPrefix", casValidationServerUrlPrefix);
        registration.addInitParameter("serverName", casServerName);
        registration.setName(CAS_FILTER_NAME);
        registration.setEnabled(true);
        return registration;
    }

    @Bean(name = CAS_FILTER_NAME)
    public Filter getCasValidationFilter() {
        return new Cas20ProxyReceivingTicketValidationFilter();
    }

    /**
     * 设置response的默认编码方式：UTF-8。
     *
     * @return
     */
    @Bean
    @Order(4)
    public FilterRegistrationBean getCharacterEncodingFilterRegistrationBean() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(getCharacterEncodingFilter());
        registration.addUrlPatterns("/*", "*.html");
        registration.setName(CHARACTER_ENCODING_NAME);
        registration.setEnabled(true);
        return registration;
    }

    @Bean(name = CHARACTER_ENCODING_NAME)
    public Filter getCharacterEncodingFilter() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        return characterEncodingFilter;
    }

    @Bean
    public FilterRegistrationBean casHttpServletRequestWrapperFilter(){
        FilterRegistrationBean authenticationFilter = new FilterRegistrationBean();
        authenticationFilter.setFilter(new HttpServletRequestWrapperFilter());
        authenticationFilter.setOrder(6);
        List<String> urlPatterns = new ArrayList<>();
        urlPatterns.add("/*");
        authenticationFilter.setUrlPatterns(urlPatterns);
        return authenticationFilter;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}