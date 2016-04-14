package jmu.edu.cn.interceptor;

import jmu.edu.cn.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

//HandlerInterceptorAdapter是单例，被所有变量共享
//you can implements ServletContextAware to get servletContext, the servletContext is application scope.
public class AbsolutelyPathInterceptor extends HandlerInterceptorAdapter {
    public static final Logger LOGGER = LoggerFactory.getLogger(AbsolutelyPathInterceptor.class);
    static final int HTTPPORT = 80;
    static final int HTTPSPORT = 8443;
    private static final String CONTEXT_PATH = "basePath";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        request.setAttribute("fullpath", request.getRequestURL());
        request.setAttribute(CONTEXT_PATH, getAbsolutelypath(request));
        request.setAttribute("date", DateUtil.formatDate(new Date(), "yyyy-MM-dd"));
        return super.preHandle(request, response, handler);
    }

    private String getAbsolutelypath(HttpServletRequest request) {
        String absoluteContextPath;
        if (request.getServerPort() == HTTPPORT) {
            absoluteContextPath = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        } else if (request.getServerPort() == HTTPSPORT) {
            absoluteContextPath = "https://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        } else {
            String portocol = "";
            if (request.isSecure()) {
                portocol = "https://";
            } else {
                portocol = "http://";
            }
            absoluteContextPath = portocol + request.getServerName() + ":" +
                    request.getServerPort() + request.getContextPath();
        }
        return absoluteContextPath;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }
}
