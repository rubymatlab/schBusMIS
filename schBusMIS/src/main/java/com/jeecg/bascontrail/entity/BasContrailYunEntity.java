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
 * @Description: 云在线通道
 * @author onlineGenerator
 * @date 2019-07-21 13:02:23
 * @version V1.0   
 *
 */
@Entity
@Table(name = "bas_contrail_yun", schema = "")
@SuppressWarnings("serial")
public class BasContrailYunEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**云在线门禁key值*/
	@Excel(name="云在线门禁key值",width=15)
	private java.lang.String bcyKey;
	/**云在线门禁名称*/
	@Excel(name="云在线门禁名称",width=15)
	private java.lang.String bcyDesc;
	/**云在线门禁value值*/
	@Excel(name="云在线门禁value值",width=15)
	private java.lang.String bcyValue;
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
	 *@return: java.lang.String  云在线门禁key值
	 */

	@Column(name ="BCY_KEY",nullable=true,length=32)
	public java.lang.String getBcyKey(){
		return this.bcyKey;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  云在线门禁key值
	 */
	public void setBcyKey(java.lang.String bcyKey){
		this.bcyKey = bcyKey;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  云在线门禁名称
	 */

	@Column(name ="BCY_DESC",nullable=true,length=32)
	public java.lang.String getBcyDesc(){
		return this.bcyDesc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  云在线门禁名称
	 */
	public void setBcyDesc(java.lang.String bcyDesc){
		this.bcyDesc = bcyDesc;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  云在线门禁value值
	 */

	@Column(name ="BCY_VALUE",nullable=true,length=100)
	public java.lang.String getBcyValue(){
		return this.bcyValue;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  云在线门禁value值
	 */
	public void setBcyValue(java.lang.String bcyValue){
		this.bcyValue = bcyValue;
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
