package jmu.edu.cn.dao;

import jmu.edu.cn.domain.Role;

/**
 * Created by Administrator on 2015/9/21.
 */
public interface RoleDao extends BaseDao<Role, Long> {
    Role findByName(String name);
}
