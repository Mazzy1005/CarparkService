import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DataBaseExcange.*;

@WebServlet("/RouteEdit")
public class RouteEdit extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getSession().getAttribute("register") != null) {
			String s = request.getParameter("id");
			int id = Integer.parseInt(s.substring(0, s.length() - 1));
			RouteData route = RouteDB.select(id);
			request.setAttribute("route", route);
			response.setContentType("text/html; charset=UTF-8");
			getServletContext().getRequestDispatcher("/WEB-INF/route_edit.jsp").forward(request, response);
		} else {
			response.sendRedirect("/MyFirstJavaEEProject/Authentication");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getSession().getAttribute("register") != null) {
			request.setCharacterEncoding("UTF-8");

			String s = request.getParameter("id");
			int id = Integer.parseInt(s.substring(0, s.length() - 1));
			String name = request.getParameter("name");
			RouteData route = new RouteData(id, name);
			RouteDB.update(route);
			response.sendRedirect("/MyFirstJavaEEProject/Routes");
		} else {
			response.sendRedirect("/MyFirstJavaEEProject/Authentication");
		}
	}
}
