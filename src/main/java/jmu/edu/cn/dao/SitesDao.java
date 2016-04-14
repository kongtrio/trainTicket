package jmu.edu.cn.dao;

import jmu.edu.cn.domain.Sites;

import java.util.List;

/**
 * Created by Administrator on 2015/9/21.
 */
public interface SitesDao extends BaseDao<Sites, Long> {
    Sites findBySite(String site);

    List<Sites> findBySiteLike(String site);
}
