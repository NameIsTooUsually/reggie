package com.chenhao.reggie.web.filter;

import com.alibaba.fastjson.JSON;
import com.chenhao.reggie.utils.BaseContextUtil;
import com.chenhao.reggie.web.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ChenHao
 * @version 1.0
 * @description:
 * @date 2022/6/8 16:16
 */
@WebFilter
@Slf4j
public class LoginCheckFilter implements Filter {
    @Value("${checkUrl}")
    String url;

    // 2.2 创建路径匹配器对象
    AntPathMatcher matcher = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        //获取URL地址
        String uri = req.getRequestURI();
        //设置需要放行的地址

        String[] uris = {"/backend/**","/front/**","/employee/login","favicon.ico","/user/sendMsg","/user/login"};
//        String[] uris = url.split("\\,");
        //判断该路径是否可以直接放行
        if(checkUri(uris,uri)){
            //放行登录相关页面
            filterChain.doFilter(req,resp);
            return;
        }

        //非登录界面。需要进行验证
        Long employee = (Long) req.getSession().getAttribute("employee");
        if(null != employee){
            BaseContextUtil.setCurrentId(employee);
           //是是登录状态，可以放行
            filterChain.doFilter(req,resp);
            return;
        }

        //非登录界面。需要进行验证
        Long user = (Long) req.getSession().getAttribute("user");
        if(null != user){
            BaseContextUtil.setCurrentId(user);
            //是登录状态，可以放行
            filterChain.doFilter(req,resp);
            return;
        }

        //跳转至登录界面
        resp.getWriter().write(JSON.toJSONString(R.fail("NOTLOGIN")));

    }

    //判断该请求是否可以直接放行
    private boolean checkUri(String[] uris, String uri) {
            boolean matcherResult = false;

        for (String s : uris) {
            //判断该请求是否在放行请求中
            matcherResult = matcher.match(s,uri);
            if(matcherResult){
                //可以放行
                return true;
            }
        }
        //该页面不在直接放行路径中
        return false;
    }
}
