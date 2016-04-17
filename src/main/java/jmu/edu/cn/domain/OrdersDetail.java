package jmu.edu.cn.domain;

import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Administrator on 2016/3/17.
 */
@Entity
@Table(name = "orders_detail")
@DynamicInsert
public class OrdersDetail extends IdEntity {
    private Orders orders;
    private Contact contact;
    private String seatSerial;

    @ManyToOne(targetEntity = Orders.class)
    @JoinColumn(name = "order_id", updatable = false)
    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    @ManyToOne(targetEntity = Contact.class)
    @JoinColumn(name = "contact_id", updatable = false)
    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    @Column(name = "seat_serial")
    public String getSeatSerial() {
        return seatSerial;
    }

    public void setSeatSerial(String seatSerial) {
        this.seatSerial = seatSerial;
    }
}
