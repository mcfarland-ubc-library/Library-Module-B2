package ca.ubc.ctlt.ubclibrary;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UBCLibraryServlet extends HttpServlet {

	/** Autogenerated */
	private static final long serialVersionUID = 4573813969069003278L;
	private boolean optsExist = false;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		if (!optsExist) {
			if (!isSkipencrypt()) {
				setSkipencrypt("false");
			}
			if (getUrl().length() <= 0) {
				setUrl("https://dev-cr.library.ubc.ca/librarytab");
			}
			optsExist = true;
		}
		
		if (request.getParameter("encrypt") != null) {
			setSkipencrypt(request.getParameter("encrypt").toString());
		}
		if (request.getParameter("url") != null) {
			setUrl(request.getParameter("url").toString());
		}
		
		// pass on request to index.jsp
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/index.jsp");
		if (dispatcher != null) {
			dispatcher.forward(request, response);
		}
	}

	public boolean isSkipencrypt() {
		UBCLibraryDao dao = new UBCLibraryDao();
		String optvalue = dao.getOption("encrypt");
		return optvalue.equals("true");
	}

	public void setSkipencrypt(String skipencrypt) {
		UBCLibraryDao dao = new UBCLibraryDao();
		dao.setOption("encrypt", skipencrypt);
	}
	
	public String getUrl() {
		UBCLibraryDao dao = new UBCLibraryDao();
		String optvalue = dao.getOption("URL");
		return optvalue;
	}
	
	public void setUrl(String url) {
		UBCLibraryDao dao = new UBCLibraryDao();
		dao.setOption("URL", url);
	}
}