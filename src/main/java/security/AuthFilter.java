// src/main/java/security/AuthFilter.java
package security;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter("/*")
public class AuthFilter implements Filter {
   private static final String[] PUBLIC_PATHS = {
       "/login", "/register", "/error", "/assets/"
   };

   @Override
   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
           throws IOException, ServletException {
       HttpServletRequest httpRequest = (HttpServletRequest) request;
       HttpServletResponse httpResponse = (HttpServletResponse) response;
       String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());

       if (isPublicPath(path)) {
           chain.doFilter(request, response);
           return;
       }

       HttpSession session = httpRequest.getSession(false);
       if (session == null || session.getAttribute("user") == null) {
           httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
           return;
       }

       chain.doFilter(request, response);
   }

   private boolean isPublicPath(String path) {
       for (String publicPath : PUBLIC_PATHS) {
           if (path.startsWith(publicPath)) {
               return true;
           }
       }
       return false;
   }
}

// src/main/java/security/CSRFFilter.java
package security;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public class CSRFFilter implements Filter {
   @Override
   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
           throws IOException, ServletException {
       HttpServletRequest httpRequest = (HttpServletRequest) request;
       HttpServletResponse httpResponse = (HttpServletResponse) response;

       if (isModifyingRequest(httpRequest)) {
           String sessionToken = (String) httpRequest.getSession().getAttribute("csrf_token");
           String requestToken = httpRequest.getParameter("csrf_token");

           if (!isValidCSRFToken(sessionToken, requestToken)) {
               httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid CSRF Token");
               return;
           }
       }

       chain.doFilter(request, response);
   }

   private boolean isModifyingRequest(HttpServletRequest request) {
       String method = request.getMethod();
       return "POST".equals(method) || "PUT".equals(method) || "DELETE".equals(method);
   }

   private boolean isValidCSRFToken(String sessionToken, String requestToken) {
       return sessionToken != null && sessionToken.equals(requestToken);
   }
}

// src/main/java/util/EncryptionUtil.java
package util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class EncryptionUtil {
   private static final String ALGORITHM = "AES";
   private static final String key = System.getenv("ENCRYPTION_KEY");

   public static String encrypt(String value) throws Exception {
       SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
       Cipher cipher = Cipher.getInstance(ALGORITHM);
       cipher.init(Cipher.ENCRYPT_MODE, secretKey);
       return Base64.getEncoder().encodeToString(cipher.doFinal(value.getBytes()));
   }

   public static String decrypt(String encrypted) throws Exception {
       SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
       Cipher cipher = Cipher.getInstance(ALGORITHM);
       cipher.init(Cipher.DECRYPT_MODE, secretKey);
       return new String(cipher.doFinal(Base64.getDecoder().decode(encrypted)));
   }
}