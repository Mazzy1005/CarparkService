

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

@WebServlet("/AutoEdit")
public class AutoEdit extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getSession().getAttribute("register") != null) {
		String s =request.getParameter("id");
		int id = Integer.parseInt(s.substring(0, s.length()-1));
		AutoData auto = AutoDB.select(id);
		ArrayList<DriversData> drivers = DriversDB.selectAll();
		request.setAttribute("auto", auto);
		request.setAttribute("drivers", drivers);
		response.setContentType("text/html; charset=UTF-8");
		getServletContext().getRequestDispatcher("/WEB-INF/auto_edit.jsp").forward(request, response);
		} else {
			response.sendRedirect("/MyFirstJavaEEProject/Authentication");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getSession().getAttribute("register") != null) {
		request.setCharacterEncoding("UTF-8");
		
		String s =request.getParameter("id");
		int id = Integer.parseInt(s.substring(0, s.length()-1));
		String num = request.getParameter("num");
		String color = request.getParameter("color");
		String mark = request.getParameter("mark");
		String driver = request.getParameter("driver");
		AutoData auto = new AutoData(id, num, color, mark, driver);
		AutoDB.update(auto);
		response.sendRedirect("/MyFirstJavaEEProject/Auto");
		} else {
			response.sendRedirect("/MyFirstJavaEEProject/Authentication");
		}
	}

}
