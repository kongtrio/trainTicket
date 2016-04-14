package jmu.edu.cn.dao;

import jmu.edu.cn.domain.Train;

/**
 * Created by Administrator on 2015/9/21.
 */
public interface TrainDao extends BaseDao<Train, Long> {
    Train findById(long id);
}
