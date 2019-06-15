package com.jeecg.bascontrail.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.lang.String;
import java.lang.Double;
import java.lang.Integer;
import java.math.BigDecimal;
import javax.xml.soap.Text;
import java.sql.Blob;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.SequenceGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 电子围栏
 * @author onlineGenerator
 * @date 2019-06-10 21:40:30
 * @version V1.0   
 *
 */
@Entity
@Table(name = "bus_mapfence", schema = "")
@SuppressWarnings("serial")
public class BusMapfenceEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**设备id*/
	@Excel(name="设备id",width=15)
	private java.lang.String deviceId;
	/**信号强度*/
	@Excel(name="信号强度",width=15)
	private java.lang.String signalPower;
	/**小区id*/
	@Excel(name="小区id",width=15)
	private java.lang.String cellId;
	/**电压*/
	@Excel(name="电压",width=15)
	private java.lang.String batteryVoltage;
	/**gps时间*/
	@Excel(name="gps时间",width=15)
	private java.lang.String gpsTime;
	/**纬度*/
	@Excel(name="纬度",width=15)
	private java.lang.String gpsLatitude;
	/**经度*/
	@Excel(name="经度",width=15)
	private java.lang.String gpsLongitude;
	/**时间戳*/
	@Excel(name="时间戳",width=15)
	private java.lang.String timestamp;
	/**设备发送时间*/
	@Excel(name="设备发送时间",width=15)
	private java.lang.String deviceTime;
	/**下一次时间戳*/
	@Excel(name="下一次时间戳",width=15)
	private java.lang.String nextTrcTime;
	/**位置精确的精度*/
	@Excel(name="位置精确的精度",width=15)
	private java.lang.String accuracy;
	/**设备发送次数*/
	@Excel(name="设备发送次数",width=15)
	private java.lang.String counter;
	/**类型*/
	@Excel(name="类型",width=15)
	private java.lang.String type;
	/**消息*/
	@Excel(name="消息",width=15)
	private java.lang.String msg;
	/**创建人名称*/
	private java.lang.String createName;
	/**创建人登录名称*/
	private java.lang.String createBy;
	/**创建日期*/
	private java.util.Date createDate;
	/**更新人名称*/
	private java.lang.String updateName;
	/**更新人登录名称*/
	private java.lang.String updateBy;
	/**更新日期*/
	private java.util.Date updateDate;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键
	 */
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")

	@Column(name ="ID",nullable=false,length=36)
	public java.lang.String getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主键
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  设备id
	 */

	@Column(name ="DEVICE_ID",nullable=true,length=32)
	public java.lang.String getDeviceId(){
		return this.deviceId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  设备id
	 */
	public void setDeviceId(java.lang.String deviceId){
		this.deviceId = deviceId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  信号强度
	 */

	@Column(name ="SIGNAL_POWER",nullable=true,length=32)
	public java.lang.String getSignalPower(){
		return this.signalPower;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  信号强度
	 */
	public void setSignalPower(java.lang.String signalPower){
		this.signalPower = signalPower;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  小区id
	 */

	@Column(name ="CELL_ID",nullable=true,length=32)
	public java.lang.String getCellId(){
		return this.cellId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  小区id
	 */
	public void setCellId(java.lang.String cellId){
		this.cellId = cellId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  电压
	 */

	@Column(name ="BATTERY_VOLTAGE",nullable=true,length=32)
	public java.lang.String getBatteryVoltage(){
		return this.batteryVoltage;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  电压
	 */
	public void setBatteryVoltage(java.lang.String batteryVoltage){
		this.batteryVoltage = batteryVoltage;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  gps时间
	 */

	@Column(name ="GPS_TIME",nullable=true,length=32)
	public java.lang.String getGpsTime(){
		return this.gpsTime;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  gps时间
	 */
	public void setGpsTime(java.lang.String gpsTime){
		this.gpsTime = gpsTime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  纬度
	 */

	@Column(name ="GPS_LATITUDE",nullable=true,length=32)
	public java.lang.String getGpsLatitude(){
		return this.gpsLatitude;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  纬度
	 */
	public void setGpsLatitude(java.lang.String gpsLatitude){
		this.gpsLatitude = gpsLatitude;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  经度
	 */

	@Column(name ="GPS_LONGITUDE",nullable=true,length=32)
	public java.lang.String getGpsLongitude(){
		return this.gpsLongitude;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  经度
	 */
	public void setGpsLongitude(java.lang.String gpsLongitude){
		this.gpsLongitude = gpsLongitude;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  时间戳
	 */

	@Column(name ="TIMESTAMP",nullable=true,length=32)
	public java.lang.String getTimestamp(){
		return this.timestamp;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  时间戳
	 */
	public void setTimestamp(java.lang.String timestamp){
		this.timestamp = timestamp;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  设备发送时间
	 */

	@Column(name ="DEVICE_TIME",nullable=true,length=32)
	public java.lang.String getDeviceTime(){
		return this.deviceTime;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  设备发送时间
	 */
	public void setDeviceTime(java.lang.String deviceTime){
		this.deviceTime = deviceTime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  下一次时间戳
	 */

	@Column(name ="NEXT_TRC_TIME",nullable=true,length=32)
	public java.lang.String getNextTrcTime(){
		return this.nextTrcTime;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  下一次时间戳
	 */
	public void setNextTrcTime(java.lang.String nextTrcTime){
		this.nextTrcTime = nextTrcTime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  位置精确的精度
	 */

	@Column(name ="ACCURACY",nullable=true,length=32)
	public java.lang.String getAccuracy(){
		return this.accuracy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  位置精确的精度
	 */
	public void setAccuracy(java.lang.String accuracy){
		this.accuracy = accuracy;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  设备发送次数
	 */

	@Column(name ="COUNTER",nullable=true,length=32)
	public java.lang.String getCounter(){
		return this.counter;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  设备发送次数
	 */
	public void setCounter(java.lang.String counter){
		this.counter = counter;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  类型
	 */

	@Column(name ="TYPE",nullable=true,length=32)
	public java.lang.String getType(){
		return this.type;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  类型
	 */
	public void setType(java.lang.String type){
		this.type = type;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  消息
	 */

	@Column(name ="MSG",nullable=true,length=32)
	public java.lang.String getMsg(){
		return this.msg;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  消息
	 */
	public void setMsg(java.lang.String msg){
		this.msg = msg;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人名称
	 */

	@Column(name ="CREATE_NAME",nullable=true,length=50)
	public java.lang.String getCreateName(){
		return this.createName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人名称
	 */
	public void setCreateName(java.lang.String createName){
		this.createName = createName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人登录名称
	 */

	@Column(name ="CREATE_BY",nullable=true,length=50)
	public java.lang.String getCreateBy(){
		return this.createBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人登录名称
	 */
	public void setCreateBy(java.lang.String createBy){
		this.createBy = createBy;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  创建日期
	 */

	@Column(name ="CREATE_DATE",nullable=true,length=20)
	public java.util.Date getCreateDate(){
		return this.createDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建日期
	 */
	public void setCreateDate(java.util.Date createDate){
		this.createDate = createDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  更新人名称
	 */

	@Column(name ="UPDATE_NAME",nullable=true,length=50)
	public java.lang.String getUpdateName(){
		return this.updateName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  更新人名称
	 */
	public void setUpdateName(java.lang.String updateName){
		this.updateName = updateName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  更新人登录名称
	 */

	@Column(name ="UPDATE_BY",nullable=true,length=50)
	public java.lang.String getUpdateBy(){
		return this.updateBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  更新人登录名称
	 */
	public void setUpdateBy(java.lang.String updateBy){
		this.updateBy = updateBy;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  更新日期
	 */

	@Column(name ="UPDATE_DATE",nullable=true,length=20)
	public java.util.Date getUpdateDate(){
		return this.updateDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  更新日期
	 */
	public void setUpdateDate(java.util.Date updateDate){
		this.updateDate = updateDate;
	}
}