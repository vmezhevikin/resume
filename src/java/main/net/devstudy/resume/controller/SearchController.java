package net.devstudy.resume.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.devstudy.resume.service.NameService;

/**
 * @see http://devstudy.net
 */
@WebServlet("/search")
public class SearchController extends HttpServlet
{
	private static final long serialVersionUID = 2847786613235117603L;

	private static final Logger LOGGER = LoggerFactory.getLogger(SearchController.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		LOGGER.debug("doGet search");
		request.getRequestDispatcher("/WEB-INF/JSP/search-form.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		LOGGER.debug("doPost search: {}", request.getParameterMap());
		String name = request.getParameter("query");
		if (!isValid(name))
		{
			request.setAttribute("invalid", Boolean.TRUE);
			request.getRequestDispatcher("/WEB-INF/JSP/search-form.jsp").forward(request, response);
			return;
		}
		String result = NameService.getInstance().convertName(name);
		request.setAttribute("name", result);
		request.getRequestDispatcher("/WEB-INF/JSP/search-result.jsp").forward(request, response);
	}

	private boolean isValid(String name)
	{
		return name != null && 	name.trim().length() != 0;
	}

}
