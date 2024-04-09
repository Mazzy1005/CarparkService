

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DataBaseExcange.DriverBonusData;
import DataBaseExcange.ReportsDB;

@WebServlet("/DriversBonus")
public class DriversBonus extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().setMaxInactiveInterval(-1);
		if(request.getSession().getAttribute("register")!=null)
		{
			String s =request.getParameter("sum");
			int sum = Integer.parseInt(s/* .substring(0, s.length()-1) */);
			ArrayList<DriverBonusData> data = ReportsDB.driversBonus(sum);
			request.setAttribute("data", data);
			response.setContentType("text/html; charset=UTF-8");
			getServletContext().getRequestDispatcher("/WEB-INF/drivers_bonus.jsp").forward(request, response);
		}
		else
		{
			response.sendRedirect("/MyFirstJavaEEProject/Authentication");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/plain");
		response.setHeader("Content-disposition", "attachment; filename=drivers_bonus.txt");

		File file = new File("C:\\Users\\yarma\\Study\\Базы данных\\Курсовая\\drivers_bonus.txt");

		OutputStream out = response.getOutputStream();
		FileInputStream in = new FileInputStream(file);

		byte[] buffer = new byte[4096];
		int length;

		while ((length = in.read(buffer)) > 0) {
			out.write(buffer, 0, length);
		}
		in.close();
		out.flush();
	}

}
