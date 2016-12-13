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
        for(var l of $scope.livres){
        	var str = "";
        	for(var a of l.lesAuteurs){
        		
        		str+= a.prenom+" " + a.nom +","
        	}
        	l.lesAuteurs = str;
        }
        
    });
    
    
    $scope.calculeMoyenne = function(list) {
    	var moy = 0;
    	if(list.length === 0)
    		return "Pas d'avis";
    	for(var i = 0; i < list.length; i++)
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
            templateUrl: 'partialsAdmin/formulaires.html',
            controller: 'contentCtrl'
        })
        .when('/info/:id', {
        templateUrl: 'partialsAdmin/info.html',
        controller: 'infoCtrl'
        })
        .when('/ajouterLivre',{
        	templateUrl : 'partialsAdmin/ajoutLivre.html',
        	controller: 'ajoutLivreCtrl'
        })
    }
]);






