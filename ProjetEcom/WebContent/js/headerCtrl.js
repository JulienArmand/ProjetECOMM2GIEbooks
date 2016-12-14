app.controller("headerCtrl", function($scope, ngCart, $rootScope, elasticSearchSuggestion, $http, userService){
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
	    	var auteurs = resp.suggest_auteur[0].options.sort();
	    	var titres = resp.suggest_titre[0].options.sort();
	    	
	    	var total = [];
	    	for(var i = 0; i < titres.length; i++)
	    		total.push(titres[i]._source.titre.trim());
	    	
	    	for(var j = 0; j < auteurs.length; j++)
	    		total.push(auteurs[j]._source.auteurs.trim());
	    	
	    	total = total.sort();
	    	for(var k = 0; k < total.length-1; k++){
	    		if(total[k] !== undefined){
	    			for(var l=k+1; k < total.length && total[k] === total[l];l++)
	    				total[l] = undefined;
	    		}
	    	}
	    	$("#barreRecherche").empty();
	    	
	    	for(var m = 0; m < total.length; m++)
	    		if(total[m] !== undefined)
	    			$("#barreRecherche").append("<option ng-selected=\"rechercheBarre()\"  value='" + total[m]+ "'>");
		});
	}
	
	if(userService.isConnected()) {
		$http.get("GestionCommande", {
			params:{"action" :"commandeClient"}}).then(function(response) {
				$rootScope.commandes = response.data;
		});
	}
	
	$scope.redirection = function(){
		window.location.href = "index.html";
	}
	
	// DECONNEXION DANS SERVICE
	$scope.deconnexion = function () {
		userService.signOut();
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
