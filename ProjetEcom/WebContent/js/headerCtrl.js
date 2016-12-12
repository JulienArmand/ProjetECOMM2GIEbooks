app.controller("headerCtrl", function($scope, ngCart, $rootScope, elasticSearchSuggestion, $http){
	$scope.suggestion = function() {
		var req = $("#schbox").val().replace(/[^\x00-\x7F]/g, "").replace("\"","").replace("'","");
		elasticSearchSuggestion.suggest({
	        index: "livres",
	        body: {
		        suggest_titre: {
		            text: req,
		            completion: {
		                field: "suggest_titre"
		            }
		        },
		        suggest_auteur:{
		            text: req,
		            completion: {
		                field : "suggest_auteurs"
		            }
		        }
	        }
	    }).then(function (resp) {
	    	var auteurs = resp.suggest_auteur[0].options;
	    	var titres = resp.suggest_titre[0].options;
	    	$("#barreRecherche").empty();
	    	
	    	for(var i = 0; i < titres.length; i++)
	    		$("#barreRecherche").append("<option ng-selected=\"rechercheBarre()\"  value='" + titres[i]._source.titre.trim() + "'>");
	    	
	    	for(var i = 0; i < auteurs.length; i++)
	    		$("#barreRecherche").append("<option ng-selected=\"rechercheBarre()\" value='" + auteurs[i]._source.auteurs.trim() + "'>");
	    	
		});
	}
	
	var login = getCookie('login');
	if (login !== null && login !== "") {
		$rootScope.login = login;
		$rootScope.estConnecte = true;
		$http.get("GestionCommande", {
			params:{"action" :"commandeClient"}}).then(function(response) {
				$rootScope.commandes = response.data;
			});	
	}
	var erreur = getCookie('erreur');
	if (erreur === "true") {
		$rootScope.estConnecte = false;
		$rootScope.commandes = null;
	}
	
	
	$scope.redirection = function(){
		window.location.href = "index.html";
	}
	
	$scope.deconnexion = function () {
		$rootScope.estConnecte = false;
		$rootScope.commandes = null;
		document.cookie = "login=; expires=Thu, 01 Jan 1970 00:00:00 GMT";
		document.cookie = "idClient=; expires=Thu, 01 Jan 1970 00:00:00 GMT";
		document.cookie = "erreur=; expires=Thu, 01 Jan 1970 00:00:00 GMT";
	}
	
	
	$rootScope.req = "@";
	$rootScope.genre = "@"; 
	$rootScope.minPrix = -1;
	$rootScope.maxPrix = -1;
	$rootScope.avisMin = -1;
	
	$scope.rechercheBarre = function(){
		
		$rootScope.req = $("#schbox").val();
		$rootScope.genre = "@"; 
		$rootScope.minPrix = -1;
		$rootScope.maxPrix = -1;
		$rootScope.avisMin = -1;
		
		
		window.location.href = "#/recherche/"+$rootScope.req+"/"+$rootScope.genre+"/"+$rootScope.minPrix+"/"+$rootScope.maxPrix+"/"+$rootScope.avisMin;
		
	}
});
