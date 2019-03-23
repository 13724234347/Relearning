package com.sojson.common.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UOrder implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String orderbh;
	private String orderType;
	private Integer orderNumber;
	private Double orderPrice;
	private String orderAddr;
	private String orderEmpnoName;
	private Date orderTime;
	private String recipient;//收件人
	private String showTime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getOrderbh() {
		return orderbh;
	}
	public void setOrderbh(String orderbh) {
		this.orderbh = orderbh;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	
	public Integer getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(Integer orderNumber) {
		this.orderNumber = orderNumber;
	}
	public Double getOrderPrice() {
		return orderPrice;
	}
	public void setOrderPrice(Double orderPrice) {
		this.orderPrice = orderPrice;
	}
	public String getOrderAddr() {
		return orderAddr;
	}
	public void setOrderAddr(String orderAddr) {
		this.orderAddr = orderAddr;
	}
	public String getOrderEmpnoName() {
		return orderEmpnoName;
	}
	public void setOrderEmpnoName(String orderEmpnoName) {
		this.orderEmpnoName = orderEmpnoName;
	}
	
	public Date getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}
	public String getRecipient() {
		return recipient;
	}
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getShowTime() {//通过showTime显示createTime的时间
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (orderTime != null && !"".equals(orderTime)) {
			String a = sd.format(orderTime);
			return a;
		} else {
			return "暂无时间显示";
	}
 }
	public void setShowTime(String showTime) {
		this.showTime = showTime;
	}
	@Override
	public String toString() {
		return "UOrder [id=" + id + ", orderbh=" + orderbh + ", orderType=" + orderType + ", orderNumber=" + orderNumber
				+ ", orderPrice=" + orderPrice + ", orderAddr=" + orderAddr + ", orderEmpnoName=" + orderEmpnoName
				+ ", orderTime=" + orderTime + ", recipient=" + recipient + "]";
	}
	
}
