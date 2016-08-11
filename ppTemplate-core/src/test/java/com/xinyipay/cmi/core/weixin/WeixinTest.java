package com.xinyipay.cmi.core.weixin;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xinyipay.cmi.core.CommEngine;
import com.xinyipay.cmi.core.support.CommConfig;
import com.xinyipay.cmi.core.support.CommEnum;
import com.xinyipay.cmi.core.utils.BodyUtils;
import com.xinyipay.cmi.core.utils.HttpUtils;
import com.xinyipay.cmi.core.utils.MD5Utils;
import com.xinyipay.cmi.core.utils.PPSingUtils;

public class WeixinTest {
	private static Logger logger = LoggerFactory.getLogger(WeixinTest.class);

	/**
	 * 签名验签 （作者：wupeng<wupengg@enn.com>）
	 * 验证参考,微信公众平台支付接口调试工具（https://pay.weixin.qq.com/wiki/tools/signverify/）
	 * 
	 * @throws Exception
	 */
	public static void signAndVertify() throws Exception {
		// 请求参数
		Map<String, String> mapParam = new HashMap<String, String>();
		mapParam.put("appid", CommConfig.WX_APPID);
		mapParam.put("attach", "支付测试");
		mapParam.put("body", "APP支付测试");
		mapParam.put("mch_id", CommConfig.WX_MCHID);
		mapParam.put("nonce_str", "ec2316275641faa3aacf3cc599e8730f");
		mapParam.put("notify_url",
				"http://wxpay.weixin.qq.com/pub_v2/pay/notify.v2.php");
		mapParam.put("out_trade_no", "1415659990");
		mapParam.put("spbill_create_ip", "14.23.150.211");
		mapParam.put("total_fee", "1");
		mapParam.put("trade_type", "APP");

		// 签名参数
		String sign = PPSingUtils.getSign(mapParam, CommEnum.WX);
		mapParam.put("sign", sign);

		// 创建请求报文
		String req = CommEngine.createSendMesByTemplate(
				CommConfig.WX_UNIFIEDORDER_REQ, mapParam);
		logger.info("发给微信的请求报文为：" + req);

		// 验证签名
		String params = BodyUtils.createLinkString(BodyUtils
				.paraFilter(mapParam));
		params = BodyUtils.addKey(params, CommConfig.WX_KEY, CommEnum.WX);
		boolean isOK = MD5Utils.verify(params, sign, CommConfig.UTF8);
		logger.info("微信验签结果为：" + isOK);
	}

	/**
	 * 统一下单 （作者：wupeng<wupengg@enn.com>）
	 * 
	 * @throws Exception
	 */
	public static void unifiedorder() throws Exception {
		// 请求参数
		Map<String, String> mapParam = new HashMap<String, String>();
		mapParam.put("appid", CommConfig.WX_APPID);
		mapParam.put("attach", "支付测试");
		mapParam.put("body", "APP支付测试");
		mapParam.put("mch_id", CommConfig.WX_MCHID);
		mapParam.put("nonce_str", "1add1a30ac87aa2db72f57a2375d8fec");
		mapParam.put("notify_url",
				"http://wxpay.weixin.qq.com/pub_v2/pay/notify.v2.php");
		mapParam.put("out_trade_no", "1415659990");
		mapParam.put("spbill_create_ip", "14.23.150.211");
		mapParam.put("total_fee", "1");
		mapParam.put("trade_type", "APP");

		// 签名参数
		mapParam.put("sign", "0CB01533B8C1EF103065174F50BCA001");

		// 创建请求报文
		String req = CommEngine.createSendMesByTemplate(
				CommConfig.WX_UNIFIEDORDER_REQ, mapParam);
		logger.info("发给微信的请求报文为：" + req);

		// 发送请求报文
		String res = HttpUtils.post(CommConfig.WX_UNIFIEDORDER, req);
		logger.info("微信返回的响应报文为：" + res);

		// 解析请求返回结果
		Map<String, String> retMap = CommEngine.transResponseBodyByTemplate(
				CommConfig.WX_UNIFIEDORDER_BACK, res);
		logger.info("解析微信返回的响应报文为：" + retMap.toString());
	}

