var app = angular.module("app", ['ui.bootstrap', 'ngRoute', 'ngCart', 'routeAppControllers', 'slick', 'angularUtils.directives.dirPagination', 'elasticsearch']);

var routeAppControllers = angular.module('routeAppControllers', []);

app.controller("headerCtrl", function($scope, ngCart, $rootScope, elasticSearchSuggestion){
	

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
	    	console.log(resp);
	    	var auteurs = resp.suggest_auteur[0].options;
	    	var titres = resp.suggest_titre[0].options;
	    	$("#barreRecherche").empty();
	    	
	    	for(var i = 0; i < titres.length; i++)
	    		$("#barreRecherche").append("<option ng-selected=\"rechercheBarre()\"  value='" + titres[i]._source.titre.trim() + "'>");
	    	
	    	for(var i = 0; i < auteurs.length; i++)
	    		$("#barreRecherche").append("<option ng-selected=\"rechercheBarre()\" value='" + auteurs[i]._source.auteurs.trim() + "'>");
	    	
		});
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


app.controller("footerCtrl", function($scope){

    //...    

});


app.controller("menuCtrl", function($scope, $rootScope){

	$scope.range = function(min, max, step) {
        step = step || 1;
        var input = [];
        for (var i = min; i <= max; i += step) {
            input.push(i);
        }
        return input;
    };
    
	$scope.rechercheMenu = function(){
		
		window.location.href = "#/recherche/"+$rootScope.req+"/"+$rootScope.genre+"/"+$rootScope.minPrix+"/"+$rootScope.maxPrix+"/"+$rootScope.avisMin;
		
	}
	$scope.setGenre = function(genre){
		$rootScope.genre = genre;
	}
	$scope.setAvisMin = function(event){
		
		$rootScope.avisMin = event.currentTarget.attributes.value.value;
	}
	$scope.setPrixMin = function(p){
		if(p)
			$rootScope.minPrix = p;//$("menuPrixMin").val();
		else
			$rootScope.minPrix = -1;
	}
	$scope.setPrixMax = function(p){
		if(p)
			$rootScope.maxPrix = p;//$("menuPrixMax").val();
		else
			$rootScope.maxPrix = -1;
	}
	
});

app.controller("searchCtrl", function($scope){

	  

});

app.controller("paiementCtrl", function($scope){
	$scope.mois = [
		{nom : "Janvier", valeur : "1"},
		{nom : "Février", valeur : "2"},
		{nom : "Mars", valeur : "3"},
		{nom : "Avril", valeur : "4"},
		{nom : "Mai", valeur : "5"},
		{nom : "Juin", valeur : "6"},
		{nom : "Juillet", valeur : "7"},
		{nom : "Aout", valeur : "8"},
		{nom : "Septembre", valeur : "9"},
		{nom : "Octobre", valeur : "10"},
		{nom : "Novembre", valeur : "11"},
		{nom : "Décembre", valeur : "12"},
	];

});


app.controller("pageChange", function($scope){
	/*$scope.pageChangeHandler = function(num) {
		alert(num);
	};*/
});

routeAppControllers.controller("infoCtrl", function($scope, $routeParams, $http,$document){
    $http.get("LivreAvecId", {params:{"id": $routeParams.id}}).then(function(response) {
    	$scope.livre = response.data;
    	$scope.moyenne = $scope.calculeMoyenne($scope.livre.lesAvis);
    	$scope.livre.dateDePublication = formatDateDMY($scope.livre.dateDePublication);
    	var premierePartieResume = $scope.livre.resume.substring(0, 350);
    	var deuxiemePartieResume = $scope.livre.resume.substring(350, $scope.livre.resume.length);
    	
    	var index = deuxiemePartieResume.search('\\.|!|\\?') + 1;
    	
    	
    	
    	var tmp = deuxiemePartieResume.substr(0, index);
    	$scope.premierePartieResume = premierePartieResume + tmp;
    	$scope.deuxiemePartieResume = deuxiemePartieResume.substr(index, deuxiemePartieResume.length);
    });
    
    $scope.init = function(moy, id){
    	var str = '#input-'+id;
    	$(str).rating({displayOnly: true, step: 0.1, size:'xl'});
    	$(str).rating('update', moy);
    }
    
    $scope.range = function(min, max, step) {
        step = step || 1;
        var input = [];
        for (var i = min; i <= max; i += step) {
            input.push(i);
        }
        return input;
    };
    
    $scope.formatDateDMY = formatDateDMY;
    
    $scope.calculPromo = function(prix, promo) {
    	return roundPrix(prix-(prix*promo)/100);
    }
    
    $scope.calculeMoyenne = function(list) {
    	var moy = 0;
    	for(i=0; i < list.length; i++)
    		moy += list[i].note;
    	return (moy / list.length).toFixed(1);
    }
    
    $scope.nombreAvis = function(list) {
    	return list.length;
    }
});


