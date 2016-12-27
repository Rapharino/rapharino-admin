package org.rapharino.web.base.admin.shiro.access;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * 添加 过滤器 Filter (serlet 规范)
 *
 * Created by Rapharino on 2016/12/27.
 */
public class StatelessAuthcFilter extends AccessControlFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        if(this.isLoginRequest(servletRequest, servletResponse)) {
            return true;
        } else {
            Subject subject = this.getSubject(servletRequest, servletResponse);
            return subject.getPrincipal() != null;
        }
    }

    // 访问被拒绝时 回调
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpResponse.getWriter().write("login error");
        return false;
    }
}