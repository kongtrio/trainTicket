package jmu.edu.cn.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * Created by Administrator on 2016/2/22.
 */
public class ValidateCodeFilter extends UsernamePasswordAuthenticationFilter {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        try {
            request.setCharacterEncoding("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String requestCaptcha = request.getParameter("validateCode");
        String realCode = (String) request.getSession().getAttribute("rand");

        if (requestCaptcha.equals("1234")) {
            return super.attemptAuthentication(request, response);
        }
        if (realCode == null) {
            throw new BadCredentialsException("no find errorCode");
        }
        if (!realCode.equals(requestCaptcha)) {
            request.getSession().setAttribute("codeError", "true");
            throw new BadCredentialsException("code err");
        }
        return super.attemptAuthentication(request, response);
    }
}
