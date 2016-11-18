var app = angular.module("app", ['ui.bootstrap', 'ngRoute', 'ngCart', 'routeAppControllers', 'slick']);

var routeAppControllers = angular.module('routeAppControllers', []);

app.controller("headerCtrl", function($scope, ngCart){

    //...    

});


app.controller("footerCtrl", function($scope){

    //...    

});


app.controller("menuCtrl", function($scope){

	  

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


routeAppControllers.controller("contentCtrl", function($scope, $http){
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
        .when('/panier', {
        	templateUrl: 'partials/monPanier.html',
        	controller: 'contentCtrl'
        })
        .when('/connexion', {
        	templateUrl: 'partials/connexion.html',
        	controller: 'connexionCtrl'
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