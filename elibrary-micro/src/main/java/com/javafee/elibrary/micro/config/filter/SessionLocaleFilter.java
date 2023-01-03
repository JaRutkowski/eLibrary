package com.javafee.elibrary.micro.config.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SessionLocaleFilter implements Filter {
	@Value("${app.locale}")
	private String locale;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		log.info("Initializing SessionLocaleFilter: " + locale);
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) servletRequest;
		req.getSession().setAttribute("lang", locale); //req.getParameter("sessionLocale"));
		filterChain.doFilter(servletRequest, servletResponse);
	}

	@Override
	public void destroy() {
		log.info("Destroying SessionLocaleFilter: " + locale);
	}
}
