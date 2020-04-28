package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.CheckUserLogic;
import model.User;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// jsp
		String nickName = request.getParameter("nickName");
		String password = request.getParameter("password");
		// user
		User user = new User(nickName, password);
		// CheckUserLogic
		CheckUserLogic checkUser = new CheckUserLogic();
		// executenullDAOUser
		User findedUser = checkUser.execute(user);
		// findedUserView
		if (findedUser != null) {
			// UsermainPage
			request.setAttribute("findedUser", findedUser);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/mainPage.jsp");
			dispatcher.forward(request, response);
		} else {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/loginFail.jsp");
			dispatcher.forward(request, response);
		}
 	}

}
