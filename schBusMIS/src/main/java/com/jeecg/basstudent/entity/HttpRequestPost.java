/**
 * 
 */
package com.jeecg.basstudent.entity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import net.sf.json.JSONObject;


/**
 * @author devzhu
 *
 */
public class HttpRequestPost {
    public static JSONObject httpRequest(String requestUrl, String outputStr, String method) {
        JSONObject jsonObject = null;
        StringBuffer buffer = new StringBuffer();
        try {
            // // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            // TrustManager[] tm = { new MyX509TrustManager() };
            // SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            // sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            // SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
            // httpUrlConn.setSSLSocketFactory(ssf);

            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod(method);
            httpUrlConn.connect();

            // 当有数据需要提交时
            if (null != outputStr) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                // 注意编码格式，防止中文乱码
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }
            // 将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();
            jsonObject = JSONObject.fromObject(buffer.toString());
            System.out.println("http-back:" + jsonObject);
        } catch (ConnectException ce) {
            System.err.println("Weixin server connection timed out.");
        } catch (Exception e) {
            System.err.println("https request error:{}" + e);
        }
        return jsonObject;
    }
}
