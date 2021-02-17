package org.zxd.utils;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;

import static org.apache.commons.lang3.StringUtils.EMPTY;

public class NetUtils {
	private static final String IP_PATTERN = "^(?:(?:1[0-9][0-9]\\.)|(?:2[0-4][0-9]\\.)|(?:25[0-5]\\.)|(?:[1-9][0-9]\\.)|(?:[0-9]\\.)){3}(?:(?:1[0-9][0-9])|(?:2[0-4][0-9])|(?:25[0-5])|(?:[1-9][0-9])|(?:[0-9]))$";
	private static Pattern pattern = Pattern.compile(IP_PATTERN);

	public static final int ip2Int(String ip) {
		if (Strings.isNullOrEmpty(ip) || !pattern.matcher(ip).find()) {
			throw new RuntimeException("" + ip);
		}

		int result = 0;
		for (String part : Splitter.on('.').splitToList(ip)) {
			result = (result << 8) | Integer.parseInt(part);
		}

		return result;
	}

	public static final String int2Ip(Integer target) {
		if (null == target) {
			return EMPTY;
		}

		StringBuilder sb = new StringBuilder();
		for (int i = 3; i >= 0; i--) {
			int ipa = (target >> (8 * i)) & (0xff);
			sb.append(ipa + ".");
		}
		sb.delete(sb.length() - 1, sb.length());
		return sb.toString();
	}

	public static final String getRemoteAddr(HttpServletRequest request) {
		String xip = request.getHeader("X-Real-IP");
		String xfor = request.getHeader("X-Forwarded-For");
		if (StringUtils.isNotEmpty(xfor) && !"unKnown".equalsIgnoreCase(xfor)) {
			int index = xfor.indexOf(",");
			if (index != -1) {
				return xfor.substring(0, index);
			} else {
				return xfor;
			}
		}
		xfor = xip;
		if (StringUtils.isBlank(xfor) || "unknown".equalsIgnoreCase(xfor)) {
			xfor = request.getHeader("Proxy-Client-IP");
		}
		if (StringUtils.isBlank(xfor) || "unknown".equalsIgnoreCase(xfor)) {
			xfor = request.getHeader("WL-Proxy-Client-IP");
		}
		if (StringUtils.isBlank(xfor) || "unknown".equalsIgnoreCase(xfor)) {
			xfor = request.getHeader("HTTP_CLIENT_IP");
		}
		if (StringUtils.isBlank(xfor) || "unknown".equalsIgnoreCase(xfor)) {
			xfor = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (StringUtils.isBlank(xfor) || "unknown".equalsIgnoreCase(xfor)) {
			xfor = request.getRemoteAddr();
		}

		if ("0:0:0:0:0:0:0:1".equals(xfor)) {
			xfor = "127.0.0.1";
		}

		return xfor;
	}

}