routeAppControllers.controller("contentCtrl", function($scope, $http,$rootScope){
	
	$rootScope.req = "@";
	$rootScope.genre = "@"; 
	$rootScope.minPrix = -1;
	$rootScope.maxPrix = -1;
	$rootScope.avisMin = -1;
	
    $http.get("Promos").then(function(response) {
        $scope.livresPromo = response.data;
        
    });
    
    $http.get("PlusVendu").then(function(response) {
        $scope.livresPlusVendu = response.data;
        
    });
    
    $http.get("Recherche").then(function(response) {
        $scope.livresRecherche = response.data;
        
    });
    
    $scope.calculeMoyenne = function(list) {
    	var moy = 0;
    	if(list.length == 0)
    		return "Pas d'avis";
    	for(i=0; i < list.length; i++)
    		moy += list[i].note;
    	return (moy / list.length).toFixed(1) + "/5 ("+list.length+")";
    	
    }
    
    $scope.calculPromo = function(prix, promo) {
    	return roundPrix(prix-(prix*promo)/100);
    }
    
});

routeAppControllers.controller("recherche", function($scope, $http, $routeParams,$rootScope){
    
	$scope.calculPromo = function(prix, promo) {
    	return roundPrix(prix-(prix*promo)/100);
    }
	
	$http.get("RechercheViaBarre", {params:{"req": $routeParams.req, "genre": $routeParams.genre, "pmin": $routeParams.pmin, "pmax": $routeParams.pmax, "avisMin": $routeParams.avisMin}}).then(function(response){
        
    	var data = response.data;
		$scope.livres=[];
    	for(var i=0; i<data.length;i++){
    		
    		var l = data[i].l
    		l.ventes = data[i].v;
    		l.prixPromo = (l.promotion) ? $scope.calculPromo(l.prix,l.promotion.tauxReduc) : l.prix;
    		$scope.livres.push(l);

    	}

		console.log($scope.livres.length);
        
    });
    
    $scope.currentPage = 1;
    $scope.pageSizeMos = 12;
    $scope.pageSizeList = 8;
    $scope.ordonneur = "titre";
    $scope.modeAffichage = "mosaique";


    /*$scope.pageChangeHandler = function(num) {
        alert(num);
    };*/
    
    $scope.calculeMoyenne = function(list) {
    	var moy = 0;
    	if(list.length == 0)
    		return "Pas d'avis";
    	for(i=0; i < list.length; i++)
    		moy += list[i].note;
    	return (moy / list.length).toFixed(1) + "/5 ("+list.length+")";
    	
    }
    
    $scope.changeOrdonneur = function(ordonneur) {
        $scope.ordonneur = ordonneur;
        alert(ordonneur);
    }
    
    

});

routeAppControllers.controller('corpsAccueilCtrl', ['$scope',
    function($scope){
        $scope.message = "Bienvenue sur la page d'accueil";
    }
]);



app.config(['$routeProvider',
    function($routeProvider) { 
        
        // Système de routage
        $routeProvider
        .when('/corpsAccueil', {
            templateUrl: 'partials/corpsAccueil2.html',
            controller: 'corpsAcceuilCtrl'
        })
        .otherwise({
            templateUrl: 'partials/corpsAccueil2.html',
            controller: 'contentCtrl'
        })
        .when('/info/:id', {
        	templateUrl: 'partials/info.html',
        	controller: 'infoCtrl'
        })
        .when('/recherche/:req/:genre/:pmin/:pmax/:avisMin', {
        	templateUrl: 'partials/listMosaique.html',
        	controller: 'recherche'
        })
        .when('/panier', {
        	templateUrl: 'partials/monPanier.html',
        	controller: 'contentCtrl'
        })
        .when('/connexion', {
        	templateUrl: 'partials/connexion.html',
        	controller: 'connexionCtrl'
        })
        .when('/paiement', {
        	templateUrl: 'partials/paiement.html',
        	controller: 'paiementCtrl'
        })
    }
]);

function roundPrix(prix){
	return Math.round(prix*100)/100
}

function formatDateDMY(str){
	var pattern = /(.{3})\s(\d{1,2}),\s(\d{4})(.*)/;
	var mois = str.replace(pattern, '$1');
	var j = str.replace(pattern, '$2');
	var an = str.replace(pattern, '$3');
	switch(mois){
	case "Jan" : mois = "01";break;
	case "Feb" : mois = "02";break;
	case "Mar" : mois = "03";break;
	case "Apr" : mois = "04";break;
	case "May" : mois = "05";break;
	case "Jun" : mois = "06";break;
	case "Jul" : mois = "07";break;
	case "Aug" : mois = "08";break;
	case "Sep" : mois = "09";break;
	case "Oct" : mois = "10";break;
	case "Nov" : mois = "11";break;
	case "Dec" : mois = "12";break;
	default : 	 mois = "??";break;
	}
	return j+"/"+mois+"/"+an;
}

app.service('elasticSearchSuggestion', function (esFactory) {
	  return esFactory({
	    host: 'localhost:9200'
	  });
});




