package jmu.edu.cn.service;

import com.google.common.collect.Lists;
import jmu.edu.cn.dao.SitesDao;
import jmu.edu.cn.dao.util.DynamicSpecifications;
import jmu.edu.cn.dao.util.SearchFilter;
import jmu.edu.cn.domain.Contact;
import jmu.edu.cn.domain.QueryParam;
import jmu.edu.cn.domain.Sites;
import jmu.edu.cn.domain.Users;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by Administrator on 2016/3/27.
 */
@Service
public class SitesService {
    @Autowired
    private SitesDao sitesDao;

    public List<Sites> findAll() {
        return (List) sitesDao.findAll();
    }

    /**
     * 根据一些参数分页获取数据
     */
    public Page<Sites> findAll(int pageNo, int pageSize, QueryParam queryParam) {
        List<SearchFilter> filters = Lists.newArrayList();
        if (StringUtils.isNotBlank(queryParam.getSites())) {
            filters.add(new SearchFilter("site", SearchFilter.Operator.LIKE, queryParam.getSites()));
        }
        if (StringUtils.isNotBlank(queryParam.getProvince())) {
            filters.add(new SearchFilter("province", SearchFilter.Operator.LIKE, queryParam.getProvince()));
        }
        Specification<Sites> spec = DynamicSpecifications.bySearchFilter(filters, Sites.class);
        PageRequest page = new PageRequest(pageNo - 1, pageSize);
        return sitesDao.findAll(spec, page);
    }

    public void delete(long id) {
        sitesDao.delete(id);
    }

    public void delete(Sites sites) {
        sitesDao.delete(sites);
    }

    public void saveOrUpdate(Sites sites) {
        if (sites != null) {
            sitesDao.save(sites);
        }
    }

    public Sites findById(long id) {
        return sitesDao.findById(id);
    }

    public Sites findBySite(String site) {
        return sitesDao.findBySite(site);
    }

    public List<Sites> findBySiteLike(String site) {
        return sitesDao.findBySiteLike("%" + site + "%");
    }

    /**
     *  根据一些站点集合在获取数据,只要有在这个集合里的站点都会被搜出来
     */
    public List<Sites> findBySites(List<String> sites) {
        List<SearchFilter> filters = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(sites)) {
            filters.add(new SearchFilter("site", SearchFilter.Operator.IN, sites));
        }
        Specification<Sites> spec = DynamicSpecifications.bySearchFilter(filters, Sites.class);
        return sitesDao.findAll(spec);
    }
}
