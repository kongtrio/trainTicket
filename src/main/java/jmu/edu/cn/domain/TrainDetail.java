package jmu.edu.cn.domain;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/3/17.
 */
@Entity
@Table(name = "train_detail")
public class TrainDetail extends IdEntity {
    private Date time;
    private String seatNumber;
    private int status;
    private List<Orders> orders;
    private Train train;
    private List<Integer> seatInt;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Column(name = "seat_number")
    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    @OneToMany(targetEntity = Orders.class, mappedBy = "trainDetail")
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    public List<Orders> getOrders() {
        return orders;
    }

    public void setOrders(List<Orders> orders) {
        this.orders = orders;
    }

    @ManyToOne(targetEntity = Train.class)
    @JoinColumn(name = "train_id", updatable = false)
    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    @Transient
    public List<Integer> getSeatInt() {
        if (StringUtils.isNotBlank(this.seatNumber)) {
            String[] split = this.seatNumber.split(",");
            List<Integer> seats = Lists.newArrayList();
            for (String string : split) {
                seats.add(Integer.parseInt(string));
            }
            return seats;
        }
        return seatInt;
    }

    public void setSeatInt(List<Integer> seatInt) {
        this.seatInt = seatInt;
    }

    //传入起始站和终点站的位置
    public int getRealSeat(int begin, int end) {
        begin = begin - 1;
        end = end - 1;
        if (begin < 0 || end > this.getSeatInt().size()) {
            return 0;
        }
        int min = Integer.MAX_VALUE;
        List<Integer> seatInt = this.getSeatInt();
        for (int i = begin; i <= end; i++) {
            min = seatInt.get(i) < min ? seatInt.get(i) : min;
        }
        return min;
    }

    //将某个区段的所有座位减去 sellSeatNum  然后返回所有区段的座位数
    public String calSeat(int begin, int end, int sellSeatNum, boolean isAdd) {
        begin = begin - 1;
        end = end - 1;
        if (begin < 0 || end > this.getSeatInt().size()) {
            return "error begin or end index";
        }
        List<Integer> seatInt = this.getSeatInt();
        List<Integer> newSeatInt = Lists.newArrayList();
        for (int i = 0; i < seatInt.size(); i++) {
            Integer integer = seatInt.get(i);
            if (i <= end && i >= begin) {
                if (isAdd) {
                    integer = integer + sellSeatNum;
                } else {
                    integer = integer - sellSeatNum;
                }

            }
            newSeatInt.add(integer);
        }
        return Joiner.on(",").join(newSeatInt);
    }
}
