/**
 * CopyRight 2016 必拓电子商务有限公司
 */
package com.xinyipay.cmi.core.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.ssl.SSLContexts;

/**
 * http帮助类
 * 
 * @author wupeng<wupengg@enn.com>
 */
public class HttpUtils {

	private final static int BUFFER_SIZE = 2048;

	/**
	 * 发送http/https get请求 （作者：wupeng<wupengg@enn.com>）
	 * 
	 * @param urlString
	 *            请求地址
	 * @param requestBody
	 *            请求报文体
	 * @return
	 * @throws Exception
	 */
	public static String get(String urlString, String requestBody)
			throws Exception {
		return send(true, urlString, requestBody, 10000, "UTF-8", null, "GET",
				"", "");

	}

	/**
	 * 发送http/https post请求 （作者：wupeng<wupengg@enn.com>）
	 * 
	 * @param urlString
	 *            请求地址
	 * @param requestBody
	 *            请求报文体
	 * @return
	 * @throws Exception
	 */
	public static String post(String urlString, String requestBody)
			throws Exception {
		return send(true, urlString, requestBody, 10000, "UTF-8", null, "POST",
				"", "");

	}

	/**
	 * 发送https ssl post请求 （作者：wupeng<wupengg@enn.com>）
	 * 
	 * @param urlString
	 *            请求地址
	 * @param requestBody
	 *            请求报文
	 * @param keyUrl
	 *            证书地址
	 * @param keyPasswrd
	 *            证书密钥
	 * @return
	 * @throws Exception
	 */
	public static String postWithSSL(String urlString, String requestBody,
			String keyUrl, String keyPasswrd) throws Exception {
		return send(false, urlString, requestBody, 10000, "UTF-8", null,
				"POST", keyUrl, keyPasswrd);

	}

	/**
	 * 发送http/https请求 （作者：wupeng<wupengg@enn.com>）
	 * 
	 * @param ignoreSSLVerify
	 *            忽略ssl校验
	 * @param urlString
	 *            发送地址
	 * @param requestBody
	 *            请求报文
	 * @param timeOut
	 *            超时时间
	 * @param encoding
	 *            渠道返回编码格式
	 * @param contentType
	 *            请求内容类型
	 * @param method
	 *            请求方法
	 * @return
	 * @throws Exception
	 */
	public static String send(boolean ignoreSSLVerify, String urlString,
			String requestBody, int timeOut, String encoding,
			String contentType, String method, String keyUrl, String keyPasswrd)
			throws Exception {

		HttpURLConnection httpConn = null;
		String ret = null;
		OutputStream outStream = null;
		InputStream inStream = null;

		try {
			URL url = new URL(urlString);
			if (isHttps(urlString)) {
				httpConn = (HttpsURLConnection) getConnection(url, method,
						timeOut, contentType);
				if (ignoreSSLVerify) {
					// 不需要证书
					ignoreSSLVerify((HttpsURLConnection) httpConn);
				} else {
					// 需要证书

					KeyStore keyStore = KeyStore.getInstance("PKCS12");
					FileInputStream instream = new FileInputStream(new File(
							keyUrl));
					try {
						keyStore.load(instream, keyPasswrd.toCharArray());
					} finally {
						instream.close();
					}

					// Trust own CA and all self-signed certs
					SSLContext sslcontext = SSLContexts
							.custom()
							.loadKeyMaterial(keyStore, keyPasswrd.toCharArray())
							.build();

					((HttpsURLConnection) httpConn)
							.setSSLSocketFactory(sslcontext.getSocketFactory());

				}
			} else {
				httpConn = getConnection(url, "POST", timeOut, contentType);
			}

			outStream = httpConn.getOutputStream();
			if (requestBody != null) {
				outStream.write(requestBody.getBytes(encoding));
			}
			outStream.flush();

			inStream = httpConn.getInputStream();
			byte[] bin = readInputStream(inStream);

			ret = new String(new String(bin, encoding).getBytes("UTF-8"),
					"UTF-8");

		} finally {
			if (inStream != null)
				inStream.close();
			if (outStream != null)
				outStream.close();
			if (httpConn != null)
				httpConn.disconnect();
		}
		return ret;
	}

	/**
	 * 获取http连接 （作者：wupeng<wupengg@enn.com>）
	 * 
	 * @param url
	 * @param method
	 * @param timeOut
	 * @param contentType
	 * @return
	 * @throws IOException
	 */
	private static HttpURLConnection getConnection(URL url, String method,
			int timeOut, String contentType) throws IOException {
		HttpURLConnection conn = null;

		if (isHttps(url.getPath())) {
			// https请求
			conn = (HttpsURLConnection) url.openConnection();
		} else {
			// http请求
			conn = (HttpURLConnection) url.openConnection();
		}

		// 设置是否从httpUrlConnection读入，默认情况下是true;
		conn.setDoInput(true);

		// 设置是否向httpUrlConnection输出，post请求，参数要放在http正文内，因此需要设为true，默认情况下是false;
		conn.setDoOutput(true);

		// Post 请求不能使用缓存
		conn.setUseCaches(false);

		// 设定请求的方法为"POST"，默认是GET
		conn.setRequestMethod(method);

		// 设定传送的内容类型
		if (contentType != null && contentType != "") {
			conn.setRequestProperty("Content-Type", contentType);
		} else {
			conn.setRequestProperty("Content-type", "text/html");
		}

		// 设置连接主机超时（单位：毫秒）
		conn.setConnectTimeout(timeOut);

		// 设置从主机读取数据超时（单位：毫秒）
		conn.setReadTimeout(timeOut);

		return conn;
	}

	/**
	 * 是https请求 （作者：wupeng<wupengg@enn.com>）
	 * 
	 * @param urlString
	 *            发送地址
	 * @return
	 */
	public static boolean isHttps(String urlString) {
		return urlString != null && urlString.startsWith("https://");
	}

	/**
	 * 读取输入流 （作者：wupeng<wupengg@enn.com>）
	 * 
	 * @param inStream
	 * @return
	 * @throws IOException
	 */
	private static byte[] readInputStream(InputStream inStream)
			throws IOException {

		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] data = null;
		byte[] buffer = new byte[BUFFER_SIZE];
		int len = 0;
		try {
			while ((len = inStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, len);
			}
			data = outStream.toByteArray();
		} finally {
			if (outStream != null)
				outStream.close();
		}
		return data;
	}

	/**
	 * 忽略ssl校验 （作者：wupeng<wupengg@enn.com>）
	 * 
	 * @param conn
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 */
	private static void ignoreSSLVerify(HttpsURLConnection conn)
			throws NoSuchAlgorithmException, KeyManagementException {

		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(
					java.security.cert.X509Certificate[] certs, String authType) {
			}

			public void checkServerTrusted(
					java.security.cert.X509Certificate[] certs, String authType) {
			}
		} };

		SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, trustAllCerts, new java.security.SecureRandom());

		conn.setSSLSocketFactory(sc.getSocketFactory());

		conn.setHostnameVerifier(new HostnameVerifier() {
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		});
	}

}
