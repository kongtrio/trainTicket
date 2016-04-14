package jmu.edu.cn.service;

import com.google.common.collect.Lists;
import jmu.edu.cn.dao.*;
import jmu.edu.cn.dao.util.DynamicSpecifications;
import jmu.edu.cn.dao.util.SearchFilter;
import jmu.edu.cn.domain.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/3/17.
 */
@Service
public class OrdersService {
    @Autowired
    private OrdersDao ordersDao;
    @Autowired
    private OrdersDetailDao ordersDetailDao;

    public Page<Orders> getUnFinishedOrder(int pageNo, int pageSize, QueryParam queryParam) {
        List<SearchFilter> filters = Lists.newArrayList(new SearchFilter("beginTime", SearchFilter.Operator.LT, new Date()));
        Specification<Orders> spec = DynamicSpecifications.bySearchFilter(filters, Orders.class);
        PageRequest page = new PageRequest(pageNo - 1, pageSize);
        return ordersDao.findAll(spec, page);
    }

    public Page<Orders> getFinishedOrder(int pageNo, int pageSize, QueryParam queryParam) {
        List<SearchFilter> filters = Lists.newArrayList(new SearchFilter("beginTime", SearchFilter.Operator.GTE, new Date()));
        Specification<Orders> spec = DynamicSpecifications.bySearchFilter(filters, Orders.class);
        PageRequest page = new PageRequest(pageNo - 1, pageSize);
        return ordersDao.findAll(spec, page);
    }

    public Orders getById(long id) {
        return ordersDao.findById(id);
    }

    public OrdersDetail getDetailById(long id) {
        return ordersDetailDao.findById(id);
    }
}
