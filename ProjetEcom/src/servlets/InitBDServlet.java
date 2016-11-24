package servlets;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import beans.InitBean;
 
public class InitBDServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1205140827058517536L;
	
	@EJB()  //ou @EJB si nom par défaut 
	private InitBean myBean; 
	


	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		initBD();
		response.getWriter().println("BD initialisée");

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		initBD();
		response.getWriter().println("BD initialisée");
	}

	private void initBD() {

		//myBean.init();
		try {
			myBean.InitBDFromCSV();
		} catch (IOException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		/*try {
			userTransaction.begin();
			userTransaction.commit();
		} catch (NotSupportedException | SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HeuristicMixedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HeuristicRollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
	}
}
