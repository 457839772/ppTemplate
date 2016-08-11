/**
 * CopyRight 2016 必拓电子商务有限公司
 */
package com.xinyipay.cmi.core.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 文件帮助类
 * 
 * @author wupeng<wupengg@enn.com>
 */
public class FileUtils {

	/**
	 * getFileContent
	 * 
	 * @param in
	 *            文件输入流
	 * @return String 返回类型
	 */
	public static String getFileContent(InputStream in) throws Exception {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String readLine = null;
			StringBuilder sb = new StringBuilder();
			while ((readLine = br.readLine()) != null) {
				if (readLine.charAt(0) == '-') {
					continue;
				} else {
					sb.append(readLine);
					sb.append('\r');
				}
			}
			return sb.toString();
		} catch (IOException e) {
			throw new Exception("数据流读取错误");
		} catch (NullPointerException e) {
			throw new Exception("输入流为空");
		}
	}

	/**
	 * getFileContent 从文件中获取内容
	 * 
	 * @param file
	 * @return String 返回类型
	 */
	public static String getFileContent(File file) throws Exception {
		InputStream in = new FileInputStream(file);
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String readLine = null;
			StringBuilder sb = new StringBuilder();
			while ((readLine = br.readLine()) != null) {
				if (readLine.charAt(0) == '-') {
					continue;
				} else {
					sb.append(readLine);
					sb.append('\r');
				}
			}
			br.close();
			return sb.toString();
		} catch (IOException e) {
			throw new Exception("数据流读取错误");
		} catch (NullPointerException e) {
			throw new Exception("输入流为空");
		} finally {
			in.close();
		}
	}

}
