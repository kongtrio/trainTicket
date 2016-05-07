package jmu.edu.cn.filter;

import com.google.common.collect.Lists;
import jmu.edu.cn.domain.Role;
import jmu.edu.cn.domain.Users;
import jmu.edu.cn.service.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/4/10.
 */
public class LoginSuccessHandle extends SimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private UsersService usersService;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private RequestCache requestCache = new HttpSessionRequestCache();

    public LoginSuccessHandle() {
    }

    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        if (userDetails != null) {
            Users users = usersService.findByUsername(userDetails.getUsername());
            users.setLastLoginTime(new Date());
            usersService.update(users);
            request.getSession().setAttribute("user", users);
        }

        SavedRequest savedRequest = this.requestCache.getRequest(request, response);
        logger.info(userDetails.getUsername() + "is login system");
        if (savedRequest == null) {
            super.onAuthenticationSuccess(request, response, authentication);
        } else {
            String targetUrlParameter = this.getTargetUrlParameter();
            if (!this.isAlwaysUseDefaultTargetUrl() && (targetUrlParameter == null || !StringUtils.hasText(request.getParameter(targetUrlParameter)))) {
                this.clearAuthenticationAttributes(request);
                String targetUrl = savedRequest.getRedirectUrl();
                this.logger.debug("Redirecting to DefaultSavedRequest Url: " + targetUrl);
                this.getRedirectStrategy().sendRedirect(request, response, targetUrl);
            } else {
                this.requestCache.removeRequest(request, response);
                super.onAuthenticationSuccess(request, response, authentication);
            }
        }
    }

    public void setRequestCache(RequestCache requestCache) {
        this.requestCache = requestCache;
    }
}
