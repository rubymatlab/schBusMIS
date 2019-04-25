package com.jeecg.basstudent.entity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.jeewx.api.wxbase.wxtoken.JwTokenAPI;

import com.alibaba.fastjson.JSON;

public class ApiV3Sms {

	private static String host = "http://sms.budiot.com.cn:9999"; // 登录系统后开发者中心获取

	// 测试单位账号
	private static String appId = "9d49e9a24e064682a25cd940205e7c42";
	private static String appKey = "okokpsmb0ail8okpk8k4jihqur";
	
	public List<ApiV3SmsEntity> listSms=new ArrayList<ApiV3SmsEntity>();

	public void send(String mobiles) {
		try {
			//String mobiles = "177****0652"; // 测试手机号
			String content = "【上海瑞亚通信技术有限公司】您的微信账号正在绑定校车安全系统，验证码为${code}";
			int intCode=(int)((Math.random()*9+1)*100000);
			content=content.replace("${code}", String.valueOf(intCode));
			String spCode = "";
			
			Map<String, Object> params = new HashMap<>();
			params.put("appid", appId);
			params.put("appkey", appKey);
			params.put("content", content);
			params.put("mobile", mobiles);
			params.put("spCode", spCode);
			long ts=System.currentTimeMillis();
			params.put("timestamp", String.valueOf(ts/1000)); // 注意正式环境系统时间需要保证同步
			params.put("nonce", UUID.randomUUID().toString());
			params.put("sign",createSign(appKey, params));
			//System.out.printf("参数:\n%s\n", JSON.toJSONString(params));

			System.out.printf("手机:"+mobiles+"当前验证码是："+ String.valueOf(intCode));
			
			//超时300s则删除
			Date times=new Date();
			int size = listSms.size();
			for(int i=size-1;i>=0;i--){
				ApiV3SmsEntity ave=listSms.get(i);
				long diffTs=times.getTime()-ave.getTs();
				if(diffTs>ave.getExpiresIn()*1000)
					listSms.remove(i);
			}
			
			//添加手机号、验证码、时间、超时时间
			ApiV3SmsEntity o=new ApiV3SmsEntity();
			o.setCode(String.valueOf(intCode));
			o.setTs(ts);
			//5分钟内有效
			o.setExpiresIn(300);
			o.setMobiles(mobiles);
			listSms.add(o);
			
			
			PrintWriter out = null;
			BufferedReader in = null;
			String result = new String();
			try {
				URL realUrl = new URL(host + "/api/v3/sms/send");
				URLConnection conn = realUrl.openConnection();
				conn.setRequestProperty("accept", "*/*");
				conn.setRequestProperty("connection", "Keep-Alive");
				conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
				conn.setDoOutput(true); // 发送POST请求必须设置如下两行
				conn.setDoInput(true);
				out = new PrintWriter(conn.getOutputStream()); // 获取URLConnection对象对应的输出流
				out.print(getUrlParamsByMap(params)); // 发送请求参数
				out.flush(); // flush输出流的缓冲
				in = new BufferedReader(new InputStreamReader(conn.getInputStream())); // 定义BufferedReader输入流来读取URL的响应
				String line;
				while ((line = in.readLine()) != null) {
					result += line;
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				// 使用finally块来关闭输出流、输入流
				try {
					if (out != null) {
						out.close();
					}
					if (in != null) {
						in.close();
					}
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
			System.out.println(String.format("result:%s", result));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
	}

	/*
	 * @Author:
	 * 
	 * @Date:
	 * 
	 * @Description: 生成sign
	 * 
	 * @param appkey
	 * 
	 * @param params
	 * 
	 * @return:
	 * 
	 * @throws
	 */
	String createSign(String appkey, Map<String, Object> params) {
		Map<String, Object> map = sortMapByKey(params);
		Iterator<String> it = map.keySet().iterator();
		StringBuffer sb = new StringBuffer();
		while (it.hasNext()) {
			String k = it.next();
			String v = (String) map.get(k);
			if (null != v && !"".equals(v) && !"sign".equals(k)) {
				sb.append(k).append("=").append(v).append("&");
			}
		}
		sb.append("appkey=").append(appkey);
		String sign = MD5Encode(sb.toString(), "UTF-8");
		return sign;
	}

	/*
	 * @Author:
	 * 
	 * @Date:
	 * 
	 * @Description: 参数排序
	 * 
	 * @param map
	 * 
	 * @return:
	 * 
	 * @throws
	 */
	Map<String, Object> sortMapByKey(Map<String, Object> map) {
		Map<String, Object> sortMap = new TreeMap<>(new MapKeyComparator());
		if (map == null || map.isEmpty()) {
			return sortMap;
		}
		sortMap.putAll(map);
		return sortMap;
	}

	class MapKeyComparator implements Comparator<String> {
		@Override
		public int compare(String str1, String str2) {
			return str1.compareTo(str2);
		}
	}

	/*
	 * @Author:
	 * 
	 * @Date: 2019/1/5 16:25
	 * 
	 * @Description: MD5
	 * 
	 * @param origin
	 * 
	 * @param charsetname
	 * 
	 * @return:
	 * 
	 * @throws
	 */
	public static String MD5Encode(String origin, String charsetname) {
		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			if (null == charsetname || "".equals(charsetname)) {
				resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
			} else {
				resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetname)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// System.out.println("加密结果：" + resultString);
		return resultString;
	}

	public static String byteArrayToHexString(byte b[]) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	private static final String hexDigIts[] = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
			"e", "f" };

	public static String byteToHexString(byte b) {
		int n = b;
		if (n < 0) {
			n += 256;
		}
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigIts[d1] + hexDigIts[d2];
	}



	public String getUrlParamsByMap(Map<String, Object> map) {
		if (map == null) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			try {
				sb.append(entry.getKey()).append("=").append(URLEncoder.encode(String.valueOf(entry.getValue()), "UTF-8"))
						.append("&");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String s = sb.toString();
		if (s.endsWith("&")) {
			s = StringUtils.substringBeforeLast(s, "&");
		}
		return s;
	}
}