	/**
	 * 支付结果通知 （作者：wupeng<wupengg@enn.com>）
	 * 
	 * @throws Exception
	 */
	public static void payNotify() throws Exception {

		String res = "<xml><appid><![CDATA[wx2421b1c4370ec43b]]></appid><attach><![CDATA[支付测试]]></attach><bank_type><![CDATA[CFT]]></bank_type><fee_type><![CDATA[CNY]]></fee_type><is_subscribe><![CDATA[Y]]></is_subscribe><mch_id><![CDATA[10000100]]></mch_id><nonce_str><![CDATA[5d2b6c2a8db53831f7eda20af46e531c]]></nonce_str><openid><![CDATA[oUpF8uMEb4qRXf22hE3X68TekukE]]></openid><out_trade_no><![CDATA[1409811653]]></out_trade_no><result_code><![CDATA[SUCCESS]]></result_code><return_code><![CDATA[SUCCESS]]></return_code><sign><![CDATA[B552ED6B279343CB493C5DD0D78AB241]]></sign><sub_mch_id><![CDATA[10000100]]></sub_mch_id><time_end><![CDATA[20140903131540]]></time_end><total_fee>1</total_fee><trade_type><![CDATA[JSAPI]]></trade_type><transaction_id><![CDATA[1004400740201409030005092168]]></transaction_id></xml>";
		logger.info("微信返回的响应报文为：" + res);

		Map<String, String> retMap = CommEngine.transResponseBodyByTemplate(
				CommConfig.WX_NOTIFY_BACK, res);
		logger.info("解析微信返回的响应报文为：" + retMap.toString());
	}

	/**
	 * 订单查询 （作者：wupeng<wupengg@enn.com>）
	 * 
	 * @throws Exception
	 */
	public static void orderquery() throws Exception {
		Map<String, String> mapParam = new HashMap<String, String>();
		mapParam.put("appid", CommConfig.WX_APPID);
		mapParam.put("mch_id", CommConfig.WX_MCHID);
		mapParam.put("nonce_str", "ec2316275641faa3aacf3cc599e8730f");
		mapParam.put("transaction_id", "1008450740201411110005820873");
		mapParam.put("sign", "FDD167FAA73459FD921B144BAF4F4CA2");
		String req = CommEngine.createSendMesByTemplate(
				CommConfig.WX_ORDERQUERY_REQ, mapParam);
		logger.info("发给微信的请求报文为：" + req);
		String res = HttpUtils.post(CommConfig.WX_ORDERQUERY, req);
		logger.info("微信返回的响应报文为：" + res);

		Map<String, String> retMap = CommEngine.transResponseBodyByTemplate(
				CommConfig.WX_ORDERQUERY_BACK, res);
		logger.info("解析微信返回的响应报文为：" + retMap.toString());
	}

	/**
	 * 关闭订单 （作者：wupeng<wupengg@enn.com>）
	 * 
	 * @throws Exception
	 */
	public static void closeorder() throws Exception {
		Map<String, String> mapParam = new HashMap<String, String>();
		mapParam.put("appid", CommConfig.WX_APPID);
		mapParam.put("mch_id", CommConfig.WX_MCHID);
		mapParam.put("nonce_str", "4ca93f17ddf3443ceabf72f26d64fe0e");
		mapParam.put("out_trade_no", "1415983244");
		mapParam.put("sign", "59FF1DF214B2D279A0EA7077C54DD95D");
		String req = CommEngine.createSendMesByTemplate(
				CommConfig.WX_CLOSEORDER_REQ, mapParam);
		logger.info("发给微信的请求报文为：" + req);
		String res = HttpUtils.post(CommConfig.WX_CLOSEORDER, req);
		logger.info("微信返回的响应报文为：" + res);

		Map<String, String> retMap = CommEngine.transResponseBodyByTemplate(
				CommConfig.WX_CLOSEORDER_BACK, res);
		logger.info("解析微信返回的响应报文为：" + retMap.toString());
	}

