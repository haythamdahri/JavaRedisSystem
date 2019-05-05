<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<!--Import Google Icon Font-->
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet">
<!--Import materialize.css-->
<link type="text/css" rel="stylesheet"
	href="${pageContext.servletContext.contextPath}/static/css/materialize.min.css"
	media="screen,projection" />

<link type="text/css" rel="stylesheet"
	href="${pageContext.servletContext.contextPath}/static/css/style.css" />

<!--Let browser know website is optimized for mobile-->
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Form étudiant</title>
</head>
<body>
	<!-- @Content section -->
	<nav>
		<div class="nav-wrapper">
			<a href="${pageContext.servletContext.contextPath}/etudiants"
				class="brand-logo">Acceuil</a>
			<ul class="right hide-on-med-and-down">
				<li><a
					href="${pageContext.servletContext.contextPath}/form-etudiant">
						Ajouter Étudiant</a></li>
			</ul>
		</div>
	</nav>

	<div class="container">
		<!-- Page Content goes here -->
		<div class="row">


			<div class="col s12">
				<c:if test="${errorMessage != null}">
					<blockquote>
						<div class="card-panel white-text orange darken-4">
							<h5>${errorMessage}</h5>
						</div>
					</blockquote>
					<c:remove var="errorMessage" scope="session" />
				</c:if>
			</div>

			<div class="col s12">
				<c:if test="${successMessage != null}">
					<blockquote>
						<div class="card-panel white-text light-green darken-1">
							<h5>${successMessage }</h5>
						</div>
					</blockquote>
					<c:remove var="successMessage" scope="session" />
				</c:if>
			</div>

			<form class="col s12"
				action="${pageContext.servletContext.contextPath}/form-etudiant"
				method="POST">

				<!-- @Action -->
				<input type="hidden" name="action" value="save" />

				<div class="row">
					<div class="input-field col s12">
						<input type="hidden" placeholder="Placeholder" name="id"
							value="${etudiant.id}" id="id" type="text" class="validate">
					</div>
				</div>
				<div class="row">
					<div class="input-field col s12">
						<input id="nom" value="${etudiant.nom}" name="nom" type="text"
							required class="validate"> <label for="nom">Nom</label> <span
							class="helper-text" data-error="Nom est obligatoire" data-success="Validé"></span>
					</div>
				</div>
				<div class="row">
					<div class="input-field col s12">
						<input id="prenom" value="${etudiant.prenom}" name="prenom"
							required type="text" class="validate"> <label
							for="prenom">Prenom</label> <span class="helper-text"
							data-error="Prénom est obligatoire" data-success="Validé"></span>
					</div>
				</div>
				<div class="row">
					<div class="input-field col s12">
						<input id="universite" value="${etudiant.universite}"
							name="universite" type="text" class="validate" required>
						<label for="universite">Université</label> <span
							class="helper-text" data-error="Université est obligatoire" data-success="Validé"></span>
					</div>
				</div>
				<div class="row">
					<button class="btn waves-effect waves-light amber darken-4"
						type="submit" name="action">
						Sauvegarder <i class="material-icons right">send</i>
					</button>
				</div>
			</form>
		</div>




	</div>


	<!--JavaScript at end of body for optimized loading-->
	<script type="text/javascript"
		src="${pageContext.servletContext.contextPath}/static/js/materialize.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.servletContext.contextPath}/static/js/custom.js"></script>
</body>
</html>