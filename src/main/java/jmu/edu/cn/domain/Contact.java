package jmu.edu.cn.domain;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Administrator on 2016/3/16.
 */

@Entity
@Table(name = "contact")
@DynamicInsert
public class Contact extends IdEntity {
    private String name;
    private String telphone;
    private String identityCard;
    private Users users;
    private List<OrdersDetail> ordersDetailList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    @Column(name = "identity_card")
    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    @ManyToOne(targetEntity = Users.class)
    @JoinColumn(name = "user_id", updatable = false)
    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    @OneToMany(targetEntity = OrdersDetail.class, mappedBy = "contact")
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    public List<OrdersDetail> getOrdersDetailList() {
        return ordersDetailList;
    }

    public void setOrdersDetailList(List<OrdersDetail> ordersDetailList) {
        this.ordersDetailList = ordersDetailList;
    }
}
