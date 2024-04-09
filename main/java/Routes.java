
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DataBaseExcange.*;

@WebServlet("/Routes")
public class Routes extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (request.getSession().getAttribute("register") != null) {
			ArrayList<RouteData> data = RouteDB.selectAll();
			request.setAttribute("data", data);
			response.setContentType("text/html; charset=UTF-8");
			getServletContext().getRequestDispatcher("/WEB-INF/routes.jsp").forward(request, response);
		} else {
			response.sendRedirect("/MyFirstJavaEEProject/Authentication");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getSession().getAttribute("register") != null) {
			String s = request.getParameter("id");
			int id = Integer.parseInt(s.substring(0, s.length() - 1));
			RouteDB.delete(id);
			response.sendRedirect("/MyFirstJavaEEProject/Routes");
		} else {
			response.sendRedirect("/MyFirstJavaEEProject/Authentication");
		}
	}

}
