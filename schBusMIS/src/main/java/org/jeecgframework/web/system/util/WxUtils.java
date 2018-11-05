package org.jeecgframework.web.system.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class WxUtils {
	public static Map parseXml(HttpServletRequest request) throws Exception {
		// 将解析结果存储在HashMap中
		Map map = new HashMap();
		// 从request中取得输入流
		InputStream inputStream = request.getInputStream();
		/*
		 * 读取request的body内容 此方法会导致流读取问题 Premature end of file. Nested exception:
		 * Premature end of file String requestBody =
		 * inputStream2String(inputStream); System.out.println(requestBody);
		 */
		// 读取输入流
		SAXReader reader = new SAXReader();
		Document document = reader.read(inputStream);
		// 得到xml根元素
		Element root = document.getRootElement();
		// 得到根元素的所有子节点
		List<Element> elementList = root.elements();
		// 遍历所有子节点
		for (Element e : elementList)
			map.put(e.getName(), e.getText());
		// 释放资源
		inputStream.close();
		inputStream = null;
		return map;
	}

	private static String inputStream2String(InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int i = -1;
		while ((i = is.read()) != -1) {
			baos.write(i);
		}
		return baos.toString();
	}
}
