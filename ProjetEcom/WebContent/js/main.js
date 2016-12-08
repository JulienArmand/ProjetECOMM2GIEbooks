var app = angular.module("app", ['ui.bootstrap', 'ngRoute', 'ngCart', 'routeAppControllers', 'slick', 'angularUtils.directives.dirPagination', 'elasticsearch']);

var routeAppControllers = angular.module('routeAppControllers', []);

app.controller("footerCtrl", function($scope){

    // ...

});



app.controller('ExampleController', ['$scope', function($scope) {
	$scope.items = ['connecte', 'visiteur','autre'];
	  $scope.selection = $scope.items[1];
	  $scope.list = [];
      $scope.text = 'hello';
      $scope.submit = function() {
        if ($scope.text) {
          $scope.list.push(this.text);
          $scope.text = '';
        }
      };
}]);


app.controller('CookiesCtrl', ['$cookies', function($cookies) {
	  // Retrieving a cookie
	  var favoriteCookie = $cookies.get('myFavorite');
	  // Setting a cookie
	  $cookies.put('myFavorite', 'oatmeal');
}]);

app.controller("coDecoCtrl", function($scope){

	$scope.getInclude = function(){
		return "'partials/loginView.html'";
	  
	}

});


app.controller("searchCtrl", function($scope){

	  

});

app.controller("pageChange", function($scope){
	/*
	 * $scope.pageChangeHandler = function(num) { alert(num); };
	 */
});

app.config(['$routeProvider',
    function($routeProvider) { 
        
        // Système de routage
        $routeProvider
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
        	controller: 'paiementCtrl'
        })
        .when('/paiement', {
        	templateUrl: 'partials/paiement.html',
        	controller: 'paiementCtrl'
        })
        .when('/connexion', {
        	templateUrl: 'partials/connexion.html',
        	controller: 'connexionCtrl'
        })
        .when('/confirmation', {
        	templateUrl: 'partials/confirmation.html',
        	controller: 'paiementCtrl'
        })
        .when('/inscription', {
        	templateUrl: 'partials/registerView.html',
        	controller: 'inscriptionCtrl'
        })
        .when('/compteClient',{
        	templateUrl : 'partials/CompteClient.html',
        	controller: 'compteClient'
        })
        .when('/modificationMotDePasse',{
        	templateUrl : 'partials/ModificationMotDePasse.html',
        	controller: 'modificationMotDePasse'
        })
        .when('/ajouterLivre',{
        	templateUrl : 'partialsAdmin/ajoutLivre.html',
        	controller: 'ajoutLivreCtrl'
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

function estEnPromo (promo) {
	if(promo != undefined) {
		var dateDebutPromo = new Date(promo.dateDebut).getTime();
		var dateFinPromo = new Date(promo.dateFin).getTime();
		var dateActuelle = new Date().getTime();
		if(dateDebutPromo <= dateActuelle && dateFinPromo >= dateActuelle) {
			return true;
		}
		else return false;
	}
	else return false;
}

function calculPromo (prix, promo) {
	return roundPrix(prix-(prix*promo)/100);
}

app.service('elasticSearchSuggestion', function (esFactory) {
	  return esFactory({
	    host: window.location.hostname+':9200'
	  });
});

function getCookie(sName) {
    var oRegex = new RegExp("(?:; )?" + sName + "=([^;]*);?");
    if (oRegex.test(document.cookie)) {
    	return decodeURIComponent(RegExp["$1"]);
    } else {
    	return null;
    }
}