	/**
	 * 申请退款 （作者：wupeng<wupengg@enn.com>）
	 * 
	 * @throws Exception
	 */
	public static void refund() throws Exception {
		Map<String, String> mapParam = new HashMap<String, String>();
		mapParam.put("appid", CommConfig.WX_APPID);
		mapParam.put("mch_id", CommConfig.WX_MCHID);
		mapParam.put("nonce_str", "6cefdb308e1e2e8aabd48cf79e546a02");
		mapParam.put("op_user_id", CommConfig.WX_MCHID);
		mapParam.put("out_refund_no", "1415701182");
		mapParam.put("out_trade_no", "1415757673");
		mapParam.put("refund_fee", "1");
		mapParam.put("total_fee", "1");
		mapParam.put("transaction_id", "");
		mapParam.put("sign", "FE56DD4AA85C0EECA82C35595A69E153");
		String req = CommEngine.createSendMesByTemplate(
				CommConfig.WX_REFUND_REQ, mapParam);
		logger.info("发给微信的请求报文为：" + req);
		String res = HttpUtils.postWithSSL(CommConfig.WX_REFUND, req,
				CommConfig.WX_CERT_PATH, CommConfig.WX_CERT_PWD);
		logger.info("微信返回的响应报文为：" + res);

		Map<String, String> retMap = CommEngine.transResponseBodyByTemplate(
				CommConfig.WX_REFUND_BACK, res);
		logger.info("解析微信返回的响应报文为：" + retMap.toString());
	}

	/**
	 * 查询退款 （作者：wupeng<wupengg@enn.com>）
	 * 
	 * @throws Exception
	 */
	public static void refundquery() throws Exception {
		Map<String, String> mapParam = new HashMap<String, String>();
		mapParam.put("appid", CommConfig.WX_APPID);
		mapParam.put("mch_id", CommConfig.WX_MCHID);
		mapParam.put("nonce_str", "0b9f35f484df17a732e537c37708d1d0");
		mapParam.put("out_refund_no", "");
		mapParam.put("out_trade_no", "1415757673");
		mapParam.put("refund_id", "");
		mapParam.put("transaction_id", "");
		mapParam.put("sign", "66FFB727015F450D167EF38CCC549521");
		String req = CommEngine.createSendMesByTemplate(
				CommConfig.WX_REFUNDQUERY_REQ, mapParam);
		logger.info("发给微信的请求报文为：" + req);
		String res = HttpUtils.postWithSSL(CommConfig.WX_REFUNDQUERY, req,
				CommConfig.WX_CERT_PATH, CommConfig.WX_CERT_PWD);
		logger.info("微信返回的响应报文为：" + res);

		Map<String, String> retMap = CommEngine.transResponseBodyByTemplate(
				CommConfig.WX_REFUNDQUERY_BACK, res);
		logger.info("解析微信返回的响应报文为：" + retMap.toString());
	}

	/**
	 * 下载对账单 （作者：wupeng<wupengg@enn.com>）
	 * 
	 * @throws Exception
	 */
	public static void downloadbill() throws Exception {
		Map<String, String> mapParam = new HashMap<String, String>();
		mapParam.put("appid", CommConfig.WX_APPID);
		mapParam.put("mch_id", CommConfig.WX_MCHID);
		mapParam.put("nonce_str", "21df7dc9cd8616b56919f20d9f679233");
		mapParam.put("bill_date", "20141110");
		mapParam.put("bill_type", "ALL");
		mapParam.put("sign", "332F17B766FC787203EBE9D6E40457A1");
		String req = CommEngine.createSendMesByTemplate(
				CommConfig.WX_DOWNLOADBILL_REQ, mapParam);
		logger.info("发给微信的请求报文为：" + req);
		String res = HttpUtils.postWithSSL(CommConfig.WX_DOWNLOADBILL, req,
				CommConfig.WX_CERT_PATH, CommConfig.WX_CERT_PWD);
		logger.info("微信返回的响应报文为：" + res);

		Map<String, String> retMap = CommEngine.transResponseBodyByTemplate(
				CommConfig.WX_DOWNLOADBILL_BACK, res);
		logger.info("解析微信返回的响应报文为：" + retMap.toString());
	}

	public static void main(String args[]) throws Exception {
		// 签名验签 signAndVertify();
		// 统一下单 unifiedorder();
		// 支付结果通知 payNotify();
		// 订单查询 orderquery();
		// 关闭订单 closeorder();
		// 申请退款 refund();
		// 查询退款 refundquery();
		// 下载对账单 downloadbill();
	}

}
