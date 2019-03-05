package com.jeecg.basstudent.entity;


public class ConvertionUtils {

	public static double[] getClear(String la,String lo)
	{
		la=la.replace(".", "");
		lo=lo.replace(".", "");
		double[] sl = gps84_To_Gcj02(convertToDegree(trm(la)), convertToDegree(trm(lo)));// 坐标系转换成高德坐标系
		return sl;
	}

	/**
	 * 对经纬度进行分割处理
	 * 
	 * @param str
	 * @return
	 */
	public static String trm(String str) {
		if (str == null)
			return null;
		StringBuffer sb = new StringBuffer();
		int length = str.length();
		if (length < 7) {
			sb.append("0.");
			for (int i = 0; i < 6 - length; i++) {
				sb.append("0");
			}
			return sb.append(str).toString();
		} else {
			sb.append(str).insert(length - 6, ".");
			return sb.toString();
		}

	}

	// 转换成度分秒
	public static Double convertToDegree(String stringDMS) {
		Float result = null;

		String[] DMS = stringDMS.split("\\.");

		String stringD = DMS[0];
		Double D0 = new Double(stringD);

		String stringM = DMS[1].substring(0, 2);

		Double M0 = new Double(stringM);
		Double S0 = new Double(0 + "." + DMS[1].substring(2, 6));
		result = new Float(D0) + new Float((M0 + S0) / 60);
		return (double) result;
	}
	//

	public static double pi = 3.1415926535897932384626;
	public static double x_pi = 3.14159265358979324 * 3000.0 / 180.0;
	public static double a = 6378245.0;
	public static double ee = 0.00669342162296594323;

	/**
	 * 84 to 火星坐标系 (GCJ-02) World Geodetic System ==> Mars Geodetic System
	 *
	 * @param lat
	 * @param lon
	 * @return
	 */
	public static double[] gps84_To_Gcj02(double lat, double lon) {
		if (outOfChina(lat, lon)) {
			return new double[] { lat, lon };
		}
		double dLat = transformLat(lon - 105.0, lat - 35.0);
		double dLon = transformLon(lon - 105.0, lat - 35.0);
		double radLat = lat / 180.0 * pi;
		double magic = Math.sin(radLat);
		magic = 1 - ee * magic * magic;
		double sqrtMagic = Math.sqrt(magic);
		dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * pi);
		dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * pi);
		double mgLat = lat + dLat;
		double mgLon = lon + dLon;
		return new double[] { mgLat, mgLon };
	}

	public static boolean outOfChina(double lat, double lon) {
		if (lon < 72.004 || lon > 137.8347)
			return true;
		if (lat < 0.8293 || lat > 55.8271)
			return true;
		return false;
	}

	public static double transformLat(double x, double y) {
		double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(Math.abs(x));
		ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
		ret += (20.0 * Math.sin(y * pi) + 40.0 * Math.sin(y / 3.0 * pi)) * 2.0 / 3.0;
		ret += (160.0 * Math.sin(y / 12.0 * pi) + 320 * Math.sin(y * pi / 30.0)) * 2.0 / 3.0;
		return ret;
	}

	public static double transformLon(double x, double y) {
		double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * Math.sqrt(Math.abs(x));
		ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
		ret += (20.0 * Math.sin(x * pi) + 40.0 * Math.sin(x / 3.0 * pi)) * 2.0 / 3.0;
		ret += (150.0 * Math.sin(x / 12.0 * pi) + 300.0 * Math.sin(x / 30.0 * pi)) * 2.0 / 3.0;
		return ret;
	}
}
