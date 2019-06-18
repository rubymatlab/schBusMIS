package com.jeecg.basstudent.entity;

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
 * @Description: 远程电子围栏信息
 * @author onlineGenerator
 * @date 2019-06-18 21:56:33
 * @version V1.0   
 *
 */
@Entity
@Table(name = "bas_fenceremote", schema = "")
@SuppressWarnings("serial")
public class BasFenceremoteEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**设备ID*/
	@Excel(name="设备ID",width=15)
	private java.lang.String deviceid;
	/**围栏ID*/
	@Excel(name="围栏ID",width=15)
	private java.lang.String fenceid;
	/**围栏名称*/
	@Excel(name="围栏名称",width=15)
	private java.lang.String fencename;
	/**ID*/
	@Excel(name="ID",width=15)
	private java.lang.String id1;
	/**围栏图片*/
	@Excel(name="围栏图片",width=15)
	private java.lang.String img;
	/**纬度*/
	@Excel(name="纬度",width=15)
	private java.lang.String la;
	/**经度*/
	@Excel(name="经度",width=15)
	private java.lang.String lo;
	/**半径*/
	@Excel(name="半径",width=15)
	private java.lang.String r;
	/**开关*/
	@Excel(name="开关",width=15)
	private java.lang.String switchtag;
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
	 *@return: java.lang.String  设备ID
	 */

	@Column(name ="DEVICEID",nullable=true,length=50)
	public java.lang.String getDeviceid(){
		return this.deviceid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  设备ID
	 */
	public void setDeviceid(java.lang.String deviceid){
		this.deviceid = deviceid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  围栏ID
	 */

	@Column(name ="FENCEID",nullable=true,length=32)
	public java.lang.String getFenceid(){
		return this.fenceid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  围栏ID
	 */
	public void setFenceid(java.lang.String fenceid){
		this.fenceid = fenceid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  围栏名称
	 */

	@Column(name ="FENCENAME",nullable=true,length=32)
	public java.lang.String getFencename(){
		return this.fencename;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  围栏名称
	 */
	public void setFencename(java.lang.String fencename){
		this.fencename = fencename;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  ID
	 */

	@Column(name ="ID1",nullable=true,length=32)
	public java.lang.String getId1(){
		return this.id1;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  ID
	 */
	public void setId1(java.lang.String id1){
		this.id1 = id1;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  围栏图片
	 */

	@Column(name ="IMG",nullable=true,length=32)
	public java.lang.String getImg(){
		return this.img;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  围栏图片
	 */
	public void setImg(java.lang.String img){
		this.img = img;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  纬度
	 */

	@Column(name ="LA",nullable=true,length=32)
	public java.lang.String getLa(){
		return this.la;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  纬度
	 */
	public void setLa(java.lang.String la){
		this.la = la;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  经度
	 */

	@Column(name ="LO",nullable=true,length=32)
	public java.lang.String getLo(){
		return this.lo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  经度
	 */
	public void setLo(java.lang.String lo){
		this.lo = lo;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  半径
	 */

	@Column(name ="R",nullable=true,length=32)
	public java.lang.String getR(){
		return this.r;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  半径
	 */
	public void setR(java.lang.String r){
		this.r = r;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  开关
	 */

	@Column(name ="SWITCHTAG",nullable=true,length=32)
	public java.lang.String getSwitchtag(){
		return this.switchtag;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  开关
	 */
	public void setSwitchtag(java.lang.String switchtag){
		this.switchtag = switchtag;
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
