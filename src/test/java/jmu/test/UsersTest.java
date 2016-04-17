package jmu.test;

import com.google.common.collect.Lists;
import jmu.edu.cn.dao.OrdersDetailDao;
import jmu.edu.cn.domain.*;
import jmu.edu.cn.service.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by yangjb on 2015/7/3.
 */
@ContextConfiguration(locations = {"/applicationContext.xml"})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@RunWith(SpringJUnit4ClassRunner.class)
//@Transactional(rollbackFor = Exception.class)
public class UsersTest {
    @Autowired
    private UsersService usersService;
    @Autowired
    private TrainService trainService;
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private SitesService sitesService;
    @Autowired
    private NotifyService notifyService;
    @Autowired
    private OrdersDetailDao ordersDetailDao;
    @Test
    public void testGetByIds() {
        ordersDetailDao.delete(37l);
    }

    @Test
//    @Transactional(rollbackFor = Exception.class)
    public void getTrain() {
        ordersService.update(10l);
//        List<TrainDetail> trainDetailList = trainService.getTrainDetailList("福州站", "厦门站", "");
//        System.out.println("begin--------------------");
    }


    @Test
//    @Transactional(rollbackFor = Exception.class)
    public void getOrders() {
        Notify notify = new Notify();
        notify.setContent("哈哈哈哈");
        notifyService.saveOrUpdate(notify);
    }
}
