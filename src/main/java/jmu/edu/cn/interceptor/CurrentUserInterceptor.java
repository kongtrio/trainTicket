package jmu.edu.cn.interceptor;//package com.ershou.www.interceptor;
//
//import com.masp.domain.Users;
//import com.masp.service.UsersService;
//import org.apache.commons.collections.CollectionUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@Component
//public class CurrentUserInterceptor extends HandlerInterceptorAdapter {
//    private static Logger loGGER = LoggerFactory.getLogger(CurrentUserInterceptor.class);
//    @Autowired
//    private UsersService usersService;
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
//                             Object handler) throws Exception {
//        getCurrentUser(request);
//        return super.preHandle(request, response, handler);
//    }
//
//    private void getCurrentUser(HttpServletRequest request) throws ServletException, IOException {
//        Users users = (Users) request.getSession().getAttribute("currentUser");
//        if (users == null || users.getId() == null) {
//            if (SecurityContextHolder.getContext() != null &&
//                    SecurityContextHolder.getContext().getAuthentication() != null &&
//                    SecurityContextHolder.getContext().getAuthentication().getPrincipal() != null &&
//                    SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetails) {
//                UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
//                        .getAuthentication().getPrincipal();
//                if (userDetails != null) {
//                    String username = userDetails.getUsername();
//                    users = usersService.findByName(username);
//                    String role = null;
//                    if (CollectionUtils.isNotEmpty(users.getRoleList())) {
//                        role = users.getRoleList().get(0).getRole();
//                    }
//                    request.getSession().setAttribute("currentUser", users);
//                    request.getSession().setAttribute("currentRole", role);
//
//                }
//            }
//        }
//    }
//
//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response,
//                           Object handler, ModelAndView modelAndView) throws Exception {
//        super.postHandle(request, response, handler, modelAndView);
//    }
//}
