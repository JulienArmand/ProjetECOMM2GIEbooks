app.controller("ajoutLivreCtrl", function($scope, $http, $rootScope){
	
	$("#menu").hide();
	
	toggle_div = function (bouton, id) { // On déclare la fonction toggle_div
											// qui prend en param le bouton et
											// un id
		  var div = document.getElementById(id); // On récupère le div ciblé
													// grâce à l'id
		  if(div.style.display=="none") { // Si le div est masqué...
		    div.style.display = "block"; // ... on l'affiche...
		    bouton.innerHTML = "-"; // ... et on change le contenu du bouton.
		  } else { // S'il est visible...
		    div.style.display = "none"; // ... on le masque...
		    bouton.innerHTML = "+"; // ... et on change le contenu du bouton.
		  }
		}
		
	creerLivre = function(){
		$http.get("AjouterLivre", {
			params:{
				"titre" : $("#titre").val(),
				"editeur" : $scope.selectedEditeur,
				"genre" : $scope.selectedGenre,
				"isbn" : $("#isbn").val(),
				"nbPage" : $("#nbPage").val(),
				"prix" : $("#prix").val(),
				"lang" : $("#langue").val(),
				"langOrig" : $("#langOrig").val(),
				"couverture" : $("#couverture").val(),
				"resume" : $("#resume").val(),
				"datePub" : $("#datePub").val()
				}}).then(function(response) {				
				console.log("Livre ajoutée");
		});
	}
	
	$scope.changeRadio = function(idForm, bool){
		$(idForm).hidden(bool);
	}
	
	$scope.formulaires = {
		genre : "creation"
	}
	
	setGenre = function(str){
		$scope.formulaires.genre = str;
	}
	
	$scope.selectedGenreToModify = null;
	
	creerGenre = function() {
		$http.get("CreerGenreServlet", {
			params:{
				"action" : "creer",
				"nom" : $("#newGenre").val()
				}}).then(function(response) {				
				console.log("Genre ajoutée");
		});
	}
	
	modifierGenre = function() {
		$http.get("CreerGenreServlet", {
			params:{
				"action" : "modif",
				"id" : $scope.selectedGenreToModify,
				"nom" : $("#modifyGenre").val()
				}}).then(function(response) {				
				console.log("Genre modifier");
		});
	}
	
	creerAuteur = function() {
		$http.get("CreerAuteurServlet", {
			params:{
				"action" : "creer",
				"nom" : $("#newNomAuteur").val(),
				"prenom" : $("#newPrenomAuteur").val()
				}}).then(function(response) {				
				console.log("Auteur ajoutée");
		});
	}
	
	modifierAuteur = function() {
		$http.get("CreerAuteurServlet", {
			params:{
				"action" : "modif",
				"id" : $scope.selectedAuteurToModify,
				"nom" : $("#modifyNomAuteur").val(),
				"prenom" : $("#modifyPrenomAuteur").val()
				}}).then(function(response) {				
				console.log("Auteur modifier");
		});
	}
	
	creerEditeur = function() {
		$http.get("CreerEditeurServlet", {
			params:{
				"action" : "creer",
				"nom" : $("#newEditeur").val()
				}}).then(function(response) {				
				console.log("Editeur ajoutée");
		});
	}
	
	modifierEditeur = function() {
		$http.get("CreerEditeurServlet", {
			params:{
				"action" : "modif",
				"id" : $scope.selectedEditeurToModify,
				"nom" : $("#modifyEditeur").val()
				}}).then(function(response) {				
				console.log("Editeur modifier");
		});
	}
	
	creerPromotion = function() {
		$http.get("AjoutPromoServlet", {
			params:{
				"id" : $scope.selectedLivre,
				"taux" : $("#newTaux").val(),
				"dateD" : $("#newDateD").val(),
				"dateF" : $("#newDateF").val()
				}}).then(function(response) {				
				console.log("Promotion ajoutée");
		});
	}
	
		
	$http.get("GetTous", {params:{"action": "genres"}}).then(function(response) {				
		$scope.genres = response.data;
	});
	
	$scope.selectedGenre = null;
	$scope.selectedGenreToModify = null;
	
	$http.get("GetTous", {params:{"action": "auteurs"}}).then(function(response) {				
			$scope.auteurs = response.data;
	});
	
	$scope.selectedAuteur = null;
	$scope.selectedAuteurToModify = null;
	
	$http.get("GetTous", {params:{"action": "editeurs"}}).then(function(response) {				
		$scope.editeurs = response.data;
	});
	
	$scope.selectedEditeur = null;
	$scope.selectedEditeurToModify = null;
	
	$http.get("GetTous", {params:{"action": "promotions"}}).then(function(response) {				
		$scope.promotions = response.data;
	});
	
	$scope.selectedPromotion = null;
	$scope.selectedPromotionToModify = null;
	
	$http.get("GetTous", {params:{"action": "livres"}}).then(function(response) {				
		$scope.livres = response.data;
	});
	
	$scope.selectedLivre = null;
	
});