/**
 * CopyRight 2016 必拓电子商务有限公司
 */
package com.xinyipay.cmi.core.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xinyipay.cmi.core.support.CommEnum;

/**
 * 报文体处理工具
 * 
 * @author wupeng<wupengg@enn.com>
 */
public class BodyUtils {

	private static Logger logger = LoggerFactory.getLogger(BodyUtils.class);

	/**
	 * 除去数组中的空值和签名参数
	 * 
	 * @param sArray
	 *            签名参数组
	 * @return 去掉空值与签名参数后的新签名参数组
	 */
	public static Map<String, String> paraFilter(Map<String, String> sArray) {

		Map<String, String> result = new HashMap<String, String>();

		if (sArray == null || sArray.size() <= 0) {
			return result;
		}

		for (String key : sArray.keySet()) {
			String value = sArray.get(key);
			if (value == null || value.equals("")
					|| key.equalsIgnoreCase("sign")
					|| key.equalsIgnoreCase("sign_type")) {
				continue;
			}
			result.put(key, value);
		}

		return result;
	}

	/**
	 * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
	 * 
	 * @param params
	 *            需要排序并参与字符拼接的参数组
	 * @return 拼接后字符串
	 */
	public static String createLinkString(Map<String, String> params) {

		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);

		String prestr = "";

		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = params.get(key);

			if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
				prestr = prestr + key + "=" + value;
			} else {
				prestr = prestr + key + "=" + value + "&";
			}
		}

		return prestr;
	}

	/**
	 * MD5拼接API密钥 （作者：wupeng<wupengg@enn.com>）
	 * 
	 * @param params
	 *            参数
	 * @param key
	 *            api密钥
	 * @return
	 */
	public static String addKey(String params, String key, CommEnum channel) {
		if (channel.equals(CommEnum.WX)) {
			return params = params + "&key=" + key;
		} else if (channel.equals(CommEnum.ZFB)) {
			return params = params + "&key=" + key;
		} else {
			return params;
		}

	}

	/**
	 * URL编码 （作者：wupeng<wupengg@enn.com>）
	 * 
	 * @param params
	 *            参数
	 * @param key
	 *            api密钥
	 * @return
	 */
	public static Map<String, String> urlEncoding(
			Map<String, String> requestParam, String coder) {
		for (Entry<String, String> en : requestParam.entrySet()) {
			try {
				requestParam.replace(en.getKey(),
						(null == en.getValue() || "".equals(en.getValue()) ? ""
								: URLEncoder.encode(en.getValue(), coder)));
			} catch (UnsupportedEncodingException e) {
				logger.error("{}", e);
				return null;
			}
		}
		return requestParam;
	}
}
