package jmu.edu.cn.dao;

import jmu.edu.cn.domain.Sites;
import jmu.edu.cn.domain.TrainSite;

import java.util.List;

/**
 * Created by Administrator on 2015/9/21.
 */
public interface TrainSiteDao extends BaseDao<TrainSite, Long> {
    List<TrainSite> findBySite(Sites sites);
}
