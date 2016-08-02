package net.devstudy.resume.util;

import javax.servlet.http.HttpServletRequest;

public class RequestDataUtil {
	
	public static String getURI(HttpServletRequest request) {
		return request.getRequestURI();
	}
	
	public static String getMethod(HttpServletRequest request) {
		return request.getMethod();
	}
	
	public static String getAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}