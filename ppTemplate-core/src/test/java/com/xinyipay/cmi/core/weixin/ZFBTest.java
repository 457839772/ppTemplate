package com.xinyipay.cmi.core.weixin;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xinyipay.cmi.core.CommEngine;
import com.xinyipay.cmi.core.support.CommConfig;
import com.xinyipay.cmi.core.support.CommEnum;
import com.xinyipay.cmi.core.utils.PPSingUtils;

public class ZFBTest {
	private static Logger logger = LoggerFactory.getLogger(ZFBTest.class);

	/**
	 * 移动支付 （作者：wupeng<wupengg@enn.com>）
	 * 
	 * @throws Exception
	 */
	public static void unifiedorder() throws Exception {
		// 请求参数
		Map<String, String> mapParam = new HashMap<String, String>();
		mapParam.put("service", "\"" + CommConfig.ZFB_SERVICE_MOBILE_PAY + "\"");
		mapParam.put("partner", "\"" + CommConfig.ZFB_PARTNER + "\"");
		mapParam.put("_input_charset", "\"" + CommConfig.UTF8.toLowerCase()
				+ "\"");
		mapParam.put("notify_url", "\"http://notify.msp.hk/notify.htm\"");
		mapParam.put("out_trade_no", "\"08191454126177\"");
		mapParam.put("subject", "\"测试\"");
		mapParam.put("payment_type", "\"" + CommConfig.ZFB_PAYMENT_TYPE_GOODS
				+ "\"");
		mapParam.put("seller_id", "\"" + CommConfig.ZFB_SELLER_ID + "\"");
		mapParam.put("total_fee", "\"0.01\"");
		mapParam.put("body", "\"测试测试\"");

		// 签名参数
		mapParam.put("sign", PPSingUtils.getSign(mapParam, CommEnum.ZFB));
		mapParam.put("sign_type", "\"" + CommConfig.RSA + "\"");

		// 创建请求报文
		String req = CommEngine.createSendMesByTemplate(
				CommConfig.ZFB_UNIFIEDORDER_REQ, mapParam);
		logger.info("发给支付宝的请求报文为：" + req);
	}

	/**
	 * 支付结果通知 （作者：wupeng<wupengg@enn.com>）
	 * 
	 * @throws Exception
	 */
	public static void payNotify() throws Exception {

		String res = "discount=0.00&payment_type=1&subject=测试&trade_no=2013082244524842&buyer_email=dlwdgl@gmail.com&gmt_create=2013-08-22 14:45:23&notify_type=trade_status_sync&quantity=1&out_trade_no=082215222612710&seller_id=2088501624816263&notify_time=2013-08-22 14:45:24&body=测试测试&trade_status=TRADE_SUCCESS&is_total_fee_adjust=N&total_fee=1.00&gmt_payment=2013-08-22 14:45:24&seller_email=xxx@alipay.com&price=1.00&buyer_id=2088602315385429&notify_id=64ce1b6ab92d00ede0ee56ade98fdf2f4c&use_coupon=N&sign_type=RSA&sign=1glihU9DPWee+UJ82u3+mw3Bdnr9u01at0M/xJnPsGuHh+JA5bk3zbWaoWhU6GmLab3dIM4JNdktTcEUI9/FBGhgfLO39BKX/eBCFQ3bXAmIZn4l26fiwoO613BptT44GTEtnPiQ6+tnLsGlVSrFZaLB9FVhrGfipH2SWJcnwYs=";
		logger.info("支付宝返回的响应报文为：" + res);

		Map<String, String> retMap = CommEngine.transResponseBodyByTemplate(
				CommConfig.ZFB_NOTIFY_BACK, res);
		logger.info("解析支付宝返回的响应报文为：" + retMap.toString());
	}

	public static void main(String args[]) throws Exception {
		// 统一下单 unifiedorder();
		// 支付结果通知
		payNotify();
	}

}
