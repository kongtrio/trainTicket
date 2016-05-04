package jmu.edu.cn.service;

import com.google.common.collect.Lists;
import jmu.edu.cn.dao.NotifyDao;
import jmu.edu.cn.dao.SitesDao;
import jmu.edu.cn.dao.util.DynamicSpecifications;
import jmu.edu.cn.dao.util.SearchFilter;
import jmu.edu.cn.domain.Notify;
import jmu.edu.cn.domain.QueryParam;
import jmu.edu.cn.domain.Sites;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016/3/27.
 */
@Service
public class NotifyService {
    @Autowired
    private NotifyDao notifyDao;

    /**
     * 根据一些参数过滤,并查找某页的数据
     *
     * @param pageNo     第几页
     * @param pageSize   一页几条
     * @param queryParam 参数
     * @return
     */
    public Page<Notify> findAll(int pageNo, int pageSize, QueryParam queryParam) {
        List<SearchFilter> filters = Lists.newArrayList();
        if (StringUtils.isNotBlank(queryParam.getSearchKey())) {
            filters.add(new SearchFilter("title" + SearchFilter.OR_SEPARATOR + "content", SearchFilter.Operator.LIKE, queryParam.getSearchKey()));
        }
        Specification<Notify> spec = DynamicSpecifications.bySearchFilter(filters, Notify.class);
        Sort.Order order = new Sort.Order(Sort.Direction.DESC, "insertTime");
        PageRequest page = new PageRequest(pageNo - 1, pageSize, new Sort(order));
        return notifyDao.findAll(spec, page);
    }

    public void delete(long id) {
        notifyDao.delete(id);
    }

    public void saveOrUpdate(Notify notify) {
        if (notify != null) {
            notifyDao.save(notify);
        }
    }

    public Notify findById(long id) {
        return notifyDao.findById(id);
    }

}
