package jmu.edu.cn.service;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import jmu.edu.cn.dao.SitesDao;
import jmu.edu.cn.dao.TrainDao;
import jmu.edu.cn.dao.TrainDetailDao;
import jmu.edu.cn.dao.TrainSiteDao;
import jmu.edu.cn.dao.util.DynamicSpecifications;
import jmu.edu.cn.dao.util.SearchFilter;
import jmu.edu.cn.domain.*;
import jmu.edu.cn.util.DateUtil;
import jmu.edu.cn.util.JsonMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/3/17.
 */
@Service
public class TrainService {
    @Autowired
    private TrainDao trainDao;
    @Autowired
    private TrainDetailDao trainDetailDao;
    @Autowired
    private TrainSiteDao trainSiteDao;
    @Autowired
    private SitesDao sitesDao;
    @Autowired
    private SitesService sitesService;

    public List<Train> list() {
        return (List) trainDao.findAll();
    }

    /**
     * 根据站点获取对应的列车信息,所有经过该站点的列车
     *
     * @param site 站点名称,支持模糊查找
     * @return
     */
    public List<Train> getTrainBySite(final String site) {
        if (StringUtils.isBlank(site)) {
            return null;
        }
        List<Sites> sites = sitesService.findBySiteLike(site);
        List<TrainSite> trainSiteList = findBySiteList(sites);
        if (trainSiteList == null) {
            return null;
        }
        //某辆列车必须同时包含这两个站点,才将它加入list中
        List<Train> trains = Lists.newArrayList();
        for (TrainSite trainSite : trainSiteList) {
            Train train = trainSite.getTrain();
            trains.add(train);
        }
        return trains;
    }

    /**
     * 搜索列车班次
     * 先根据beginSite和endsite获取对应站点,获取到的站点去train_site表中找到对应的列车(列车站点对应表)
     * 找出所有有经过这两个站点的列车后,在排除掉那些方向不对的列车,剩下的就是可以搭乘的所有列车
     *
     * @param beginSite 起始站
     * @param endSite   终点站
     * @param time      时间
     * @return
     */
    public List<TrainReport> getTrainDetailList(final String beginSite, String endSite, String time) {
        List<Sites> sites = sitesService.findBySites(Lists.newArrayList(beginSite, endSite));
        if (sites == null || sites.size() != 2) {
            return null;
        }
        List<TrainSite> trainSiteList = findBySiteList(sites);
        if (trainSiteList == null) {
            return null;
        }
        //某辆列车必须同时包含这两个站点,才将它加入list中
        List<Train> trains = Lists.newArrayList();
        for (TrainSite trainSite : trainSiteList) {
            Train train = trainSite.getTrain();
            List<TrainSite> trainSites = train.getTrainSites();
            List<String> tmpSite = Lists.newArrayList();
            for (TrainSite trainSite1 : trainSites) {
                tmpSite.add(trainSite1.getSite().getSite());
            }
            if (tmpSite.containsAll(Lists.newArrayList(beginSite, endSite))) {
                trains.add(train);
            }
        }
        if (CollectionUtils.isEmpty(trains)) {
            return null;
        }
        Date date = DateUtil.parseDate(time, "yyyy-MM-dd");
        List<TrainDetail> trainDetailByTrains = findByTrains(trains, date);
        //将查到的数据填充到report里面,方便等会页面展示
        List<TrainReport> trainReports = Lists.newArrayList();
        for (TrainDetail trainDetail : trainDetailByTrains) {
            TrainReport trainReport = new TrainReport(beginSite, endSite);
            int beginIndex = 0;
            int endIndex = 0;
            for (TrainScribe trainScribe : trainDetail.getTrain().getTrainScribeList()) {
                if (trainScribe.getSiteName().equals(beginSite)) {
                    trainReport.setBeginTime(trainScribe.getTime());
                    beginIndex = trainScribe.getSiteIndex();
                }
                if (trainScribe.getSiteName().equals(endSite)) {
                    trainReport.setEndTime(trainScribe.getTime());
                    endIndex = trainScribe.getSiteIndex();
                }
            }
            //过滤掉方向相反的列车
            if (DateUtil.getDayTimes(trainReport.getBeginTime()) >= DateUtil.getDayTimes(trainReport.getEndTime())) {
                continue;
            }
            //过滤掉今天已经开出的列车
            String todayTime = DateUtil.formatDate(new Date(), "HH:mm:ss");
            if (DateUtil.isToday(time) && DateUtil.getDayTimes(todayTime) > DateUtil.getDayTimes(trainReport.getBeginTime())) {
                continue;
            }

            int minSeatNum = getMinSeatNum(trainDetail, beginIndex, endIndex);
            trainReport.setSeatNum(minSeatNum);
            trainReport.setTrainDetail(trainDetail);
            trainReports.add(trainReport);
        }


        //按发车时间顺序进行排序
        Collections.sort(trainReports, new Comparator<TrainReport>() {
            public int compare(TrainReport o1, TrainReport o2) {
                return Integer.compare(DateUtil.getDayTimes(o1.getBeginTime()), DateUtil.getDayTimes(o2.getBeginTime()));
            }
        });
        return trainReports;
    }


