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


routeAppControllers.controller("contentCtrl", function($scope, $http){
    $http.get("First").then(function(response) {
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
});

routeAppControllers.controller('corpAcceuilCtrl', ['$scope',
    function($scope){
        $scope.message = "Bienvenue sur la page d'accueil";
    }
]);

app.config(['$routeProvider',
    function($routeProvider) { 
        
        // Système de routage
        $routeProvider
        .when('/corpAcceuil', {
            templateUrl: 'partials/corpAcceuil.html',
            controller: 'corpAcceuilCtrl'
        })
        .otherwise({
            templateUrl: 'partials/corpAcceuil.html',
            controller: 'contentCtrl'
        })
    }
]);








