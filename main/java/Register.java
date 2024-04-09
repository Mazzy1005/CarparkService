

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DataBaseExcange.AuthenticationDB;
import DataBaseExcange.UserData;

@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		getServletContext().getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			request.setCharacterEncoding("UTF-8");
			String login = request.getParameter("login");
			String password = request.getParameter("password");
			UserData user = new UserData(login, password);
			AuthenticationDB.register(user);
			response.sendRedirect("/MyFirstJavaEEProject/Authentication");
		} catch (Exception e) {
			request.setAttribute("error", true);
			getServletContext().getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
		}
	}

}
