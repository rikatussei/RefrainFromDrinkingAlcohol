// src/main/java/config/EnvironmentConfig.java
package config;

import java.io.IOException;
import java.util.Properties;

public class EnvironmentConfig {
	private static final Properties props = new Properties();
	private static final String ENV = System.getProperty("env", "development");

	static {
		loadProperties();
	}

	private static void loadProperties() {
		try {
			props.load(EnvironmentConfig.class.getResourceAsStream("/env/" + ENV + ".properties"));
		} catch (IOException e) {
			throw new RuntimeException("環境設定ファイルの読み込みに失敗: " + ENV, e);
		}
	}

	public static String get(String key) {
		return props.getProperty(key);
	}
}

// src/main/java/util/LoggerUtil.java
package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerUtil {
	public static Logger getLogger() {
		return LoggerFactory.getLogger(getCaller());
	}

	private static String getCaller() {
		return Thread.currentThread().getStackTrace()[2].getClassName();
	}}

	// src/main/resources/logback.xml
	<?xml version="1.0"encoding="UTF-8"?><configuration><
	appender name = "CONSOLE"class="ch.qos.logback.core.ConsoleAppender"><encoder><pattern>%d{HH:mm:ss.SSS}[%thread]%-5l evel%logger{36}-%msg%n</pattern></encoder></appender>

	<
	appender name = "FILE"class="ch.qos.logback.core.rolling.RollingFileAppender"><file>logs/application.log</file><rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy"><fileNamePattern>logs/application.%d{yyyy-MM-dd}.log</fileNamePattern><maxHistory>30</maxHistory></rollingPolicy><encoder><pattern>%d{yyyy-MM-dd HH:mm:ss.SSS}[%thread]%-5l evel%logger{36}-%msg%n</pattern></encoder></appender>

	<
	appender name = "ERROR_FILE"class="ch.qos.logback.core.rolling.RollingFileAppender"><file>logs/error.log</file><filter class="ch.qos.logback.classic.filter.ThresholdFilter"><level>ERROR</level></filter><rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy"><fileNamePattern>logs/error.%d{yyyy-MM-dd}.log</fileNamePattern><maxHistory>30</maxHistory></rollingPolicy><encoder><pattern>%d{yyyy-MM-dd HH:mm:ss.SSS}[%thread]%-5l evel%logger{36}-%msg%n</pattern></encoder></appender>

<root level="INFO">
       <appender-ref ref="CONSOLE" />
       <appender-ref ref="FILE" />
       <appender-ref ref="ERROR_FILE" />
   </root>
</configuration>