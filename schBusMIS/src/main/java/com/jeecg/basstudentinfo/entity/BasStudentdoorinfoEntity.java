package com.jeecg.basstudentinfo.entity;

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
 * @Description: 学生门禁信息
 * @author onlineGenerator
 * @date 2019-08-27 16:17:51
 * @version V1.0   
 *
 */
@Entity
@Table(name = "bas_studentdoorinfo", schema = "")
@SuppressWarnings("serial")
public class BasStudentdoorinfoEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**卡编号*/
	@Excel(name="卡编号",width=15)
	private java.lang.String bsSeq;
	/**卡号*/
	@Excel(name="卡号",width=15)
	private java.lang.String bsCardno;
	/**门设备号*/
	@Excel(name="门设备号",width=15,dictTable ="vw_basmacno",dicCode ="macno",dicText ="macno")
	private java.lang.String bsMacno;
	/**状态*/
	private java.lang.Integer bsState;
	/**创建日期*/
	@Excel(name="创建日期",width=15,format = "yyyy-MM-dd")
	private java.util.Date createDate;
	
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
	 *@return: java.lang.String  卡编号
	 */

	@Column(name ="BS_SEQ",nullable=true,length=15)
	public java.lang.String getBsSeq(){
		return this.bsSeq;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  卡编号
	 */
	public void setBsSeq(java.lang.String bsSeq){
		this.bsSeq = bsSeq;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  卡号
	 */

	@Column(name ="BS_CARDNO",nullable=true,length=30)
	public java.lang.String getBsCardno(){
		return this.bsCardno;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  卡号
	 */
	public void setBsCardno(java.lang.String bsCardno){
		this.bsCardno = bsCardno;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  门设备号
	 */

	@Column(name ="BS_MACNO",nullable=true,length=30)
	public java.lang.String getBsMacno(){
		return this.bsMacno;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  门设备号
	 */
	public void setBsMacno(java.lang.String bsMacno){
		this.bsMacno = bsMacno;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  状态
	 */

	@Column(name ="BS_STATE",nullable=true,length=1)
	public java.lang.Integer getBsState(){
		return this.bsState;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  状态
	 */
	public void setBsState(java.lang.Integer bsState){
		this.bsState = bsState;
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
}
