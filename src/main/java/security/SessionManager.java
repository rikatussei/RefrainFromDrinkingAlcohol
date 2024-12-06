// src/main/java/security/SessionManager.java
package security;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {
   private static final Map<String, LocalDateTime> sessionActivity = new ConcurrentHashMap<>();
   private static final int SESSION_TIMEOUT_MINUTES = 30;

   public static void trackSession(HttpSession session) {
       sessionActivity.put(session.getId(), LocalDateTime.now());
       cleanupExpiredSessions();
   }

   public static void invalidateExpiredSession(HttpSession session) {
       LocalDateTime lastActivity = sessionActivity.get(session.getId());
       if (lastActivity != null && 
           lastActivity.plusMinutes(SESSION_TIMEOUT_MINUTES).isBefore(LocalDateTime.now())) {
           session.invalidate();
           sessionActivity.remove(session.getId());
       }
   }

   private static void cleanupExpiredSessions() {
       LocalDateTime expirationTime = LocalDateTime.now().minusMinutes(SESSION_TIMEOUT_MINUTES);
       sessionActivity.entrySet().removeIf(entry -> entry.getValue().isBefore(expirationTime));
   }
}

// src/main/java/security/PasswordValidator.java
package security;

import java.util.regex.Pattern;

public class PasswordValidator {
   private static final Pattern PASSWORD_PATTERN = 
       Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");

   public static boolean isValid(String password) {
       return password != null && PASSWORD_PATTERN.matcher(password).matches();
   }

   public static String getPasswordRequirements() {
       return "パスワードは以下の条件を満たす必要があります：\n" +
              "- 8文字以上\n" +
              "- 大文字を含む\n" +
              "- 小文字を含む\n" +
              "- 数字を含む\n" +
              "- 特殊文字(@#$%^&+=)を含む\n" +
              "- スペースを含まない";
   }
}

// src/main/java/security/InjectionPreventionUtil.java
package security;

import org.owasp.encoder.Encode;
import java.util.regex.Pattern;

public class InjectionPreventionUtil {
   private static final Pattern SQL_INJECTION_PATTERN = 
       Pattern.compile(".*[;'].*|.*--.*|.*drop.*|.*select.*|.*delete.*|.*update.*", 
                      Pattern.CASE_INSENSITIVE);

   public static String sanitizeInput(String input) {
       if (input == null) {
           return null;
       }
       return Encode.forHtml(input);
   }

   public static boolean containsSQLInjection(String input) {
       return input != null && SQL_INJECTION_PATTERN.matcher(input).matches();
   }

   public static String sanitizeForSQL(String input) {
       if (input == null) {
           return null;
       }
       return input.replaceAll("['\"\\\\]", "\\\\$0");
   }
}