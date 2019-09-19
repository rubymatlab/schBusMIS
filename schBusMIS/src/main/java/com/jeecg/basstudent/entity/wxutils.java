/**
 * 
 */
package com.jeecg.basstudent.entity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.jeewx.api.core.common.MyX509TrustManager;
import org.jeewx.api.core.exception.WexinReqException;
import org.jeewx.api.core.req.model.menu.MenuCreate;
import org.jeewx.api.core.req.model.menu.WeixinButton;
import org.jeewx.api.wxbase.wxtoken.JwTokenAPI;
import org.jeewx.api.wxmenu.JwMenuAPI;

import com.jeecg.basstudent.controller.basWXController;

import net.sf.json.JSONObject;


/**
 * @author 
 *
 */
public class wxutils {

	//配置信息
	public static  String basurl="http://www.infmore.com/ssby";

	public static  String storePath="C:/apache-tomcat-7.0.92-windows-x64/apache-tomcat-7.0.92/webapps/schBusMIS/photos/";

	//ssby
	public static  String appid = "wx5a9efacb2d950b33";
	public static  String appscret = "cc8649f517abfb78faa969ef8b9adca5";
	
	private static final String token = "JYWX";
	
	private static final String openidurl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appid+"&redirect_uri="+basurl+"/baswxcontroller.do?gopenid&response_type=code&scope=snsapi_base&state=1#wechat_redirect";

	private static final String qrybusurl = "http://www.infmore.com/xiaoce/index.html";
	private static final String leaerecsurl = basurl+"/page/leaverecs.html";
	//地图地址openid
	private static final String openidurlLocation = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appid+"&redirect_uri="+basurl+"/basStudentLocationController.do?list&response_type=code&scope=snsapi_base&state=1#wechat_redirect";
	
	
	//创建静态对象Token
	private static Token accessToken=new Token();
	
	//获取Acctonken
	public static String getAcctonken() throws WexinReqException {
		/*String strAcctonken="";
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		
		String[] sc=new String[2];
		Date expiresTime = new Date();
		Date curTime=new Date();
		basWXController bwx=new basWXController();
		sc=bwx.qryAcctooken();
		try {
			expiresTime=df.parse(sc[1]);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(expiresTime.getTime()>curTime.getTime()){
			strAcctonken=sc[0];			
		}else{
			strAcctonken=JwTokenAPI.getAccessToken(appid, appscret);
			bwx.updateAcctooken(strAcctonken);
		}
		System.out.println("getAcctonken:"+strAcctonken+";"+df.format(expiresTime));*/
		
		//判断时间是否超过1小时
		Date times=new Date();
		long diffTs=times.getTime()-accessToken.getTs();
		//System.out.println(diffTs);
		if(diffTs>3600*1000)
		{
			String strToken=JwTokenAPI.getAccessToken(appid, appscret);
			accessToken.setAccessToken(strToken);
			accessToken.setExpiresIn(7200);
			accessToken.setTs(times.getTime());
		}
		//System.out.println("当前token值为:"+accessToken.getAccessToken()+",时间差为："+diffTs);
		return accessToken.getAccessToken();
	}

	//获取菜单 
	public static List<WeixinButton> getMenu() throws WexinReqException {
		List<WeixinButton> lstButton = null;
		String acctoken = getAcctonken();
		lstButton = JwMenuAPI.getAllMenu(acctoken);
		return lstButton;

	}

