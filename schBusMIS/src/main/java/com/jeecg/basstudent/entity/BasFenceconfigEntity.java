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
 * @Description: 围栏监控维护
 * @author onlineGenerator
 * @date 2019-06-19 07:22:27
 * @version V1.0   
 *
 */
@Entity
@Table(name = "bas_fenceconfig", schema = "")
@SuppressWarnings("serial")
public class BasFenceconfigEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**监控类型*/
	@Excel(name="监控类型",width=15,dicCode="bf_status")
	private java.lang.String bfType;
	/**监控名称*/
	@Excel(name="监控名称",width=15)
	private java.lang.String bfName;
	/**监控开始时间*/
	@Excel(name="监控开始时间",width=15,format = "yyyy-MM-dd")
	private java.util.Date bfBegin;
	/**监控结束时间*/
	@Excel(name="监控结束时间",width=15,format = "yyyy-MM-dd")
	private java.util.Date bfEnd;
	/**处理监控学生*/
	@Excel(name="处理监控学生",width=15)
	private java.lang.String bfStudent;
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
	 *@return: java.lang.String  监控类型
	 */

	@Column(name ="BF_TYPE",nullable=true,length=32)
	public java.lang.String getBfType(){
		return this.bfType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  监控类型
	 */
	public void setBfType(java.lang.String bfType){
		this.bfType = bfType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  监控名称
	 */

	@Column(name ="BF_NAME",nullable=true,length=50)
	public java.lang.String getBfName(){
		return this.bfName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  监控名称
	 */
	public void setBfName(java.lang.String bfName){
		this.bfName = bfName;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  监控开始时间
	 */

	@Column(name ="BF_BEGIN",nullable=true,length=32)
	public java.util.Date getBfBegin(){
		return this.bfBegin;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  监控开始时间
	 */
	public void setBfBegin(java.util.Date bfBegin){
		this.bfBegin = bfBegin;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  监控结束时间
	 */

	@Column(name ="BF_END",nullable=true,length=32)
	public java.util.Date getBfEnd(){
		return this.bfEnd;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  监控结束时间
	 */
	public void setBfEnd(java.util.Date bfEnd){
		this.bfEnd = bfEnd;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  处理监控学生
	 */

	@Column(name ="BF_STUDENT",nullable=true,length=3000)
	public java.lang.String getBfStudent(){
		return this.bfStudent;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  处理监控学生
	 */
	public void setBfStudent(java.lang.String bfStudent){
		this.bfStudent = bfStudent;
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
