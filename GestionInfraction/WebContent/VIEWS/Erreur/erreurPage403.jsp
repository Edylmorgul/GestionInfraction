<!-- Header - informations -->
<%@include file="../Layout/header.jsp"%>
<% response.setStatus(403); %>
	<header>
		<!-- Titre -->
        <h1>Erreur 403</h1>
        <div class="lineTitre"></div>
	</header>
	
	<div class="text-center">
		<p> Bonjour, Vous n'avez pas le droit d'acc�der � la page demand�e !<br>
		Veuillez retourner � la page d'accueil et contacter l'administrateur ! ==> <a href="${pageContext.request.contextPath}/GererErreur">Accueil</a> </p>
	</div>
	
	<!-- Footer - informations -->
	<%@include file="../Layout/footer.jsp" %>