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
 * @Description: 配置表
 * @author onlineGenerator
 * @date 2019-04-17 15:54:55
 * @version V1.0   
 *
 */
@Entity
@Table(name = "bus_config", schema = "")
@SuppressWarnings("serial")
public class BusConfigEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**配置编码*/
	@Excel(name="配置编码",width=15)
	private java.lang.String cfCode;
	/**配置名称*/
	@Excel(name="配置名称",width=15)
	private java.lang.String cfName;
	/**配置描述*/
	@Excel(name="配置描述",width=15)
	private java.lang.String cfDesc;
	/**配置值*/
	@Excel(name="配置值",width=15)
	private java.lang.String cfValue;
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
	 *@return: java.lang.String  配置编码
	 */

	@Column(name ="CF_CODE",nullable=true,length=36)
	public java.lang.String getCfCode(){
		return this.cfCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  配置编码
	 */
	public void setCfCode(java.lang.String cfCode){
		this.cfCode = cfCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  配置名称
	 */

	@Column(name ="CF_NAME",nullable=true,length=100)
	public java.lang.String getCfName(){
		return this.cfName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  配置名称
	 */
	public void setCfName(java.lang.String cfName){
		this.cfName = cfName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  配置描述
	 */

	@Column(name ="CF_DESC",nullable=true,length=500)
	public java.lang.String getCfDesc(){
		return this.cfDesc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  配置描述
	 */
	public void setCfDesc(java.lang.String cfDesc){
		this.cfDesc = cfDesc;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  配置值
	 */

	@Column(name ="CF_VALUE",nullable=true,length=500)
	public java.lang.String getCfValue(){
		return this.cfValue;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  配置值
	 */
	public void setCfValue(java.lang.String cfValue){
		this.cfValue = cfValue;
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
