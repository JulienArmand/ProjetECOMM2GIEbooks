var app = angular.module("app", ['ui.bootstrap', 'ngRoute', 'routeAppControllers']);

var routeAppControllers = angular.module('routeAppControllers', []);

app.controller("headerCtrl", function($scope){

    //...    

});


app.controller("footerCtrl", function($scope){

    //...    

});


app.controller("menuCtrl", function($scope){

    //...    

});

routeAppControllers.controller("infoCtrl", function($scope, $routeParams, $http){
    $http.get("LivreAvecId", {params:{"id": $routeParams.id}}).then(function(response) {
    	$scope.livre = response.data;
    });   

});


routeAppControllers.controller("contentCtrl", function($scope, $http){
    $http.get("Promos").then(function(response) {
        $scope.livres = response.data;
        
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
            templateUrl: 'partials/corpsAccueil.html',
            controller: 'corpsAcceuilCtrl'
        })
        .otherwise({
            templateUrl: 'partials/corpsAccueil.html',
            controller: 'contentCtrl'
        })
        .when('/info/:id', {
        templateUrl: 'partials/info.html',
        controller: 'infoCtrl'
      })
    }
]);

function roundPrix(prix){
	return Math.round(prix*100)/100
}







