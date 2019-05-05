package com.redis.web;

import java.io.Serializable;

public class Etudiant implements Serializable{

	private Long id;
	private String nom;
	private String prenom;
	private String universite;
	
	public Etudiant() {
		
	}

	public Etudiant(Long id, String nom, String prenom, String universite) {
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.universite = universite;
	}
	
	public Etudiant(String nom, String prenom, String universite) {
		this.nom = nom;
		this.prenom = prenom;
		this.universite = universite;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getUniversite() {
		return universite;
	}

	public void setUniversite(String universite) {
		this.universite = universite;
	}
	
	public String toString() {
		return this.id + "&&" + this.nom + "&&" + this.prenom + "&&" + this.universite;
	}
	
	
	
}
