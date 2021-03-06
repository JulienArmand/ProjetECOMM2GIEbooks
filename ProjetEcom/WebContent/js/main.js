var app = angular.module("app", [ 'ui.bootstrap', 'ngRoute', 'ngCart',
		'routeAppControllers', 'slick',
		'angularUtils.directives.dirPagination', 'elasticsearch', 'ngResource', 'ngCookies' ]);

var routeAppControllers = angular.module('routeAppControllers', []);

app.controller("coDecoCtrl", function($scope) {

	$scope.getInclude = function() {
		return "'partials/loginView.html'";

	}

});

app.config([ '$resourceProvider', function($resourceProvider) {
	// Don't strip trailing slashes from calculated URLs
	$resourceProvider.defaults.stripTrailingSlashes = false;
} ]);

app.config([ '$routeProvider', function($routeProvider) {

	// Système de routage
	$routeProvider.otherwise({
		templateUrl : 'partials/corpsAccueil.html',
		controller : 'contentCtrl'
	}).when('/info/:id', {
		templateUrl : 'partials/info.html',
		controller : 'infoCtrl'
	}).when('/recherche/:req/:genre/:pmin/:pmax/:avisMin', {
		templateUrl : 'partials/listMosaique.html',
		controller : 'recherche'
	}).when('/panier', {
		templateUrl : 'partials/monPanier.html',
		controller : 'paiementCtrl'
	}).when('/paiement', {
		templateUrl : 'partials/paiement.html',
		controller : 'paiementCtrl'
	}).when('/connexion', {
		templateUrl : 'partials/connexion.html',
		controller : 'connexionCtrl'
	}).when('/confirmation', {
		templateUrl : 'partials/confirmation.html',
		controller : 'paiementCtrl'
	}).when('/inscription', {
		templateUrl : 'partials/registerView.html',
		controller : 'inscriptionCtrl'
	}).when('/compteClient', {
		templateUrl : 'partials/CompteClient.html',
		controller : 'compteClient'
	}).when('/modificationMotDePasse', {
		templateUrl : 'partials/ModificationMotDePasse.html',
		controller : 'modificationMotDePasseCtrl'
	}).when('/ajouterLivre', {
		templateUrl : 'partialsAdmin/ajoutLivre.html',
		controller : 'ajoutLivreCtrl'
	})

} ]);

function roundPrix(prix) {
	return Math.round(prix * 100) / 100
}

function formatDateDMY(str) {
	var pattern = /(.{3})\s(\d{1,2}),\s(\d{4})(.*)/;
	var mois = str.replace(pattern, '$1');
	var j = str.replace(pattern, '$2');
	var an = str.replace(pattern, '$3');
	switch (mois) {
	case "Jan":
		mois = "01";
		break;
	case "Feb":
		mois = "02";
		break;
	case "Mar":
		mois = "03";
		break;
	case "Apr":
		mois = "04";
		break;
	case "May":
		mois = "05";
		break;
	case "Jun":
		mois = "06";
		break;
	case "Jul":
		mois = "07";
		break;
	case "Aug":
		mois = "08";
		break;
	case "Sep":
		mois = "09";
		break;
	case "Oct":
		mois = "10";
		break;
	case "Nov":
		mois = "11";
		break;
	case "Dec":
		mois = "12";
		break;
	default:
		mois = "??";
		break;
	}
	return j + "/" + mois + "/" + an;
}

function estEnPromo(promo) {
	if (promo !== undefined) {
		var dateDebutPromo = new Date(promo.dateDebut).getTime();
		var dateFinPromo = new Date(promo.dateFin).getTime();
		var dateActuelle = new Date().getTime();
		return (dateDebutPromo <= dateActuelle && dateFinPromo >= dateActuelle)
	} else
		return false;
}

function calculPromo(prix, promo) {
	return roundPrix(prix - (prix * promo) / 100);
}

app.service('elasticSearchSuggestion', function(esFactory) {
	return esFactory({
		host : '152.77.78.20:9200'
	});
});

app.service("userService", function($rootScope, $http, $cookies, dechiffrageChiffrage) {
    return {
    	isConnected: function() {
        	var estConnecte = false;
        	var login = $cookies.get('login');
        	if (login !== null && login !== "" && login !== undefined) {
        		estConnecte = true;
        	}
        	var erreur = $cookies.get('erreur');
        	if (erreur === "true") {
        		estConnecte = false;
        		$rootScope.commandes = null;
        	}
        	return estConnecte;
        },
        signIn: function(pseudo, mdp, idMessageErreur) {
        	
    		$http.get("ConnexionClient", {params:{"pseudo": pseudo, "motDePasse": mdp}}).then(function() {
    			var login = $cookies.get('login');
    			if (login !== null && login !== "" && login !== undefined) {
    				$http.get("GestionCommande", {
    					params:{"action" :"commandeClient"}}).then(function(response) {
    						$rootScope.commandes = response.data;
    					});	
    				document.getElementById(idMessageErreur).style.display = "none";
    			}
    			var erreur = $cookies.get('erreur');
    			if (erreur === "true") {
    				document.getElementById(idMessageErreur).style.display = "block";
    			}
    			$rootScope.$broadcast("connectionStateChanged");
    		});
        },
        signOut: function() {
    		$rootScope.commandes = null;
    		$cookies.remove("login");
    		$cookies.remove("idClient");
    		$cookies.remove("erreur");
            $rootScope.$broadcast("connectionStateChanged");
        }
    };
});

app.directive("affichageQuandConnecte", function (userService, dechiffrageChiffrage, $cookies) {
	    return {
	        restrict: 'A',
	        link: function (scope, element, attrs) {
	            var showIfConnected = function() {
	                if(userService.isConnected()) {
						scope.login = dechiffrageChiffrage.dechiffrageString($cookies.get('login'));
	                    $(element).show();
	                } else {
	                    $(element).hide();
	                }
	            };
	            showIfConnected();
	            scope.$on("connectionStateChanged", showIfConnected);
	        }
	    };
});

app.directive("affichageNonConnecte", function (userService) {
    return {
        restrict: 'A',
        link: function (scope, element, attrs) {
            var hideIfConnected = function() {
                if(userService.isConnected()) {
                    $(element).hide();
                } else {
                    $(element).show();
                }
            };
 
            hideIfConnected();
            scope.$on("connectionStateChanged", hideIfConnected);
        }
    };
});


app.service("dechiffrageChiffrage", function($rootScope, $http) {
    return {
    	chiffrageString: function(aChiffrer) {
    		return aChiffrer.split('').reverse().join('');
        },
        dechiffrageString: function(aDechiffrer) {
        	return aDechiffrer.split('').reverse().join('');
        }
    };
});