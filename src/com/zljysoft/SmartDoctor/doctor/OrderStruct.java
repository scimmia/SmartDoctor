package com.zljysoft.SmartDoctor.doctor;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * User: ASUS
 * Date: 13-10-28
 * Time: 下午2:24
 * To change this template use File | Settings | File Templates.
 */
public class OrderStruct {
    Date orderCreateTime;
    int orderType;
    LinkedList<OrderDetailStruct> details;

    public OrderStruct(Date orderCreateTime, int orderType, LinkedList<OrderDetailStruct> details) {
        this.orderCreateTime = orderCreateTime;
        this.orderType = orderType;
        this.details = details;
        if (this.details == null){
            this.details = new LinkedList<OrderDetailStruct>();
        }
    }

    public Date getOrderCreateTime() {
        return orderCreateTime;
    }

    public void setOrderCreateTime(Date orderCreateTime) {
        this.orderCreateTime = orderCreateTime;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public LinkedList<OrderDetailStruct> getDetails() {
        return details;
    }

    public void setDetails(LinkedList<OrderDetailStruct> details) {
        this.details = details;
    }
}
