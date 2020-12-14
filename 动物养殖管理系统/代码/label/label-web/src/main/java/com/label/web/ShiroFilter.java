package com.label.web;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

/**
 * shiroFilter
 * @author jolley
 */
@WebFilter(
        filterName = "shiroFilter",
        urlPatterns = "/*",
        initParams = { 
                @WebInitParam(name = "targetFilterLifecycle", value = "true")
        }
)
public class ShiroFilter extends org.springframework.web.filter.DelegatingFilterProxy {

}
