
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DataBaseExcange.*;

@WebServlet("/JournalEdit")
public class JournalEdit extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getSession().getAttribute("register") != null) {
			String s = request.getParameter("id");
			int id = Integer.parseInt(s.substring(0, s.length() - 1));
			JournalData record = JournalDB.select(id);
			request.setAttribute("record", record);
			ArrayList<AutoData> auto = AutoDB.selectAll();
			request.setAttribute("auto", auto);
			ArrayList<RouteData> routes = RouteDB.selectAll();
			request.setAttribute("routes", routes);
			response.setContentType("text/html; charset=UTF-8");
			getServletContext().getRequestDispatcher("/WEB-INF/journal_edit_en.jsp").forward(request, response);
		} else {
			response.sendRedirect("/MyFirstJavaEEProject/Authentication");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int id;
		try {
			if (request.getSession().getAttribute("register") != null) {
				request.setCharacterEncoding("UTF-8");
				String s = request.getParameter("id");
				id = Integer.parseInt(s.substring(0, s.length() - 1));
				String time_out = request.getParameter("time_out");
				String time_in = request.getParameter("time_in");
				String num = request.getParameter("num");
				String route = request.getParameter("route");
				JournalData record = new JournalData(id, time_out, time_in, num, route);

				JournalDB.update(record);

				response.sendRedirect("/MyFirstJavaEEProject/Journal");
			} else {
				response.sendRedirect("/MyFirstJavaEEProject/Authentication");
			}
		} catch (Exception e) {
			request.setAttribute("error", true);
			String s = request.getParameter("id");
			id = Integer.parseInt(s.substring(0, s.length() - 1));
			JournalData record = JournalDB.select(id);
			request.setAttribute("record", record);
			ArrayList<AutoData> auto = AutoDB.selectAll();
			request.setAttribute("auto", auto);
			ArrayList<RouteData> routes = RouteDB.selectAll();
			request.setAttribute("routes", routes);
			response.setContentType("text/html; charset=UTF-8");
			getServletContext().getRequestDispatcher("/WEB-INF/journal_edit_en.jsp").forward(request, response);
		}
	}

}
