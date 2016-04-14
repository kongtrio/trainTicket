package jmu.edu.cn.domain;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Administrator on 2015/9/23.
 */
@Entity
@Table(name = "role")
@DynamicInsert
public class Role extends IdEntity {
    private String name;
    private String remark;
    private List<Users> users;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @OneToMany(targetEntity = Role.class)
    @JoinTable(name = "user_role", joinColumns = {@JoinColumn(name = "role_id",
            referencedColumnName = "id")}, inverseJoinColumns = {@JoinColumn(name = "user_id",
            referencedColumnName = "id")})
    @Cascade(value = {org.hibernate.annotations.CascadeType.LOCK})
    public List<Users> getUsers() {
        return users;
    }

    public void setUsers(List<Users> users) {
        this.users = users;
    }
}
