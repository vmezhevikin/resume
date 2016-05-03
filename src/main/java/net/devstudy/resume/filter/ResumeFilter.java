package net.devstudy.resume.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ResumeFilter extends AbstractFilter
{
	@Value("${application.production}")
	private boolean production;

	@Override
	public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException
	{
		String requestUrl = request.getRequestURI();
		request.setAttribute("REQUEST_URL", requestUrl);
		try
		{
			chain.doFilter(request, response);
		} catch (Throwable th)
		{
			LOGGER.error("Process request failed: " + requestUrl, th);
			handlerException(th, requestUrl, response);
		}
	}

	private void handlerException(Throwable th, String requestUrl, HttpServletResponse response) throws IOException, ServletException
	{
		if (production)
		{
			if ("/error".equals(requestUrl))
				throw new ServletException(th);
			else
				response.sendRedirect("/error");
		}
		else
			throw new ServletException(th);
	}
}
