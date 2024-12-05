// src/main/java/logging/ApplicationLogger.java
package logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;

public class ApplicationLogger {
   private static final Logger logger = LoggerFactory.getLogger(ApplicationLogger.class);

   public static void logError(String message, Throwable e) {
       logger.error(message, e);
   }

   public static void logInfo(String message) {
       logger.info(message);
   }

   public static void logDebug(String message) {
       logger.debug(message);
   }

   public static void logWarning(String message) {
       logger.warn(message);
   }

   public static void printLoggerStatus() {
       LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
       StatusPrinter.print(lc);
   }
}

// src/main/java/exception/ApplicationException.java
package exception;

public class ApplicationException extends RuntimeException {
   private static final long serialVersionUID = 1L;
   private final String errorCode;

   public ApplicationException(String message, String errorCode) {
       super(message);
       this.errorCode = errorCode;
   }

   public ApplicationException(String message, String errorCode, Throwable cause) {
       super(message, cause);
       this.errorCode = errorCode;
   }

   public String getErrorCode() {
       return errorCode;
   }
}

// src/main/java/config/ErrorCodes.java
package config;

public class ErrorCodes {
   public static final String DB_CONNECTION_ERROR = "E001";
   public static final String API_ERROR = "E002";
   public static final String AUTHENTICATION_ERROR = "E003";
   public static final String VALIDATION_ERROR = "E004";
   public static final String MONSTER_GENERATION_ERROR = "E005";
}