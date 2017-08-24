package com.wbteam.onesearch.app.utils;
/**
 *  
 * 
 * @autor:码农哥
 * @version:1.0
 * @created:2016-10-14  上午9:46:58
 * @contact:QQ-441293364 TEL-15105695563
 **/
public class AMapUtils {
	
	private static double x_pi = 3.14159265358979324 * 3000.0 / 180.0;

	public static double bd_decrypt_lng(double bd_lat, double bd_lon) {
		double x = bd_lon - 0.0065, y = bd_lat - 0.006;
		double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
		double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
		return z * Math.cos(theta);
	}

	public static double bd_decrypt_lat(double bd_lat, double bd_lon) {
		double x = bd_lon - 0.0065, y = bd_lat - 0.006;
		double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
		double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
		return z * Math.sin(theta);
	}
}
