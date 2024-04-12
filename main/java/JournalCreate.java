
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DataBaseExcange.*;

@WebServlet("/JournalCreate")
public class JournalCreate extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getSession().getAttribute("register") != null) {
			ArrayList<AutoData> auto = AutoDB.selectAll();
			request.setAttribute("auto", auto);
			ArrayList<RouteData> routes = RouteDB.selectAll();
			request.setAttribute("routes", routes);
			response.setContentType("text/html; charset=UTF-8");
			getServletContext().getRequestDispatcher("/WEB-INF/journal_create_en.jsp").forward(request, response);
		} else {
			response.sendRedirect("/MyFirstJavaEEProject/Authentication");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
		if (request.getSession().getAttribute("register") != null) {
			request.setCharacterEncoding("UTF-8");
			String time_out = request.getParameter("time_out");
			String time_in = request.getParameter("time_in");
			String num = request.getParameter("num");
			String route = request.getParameter("route");
			JournalData record = new JournalData(0, time_out, time_in, num, route);
			JournalDB.insert(record);
			response.sendRedirect("/MyFirstJavaEEProject/Journal");
		} else {
			response.sendRedirect("/MyFirstJavaEEProject/Authentication");
		}
		}catch (Exception e) {
			request.setAttribute("error", true);
			
			ArrayList<AutoData> auto = AutoDB.selectAll();
			request.setAttribute("auto", auto);
			ArrayList<RouteData> routes = RouteDB.selectAll();
			request.setAttribute("routes", routes);
			response.setContentType("text/html; charset=UTF-8");
			getServletContext().getRequestDispatcher("/WEB-INF/journal_create_en.jsp").forward(request, response);
		}
	}

}
