package jmu.edu.cn.domain;

import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/3/17.
 */
@Entity
@Table(name = "orders")
@DynamicInsert
public class Orders extends IdEntity {
    private Date time;
    private Date beginTime;
    private Date endTime;
    private String beginSite;
    private String endSite;
    private String useTime;
    private float price;
    private int status;
    private Users users;
    private List<OrdersDetail> ordersDetails;
    private TrainDetail trainDetail;
    private String contactFormat;

    @Column(name = "begin_time")
    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @ManyToOne(targetEntity = Users.class)
    @JoinColumn(name = "user_id", updatable = false)
    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    @Column(name = "end_time")
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Column(name = "begin_site")
    public String getBeginSite() {
        return beginSite;
    }

    public void setBeginSite(String beginSite) {
        this.beginSite = beginSite;
    }

    @Column(name = "end_site")
    public String getEndSite() {
        return endSite;
    }

    public void setEndSite(String endSite) {
        this.endSite = endSite;
    }

    @Column(name = "use_time")
    public String getUseTime() {
        return useTime;
    }

    public void setUseTime(String useTime) {
        this.useTime = useTime;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @OneToMany(targetEntity = OrdersDetail.class, mappedBy = "orders")
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    public List<OrdersDetail> getOrdersDetails() {
        return ordersDetails;
    }

    public void setOrdersDetails(List<OrdersDetail> ordersDetails) {
        this.ordersDetails = ordersDetails;
    }

    @ManyToOne(targetEntity = TrainDetail.class)
    @JoinColumn(name = "train_detail_id ", updatable = false)
    public TrainDetail getTrainDetail() {
        return trainDetail;
    }

    public void setTrainDetail(TrainDetail trainDetail) {
        this.trainDetail = trainDetail;
    }

    @Transient
    public String getContactFormat() {
        if (ordersDetails != null && ordersDetails.size() > 0) {
            StringBuilder builder = new StringBuilder();
            for (OrdersDetail ordersDetail : ordersDetails) {
                Contact contact = ordersDetail.getContact();
                builder.append("联系人:&nbsp;" + contact.getName() + "&nbsp;&nbsp;&nbsp;&nbsp;身份证:&nbsp;"
                        + contact.getIdentityCard() + "&nbsp;&nbsp;&nbsp;&nbsp;座位号:&nbsp;" + ordersDetail.getSeatSerial() + "<br/>");
            }
            return builder.toString();
        }
        return contactFormat;
    }

    public void setContactFormat(String contactFormat) {
        this.contactFormat = contactFormat;
    }

    public void copy(Orders orders) {
        this.beginSite = orders.getBeginSite();
        this.endSite = orders.getEndSite();
        this.beginTime = orders.getBeginTime();
        this.endTime = orders.getEndTime();
        this.useTime = orders.getUseTime();
        this.price = orders.getPrice();
        this.trainDetail = orders.getTrainDetail();
        this.time = new Date();
    }
}
