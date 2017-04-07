package com.mjoys.web.filter;

import com.mjoys.springboot.track.biz.utils.AjaxUtils;
import com.mjoys.springboot.track.biz.utils.CookieUtils;
import com.mjoys.springboot.track.common.constants.CommonConstants;
import com.mjoys.springboot.track.common.constants.ErrorCode;
import com.mjoys.springboot.track.common.utils.ResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * CSRF安全验证过滤器.
 * 
 * 基本过程：用户登录时，在用户cookie中，设置用户提交数据时需要使用的凭证token。
 * 前端页面在当前域下请求数据时，将此token凭证设置到请求head中。过滤器通过获取当次请求的head内对应的token值进行比较验证。
 * 
 * 解决问题：所有修改操作POST提交，必须经过token验证，而非当前域下，无法获取初始设置cookie值而得到保护。
 */
//@Component("csrfFilter")
public class CsrfFilter implements Filter {

	/** The logger. */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	private Set<String> excludePaths = new HashSet<>();
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.excludePaths.add("/account/register");
		this.excludePaths.add("/account/login");
		this.excludePaths.add("/account/logout");
		this.excludePaths.add("/account/employeeLogin");
		this.excludePaths.add("/account/manager/login");
		this.excludePaths.add("/account/resetPasswd");
		this.excludePaths.add("/account/resetPasswd/redirect");
		this.excludePaths.add("/advert/upload/couponCodeFile");
		this.excludePaths.add("/upload/index");
		this.excludePaths.add("/account/modifyAuditData");
		this.excludePaths.add("/account/verifyEmail");
		this.excludePaths.add("/account/repeatVerifyEmail");
		this.excludePaths.add("/account/sendResetPasswdEmail");
		this.excludePaths.add("/account/getResetPasswdEmail");
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException,
			ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;

		String path = request.getServletPath();
		if (!this.excludePaths.contains(path) && "POST".equalsIgnoreCase(request.getMethod())) {
			if (logger.isDebugEnabled()) {
				logger.debug("Protected path " + path);
			}
			String headToken = request.getHeader(CommonConstants.COOKIE_KEY.CSRF_TOKEN_KEY);
			Cookie tokenCookie = CookieUtils.getCookieByName(request, CommonConstants.COOKIE_KEY.CSRF_TOKEN_KEY);
			if (headToken == null || tokenCookie == null || !headToken.equals(tokenCookie.getValue())) {
				AjaxUtils.renderJson(response, ResultUtils.fail(ErrorCode.E0001002));
				return;
			}
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		 this.excludePaths.clear();
	}
}
