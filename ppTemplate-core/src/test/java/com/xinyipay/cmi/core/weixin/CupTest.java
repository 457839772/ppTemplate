package com.xinyipay.cmi.core.weixin;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xinyipay.cmi.core.CommEngine;
import com.xinyipay.cmi.core.support.CommConfig;
import com.xinyipay.cmi.core.support.CommEnum;
import com.xinyipay.cmi.core.utils.BodyUtils;
import com.xinyipay.cmi.core.utils.KeyStoreUtils;
import com.xinyipay.cmi.core.utils.PPSingUtils;

public class CupTest {
	private static Logger logger = LoggerFactory.getLogger(CupTest.class);

	/**
	 * 订单推送 （作者：wupeng<wupengg@enn.com>）
	 * 
	 * @throws Exception
	 */
	public static void unifiedorder() throws Exception {
		// 请求参数
		Map<String, String> mapParam = new HashMap<String, String>();
		mapParam.put("version", CommConfig.CUP_VERSION);
		mapParam.put("encoding", CommConfig.UTF8);
		mapParam.put("certId", KeyStoreUtils.getCertId(
				CommConfig.CUP_SIGNCERT_PATH, CommConfig.CUP_SIGNCERT_PWD));
		mapParam.put("signMethod", CommConfig.CUP_RSA);
		mapParam.put("txnType", CommConfig.CUP_TRADE_TYPE_CONSUME);
		mapParam.put("txnSubType", CommConfig.CUP_TRADE_TYPE_CONSUME);
		mapParam.put("bizType", CommConfig.CUP_BIZTYPE_B2CGATEPAY);
		mapParam.put("channelType", CommConfig.CUP_CHANNEL_MOBILE);
		mapParam.put("accessType", CommConfig.CUP_ACCESSTYPE_MCH);
		mapParam.put("merId", CommConfig.CUP_MERCID);
		mapParam.put("backUrl",
				"http://wxpay.weixin.qq.com/pub_v2/pay/notify.v2.php");
		mapParam.put("orderId", "12345asdf");
		mapParam.put("currencyCode", CommConfig.CUP_CNY);
		mapParam.put("txnAmt", "100");
		mapParam.put("txnTime", "20151118100505");
		mapParam.put("frontUrl",
				"http://wxpay.weixin.qq.com/pub_v2/pay/notify.v2.php");
		mapParam.put("payTimeout", "20171118100505");

		// 签名参数
		mapParam.put("signature", PPSingUtils.getSign(mapParam, CommEnum.CUP));
		
		// 进行URL编码
		BodyUtils.urlEncoding(mapParam, CommConfig.UTF8);

		// 创建请求报文
		String req = CommEngine.createSendMesByTemplate(
				CommConfig.CUP_APPTRANS_REQ, mapParam);
		logger.info("发给银联的请求报文为：" + req);
	}

	public static void main(String args[]) throws Exception {
		// 订单推送 unifiedorder();
	}

}
