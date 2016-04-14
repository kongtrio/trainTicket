package jmu.edu.cn.dao;

import jmu.edu.cn.domain.Users;

/**
 * Created by Administrator on 2015/9/21.
 */
public interface UsersDao extends BaseDao<Users, Long> {
    Users findByUserName(String userName);
}
