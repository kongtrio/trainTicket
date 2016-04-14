package jmu.edu.cn.control;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2016/3/17.
 */
public class BaseController {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    protected HttpSession session;
    @Autowired
    protected HttpServletRequest request;
}
