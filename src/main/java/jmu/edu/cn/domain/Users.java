package jmu.edu.cn.domain;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/9/23.
 */
@Entity
@Table(name = "users")
@DynamicInsert
public class Users extends IdEntity {
    private String userName;
    private String password;
    private String telphone;
    private Date registerTime;
    private Date lastLoginTime;
    private String lastLoginIp;
    private int status;
    private String remark;
    private List<Role> roles;
    private List<Role> rolesNoLazy;
    private List<Contact> contacts;
    private List<Orders> orderses;

    @Column(name = "user_name")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    @Column(name = "register_time")
    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    @Column(name = "last_login_time")
    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    @Column(name = "last_login_ip")
    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @OneToMany(targetEntity = Role.class)
    @JoinTable(name = "user_role", joinColumns = {@JoinColumn(name = "user_id",
            referencedColumnName = "id")}, inverseJoinColumns = {@JoinColumn(name = "role_id",
            referencedColumnName = "id")})
    @Cascade(value = {CascadeType.LOCK})
    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @OneToMany(targetEntity = Contact.class, mappedBy = "users")
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    @OneToMany(targetEntity = Orders.class, mappedBy = "users")
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    public List<Orders> getOrderses() {
        return orderses;
    }

    public void setOrderses(List<Orders> orderses) {
        this.orderses = orderses;
    }

    @Transient
    public List<Role> getRolesNoLazy() {
        return rolesNoLazy;
    }

    public void setRolesNoLazy(List<Role> rolesNoLazy) {
        this.rolesNoLazy = rolesNoLazy;
    }
}
