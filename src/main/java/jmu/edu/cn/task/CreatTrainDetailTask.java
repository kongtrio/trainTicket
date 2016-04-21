package jmu.edu.cn.task;

import jmu.edu.cn.domain.Train;
import jmu.edu.cn.domain.TrainDetail;
import jmu.edu.cn.service.TrainService;
import jmu.edu.cn.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Created by yangjb on 2016/4/20.
 * hello
 * 为所有创建的列车每天都添加新的班次
 */
@Component
public class CreatTrainDetailTask {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private TrainService trainService;

    //凌晨1点整自动跑这个
    @Scheduled(cron = "0 0 1 * * ?")
    public void run() {
        logger.info("begin to run the create train detail task");
        List<Train> list = trainService.list();
        for (Train train : list) {
            TrainDetail trainDetail = new TrainDetail();
            trainDetail.setSeatNumber(105);
            trainDetail.setTime(new Date(DateUtil.nextDays(60)));
            trainDetail.setTrain(train);
            trainDetail.setStatus(1);
            trainService.saveTrainDetail(trainDetail);
        }
    }
}
