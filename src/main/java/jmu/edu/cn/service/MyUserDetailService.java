package jmu.edu.cn.service;

import jmu.edu.cn.domain.Role;
import jmu.edu.cn.domain.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Administrator on 2016/2/21.
 */
public class MyUserDetailService implements UserDetailsService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UsersService usersService;

    @Transactional(rollbackFor = Exception.class)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users dbUser = usersService.findByUsername(username);
        if (dbUser == null) {
            throw new UsernameNotFoundException("");
        }
        Collection<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
        for (Role role : dbUser.getRoles()) {
            SimpleGrantedAuthority simpleGrantedAuthority1 = new SimpleGrantedAuthority(role.getName());
            auths.add(simpleGrantedAuthority1);
        }
        User user = new User(dbUser.getUserName(), dbUser.getPassword(), auths);
        return user;
    }
}
