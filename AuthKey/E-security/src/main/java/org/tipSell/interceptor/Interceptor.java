package org.tipSell.interceptor;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class Interceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String clientIpAddress = request.getRemoteAddr(); // Get client's IP address
		String localIpAddress = request.getLocalAddr();
		String user = "1";
		// String requestId = request.getHeader("X-Request-ID"); // Optionally get
		// request ID from header

		MDC.put("clientIpAddress", clientIpAddress);
		MDC.put("localIpAddress", localIpAddress);
		MDC.put("user", user);

		// MDC.put("requestId", requestId != null ? requestId : "unknown");

		return true; // Continue processing the request
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		MDC.remove("clientIpAddress");
		MDC.remove("localIpAddress");
		MDC.remove("user");
		MDC.clear();
	}
}