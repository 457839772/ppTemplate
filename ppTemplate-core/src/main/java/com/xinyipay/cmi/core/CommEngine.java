package com.xinyipay.cmi.core;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xinyipay.cmi.core.utils.FileUtils;

/**
 * 通道处理引擎
 * 
 * @author wupeng<wupengg@enn.com>
 */
public class CommEngine {
	private static Logger logger = LoggerFactory.getLogger(CommEngine.class);

	// 获取 CDATA标签 正则表达
	private static final String REGEX = ".*<!\\[CDATA\\[(.*)\\]\\]>.*";

	/**
	 * 将Map中的数据映射到template文件中,返回String类型的报文 （作者：wupeng<wupengg@enn.com>）
	 * 
	 * @param templateFileAddress
	 *            模板路径（例如："/template/weixin/weixin-orderquery.template"）
	 * @param sendMesMap
	 *            参数对象
	 * @return
	 */
	public static String createSendMesByTemplate(String templateFileAddress,
			Map<String, String> sendMesMap) {
		logger.info("使用解析模板地址为:{},待嵌入参数为:{}", templateFileAddress,
				sendMesMap.toString());

		String fileContent = null;
		try {
			fileContent = FileUtils.getFileContent(CommEngine.class
					.getResourceAsStream(templateFileAddress));
			fileContent = fileContent.replaceAll("\r", "");
			int targetCharFirstIndex = fileContent.indexOf("#");
			while (targetCharFirstIndex != -1) {
				int targetCharSecondIndex = fileContent.indexOf("#",
						targetCharFirstIndex + 1);
				String attribute = fileContent.substring(
						targetCharFirstIndex + 1, targetCharSecondIndex);
				String mapValue = sendMesMap.get(attribute);
				if (mapValue != null && !mapValue.equals("")) {
					fileContent = fileContent.replaceAll("#" + attribute + "#",
							mapValue);
				} else {
					fileContent = fileContent.replaceAll("#" + attribute + "#",
							"");
				}
				targetCharFirstIndex = fileContent.indexOf("#");
			}
		} catch (Exception e) {
			logger.error("{}", e);
		}
		return fileContent;
	}

	/**
	 * 通过模板解析渠道返回结果 （作者：wupeng<wupengg@enn.com>）
	 * 
	 * @param templateFileAddress
	 *            模板路径（例如："/template/weixin/weixin-orderquery.template"）
	 * @param responseBody
	 *            渠道返回报文
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> transResponseBodyByTemplate(
			String templateFileAddress, String responseBody) throws Exception {
		logger.info("使用解析模板地址为:{},待解析报文为:{}", templateFileAddress, responseBody);

		Map<String, String> retMap = new HashMap<String, String>();

		// 1.获取解析模板
		String templateContent = FileUtils.getFileContent(CommEngine.class
				.getResourceAsStream(templateFileAddress));
		templateContent = templateContent.replaceAll("\r", "");

		// 2.美化返回结果
		responseBody = responseBody.replace("{", "");
		responseBody = responseBody.replace("}", "");

		// 3.分解模板
		// 3.1 分组
		String templateContentGroup[] = templateContent.split("~");

		for (int i = 0; i < templateContentGroup.length; i++) {

			// 获取分组索引
			String groupArray[] = templateContentGroup[i].split("#");
			String preIndex = "";
			String key = "";
			String nextIndex = "";
			String value = "";
			if (groupArray.length == 3) {
				// 前后索引，中间为value
				preIndex = groupArray[0];
				key = groupArray[1];
				nextIndex = groupArray[2];
			} else if (groupArray.length == 2) {
				// 前面为索引，后面为value,分组到了末尾
				preIndex = groupArray[0];
				key = groupArray[1];
			} else {
				logger.error("暂不支持该类分组。待分组数据为：{}", templateContentGroup[i]);
				break;
			}

			// 根据索引解析返回数据
			int preIndexPosition = responseBody.indexOf(preIndex); // 前置索引的起始位置
			String resPreArray[] = responseBody.split(preIndex);
			if (resPreArray.length == 1) {
				// 返回结果不包含该索引
				continue;
			}
			responseBody = responseBody.substring(0, preIndexPosition)
					+ responseBody.substring(preIndexPosition
							+ preIndex.length());
			if (nextIndex == "") {
				// 已经到了模板数据末尾
				value = responseBody;
				Matcher matcher = Pattern.compile(REGEX).matcher(value);
				while (matcher.find()) {
					value = matcher.group(1);
				}

			} else if (responseBody.substring(preIndexPosition)
					.split(nextIndex).length == 1) {
				// 已经到了待解析数据末尾

				String responseBodyTemp = responseBody
						.substring(preIndexPosition);
				String resNextArray[] = responseBodyTemp.split(nextIndex);
				String valueTemp = resNextArray[0];
				Matcher matcher = Pattern.compile(REGEX).matcher(valueTemp);
				if (matcher.find()) {
					value = matcher.group(1);
				} else {
					value = valueTemp;
				}
				responseBody = responseBody.substring(0, preIndexPosition)
						+ responseBody.substring(preIndexPosition
								+ valueTemp.length());

			} else {
				String responseBodyTemp = responseBody
						.substring(preIndexPosition);
				String resNextArray[] = responseBodyTemp.split(nextIndex);
				String valueTemp = resNextArray[0];
				Matcher matcher = Pattern.compile(REGEX).matcher(valueTemp);
				if (matcher.find()) {
					value = matcher.group(1);
				} else {
					value = valueTemp;
				}
				responseBody = responseBody.substring(0, preIndexPosition)
						+ responseBody.substring(preIndexPosition
								+ valueTemp.length() + nextIndex.length());
			}

			retMap.put(key, value);
		}

		return retMap;
	}

}
