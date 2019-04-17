package com.jeecg.basstudent.entity;

public class Distance {
    /** 地球半径，以千米(km)为计算单位 */  
    private final static double EARTH_RADIUS = 6378.137;  
  
    /** 求弧度大小 */  
    private static double rad(double d) {  
        return d * Math.PI / 180.0;  
    }  
  
    /** 
     * 根据地球上两点间的经纬度求距离 
     *  
     * @param lat1 
     * @param lng1 
     * @param lat2 
     * @param lng2 
     * @return 两点间的距离千米数(km) 
     */  
    public static double getDistance(double lng1, double lat1, double lng2,double lat2) {  
        double radLng1 = rad(lng1);  
        double radLng2 = rad(lng2);  
        double a = radLng1 - radLng2;  
        double b = rad(lat1) - rad(lat2);  
        double dis = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)+  
                Math.cos(radLng1) * Math.cos(radLng2)* Math.pow(Math.sin(b / 2), 2)));  
        dis = dis * EARTH_RADIUS;  
        dis = Math.round(dis * 10000) / 10000.0;//除数写成10000.0减小误差  
        return dis;  
    }  
  
    /** 
     * @param args 
     */  
    public static void main(String[] args) {  
        System.out.println("地球周长："+2*Math.PI*EARTH_RADIUS);  
        System.out.println("90度："+getDistance(120.1590310,29.4054650,120.1590310,29.4154650));  
    }  
}
