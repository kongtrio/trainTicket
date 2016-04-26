package jmu.edu.cn.service;

import com.google.common.collect.Lists;
import jmu.edu.cn.dao.ContactDao;
import jmu.edu.cn.dao.RoleDao;
import jmu.edu.cn.dao.UsersDao;
import jmu.edu.cn.dao.util.DynamicSpecifications;
import jmu.edu.cn.dao.util.SearchFilter;
import jmu.edu.cn.domain.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/9/21.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UsersService {
    @Autowired
    private UsersDao usersDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private ContactDao contactDao;

    public List<Users> findAll() {
        return (List) usersDao.findAll();
    }

    public Page<Users> findAll(int pageNo, int pageSize, QueryParam queryParam) {
        List<SearchFilter> filters = Lists.newArrayList();
        if (StringUtils.isNotBlank(queryParam.getUsername())) {
            filters.add(new SearchFilter("userName", SearchFilter.Operator.LIKE, queryParam.getUsername()));
        }
        Specification<Users> spec = DynamicSpecifications.bySearchFilter(filters, Users.class);
        PageRequest page = new PageRequest(pageNo - 1, pageSize);
        return usersDao.findAll(spec, page);
    }

    public Page<Contact> findAllContacts(int pageNo, int pageSize, QueryParam queryParam) {
        List<SearchFilter> filters = Lists.newArrayList();
        if (StringUtils.isNotBlank(queryParam.getUsername())) {
            Users byUserName = usersDao.findByUserName(queryParam.getUsername());
            if (byUserName != null) {
                filters.add(new SearchFilter("users", SearchFilter.Operator.EQ, byUserName));
            }
        }
        Specification<Contact> spec = DynamicSpecifications.bySearchFilter(filters, Contact.class);
        PageRequest page = new PageRequest(pageNo - 1, pageSize);
        return contactDao.findAll(spec, page);
    }

    public Page<Contact> findContactsByUser(int pageNo, int pageSize, Users user) {
        List<SearchFilter> filters = Lists.newArrayList(new SearchFilter("users", SearchFilter.Operator.EQ, user));
        Specification<Contact> spec = DynamicSpecifications.bySearchFilter(filters, Contact.class);
        PageRequest page = new PageRequest(pageNo - 1, pageSize);
        return contactDao.findAll(spec, page);
    }


    public Contact findContactById(long contactId) {
        Contact contact = contactDao.findById(contactId);
        return contact;
    }

    public List<Contact> findContactByIds(List<Long> contactIds) {
        List<SearchFilter> filters = Lists.newArrayList(new SearchFilter("id", SearchFilter.Operator.IN, contactIds));
        Specification<Contact> spec = DynamicSpecifications.bySearchFilter(filters, Contact.class);
        return contactDao.findAll(spec);
    }

    public void save(Users user) {
        if (user == null) {
            return;
        }
        Role role = roleDao.findByName(RoleEnum.ROLE_USER.name());
        user.setRoles(Lists.newArrayList(role));
        user.setStatus(1);
        user.setLastLoginTime(new Date());
        usersDao.save(user);
    }

    public void saveContact(Contact contact, Users users) {
        if (contact == null) {
            return;
        }
        contact.setUsers(users);
        contactDao.save(contact);
    }

    public void updateContact(Contact contact) {
        if (contact == null) {
            return;
        }
        contactDao.save(contact);
    }

    public void update(Users user) {
        if (user == null) {
            return;
        }
        usersDao.save(user);
    }


    //查询的时候不需要配置事务,提高速度
    //    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public Users getById(long id) {
        Users user = usersDao.findById(id);
        return user;
    }

    public Users findByUsername(String username) {
        return usersDao.findByUserName(username);
    }

    public void delete(Users users) {
        usersDao.delete(users);
    }

    public void delete(Long userId) {
        //防止删除管理员
        if (userId == null || userId == 1) {
            return;
        }
        usersDao.delete(userId);
    }

    public void deleteContact(long contactId) {
        contactDao.delete(contactId);
    }

    public Users getCurrentUser() {
        UserDetails userDetails = null;
        Object object = SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        Users users = null;
        if (object != null) {
            userDetails = (UserDetails) object;
            users = findByUsername(userDetails.getUsername());
        }
        return users;
    }
}
