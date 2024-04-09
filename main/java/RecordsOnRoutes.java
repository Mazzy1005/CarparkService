

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

import DataBaseExcange.RecordTimeData;
import DataBaseExcange.ReportsDB;

@WebServlet("/RecordsOnRoutes")
public class RecordsOnRoutes extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().setMaxInactiveInterval(-1);
		if(request.getSession().getAttribute("register")!=null)
		{
			ArrayList<RecordTimeData> data = ReportsDB.recordTime();
			request.setAttribute("data", data);
			response.setContentType("text/html; charset=UTF-8");
			getServletContext().getRequestDispatcher("/WEB-INF/record_time_on_routes.jsp").forward(request, response);
		}
		else
		{
			response.sendRedirect("/MyFirstJavaEEProject/Authentication");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/plain");
		response.setHeader("Content-disposition", "attachment; filename=record_time_on_routes.txt");

		File file = new File("C:\\Users\\yarma\\Study\\Базы данных\\Курсовая\\record_time_on_routes.txt");

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
