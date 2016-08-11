/**
 * CopyRight 2016 必拓电子商务有限公司
 */
package com.xinyipay.cmi.core.utils;

import java.net.URLEncoder;
import java.util.Map;

import com.xinyipay.cmi.core.support.CommConfig;
import com.xinyipay.cmi.core.support.CommEnum;

/**
 * 封装签名工具类
 * 
 * @author wupeng<wupengg@enn.com>
 */
public class PPSingUtils {
	public static String getSign(Map<String, String> param, CommEnum channel)
			throws Exception {
		String sign = null;
		String params = null;

		switch (channel) {
		case CUP:
			// 签名参数
			params = BodyUtils.createLinkString(BodyUtils.paraFilter(param));
			// 对签名串使用SHA-1算法做摘要，并转成16进制
			sign = SHA1Utils.sign(params, CommConfig.UTF8);
			// 使用银联颁发给商户的商户RSA私钥证书对摘要做签名操作（签名时算法选择SHA-1）,对签名做Base64编码
			sign = Base64Utils.encode(SHA1Utils.signBySoft(KeyStoreUtils
					.getSignCertPrivateKeyByStoreMap(
							CommConfig.CUP_SIGNCERT_PATH,
							CommConfig.CUP_SIGNCERT_PWD), sign.getBytes()));
			break;
		case ZFB:
			// 签名参数
			params = BodyUtils.createLinkString(BodyUtils.paraFilter(param));
			sign = RSAUtils.sign(params, CommConfig.ZFB_RSA_MCH_PRIVATE_KEY,
					CommConfig.UTF8);
			sign = URLEncoder.encode(sign, CommConfig.UTF8);
			break;
		case WX:
			// 签名参数
			params = BodyUtils.createLinkString(BodyUtils.paraFilter(param));
			params = BodyUtils.addKey(params, CommConfig.WX_KEY, CommEnum.WX);
			sign = MD5Utils.sign(params, CommConfig.UTF8);
			break;
		default:
			break;
		}

		return sign;
	}
}
