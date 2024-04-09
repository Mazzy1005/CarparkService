

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DataBaseExcange.DriversDB;
import DataBaseExcange.DriversData;

@WebServlet("/DriverCreate")
public class DriverCreate extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getSession().getAttribute("register") != null) {
		response.setContentType("text/html; charset=UTF-8");
		getServletContext().getRequestDispatcher("/WEB-INF/driver_create.jsp").forward(request, response);
		} else {
			response.sendRedirect("/MyFirstJavaEEProject/Authentication");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getSession().getAttribute("register") != null) {
		request.setCharacterEncoding("UTF-8");
		String fiName = request.getParameter("first_name");
		String lName = request.getParameter("last_name");
		String faName = request.getParameter("father_name");
		DriversData driver = new DriversData(0, fiName, lName, faName);
		DriversDB.insert(driver);
		response.sendRedirect("/MyFirstJavaEEProject/Drivers");
		} else {
			response.sendRedirect("/MyFirstJavaEEProject/Authentication");
		}
	}

}
