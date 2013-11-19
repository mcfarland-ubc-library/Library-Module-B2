package ca.ubc.ctlt.ubclibrary;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import blackboard.data.user.User;
import blackboard.platform.context.Context;
import blackboard.platform.context.ContextManagerFactory;

public class UBCLibraryServlet extends HttpServlet {

	/** Autogenerated */
	private static final long serialVersionUID = 4573813969069003278L;
	private boolean optsExist = false;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		if (!optsExist) {
			if (getParam("url").length() <= 0) {
				setParam("url", "https://dev-cr.library.ubc.ca/librarytab");
			}
			if (getParam("secret_key").length() <= 0) {
				setParam("secret_key", "librarytabsecret");
			}
			optsExist = true;
		}
		
		if (request.getParameter("url") != null) {
			setParam("url", request.getParameter("url").toString());
		}
		if (request.getParameter("secret_key") != null) {
			setParam("secret_key", request.getParameter("secret_key").toString());
		}
		
		RequestDispatcher dispatcher = null;
		Context ctx = ContextManagerFactory.getInstance().getContext();
        User user = ctx.getUser();
        
        if (user != null && user.getSystemRole().equals(User.SystemRole.SYSTEM_ADMIN)) {
    		request.setAttribute("url", getParam("url"));
    		request.setAttribute("secret_key", getParam("secret_key"));
    		dispatcher = request.getRequestDispatcher("/WEB-INF/view/index.jsp");
        }
        else {
        	dispatcher = request.getRequestDispatcher("/WEB-INF/view/auth.jsp");
        }
        
        if (dispatcher != null) {
			dispatcher.forward(request, response);
		}
	}
	
	public String getParam(String param) {
		UBCLibraryDao dao = new UBCLibraryDao();
		String optvalue = dao.getOption(param);
		return optvalue;
	}
	
	public void setParam(String param, String value) {
		UBCLibraryDao dao = new UBCLibraryDao();
		dao.setOption(param, value);
	}
}
