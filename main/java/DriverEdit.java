
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DataBaseExcange.*;

@WebServlet("/DriverEdit")
public class DriverEdit extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getSession().getAttribute("register") != null) {
			String s = request.getParameter("id");
			int id = Integer.parseInt(s.substring(0, s.length() - 1));
			DriversData driver = DriversDB.select(id);
			request.setAttribute("driver", driver);
			response.setContentType("text/html; charset=UTF-8");
			getServletContext().getRequestDispatcher("/WEB-INF/driver_edit_en.jsp").forward(request, response);
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
			String fiName = request.getParameter("first_name");
			String lName = request.getParameter("last_name");
			String faName = request.getParameter("father_name");
			DriversData driver = new DriversData(id, fiName, lName, faName);
			DriversDB.update(driver);
			response.sendRedirect("/MyFirstJavaEEProject/Drivers");
		} else {
			response.sendRedirect("/MyFirstJavaEEProject/Authentication");
		}
	}

}
