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
<title>Page étudiant</title>
</head>
<body>
	<!-- @Content section -->
	<nav>
		<div class="nav-wrapper">
			<a href="${pageContext.servletContext.contextPath}/etudiants" class="brand-logo">Acceuil</a>
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
							<h5>${errorMessage }</h5>
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

			<div class="col s12">
				<table class="striped highlight centered responsive-table">
					<thead>
						<tr>
							<th>#</th>
							<th>Nom</th>
							<th>Diplome</th>
							<th>Université</th>
							<th colspan="2">Actions</th>
						</tr>
					</thead>

					<tbody>
						<c:forEach var="etudiant" items="${etudiants}">
							<tr>
								<td>${etudiant.id}</td>
								<td>${etudiant.nom}</td>
								<td>${etudiant.prenom}</td>
								<td>${etudiant.universite}</td>
								<td><a href="${pageContext.servletContext.contextPath}/form-etudiant?id=${etudiant.id}"
									class="waves-effect waves-grey btn green"> 
									<i class="edit material-icons left">edit</i> Modifier</a></td>
								<td>
									<form
									onsubmit="return confirm('Êtes-vous sûre de supprimer l\'étudiant?');"
										action="${pageContext.servletContext.contextPath}/form-etudiant"
										method="POST">
										<input type="hidden" name="action" value="delete" />
										<input type="hidden" name="id" value="${etudiant.id}" />
										<button type="submit" class="waves-effect waves-orange red btn waves-effect waves-light">
										<i class="delete material-icons left">add</i> Supprimer</button>
									</form>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>

		</div>




	</div>


	<!--JavaScript at end of body for optimized loading-->
	<script type="text/javascript"
		src="${pageContext.servletContext.contextPath}/static/js/materialize.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.servletContext.contextPath}/static/js/custom.js"></script>
</body>
</html>