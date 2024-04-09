

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




@WebServlet("/BonusSum")
public class BonusSum extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().setMaxInactiveInterval(-1);
		if(request.getSession().getAttribute("register")!=null)
		{
			response.setContentType("text/html; charset=UTF-8");
			getServletContext().getRequestDispatcher("/WEB-INF/bonus_sum.jsp").forward(request, response);
		}
		else
		{
			response.sendRedirect("/MyFirstJavaEEProject/Authentication");
		}
	}

}
