package com.label.common.plugin;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.util.StringUtils;

import com.label.common.util.UtilAES;

/**
 * 支持加密配置文件插件
 * @author jolley
 */
public class EncryptPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

	private String[] propertyNames = {
		"master.jdbc.password", "slave.jdbc.password", "generator.jdbc.password", "master.redis.password"
	};

	/**
	 * 解密指定propertyName的加密属性值
	 * @param propertyName
	 * @param propertyValue
	 * @return
	 */
	@Override
	protected String convertProperty(String propertyName, String propertyValue) {
		for (String p : propertyNames) {
			if (p.equalsIgnoreCase(propertyName)) {
				String v = UtilAES.AESDecode(propertyValue);
				return StringUtils.isEmpty(v) ? propertyValue : v;
			}
		}
		return super.convertProperty(propertyName, propertyValue);
	}

}
