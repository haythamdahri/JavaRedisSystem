package com.redis.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import redis.clients.jedis.Jedis;

@WebServlet("/etudiants")
public class EtudiantsServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final static String SERVER_ENDPOINT = "localhost";
	
	private Jedis jedis;
	
	@PostConstruct
	public void initServlet() {
		this.jedis = new Jedis(this.SERVER_ENDPOINT);
		
		// Creer une map pour les étudiants et ses scores
		Map<Double, String> data = new HashMap<>();
		// Creer des étudants avec le score
		data.put((double)1, new Etudiant(1L, "DAHRI", "HAYTHAM", "IBN TOFAIL").toString());
		data.put((double)2, new Etudiant(2L, "KHADIRI", "HASSAN", "MOULAY ISMAIL").toString());
		data.put((double)3, new Etudiant(3L, "BABA", "LEILA", "MOULAY SLIMANE").toString());
		// Ajouter les étudiants dans la base de données
		this.jedis.zadd("etudiants", data);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Chercher les étudiants dans la base de données
		Set<String> setEtudiants = this.jedis.zrange("etudiants", 0, -1);
		
		// Mapper les données
		List<Etudiant> etudiants = this.etudiantsMapper(this.jedis.zrange("etudiants", 0, -1));
		
		// Envoyer les données vers la vue
		request.setAttribute("etudiants", etudiants);
		request.getRequestDispatcher("etudiants.jsp").forward(request, response);
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) {

	}
	
	private List<Etudiant> etudiantsMapper(Set<String> setEtudiants) {
		List<Etudiant> etudiants = new ArrayList<>();
		setEtudiants.forEach(etudiant -> {
			String[] etudiantString = etudiant.split("&&");
			for( int i=0; i<etudiantString.length; i++ ) {
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

//	public void service(HttpServletRequest request, HttpServletResponse response) {
//
//	}

}
