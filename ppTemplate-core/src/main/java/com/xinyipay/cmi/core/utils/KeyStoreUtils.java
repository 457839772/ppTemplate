/**
 * CopyRight 2016 必拓电子商务有限公司
 */
package com.xinyipay.cmi.core.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 证书工具类
 * 
 * @author wupeng<wupengg@enn.com>
 */
public class KeyStoreUtils {
	private static Logger logger = LoggerFactory.getLogger(KeyStoreUtils.class);

	/**
	 * 将证书文件读取为证书存储对象
	 * 
	 * @param pfxkeyfile
	 *            证书文件名
	 * @param keypwd
	 *            证书密码
	 * @param type
	 *            证书类型
	 * @return 证书对象
	 * @throws IOException
	 */
	public static KeyStore getKeyInfo(String pfxkeyfile, String keypwd,
			String type) throws IOException {
		logger.info("加载签名证书==>{}", pfxkeyfile);
		FileInputStream fis = null;
		try {
			if (Security.getProvider("BC") == null) {
				logger.info("add BC provider");
				Security.addProvider(new BouncyCastleProvider());
			} else {
				Security.removeProvider("BC"); // 解决eclipse调试时tomcat自动重新加载时，BC存在不明原因异常的问题。
				Security.addProvider(new BouncyCastleProvider());
				logger.info("re-add BC provider");
			}
			KeyStore ks = null;
			if ("JKS".equals(type)) {
				ks = KeyStore.getInstance(type, "BC");
			} else if ("PKCS12".equals(type)) {
				String jdkVendor = System.getProperty("java.vm.vendor");
				String javaVersion = System.getProperty("java.version");
				logger.info("java.vm.vendor=[{}]", jdkVendor);
				logger.info("java.version=[{}]", javaVersion);
				ks = KeyStore.getInstance(type, "BC");
			}
			logger.info("Load RSA CertPath=[{}],Pwd=[{}]", pfxkeyfile, keypwd);
			fis = new FileInputStream(new File(pfxkeyfile));
			char[] nPassword = null;
			nPassword = null == keypwd || "".equals(keypwd.trim()) ? null
					: keypwd.toCharArray();
			if (null != ks) {
				ks.load(fis, nPassword);
			}
			return ks;
		} catch (Exception e) {
			if (Security.getProvider("BC") == null) {
				logger.info("BC Provider not installed.");
			}
			logger.error("{}", e);
			if ((e instanceof KeyStoreException) && "PKCS12".equals(type)) {
				Security.removeProvider("BC");
			}
			return null;
		} finally {
			if (null != fis)
				fis.close();
		}
	}

	/**
	 * 获取证书ID （作者：wupeng<wupengg@enn.com>）
	 * 
	 * @param keyStore
	 * @return
	 */
	private static String getCertIdIdByStore(KeyStore keyStore) {
		Enumeration<String> aliasenum = null;
		try {
			aliasenum = keyStore.aliases();
			String keyAlias = null;
			if (aliasenum.hasMoreElements()) {
				keyAlias = aliasenum.nextElement();
			}
			X509Certificate cert = (X509Certificate) keyStore
					.getCertificate(keyAlias);
			return cert.getSerialNumber().toString();
		} catch (KeyStoreException e) {
			logger.error("getCertIdIdByStore Error And e = {}", e);
			return null;
		}
	}

	/**
	 * 获取证书ID （作者：wupeng<wupengg@enn.com>）
	 * 
	 * @param certPath
	 * @param certPwd
	 * @return
	 * @throws IOException
	 */
	public static String getCertId(String certPath, String certPwd)
			throws IOException {
		KeyStore ks = getKeyInfo(certPath, certPwd, "PKCS12");
		return getCertIdIdByStore(ks);
	}

	/**
	 * 获取私钥 （作者：wupeng<wupengg@enn.com>）
	 * 
	 * @param certPath
	 * @param certPwd
	 * @return
	 * @throws IOException
	 */
	public static PrivateKey getSignCertPrivateKeyByStoreMap(String certPath,
			String certPwd) throws IOException {
		KeyStore ks = getKeyInfo(certPath, certPwd, "PKCS12");
		try {
			Enumeration<String> aliasenum = ks.aliases();
			String keyAlias = null;
			if (aliasenum.hasMoreElements()) {
				keyAlias = aliasenum.nextElement();
			}
			PrivateKey privateKey = (PrivateKey) ks.getKey(keyAlias,
					certPwd.toCharArray());
			return privateKey;
		} catch (Exception e) {
			logger.error("{}", e);
			return null;
		}
	}
}
