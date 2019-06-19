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
 * @Description: 学生资料
 * @author onlineGenerator
 * @date 2019-03-17 21:32:12
 * @version V1.0   
 *
 */
@Entity
@Table(name = "bas_student", schema = "")
@SuppressWarnings("serial")
public class BasStudentInfoEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**学生姓名*/
	@Excel(name="学生姓名",width=15)
	private java.lang.String bsName;
	/**性别*/
	@Excel(name="性别",width=15,dicCode="sex")
	private java.lang.String bsSex;
	/**家长*/
	@Excel(name="家长",width=15)
	private java.lang.String bcParent;
	/**家长手机*/
	@Excel(name="家长手机",width=15)
	private java.lang.String bsTel;
	/**班级ID*/
	/*@Excel(name="班级ID",width=15)*/
	private java.lang.String bcId;
	/**年级*/
	@Excel(name="年级",width=15,importConvert=true)
	private java.lang.String bcGrade;
	/**
	 * 转换值示例： 替换掉.0
	 * @return
	 */
	public void convertsetBcGrade(String bcGrade){
		this.bcGrade = bcGrade.replace(".0", "");
	}
	
	/**班级*/
	@Excel(name="班级",width=15)
	private java.lang.String bcName;
	/**卡号*/
	@Excel(name="卡号",width=15)
	private java.lang.String bsCardno;
	/**加密卡号*/
	@Excel(name="加密卡号",width=15)
	private java.lang.String bsImei;
	/**设备ID*/
	@Excel(name="设备ID",width=15)
	private java.lang.String bsDeviceid;
	/**上学站点ID*/
	/*@Excel(name="上学站点ID",width=15)*/
	private java.lang.String blSizeid;
	/**上学线路*/
	@Excel(name="上学线路",width=15)
	private java.lang.String blName;
	/**上学站点*/
	@Excel(name="上学站点",width=15)
	private java.lang.String blSize;
	/**放学站点ID*/
	/*@Excel(name="放学站点ID",width=15)*/
	private java.lang.String blSizeid1;
	/**放学路线*/
	@Excel(name="放学路线",width=15)
	private java.lang.String blName1;
	/**放学站点*/
	@Excel(name="放学站点",width=15)
	private java.lang.String blSize1;
	/**地址*/
	@Excel(name="地址",width=15)
	private java.lang.String bsAddress;
	/**备注*/
	@Excel(name="备注",width=15)
	private java.lang.String bsDesc;
	/**是否有效*/
	@Excel(name="是否有效",width=15,dicCode="sf_yn")
	private java.lang.String bsStatus;
	/**监控名称*/
	private java.lang.String bfName;
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
	 *@return: java.lang.String  学生姓名
	 */

	@Column(name ="BS_NAME",nullable=true,length=50)
	public java.lang.String getBsName(){
		return this.bsName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  学生姓名
	 */
	public void setBsName(java.lang.String bsName){
		this.bsName = bsName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  性别
	 */

	@Column(name ="BS_SEX",nullable=true,length=10)
	public java.lang.String getBsSex(){
		return this.bsSex;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  性别
	 */
	public void setBsSex(java.lang.String bsSex){
		this.bsSex = bsSex;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  家长
	 */

	@Column(name ="BC_PARENT",nullable=true,length=50)
	public java.lang.String getBcParent(){
		return this.bcParent;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  家长
	 */
	public void setBcParent(java.lang.String bcParent){
		this.bcParent = bcParent;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  家长手机
	 */

	@Column(name ="BS_TEL",nullable=true,length=32)
	public java.lang.String getBsTel(){
		return this.bsTel;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  家长手机
	 */
	public void setBsTel(java.lang.String bsTel){
		this.bsTel = bsTel;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  班级ID
	 */

	@Column(name ="BC_ID",nullable=true,length=36)
	public java.lang.String getBcId(){
		return this.bcId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  班级ID
	 */
	public void setBcId(java.lang.String bcId){
		this.bcId = bcId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  年级
	 */

	@Column(name ="BC_GRADE",nullable=true,length=4)
	public java.lang.String getBcGrade(){
		return this.bcGrade;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  年级
	 */
	public void setBcGrade(java.lang.String bcGrade){
		this.bcGrade = bcGrade;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  班级
	 */

	@Column(name ="BC_NAME",nullable=true,length=50)
	public java.lang.String getBcName(){
		return this.bcName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  班级
	 */
	public void setBcName(java.lang.String bcName){
		this.bcName = bcName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  卡号
	 */

	@Column(name ="BS_CARDNO",nullable=true,length=24)
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
	 *@return: java.lang.String  加密卡号
	 */

	@Column(name ="BS_IMEI",nullable=true,length=32)
	public java.lang.String getBsImei(){
		return this.bsImei;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  加密卡号
	 */
	public void setBsImei(java.lang.String bsImei){
		this.bsImei = bsImei;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  设备ID
	 */

	@Column(name ="BS_DEVICEID",nullable=true,length=50)
	public java.lang.String getBsDeviceid(){
		return this.bsDeviceid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  设备ID
	 */
	public void setBsDeviceid(java.lang.String bsDeviceid){
		this.bsDeviceid = bsDeviceid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  上学站点ID
	 */

	@Column(name ="BL_SIZEID",nullable=true,length=36)
	public java.lang.String getBlSizeid(){
		return this.blSizeid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  上学站点ID
	 */
	public void setBlSizeid(java.lang.String blSizeid){
		this.blSizeid = blSizeid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  上学线路
	 */

	@Column(name ="BL_NAME",nullable=true,length=32)
	public java.lang.String getBlName(){
		return this.blName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  上学线路
	 */
	public void setBlName(java.lang.String blName){
		this.blName = blName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  上学站点
	 */

	@Column(name ="BL_SIZE",nullable=true,length=32)
	public java.lang.String getBlSize(){
		return this.blSize;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  上学站点
	 */
	public void setBlSize(java.lang.String blSize){
		this.blSize = blSize;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  放学站点ID
	 */

	@Column(name ="BL_SIZEID1",nullable=true,length=36)
	public java.lang.String getBlSizeid1(){
		return this.blSizeid1;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  放学站点ID
	 */
	public void setBlSizeid1(java.lang.String blSizeid1){
		this.blSizeid1 = blSizeid1;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  放学路线
	 */

	@Column(name ="BL_NAME1",nullable=true,length=32)
	public java.lang.String getBlName1(){
		return this.blName1;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  放学路线
	 */
	public void setBlName1(java.lang.String blName1){
		this.blName1 = blName1;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  放学站点
	 */

	@Column(name ="BL_SIZE1",nullable=true,length=32)
	public java.lang.String getBlSize1(){
		return this.blSize1;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  放学站点
	 */
	public void setBlSize1(java.lang.String blSize1){
		this.blSize1 = blSize1;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  地址
	 */

	@Column(name ="BS_ADDRESS",nullable=true,length=100)
	public java.lang.String getBsAddress(){
		return this.bsAddress;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  地址
	 */
	public void setBsAddress(java.lang.String bsAddress){
		this.bsAddress = bsAddress;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */

	@Column(name ="BS_DESC",nullable=true,length=50)
	public java.lang.String getBsDesc(){
		return this.bsDesc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备注
	 */
	public void setBsDesc(java.lang.String bsDesc){
		this.bsDesc = bsDesc;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  是否有效
	 */

	@Column(name ="BS_STATUS",nullable=true,length=32)
	public java.lang.String getBsStatus(){
		return this.bsStatus;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否有效
	 */
	public void setBsStatus(java.lang.String bsStatus){
		this.bsStatus = bsStatus;
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
