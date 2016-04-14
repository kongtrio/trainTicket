package jmu.edu.cn.filter;

import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by yangjb on 2015/4/13.
 */
public class MyFreeMakerView extends FreeMarkerView {


    @Override
    protected void exposeHelpers(Map model,
                                 HttpServletRequest request) throws Exception {
        super.exposeHelpers(model, request);
    }
}
