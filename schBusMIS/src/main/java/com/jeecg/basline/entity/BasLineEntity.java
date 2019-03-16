package com.jeecg.basline.entity;
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
 * @Description: 线路资料
 * @author onlineGenerator
 * @date 2019-03-17 02:06:38
 * @version V1.0   
 *
 */
@Entity
@Table(name = "bas_line", schema = "")
@SuppressWarnings("serial")
public class BasLineEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**线路状态*/
	@Excel(name="线路状态",width=15,dicCode="bus_status")
	private java.lang.String lineStatus;
	/**线路名称*/
	@Excel(name="线路名称",width=15)
	private java.lang.String blName;
	/**线路描述*/
	@Excel(name="线路描述",width=15)
	private java.lang.String blDesc;
	/**司机编号*/
	@Excel(name="司机编号",width=15)
	private java.lang.String blDriverid;
	/**线路司机*/
	@Excel(name="线路司机",width=15)
	private java.lang.String blDriver;
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
	 *@return: java.lang.String  线路状态
	 */
	
	@Column(name ="LINE_STATUS",nullable=true,length=32)
	public java.lang.String getLineStatus(){
		return this.lineStatus;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  线路状态
	 */
	public void setLineStatus(java.lang.String lineStatus){
		this.lineStatus = lineStatus;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  线路名称
	 */
	
	@Column(name ="BL_NAME",nullable=true,length=50)
	public java.lang.String getBlName(){
		return this.blName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  线路名称
	 */
	public void setBlName(java.lang.String blName){
		this.blName = blName;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  线路描述
	 */
	
	@Column(name ="BL_DESC",nullable=true,length=100)
	public java.lang.String getBlDesc(){
		return this.blDesc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  线路描述
	 */
	public void setBlDesc(java.lang.String blDesc){
		this.blDesc = blDesc;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  司机编号
	 */
	
	@Column(name ="BL_DRIVERID",nullable=true,length=36)
	public java.lang.String getBlDriverid(){
		return this.blDriverid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  司机编号
	 */
	public void setBlDriverid(java.lang.String blDriverid){
		this.blDriverid = blDriverid;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  线路司机
	 */
	
	@Column(name ="BL_DRIVER",nullable=true,length=50)
	public java.lang.String getBlDriver(){
		return this.blDriver;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  线路司机
	 */
	public void setBlDriver(java.lang.String blDriver){
		this.blDriver = blDriver;
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
