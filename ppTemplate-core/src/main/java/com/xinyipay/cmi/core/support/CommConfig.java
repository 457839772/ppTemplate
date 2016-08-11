/**
 * CopyRight 2016 必拓电子商务有限公司
 */
package com.xinyipay.cmi.core.support;

/**
 * 项目配置
 * 
 * @author wupeng<wupengg@enn.com>
 */
public class CommConfig {

	// 编码
	public static final String UTF8 = "UTF-8";

	// 签名方式
	public static final String RSA = "RSA";
	public static final String MD5 = "MD5";

	// ****************微信************************************************************************

	// 微信应用ID
	public static final String WX_APPID = "wx2421b1c4370ec43b";
	// 微信商户号
	public static final String WX_MCHID = "10000100";
	// 微信MD5签名密钥
	public static final String WX_KEY = "192006250b4c09247ec02edce69f6a2d";
	// 微信商户证书文件路径
	public static final String WX_CERT_PATH = "src/main/resources/apiclient_cert.p12";
	// 微信商户证书密钥
	public static final String WX_CERT_PWD = "1233410002";

	// 微信相关模板
	public static final String WX_UNIFIEDORDER_REQ = "/template/weixin/weixin-unifieorder-req.template";
	public static final String WX_UNIFIEDORDER_BACK = "/template/weixin/weixin-unifiedorder-back.template";
	public static final String WX_NOTIFY_BACK = "/template/weixin/weixin-notify-back.template";
	public static final String WX_ORDERQUERY_REQ = "/template/weixin/weixin-orderquery-req.template";
	public static final String WX_ORDERQUERY_BACK = "/template/weixin/weixin-orderquery-back.template";
	public static final String WX_CLOSEORDER_REQ = "/template/weixin/weixin-closeorder-req.template";
	public static final String WX_CLOSEORDER_BACK = "/template/weixin/weixin-closeorder-back.template";
	public static final String WX_REFUND_REQ = "/template/weixin/weixin-refund-req.template";
	public static final String WX_REFUND_BACK = "/template/weixin/weixin-refund-back.template";
	public static final String WX_REFUNDQUERY_REQ = "/template/weixin/weixin-refundquery-req.template";
	public static final String WX_REFUNDQUERY_BACK = "/template/weixin/weixin-refundquery-back.template";
	public static final String WX_DOWNLOADBILL_REQ = "/template/weixin/weixin-downloadbill-req.template";
	public static final String WX_DOWNLOADBILL_BACK = "/template/weixin/weixin-closeorder-back.template";

	// 微信API地址
	public static final String WX_UNIFIEDORDER = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	public static final String WX_ORDERQUERY = "https://api.mch.weixin.qq.com/pay/orderquery";
	public static final String WX_CLOSEORDER = "https://api.mch.weixin.qq.com/pay/closeorder";
	public static final String WX_REFUND = "https://api.mch.weixin.qq.com/secapi/pay/refund";
	public static final String WX_REFUNDQUERY = "https://api.mch.weixin.qq.com/pay/refundquery";
	public static final String WX_DOWNLOADBILL = "https://api.mch.weixin.qq.com/pay/downloadbill";

	// ****************支付宝************************************************************************

