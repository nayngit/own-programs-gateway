package com.own.core.utils;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 类 名: IpAddressUtil<br/>
 * 描 述: IP地址工具类<br/>
 * 作 者: 展望<br/>
 * 创 建： 2013-6-26<br/>
 *
 * 历 史: (版本) 作者 时间 注释
 */
public class IpAddressUtil {

	/**
	 * 日志输出
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(IpAddressUtil.class);

	/**
	 * 描 述：获取IP地址列表<br/>
	 * 作 者：郭昕<br/>
	 * 历 史: (版本) 作者 时间 注释 <br/>
	 * 
	 * @return IP地址列表
	 */
	public static List<String> getIpAddressList() {
		List<String> iplist = new ArrayList<String>();
		try {
			Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
			while (netInterfaces.hasMoreElements()) {
				NetworkInterface ni = netInterfaces.nextElement();
				Enumeration<InetAddress> ips = ni.getInetAddresses();
				while (ips.hasMoreElements()) {
					InetAddress ip = ips.nextElement();
					if (ip instanceof Inet4Address && !ip.isLoopbackAddress()) {
						iplist.add(ip.getHostAddress());
					}
				}
			}
		} catch (SocketException e) {
			LOGGER.warn(e.getMessage(), e);
		}
		return iplist;
	}

	/**
	 * 描 述：从请求中获取ip地址<br/>
	 * 作 者：郭昕<br/>
	 * 历 史: (版本) 作者 时间 注释 <br/>
	 * 
	 * @param request
	 *            http请求
	 * @return ip地址
	 */
	public static String getIpAddr(final HttpServletRequest request) {
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

	
	public static boolean isInRange(String ip, String cidr) {
		String[] ips = ip.split("\\.");
		int ipAddr = (Integer.parseInt(ips[0]) << 24) | (Integer.parseInt(ips[1]) << 16)
				| (Integer.parseInt(ips[2]) << 8) | Integer.parseInt(ips[3]);
		int type = Integer.parseInt(cidr.replaceAll(".*/", ""));
		int mask = 0xFFFFFFFF << (32 - type);
		String cidrIp = cidr.replaceAll("/.*", "");
		String[] cidrIps = cidrIp.split("\\.");
		int cidrIpAddr = (Integer.parseInt(cidrIps[0]) << 24) | (Integer.parseInt(cidrIps[1]) << 16)
				| (Integer.parseInt(cidrIps[2]) << 8) | Integer.parseInt(cidrIps[3]);

		return (ipAddr & mask) == (cidrIpAddr & mask);
	}
}
