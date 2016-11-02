var app = angular.module("appAdmin", ['ui.bootstrap', 'ngRoute', 'routeAppControllers']);

var routeAppControllers = angular.module('routeAppControllers', []);

routeAppControllers.controller("infoCtrl", function($scope, $routeParams, $http){
    $http.get("LivreAvecId", {params:{"id": $routeParams.id}}).then(function(response) {
    	$scope.livre = response.data;
    	$scope.livre.dateDePublication = formatDateDMY($scope.livre.dateDePublication);
    });   

    $scope.calculPromo = function(prix, promo) {
    	return roundPrix(prix-(prix*promo)/100);
    }
    
});


routeAppControllers.controller("contentCtrl", function($scope, $http){
    $http.get("Promos").then(function(response) {
        
        var livres = response.data;
        $scope.livres = livres;
        for(l of $scope.livres){
        	var str = "";
        	for(a of l.lesAuteurs){
        		
        		str+= a.prenom+" " + a.nom +","
        	}
        	l.lesAuteurs = str;
        }
        
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
            templateUrl: 'partialsAdmin/mainContent.html',
            controller: 'corpsAcceuilCtrl'
        })
        .otherwise({
            templateUrl: 'partialsAdmin/mainContent.html',
            controller: 'contentCtrl'
        })
        .when('/info/:id', {
        templateUrl: 'partialsAdmin/info.html',
        controller: 'infoCtrl'
      })
    }
]);

function roundPrix(prix){
	return Math.round(prix*100)/100
}

function formatDateDMY(str){
	var pattern = /(.{3})\s(\d{2}),\s(\d{4})(.*)/;
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




