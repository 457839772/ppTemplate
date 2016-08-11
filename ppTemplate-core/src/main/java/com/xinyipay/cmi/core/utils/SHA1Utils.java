/**
 * CopyRight 2016 必拓电子商务有限公司
 */
package com.xinyipay.cmi.core.utils;

import java.io.UnsupportedEncodingException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SHA1工具类
 * 
 * @author wupeng<wupengg@enn.com>
 */
public class SHA1Utils {
	private static Logger logger = LoggerFactory.getLogger(SHA1Utils.class);

	/**
	 * 算法常量：SHA1withRSA
	 */
	private static final String BC_PROV_ALGORITHM_SHA1RSA = "SHA1withRSA";

	/**
	 * 签名字符串
	 * 
	 * @param text
	 *            需要签名的字符串
	 * @param key
	 *            密钥
	 * @param input_charset
	 *            编码格式
	 * @return 签名结果
	 */
	public static String sign(String text, String input_charset) {
		return DigestUtils.sha1Hex(getContentBytes(text, input_charset));
	}

	/**
	 * 签名字符串校验
	 * 
	 * @param text
	 *            需要签名的字符串
	 * @param sign
	 *            签名结果
	 * @param key
	 *            密钥
	 * @param input_charset
	 *            编码格式
	 * @return 签名结果
	 */
	public static boolean verify(String text, String sign, String input_charset) {
		String mysign = DigestUtils
				.sha1Hex(getContentBytes(text, input_charset));
		if (mysign.equals(sign)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @param content
	 * @param charset
	 * @return
	 * @throws SignatureException
	 * @throws UnsupportedEncodingException
	 */
	private static byte[] getContentBytes(String content, String charset) {
		if (charset == null || "".equals(charset)) {
			return content.getBytes();
		}
		try {
			return content.getBytes(charset);
		} catch (UnsupportedEncodingException e) {
			logger.error("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:{},异常详情为:{}",
					charset, e);
			throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:"
					+ charset);
		}
	}

	/**
	 * 软签名
	 * 
	 * @param privateKey
	 *            私钥
	 * @param data
	 *            待签名数据
	 * @param signMethod
	 *            签名方法
	 * @return 结果
	 * @throws Exception
	 */
	public static byte[] signBySoft(PrivateKey privateKey, byte[] data)
			throws Exception {
		byte[] result = null;
		Signature st = Signature.getInstance(BC_PROV_ALGORITHM_SHA1RSA, "BC");
		st.initSign(privateKey);
		st.update(data);
		result = st.sign();
		return result;
	}

	/**
	 * 软验证签名
	 * 
	 * @param publicKey
	 *            公钥
	 * @param signData
	 *            签名数据
	 * @param srcData
	 *            摘要
	 * @param validateMethod
	 *            签名方法.
	 * @return
	 * @throws Exception
	 */
	public static boolean validateSignBySoft(PublicKey publicKey,
			byte[] signData, byte[] srcData) throws Exception {
		Signature st = Signature.getInstance(BC_PROV_ALGORITHM_SHA1RSA, "BC");
		st.initVerify(publicKey);
		st.update(srcData);
		return st.verify(signData);
	}

}
