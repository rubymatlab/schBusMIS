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
 * @Description: 站点明细
 * @author onlineGenerator
 * @date 2019-03-17 02:06:38
 * @version V1.0   
 *
 */
@Entity
@Table(name = "bas_size", schema = "")
@SuppressWarnings("serial")
public class BasSizeEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**排序*/
	@Excel(name="排序",width=15)
	private java.lang.Integer bsSeq;
	/**站点名称*/
	@Excel(name="站点名称",width=15)
	private java.lang.String bsName;
	/**站点描述*/
	@Excel(name="站点描述",width=15)
	private java.lang.String bsDesc;
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
	/**外键*/
	private java.lang.String fkBlId;
	/**起点或终点*/
	@Excel(name="起点或终点",width=15,dicCode="sz_status")
	private java.lang.String sizeStatus;
	
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
	 *@return: java.lang.Integer  排序
	 */
	
	@Column(name ="BS_SEQ",nullable=true,length=3)
	public java.lang.Integer getBsSeq(){
		return this.bsSeq;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  排序
	 */
	public void setBsSeq(java.lang.Integer bsSeq){
		this.bsSeq = bsSeq;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  站点名称
	 */
	
	@Column(name ="BS_NAME",nullable=true,length=50)
	public java.lang.String getBsName(){
		return this.bsName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  站点名称
	 */
	public void setBsName(java.lang.String bsName){
		this.bsName = bsName;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  站点描述
	 */
	
	@Column(name ="BS_DESC",nullable=true,length=100)
	public java.lang.String getBsDesc(){
		return this.bsDesc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  站点描述
	 */
	public void setBsDesc(java.lang.String bsDesc){
		this.bsDesc = bsDesc;
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
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  外键
	 */
	
	@Column(name ="FK_BL_ID",nullable=true,length=36)
	public java.lang.String getFkBlId(){
		return this.fkBlId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  外键
	 */
	public void setFkBlId(java.lang.String fkBlId){
		this.fkBlId = fkBlId;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  起点或终点
	 */
	
	@Column(name ="SIZE_STATUS",nullable=true,length=2)
	public java.lang.String getSizeStatus(){
		return this.sizeStatus;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  起点或终点
	 */
	public void setSizeStatus(java.lang.String sizeStatus){
		this.sizeStatus = sizeStatus;
	}
	
}