	// 支付宝合作者身份ID
	public static final String ZFB_PARTNER = "2088101568358171";
	// 支付宝卖家支付宝账号
	public static final String ZFB_SELLER_ID = "xxx@alipay.com";
	// 支付宝商户私钥
	public static final String ZFB_RSA_MCH_PRIVATE_KEY = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAN0yqPkLXlnhM+2H/57aHsYHaHXazr9pFQun907TMvmbR04wHChVsKVgGUF1hC0FN9hfeYT5v2SXg1WJSg2tSgk7F29SpsF0I36oSLCIszxdu7ClO7c22mxEVuCjmYpJdqb6XweAZzv4Is661jXP4PdrCTHRdVTU5zR9xUByiLSVAgMBAAECgYEAhznORRonHylm9oKaygEsqQGkYdBXbnsOS6busLi6xA+iovEUdbAVIrTCG9t854z2HAgaISoRUKyztJoOtJfI1wJaQU+XL+U3JIh4jmNx/k5UzJijfvfpT7Cv3ueMtqyAGBJrkLvXjiS7O5ylaCGuB0Qz711bWGkRrVoosPM3N6ECQQD8hVQUgnHEVHZYtvFqfcoq2g/onPbSqyjdrRu35a7PvgDAZx69Mr/XggGNTgT3jJn7+2XmiGkHM1fd1Ob/3uAdAkEA4D7aE3ZgXG/PQqlm3VbE/+4MvNl8xhjqOkByBOY2ZFfWKhlRziLEPSSAh16xEJ79WgY9iti+guLRAMravGrs2QJBAOmKWYeaWKNNxiIoF7/4VDgrcpkcSf3uRB44UjFSn8kLnWBUPo6WV+x1FQBdjqRviZ4NFGIP+KqrJnFHzNgJhVUCQFzCAukMDV4PLfeQJSmna8PFz2UKva8fvTutTryyEYu+PauaX5laDjyQbc4RIEMU0Q29CRX3BA8WDYg7YPGRdTkCQQCG+pjU2FB17ZLuKRlKEdtXNV6zQFTmFc1TKhlsDTtCkWs/xwkoCfZKstuV3Uc5J4BNJDkQOGm38pDRPcUDUh2/";
	// 支付宝公钥
	public static final String ZFB_RSA_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDQWiDVZ7XYxa4CQsZoB3n7bfxLDkeGKjyQPt2FUtm4TWX9OYrd523iw6UUqnQ+Evfw88JgRnhyXadp+vnPKP7unormYQAfsM/CxzrfMoVdtwSiGtIJB4pfyRXjA+KL8nIa2hdQy5nLfgPVGZN4WidfUY/QpkddCVXnZ4bAUaQjXQIDAQAB";

	// 支付宝接口名称
	public static final String ZFB_SERVICE_MOBILE_PAY = "mobile.securitypay.pay";

	// 支付宝相关模板
	public static final String ZFB_UNIFIEDORDER_REQ = "/template/zfb/zfb-unifieorder-req.template";
	public static final String ZFB_NOTIFY_BACK = "/template/zfb/zfb-notify-back.template";

	// 支付宝支付类型
	public static final String ZFB_PAYMENT_TYPE_GOODS = "1";

	// ****************银联控件************************************************************************
	// 银联商户代码
	public static final String CUP_MERCID = "700000000000001";
	// 银联证书路径
	public static final String CUP_SIGNCERT_PATH = "src/main/resources/PM_700000000000001_acp.pfx";
	// 银联证书密钥
	public static final String CUP_SIGNCERT_PWD = "000000";
	// 银联版本号
	public static final String CUP_VERSION = "5.0.0";
	// 银联签名方法
	public static final String CUP_RSA = "01";

	// 银联交易类型
	public static final String CUP_TRADE_TYPE_CONSUME = "01";
	public static final String CUP_TRADE_TYPE_CONSUME_CANCLE = "31";
	public static final String CUP_TRADE_TYPE_REFUND = "04";

	// 银联产品类型
	public static final String CUP_BIZTYPE_B2CGATEPAY = "000201";

	// 银联渠道类型
	public static final String CUP_CHANNEL_MOBILE = "08";

	// 银联接入类型
	public static final String CUP_ACCESSTYPE_MCH = "0";

	/**
	 * 银联-人民币
	 */
	public static final String CUP_CNY = "156";

	// 银联相关模板
	public static final String CUP_APPTRANS_REQ = "/template/cup/cup-apptrans-req.template";
	public static final String CUP_APPTRANS_BACK = "/template/cup/cup-apptrans-back.template";
	public static final String CUP_NOTIFY_BACK = "/template/cup/cup-notify-back.template";
	public static final String CUP_SINGEQUERY_REQ = "/template/cup/cup-singlequery-req.template";
	public static final String CUP_SINGEQUERY_BACK = "/template/cup/cup-singlequery-back.template";
}
