package jmu.edu.cn.service;

import com.google.common.collect.Lists;
import jmu.edu.cn.dao.AdvicesDao;
import jmu.edu.cn.dao.util.DynamicSpecifications;
import jmu.edu.cn.dao.util.SearchFilter;
import jmu.edu.cn.domain.Advices;
import jmu.edu.cn.domain.Notify;
import jmu.edu.cn.domain.QueryParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016/3/27.
 */
@Service
public class AdvicesService {
    @Autowired
    private AdvicesDao advicesDao;

    public void save(Advices advices) {
        advicesDao.save(advices);
    }

    public Page<Advices> list(int pageNo, int pageSize, QueryParam queryParam) {
        List<SearchFilter> filters = Lists.newArrayList();
        if (StringUtils.isNotBlank(queryParam.getUsername())) {
            filters.add(new SearchFilter("username", SearchFilter.Operator.LIKE, queryParam.getSearchKey()));
        }
        Specification<Advices> spec = DynamicSpecifications.bySearchFilter(filters, Advices.class);
        Sort.Order order = new Sort.Order(Sort.Direction.DESC, "insertTime");
        PageRequest page = new PageRequest(pageNo - 1, pageSize, new Sort(order));
        return advicesDao.findAll(spec, page);
    }

}