    //获取某班列车第beginIndex个站点到第endIndex个站点之间的实际余票数
    private int getMinSeatNum(TrainDetail trainDetail, int beginIndex, int endIndex) {
        if (endIndex - 1 > trainDetail.getSeatInt().size()) {
            return 0;
        }
        List<Integer> seatInt = trainDetail.getSeatInt();
        List<Integer> realSeat = seatInt.subList(beginIndex - 1, endIndex - 1);
        int minNum = Integer.MAX_VALUE;
        for (int value : realSeat) {
            minNum = minNum < value ? minNum : value;
        }
        return minNum;
    }

    //根据列车获取列车和时间详情
    public List<TrainDetail> findByTrains(List<Train> trains, Date date) {
        List<SearchFilter> filters = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(trains)) {
            filters.add(new SearchFilter("train", SearchFilter.Operator.IN, trains));
            filters.add(new SearchFilter("time", SearchFilter.Operator.EQ, date));
        }

        Specification<TrainDetail> spec = DynamicSpecifications.bySearchFilter(filters, TrainDetail.class);
        return trainDetailDao.findAll(spec);
    }

    //分页查询列车信息
    public Page<Train> findAll(int pageNo, int pageSize, QueryParam queryParam) {
        List<SearchFilter> filters = Lists.newArrayList();
        if (StringUtils.isNotBlank(queryParam.getSites())) {
            List<Sites> searchSite = sitesService.findBySiteLike(queryParam.getSites());

            List<TrainSite> bySites = findBySiteList(searchSite);

            List<Long> trainIds = Lists.newArrayList();
            if (bySites != null) {
                for (TrainSite trainSite : bySites) {
                    trainIds.add(trainSite.getTrain().getId());
                }
            }

            filters.add(new SearchFilter("id", SearchFilter.Operator.IN, trainIds));
        }
        Specification<Train> spec = DynamicSpecifications.bySearchFilter(filters, Train.class);
        PageRequest page = new PageRequest(pageNo - 1, pageSize);
        return trainDao.findAll(spec, page);
    }

    //根据参数分页查询
    public Page<TrainDetail> findAllDetail(int pageNo, int pageSize, QueryParam queryParam, List<Train> trains) {
        List<SearchFilter> filters = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(trains)) {
            filters.add(new SearchFilter("train", SearchFilter.Operator.IN, trains));
        }
        if (StringUtils.isNotBlank(queryParam.getSerial())) {
            filters.add(new SearchFilter("train.trainSerial", SearchFilter.Operator.LIKE, queryParam.getSerial()));
        }
        if (queryParam.getId() != 0) {
            filters.add(new SearchFilter("id", SearchFilter.Operator.EQ, queryParam.getId()));
        }
        if (StringUtils.isNotBlank(queryParam.getBeginTime()) && StringUtils.isNotBlank(queryParam.getEndTime())) {
            Date beginTime = DateUtil.parseDate(queryParam.getBeginTime(), "yyyy-MM-dd");
            Date endTime = DateUtil.parseDate(queryParam.getEndTime(), "yyyy-MM-dd");
            filters.add(new SearchFilter("time", SearchFilter.Operator.LT, endTime));
            filters.add(new SearchFilter("time", SearchFilter.Operator.GTE, beginTime));
        }
        if (StringUtils.isNotBlank(queryParam.getStatus()) && !queryParam.getStatus().equalsIgnoreCase("all")) {
            int status = queryParam.getStatus().equals("normal") ? 1 : 0;
            filters.add(new SearchFilter("status", SearchFilter.Operator.EQ, status));
        }

        Sort.Order order = new Sort.Order(Sort.Direction.ASC, "time");
        Specification<TrainDetail> spec = DynamicSpecifications.bySearchFilter(filters, TrainDetail.class);
        PageRequest page = new PageRequest(pageNo - 1, pageSize, new Sort(order));
        return trainDetailDao.findAll(spec, page);
    }

    //根据站点集合查询对应的train_site集合
    public List<TrainSite> findBySiteList(List<Sites> sites) {
        if (CollectionUtils.isEmpty(sites)) {
            return null;
        }
        List<SearchFilter> filters = Lists.newArrayList(new SearchFilter("site", SearchFilter.Operator.IN, sites));
        Specification<TrainSite> spec = DynamicSpecifications.bySearchFilter(filters, TrainSite.class);
        return trainSiteDao.findAll(spec);
    }

    //添加一辆列车
    public void createTrain(String trainSerial, List<TrainScribe> trainScribeList) {
        Train train = saveTrain(trainSerial, trainScribeList);
        if (CollectionUtils.isEmpty(trainScribeList)) {
            return;
        }
        List<String> sites = Lists.newArrayList();
        for (TrainScribe trainScribe : trainScribeList) {
            sites.add(trainScribe.getSiteName());
        }
        List<Sites> siteBySiteNames = getBySiteNames(sites);
        if (train == null || CollectionUtils.isEmpty(siteBySiteNames)) {
            return;
        }
        for (Sites saveSite : siteBySiteNames) {
            TrainSite trainSite = new TrainSite(saveSite, train);
            trainSiteDao.save(trainSite);
        }

        //同时添加未来60天的列车班次
        List<TrainDetail> trainDetailList = Lists.newArrayList();
        for (int i = 0; i < 60; i++) {
            Date date = DateUtil.nextDayBeginTime(i);
            TrainDetail trainDetail = new TrainDetail();
            trainDetail.setTrain(train);
            trainDetail.setSeatNumber(getInitSeat(train.getTrainScribeList().size()));
            trainDetail.setTime(date);
            trainDetail.setStatus(1);
            trainDetailList.add(trainDetail);
        }
        trainDetailDao.save(trainDetailList);

    }

    //初始化列车座位
    public static String getInitSeat(int siteSize) {
        List<Integer> seat = Lists.newArrayList();
        for (int i = 0; i < siteSize; i++) {
            seat.add(105);
        }
        return Joiner.on(",").join(seat);
    }

    public void saveTrainDetail(TrainDetail trainDetail) {
        trainDetailDao.save(trainDetail);
    }

    public Train saveTrain(Train train) {
        return trainDao.save(train);
    }

    public Train saveTrain(String trainSerial, List<TrainScribe> trainScribeList) {
        Train train = new Train();
        train.setStatus(1);
        train.setTrainSerial(trainSerial);
        train.setTrainSiteDetail(JsonMapper.nonEmptyMapper().toJson(trainScribeList));
        trainDao.save(train);

        return train;
    }

    public void delTrain(long id) {
        trainDao.delete(id);
    }

    public Train findById(long id) {
        return trainDao.findById(id);
    }

    public TrainDetail findDetailById(long id) {
        return trainDetailDao.findById(id);
    }

    public List<Sites> getBySiteNames(List<String> siteNames) {
        List<SearchFilter> filters = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(siteNames)) {
            filters.add(new SearchFilter("site", SearchFilter.Operator.IN, siteNames));
        }
        Specification<Sites> spec = DynamicSpecifications.bySearchFilter(filters, Sites.class);
        return sitesDao.findAll(spec);
    }

    public Train getById(long id) {
        return trainDao.findById(id);
    }

    public TrainDetail getDetailById(long id) {
        return trainDetailDao.findById(id);
    }
}
