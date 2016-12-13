var app = angular.module("app", [ 'ui.bootstrap', 'ngRoute', 'ngCart',
		'routeAppControllers', 'slick',
		'angularUtils.directives.dirPagination', 'elasticsearch' ]);

var routeAppControllers = angular.module('routeAppControllers', []);

app.controller("coDecoCtrl", function($scope) {

	$scope.getInclude = function() {
		return "'partials/loginView.html'";

	}

});

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
	}).when('/ajouterLivre',{
    	templateUrl : 'partialsAdmin/ajoutLivre.html',
    	controller: 'ajoutLivreCtrl'
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

app.service('elasticSearchSuggestion', function (esFactory) {
	  return esFactory({
	    host: '152.77.78.20:9200'
	  });
});

function dechiffrageString(value) {
	return value.split('').reverse().join('');
}

function getCookie(sName) {
	var oRegex = new RegExp("(?:; )?" + sName + "=([^;]*);?");
	if (oRegex.test(document.cookie)) {
		return dechiffrageString(decodeURIComponent(RegExp["$1"]));
	} else {
		return null;
	}
}