	//创建菜单
	// @RequestMapping("/createMenu")
	public static String createMenu(){
		try {
			String acctoken = getAcctonken();
			System.out.println("acctoken-->" + acctoken);

			List<WeixinButton> button = new ArrayList<WeixinButton>();
			
			List<WeixinButton> sub_button1 = new ArrayList<WeixinButton>();
			WeixinButton o1_s1 = new WeixinButton();
			o1_s1.setType("view");
			o1_s1.setName("学校概况");
			o1_s1.setUrl("https://c.eqxiu.com/s/YUFNfawJ");
			sub_button1.add(o1_s1);
			WeixinButton o1_s2 = new WeixinButton();
			o1_s2.setType("view");
			o1_s2.setName("校园网站");
			o1_s2.setUrl("http://www.ssboya.cn");
			sub_button1.add(o1_s2);
			WeixinButton o1 = new WeixinButton();
			o1.setName("学校概况");
			o1.setSub_button(sub_button1);
			button.add(o1);
			//******************************//*
			
			List<WeixinButton> sub_button2 = new ArrayList<WeixinButton>();
			WeixinButton o2_s12 = new WeixinButton();
			o2_s12.setType("view");
			o2_s12.setName("学生登录");
			o2_s12.setUrl("https://boyastaff.schoolis.cn/wechat/stuindex/567");
			sub_button2.add(o2_s12);
			WeixinButton o2_s11 = new WeixinButton();
			o2_s11.setType("view");
			o2_s11.setName("老师登录");
			o2_s11.setUrl("https://boyastaff.schoolis.cn/wechat/index/567");
			sub_button2.add(o2_s11);
			WeixinButton o2 = new WeixinButton();
			o2.setName("校宝在线");
			o2.setSub_button(sub_button2);
			button.add(o2);
			//******************************//*
			
			List<WeixinButton> sub_button4 = new ArrayList<WeixinButton>();
			WeixinButton o2_s1 = new WeixinButton();
			o2_s1.setType("view");
			o2_s1.setName("学生位置");
			o2_s1.setUrl(openidurlLocation);
			sub_button4.add(o2_s1);			
			WeixinButton o2_s2 = new WeixinButton();
			o2_s2.setType("view");
			o2_s2.setName("车辆位置");
			o2_s2.setUrl(qrybusurl);
			sub_button4.add(o2_s2);			
			WeixinButton o2_s3 = new WeixinButton();
			o2_s3.setType("view");
			o2_s3.setName("不坐校车");
			o2_s3.setUrl(leaerecsurl);			
			sub_button4.add(o2_s3);			
			WeixinButton o4_s1 = new WeixinButton();
			o4_s1.setType("view");
			o4_s1.setName("个人中心");
			o4_s1.setUrl(openidurl);
			sub_button4.add(o4_s1);			
			WeixinButton o4 = new WeixinButton();
			o4.setName("智慧校园");
			o4.setSub_button(sub_button4);
			button.add(o4);
			//******************************//*
			
			MenuCreate m=new MenuCreate();
			m.setButton(button);
			String jsonMenu =JSONObject.fromObject(m).toString();
			//String message = JwMenuAPI.createMenu(acctoken, button);
			System.out.println("jsonMenu-->"+jsonMenu);
			int status = 0;
			try {
				String path = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + acctoken;
				URL url = new URL(path);
				HttpURLConnection http = (HttpURLConnection) url.openConnection();
				http.setDoOutput(true);
				http.setDoInput(true);
				http.setRequestMethod("POST");
				http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				http.connect();
				OutputStream os = http.getOutputStream();
				os.write(jsonMenu.getBytes("UTF-8"));
				os.close();

				InputStream is = http.getInputStream();
				int size = is.available();
				byte[] bt = new byte[size];
				is.read(bt);
				String message = new String(bt, "UTF-8");
				JSONObject jsonMsg = JSONObject.fromObject(message);
				status = Integer.parseInt(jsonMsg.getString("errcode"));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return "ok";
		} catch (WexinReqException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


	//通过页面OAuth授权,用code获取openid
	public static String OAuthGetOpenid(String code) {
	    JSONObject json = new JSONObject();
	    String requestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appid+"&secret="+appscret+"&code=" + code + "&grant_type=authorization_code";
	    String outputStr = "";
	    new HttpRequestPost();
	    json = HttpRequestPost.httpRequest(requestUrl, outputStr, "POST");
	    String openid = json.getString("openid");
	    return openid;
	}
	
	
    //和微信公众平台配置的token须保持一致
	public static boolean checkSignature(String signature, String timestamp, String nonce) {
		System.out.println(signature);
		String[] arr = new String[] { token, timestamp, nonce };
		// 字典排序
		Arrays.sort(arr);
		// 生成字符串
		StringBuffer content = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			content.append(arr[i]);
		}
		// sha1加密
		String temp = getSha1(content.toString());
		return temp.equals(signature);
	}

	private static String getSha1(String str) {
		if (str == null || str.length() == 0) {
			return null;
		}
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		MessageDigest mdTemp;
		try {
			mdTemp = MessageDigest.getInstance("SHA1");
			mdTemp.update(str.getBytes("UTF-8"));
			byte[] md = mdTemp.digest();
			int j = md.length;
			char buf[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte b0 = md[i];
				buf[k++] = hexDigits[b0 >>> 4 & 0xf];
				buf[k++] = hexDigits[b0 & 0xf];
			}
			return new String(buf);
		} catch (Exception e) {
			return null;
		}
	}

	
	  /**
	   * 发送https请求
	   * 
	   * @param requestUrl 请求地址
	   * @param requestMethod 请求方式（GET、POST）
	   * @param outputStr 提交的数据
	   * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	   */
	  public static JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) {
	    JSONObject jsonObject = null;
	    try {
	      // 创建SSLContext对象，并使用我们指定的信任管理器初始化
	      TrustManager[] tm = { new MyX509TrustManager() };
	      SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
	      sslContext.init(null, tm, new java.security.SecureRandom());
	      // 从上述SSLContext对象中得到SSLSocketFactory对象
	      SSLSocketFactory ssf = sslContext.getSocketFactory();
	      URL url = new URL(requestUrl);
	      HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
	      conn.setSSLSocketFactory(ssf);
	      conn.setDoOutput(true);
	      conn.setDoInput(true);
	      conn.setUseCaches(false);
	      // 设置请求方式（GET/POST）
	      conn.setRequestMethod(requestMethod);
	      // 当outputStr不为null时向输出流写数据
	      if (null != outputStr) {
	        OutputStream outputStream = conn.getOutputStream();
	        // 注意编码格式
	        outputStream.write(outputStr.getBytes("UTF-8"));
	        outputStream.close();
	      }
	      // 从输入流读取返回内容
	      InputStream inputStream = conn.getInputStream();
	      InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
	      BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
	      String str = null;
	      StringBuffer buffer = new StringBuffer();
	      while ((str = bufferedReader.readLine()) != null) {
	        buffer.append(str);
	      }
	      // 释放资源
	      bufferedReader.close();
	      inputStreamReader.close();
	      inputStream.close();
	      inputStream = null;
	      conn.disconnect();
	      jsonObject = JSONObject.fromObject(buffer.toString());
	    } catch (ConnectException ce) {
	    	System.out.println("连接超时：{}"+ce);
	    } catch (Exception e) {
	    	System.out.println("https请求异常：{}"+ e);
	    }
	    return jsonObject;
	  }
	
}
