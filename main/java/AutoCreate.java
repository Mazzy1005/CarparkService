
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DataBaseExcange.AutoDB;
import DataBaseExcange.AutoData;
import DataBaseExcange.DriversDB;
import DataBaseExcange.DriversData;

@WebServlet("/AutoCreate")
public class AutoCreate extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getSession().getAttribute("register") != null) {
			ArrayList<DriversData> data = DriversDB.selectAll();
			request.setAttribute("drivers", data);
			response.setContentType("text/html; charset=UTF-8");
			getServletContext().getRequestDispatcher("/WEB-INF/auto_create_en.jsp").forward(request, response);
		} else {
			response.sendRedirect("/MyFirstJavaEEProject/Authentication");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getSession().getAttribute("register") != null) {
			request.setCharacterEncoding("UTF-8");
			String num = request.getParameter("num");
			String color = request.getParameter("color");
			String mark = request.getParameter("mark");
			String driver = request.getParameter("driver");
			AutoData auto = new AutoData(0, num, color, mark, driver);
			AutoDB.insert(auto);
			response.sendRedirect("/MyFirstJavaEEProject/Auto");
		} else {
			response.sendRedirect("/MyFirstJavaEEProject/Authentication");
		}
	}

}
