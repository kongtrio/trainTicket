package jmu.edu.cn.service;

import com.google.common.collect.Lists;
import jmu.edu.cn.dao.OrdersDao;
import jmu.edu.cn.dao.OrdersDetailDao;
import jmu.edu.cn.dao.util.DynamicSpecifications;
import jmu.edu.cn.dao.util.SearchFilter;
import jmu.edu.cn.domain.Orders;
import jmu.edu.cn.domain.OrdersDetail;
import jmu.edu.cn.domain.QueryParam;
import jmu.edu.cn.domain.TrainDetail;
import jmu.edu.cn.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Autowired
    private TrainService trainService;

    /**
     * 获取列车未发车的订单
     */
    public Page<Orders> getUnFinishedOrder(int pageNo, int pageSize, QueryParam queryParam) {
        List<SearchFilter> filters = Lists.newArrayList(new SearchFilter("beginTime", SearchFilter.Operator.GT, new Date()), new SearchFilter("users", SearchFilter.Operator.EQ, queryParam.getUsers()));
        filterParam(queryParam, filters);
        Specification<Orders> spec = DynamicSpecifications.bySearchFilter(filters, Orders.class);
        Sort.Order order = new Sort.Order(Sort.Direction.DESC, "time");
        PageRequest page = new PageRequest(pageNo - 1, pageSize, new Sort(order));
        return ordersDao.findAll(spec, page);
    }

    /**
     * 获取列车已发车的订单
     */
    public Page<Orders> getFinishedOrder(int pageNo, int pageSize, QueryParam queryParam) {
        List<SearchFilter> filters = Lists.newArrayList(new SearchFilter("beginTime", SearchFilter.Operator.LTE, new Date()), new SearchFilter("users", SearchFilter.Operator.EQ, queryParam.getUsers()));
        filterParam(queryParam, filters);
        Specification<Orders> spec = DynamicSpecifications.bySearchFilter(filters, Orders.class);
        Sort.Order order = new Sort.Order(Sort.Direction.DESC, "time");
        PageRequest page = new PageRequest(pageNo - 1, pageSize, new Sort(order));
        return ordersDao.findAll(spec, page);
    }

    public Orders save(Orders orders) {
        return ordersDao.save(orders);
    }

    @Transactional
    public Orders update(Orders orders) {
        List<OrdersDetail> ordersDetails = orders.getOrdersDetails();
        deleteOrderDetailList(ordersDetails);
        orders.getOrdersDetails().clear();
        return ordersDao.save(orders);
    }

    @Transactional
    public Orders update(long id) {
        Orders orders = getById(id);
        List<OrdersDetail> ordersDetails = orders.getOrdersDetails();
        deleteOrderDetailList(ordersDetails);
        orders.getOrdersDetails().clear();
        Orders save = ordersDao.save(orders);
        return save;
    }

    //保存订单详情
    public void saveDetailList(List<OrdersDetail> ordersDetailList) {
        ordersDetailDao.save(ordersDetailList);
    }

    public void delete(Orders orders) {
        TrainDetail trainDetail = orders.getTrainDetail();
        int saleSize = orders.getOrdersDetails().size();
        String nowSeatSize = trainDetail.calSeat(orders.getBeginIndex(), orders.getEndIndex(), saleSize, true);
        trainDetail.setSeatNumber(nowSeatSize);
        trainService.saveTrainDetail(trainDetail);
        ordersDao.delete(orders);
    }

    //删除一个集合的订单详情
    public void deleteOrderDetailList(List<OrdersDetail> ordersDetailList) {
        ordersDetailDao.delete(ordersDetailList);
    }

    public void deleteOrderDetail(long ordersDetailId) {
        ordersDetailDao.delete(ordersDetailId);
    }

    public Orders getById(long id) {
        return ordersDao.findById(id);
    }

    public OrdersDetail getDetailById(long id) {
        return ordersDetailDao.findById(id);
    }

    public List<Orders> getByTrainDetail(TrainDetail trainDetail) {
        return ordersDao.findByTrainDetail(trainDetail);
    }

    /**
     * 用来过滤一些参数,有两个地方都用到了这些代码,所以把他们抽了出来
     */
    private void filterParam(QueryParam queryParam, List<SearchFilter> filters) {
        if (StringUtils.isNotBlank(queryParam.getTimeType()) && StringUtils.isNotBlank(queryParam.getBeginTime()) && StringUtils.isNotBlank(queryParam.getEndTime())) {
            String var = "time";
            if ("orderTime".equals(queryParam.getTimeType())) {
                var = "beginTime";
            }
            Date beginDate = DateUtil.parseDate(queryParam.getBeginTime(), "yyyy-MM-dd");
            Date endDate = DateUtil.parseDate(queryParam.getEndTime(), "yyyy-MM-dd");
            filters.add(new SearchFilter(var, SearchFilter.Operator.GT, beginDate));
            filters.add(new SearchFilter(var, SearchFilter.Operator.LTE, endDate));
        }
    }
}
