// src/main/java/filter/XSSFilter.java
package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.owasp.encoder.Encode;

@WebFilter("/*")
public class XSSFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		chain.doFilter(new XSSRequestWrapper((HttpServletRequest) request), response);
	}

	private static class XSSRequestWrapper extends HttpServletRequestWrapper {
		public XSSRequestWrapper(HttpServletRequest request) {
			super(request);
		}

		@Override
		public String getParameter(String name) {
			return Encode.forHtml(super.getParameter(name));
		}

		@Override
		public String[] getParameterValues(String name) {
			String[] values = super.getParameterValues(name);
			if (values == null) {
				return null;
			}

			String[] encodedValues = new String[values.length];
			for (int i = 0; i < values.length; i++) {
				encodedValues[i] = Encode.forHtml(values[i]);
			}
			return encodedValues;
		}
	}
}