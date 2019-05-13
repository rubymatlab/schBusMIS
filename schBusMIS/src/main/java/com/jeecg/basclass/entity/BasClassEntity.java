package com.jeecg.basclass.entity;

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
 * @Description: 班级资料
 * @author onlineGenerator
 * @date 2019-03-16 22:51:23
 * @version V1.0   
 *
 */
@Entity
@Table(name = "bas_class", schema = "")
@SuppressWarnings("serial")
public class BasClassEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;

	@Excel(name="年级",width=15,importConvert=true)
	private java.lang.String bcGrade;
	
	/**年级
	@Excel(name="年级",width=15,importConvert=true)
	private java.lang.String bcGrade1;*/
	/**
	 * 转换值示例： 替换掉.0
	 * @return
	 */
	public void convertsetBcGrade(String bcGrade){
		this.bcGrade = bcGrade.replace(".0", "");
	}
	/**班级名称*/
	@Excel(name="班级名称",width=15)
	private java.lang.String bcName;
	/**班主任ID*/
	/**@Excel(name="班主任ID",width=15)*/
	private java.lang.String bcPersonid;
	/**班主任*/
	@Excel(name="班主任",width=15)
	private java.lang.String bcPerson;
	/**描述*/
	@Excel(name="描述",width=15)
	private java.lang.String bcDesc;
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
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  年级
	 */

	@Column(name ="BC_GRADE",nullable=true,length=4)
	public java.lang.String getBcGrade(){
		return this.bcGrade;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  年级
	 */
	public void setBcGrade(java.lang.String bcGrade){
		this.bcGrade = bcGrade;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  班级名称
	 */

	@Column(name ="BC_NAME",nullable=true,length=32)
	public java.lang.String getBcName(){
		return this.bcName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  班级名称
	 */
	public void setBcName(java.lang.String bcName){
		this.bcName = bcName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  班主任ID
	 */

	@Column(name ="BC_PERSONID",nullable=true,length=36)
	public java.lang.String getBcPersonid(){
		return this.bcPersonid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  班主任ID
	 */
	public void setBcPersonid(java.lang.String bcPersonid){
		this.bcPersonid = bcPersonid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  班主任
	 */

	@Column(name ="BC_PERSON",nullable=true,length=32)
	public java.lang.String getBcPerson(){
		return this.bcPerson;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  班主任
	 */
	public void setBcPerson(java.lang.String bcPerson){
		this.bcPerson = bcPerson;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  描述
	 */

	@Column(name ="BC_DESC",nullable=true,length=100)
	public java.lang.String getBcDesc(){
		return this.bcDesc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  描述
	 */
	public void setBcDesc(java.lang.String bcDesc){
		this.bcDesc = bcDesc;
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
