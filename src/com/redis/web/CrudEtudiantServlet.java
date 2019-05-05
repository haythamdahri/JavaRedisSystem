package com.redis.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import redis.clients.jedis.Jedis;

@WebServlet("/form-etudiant")
public class CrudEtudiantServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final static String SERVER_ENDPOINT = "localhost";

	private Jedis jedis;

	@PostConstruct
	public void initServlet() {
		this.jedis = new Jedis(this.SERVER_ENDPOINT);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Long id = request.getParameter("id") != null ? Long.parseLong(request.getParameter("id")) : null;

		if (id == null) {
			// Redireger l'utilisateur pour ajouter un nouveau etudiant
			request.setAttribute("etudiant", new Etudiant());
			request.getRequestDispatcher("form-etudiant.jsp").forward(request, response);

		} else {
			// Chercher les étudiants dans la base de données
			Set<String> setEtudiants = this.jedis.zrange("etudiants", 0, id);

			// Mapper les données
			List<Etudiant> etudiants = this.etudiantsMapper(this.jedis.zrange("etudiants", 0, id));

			// Chercher l'étudiant
			Optional etudiant = etudiants.stream().filter(etd -> etd.getId() == id).findFirst();

			// Envoyer l'étudiant s'il est trouvé vers la vue
			if (etudiant.isPresent()) {
				request.setAttribute("etudiant", etudiant.get());
				request.getRequestDispatcher("form-etudiant.jsp").forward(request, response);
			} else {
				HttpSession session = request.getSession();
				session.setAttribute("errorMessage", "Étudiant avec id=" + id + " n'éxiste pas!");
				response.sendRedirect(request.getContextPath() + "/etudiants");
			}
		}

	}

	/*
	 * @Mapper les étudiants à partir des chaines de caractéres
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

		HttpSession session = request.getSession();

		// Capter id d'étudiant
		String id = request.getParameter("id");
		String nom = request.getParameter("nom");
		String prenom = request.getParameter("prenom");
		String universite = request.getParameter("universite");
		Etudiant etudiant = new Etudiant(nom, prenom, universite);

		if (request.getParameter("action") != null && request.getParameter("action").equalsIgnoreCase("save")) {
			if (id != null && !id.equals("")) {
				// L'étudiant est la chaine de caractére stockée, c'est le string à passer en
				// paramétres
				Long idEtudiant = Long.parseLong(id);
				etudiant.setId(idEtudiant);
				this.jedis.zremrangeByScore("etudiants", etudiant.getId(), etudiant.getId());
				this.jedis.zadd("etudiants", etudiant.getId(), etudiant.toString());
			} else {
				etudiant.setId(getavailableId());
				this.jedis.zadd("etudiants", etudiant.getId(), etudiant.toString());
			}
			// Redireger l'utilisateur vers la liste des étudiants
			session.setAttribute("successMessage", "Étudiant est enregistré avec succé");
		} else if( request.getParameter("action") != null && request.getParameter("action").equalsIgnoreCase("delete") ) {
			this.jedis.zremrangeByScore("etudiants", id, id);
			// Redireger l'utilisateur vers la liste des étudiants
			session.setAttribute("successMessage", "Étudiant est supprimé avec succé!");
		} 
		
		response.sendRedirect(request.getContextPath() + "/etudiants");
		

	}

	private List<Etudiant> etudiantsMapper(Set<String> setEtudiants) {
		List<Etudiant> etudiants = new ArrayList<>();
		setEtudiants.forEach(etudiant -> {
			String[] etudiantString = etudiant.split("&&");
			for (int i = 0; i < etudiantString.length; i++) {
				Etudiant etd = new Etudiant();
				etd.setId(Long.parseLong(etudiantString[i++]));
				etd.setNom(etudiantString[i++]);
				etd.setPrenom(etudiantString[i++]);
				etd.setUniversite(etudiantString[i++]);
				etudiants.add(etd);
			}
		});
		return etudiants;
	}

	private Long getavailableId() {
		List<Etudiant> etudiants = this.etudiantsMapper(this.jedis.zrange("etudiants", 0, -1));
		return etudiants.get(etudiants.size() - 1).getId() + 1;
	}

//	public void service(HttpServletRequest request, HttpServletResponse response) {
//
//	}

}
