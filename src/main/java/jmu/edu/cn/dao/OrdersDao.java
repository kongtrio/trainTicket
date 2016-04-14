package jmu.edu.cn.dao;

import jmu.edu.cn.domain.Orders;

/**
 * Created by Administrator on 2015/9/21.
 */
public interface OrdersDao extends BaseDao<Orders, Long> {
    Orders findById(long id);
}
