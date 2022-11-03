package Controller.Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Model.BEANS.C_Administrateur;
import Model.BEANS.C_Utilisateur;
import Model.BEANS.Global;

/**
 * Servlet implementation class ModifierUtilisateur
 */
@WebServlet("/ModifierUtilisateur")
public class ModifierUtilisateur extends HttpServlet {
	private static final long serialVersionUID = 1L;
	C_Utilisateur uti = null;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModifierUtilisateur() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    // V�rifier champs vide
    private boolean estVide(String mdp, String nom, String prenom) {
    	
    	if(mdp == null || mdp.equals(""))
    		 return true;
    	
    	if(nom == null || nom.equals(""))
    		return true;
		   	
    	if(prenom == null || prenom.equals(""))
    		return true; 	
    	
    	return false;
    }
    
    // V�rifier regexp
    private String estValide(String nom, String prenom,
    		String mdp) {
    	
    	String erreur = null;
    	if(!nom.matches(Model.BEANS.Global.getLetterPattern()))
    		erreur = "nom";
    	
    	if(!prenom.matches(Model.BEANS.Global.getLetterPattern()))
    		erreur = "prenom";
    	
    	if(!mdp.matches(Model.BEANS.Global.getPasswordPattern()))
    		erreur = "mdp";
    	
    	return erreur;
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		// 0. Variables
		List<C_Utilisateur> liste = new LinkedList<>();
		
		// 1. R�cup�rer id utilisateur
		String strId = request.getParameter("id");
		int id = Integer.parseInt(strId);
		
		// 2. R�cup�rer donn�es utilisateur
		uti = new C_Utilisateur();
		uti = uti.trouver(id);
		
		// 3. R�cup�rer liste utilisateur(chef)
		liste = (List<C_Utilisateur>) C_Utilisateur.lister();
		
		// 4. Envoyer donn�es vers page
		request.setAttribute("utilisateur", uti);
		request.setAttribute("liste", liste);
		request.getRequestDispatcher("VIEWS/Administrateur/modifierUtilisateur.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		
		// 0. Variables
		response.setContentType("text/javascript");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = response.getWriter();
		C_Administrateur admin = null;
		
		// 1. Param�tres
		String nom = request.getParameter("nom");
		String prenom = request.getParameter("prenom");
		String mdp = request.getParameter("mdp");
		String strTypeUtilisateur = request.getParameter("typeUtilisateur");
		String typeBrigadier = request.getParameter("typeBrigadier");
			
		// 2. V�rifier champs vide
		if(estVide(mdp, nom, prenom)) {
			out.print("1");
			out.close();
		}
										
		// 3. V�rifier validit� des champs(regexp)
		else if(estValide(nom, prenom, mdp) != null) {
			out.print(estValide(nom, prenom, mdp));
			out.close();
		}				
					
		// 5. Modification des donn�es
		else {
			// 5.1 Crypter nouveau mdp				
			mdp = C_Utilisateur.crypterMdp(mdp);
			
			// 5.2 Conversion donn�es
			int typeUtilisateur = Global.tryParseInt(strTypeUtilisateur);
			
			// 5.3 R�cup�ration session courante
			HttpSession session = request.getSession();
			admin = (C_Administrateur) session.getAttribute("administrateur");
			
			// 5.4 Modification de l'objet			
			if(admin.modifierCompte(uti, mdp, nom, prenom, typeUtilisateur, typeBrigadier)) {
				out.print("0");
				out.close();
			}	
			
			else {
				out.print("2");
				out.close();
			}
		}
	}
}
