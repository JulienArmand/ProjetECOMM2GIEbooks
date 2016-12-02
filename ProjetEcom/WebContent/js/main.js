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
	
	$scope.redirection = function(){
		window.location.href = "/index.html";
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

app.controller("menuCtrl", function($scope, $rootScope, $http){

	$scope.range = function(min, max, step) {
        step = step || 1;
        var input = [];
        for (var i = min; i <= max; i += step) {
            input.push(i);
        }
        return input;
    };
    
    $http.get("Genres").then(function(response) {
    	$scope.genres = response.data;
    });


	$scope.rechercheMenu = function(){
		
		window.location.href = "#/recherche/"+$rootScope.req+"/"+$rootScope.genre+"/"+$rootScope.minPrix+"/"+$rootScope.maxPrix+"/"+$rootScope.avisMin;
		
	}
	$scope.setGenre = function(genre, id){
		if(genre == $rootScope.genre) {
			$("#"+genre).prop('checked', false);
			$rootScope.genre = "@";
		}
		else {$rootScope.genre = genre;}
	}
	$scope.setAvisMin = function(avisMin, id){
		
		if(avisMin == $rootScope.avisMin) {
			$("#avis"+id).prop('checked', false);
			$rootScope.avisMin = -1;
		}
		else {$rootScope.avisMin = avisMin;}
	}
	$scope.setPrixMin = function(p){
		if(p)
			$rootScope.minPrix = p;// $("menuPrixMin").val();
		else
			$rootScope.minPrix = -1;
	}
	$scope.setPrixMax = function(p){
		if(p)
			$rootScope.maxPrix = p;// $("menuPrixMax").val();
		else
			$rootScope.maxPrix = -1;
	}
	
});

app.controller("searchCtrl", function($scope){

	  

});

app.controller("paiementCtrl", function($scope, $http, ngCart){
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
		{nom : "Mois", valeur : "0", selected : "true", disabled : "true"}
	];
	$scope.selectedMois = $scope.mois[0].value;
	
	$scope.changeRadio = function(idForm, bool){
		$(idForm).hidden(bool);
	}
	
	$scope.moyenPaiement = {
		moyen : "CB"
	}
	
	creerCommande = function(){
		idLivres = [];
		for (i = 0; i < ngCart.getCart().items.length;i++){
			idLivres.push((ngCart.getCart().items[i])._id);
		};
		$http.get("GestionCommande", {
			params:{"action" :"post",
			"type" : $scope.moyenPaiement.moyen,
			"livres" : idLivres }}).then(function(response) {				
				for(i = ngCart.getItems().length; i >= 0; i--){
					ngCart.removeItem(i);
				}
				commande = response.data;
				console.log(response.data);
				$scope.prixCommande = commande.prixTotal;
				console.log(commande.prixTotal + " , " + $scope.prixCommande);
				$scope.numeroCommande = commande.id;
				window.location.href="#/confirmation";
		}, function(){
					
		});
	}
	
	afficher = function(){
		return $scope.prixCommande;
	}
});


app.controller("pageChange", function($scope){
	/*
	 * $scope.pageChangeHandler = function(num) { alert(num); };
	 */
});

routeAppControllers.controller("infoCtrl", function($scope, $routeParams, $http, $document, $uibModal){
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
    
    $scope.calculPromo = calculPromo;
    
    $scope.estEnPromo = estEnPromo;
    
    $scope.calculeMoyenne = function(list) {
    	var moy = 0;
    	for(i=0; i < list.length; i++)
    		moy += list[i].note;
    	return (moy / list.length).toFixed(1);
    }
    
    $scope.posterCommentaire = function(note, commentaire) {
    	if(document.cookie != "" && note != undefined && commentaire != undefined) {
    		$http.get("AjouterCommentaire", {params:{"note": note, "commentaire": commentaire, "idLivre": $scope.livre.id, "idClient" : 31001}}).then(function(response) {
    			$scope.livre = response.data;
    		});
    	} else if ( note == undefined || commentaire == undefined ) {
    		document.getElementById('erreurPosterUnCommentaire').style.display = "block";
    	}
    	else { // popup connection
    		var modalInstance = $uibModal.open({
    		      // animation: $ctrl.animationsEnabled,
    		      ariaLabelledBy: 'modal-title',
    		      ariaDescribedBy: 'modal-body',
    		      templateUrl: '/template/modalConnection/modalConnection.html',
    		      controller: 'modalConnexionCtrl'
    		      // controllerAs: '$ctrl',
    		      // size: size,
    		      // appendTo: parentElem,
    		      /*
					 * resolve: { items: function () { return $ctrl.items; } }
					 */
    		    });
    	}
    }
    
    
    $scope.nombreAvis = function(list) {
    	return list.length;
    }
});

app.controller("modalConnexionCtrl", function($scope, $http, $uibModalInstance){
	$scope.connexion = function(pseudo, mdp) {
    	$http.get("ConnexionClient", {params:{"pseudo": pseudo, "motDePasse": mdp}}).then(function(response) {
    		var cookieValue = document.cookie.replace(
    				/(?:(?:^|.*;\s*)login\s*\=\s*([^;]*).*$)|^.*$/, "$1");
    		if (cookieValue != null && cookieValue != "") {
    			document.getElementById('login').innerHTML = cookieValue;
    			document.getElementById('connexion').style.display = "none";
    			document.getElementById('profil').style.display = "";
    		}
    		var cookieValue = document.cookie.replace(
    				/(?:(?:^|.*;\s*)erreur\s*\=\s*([^;]*).*$)|^.*$/, "$1");
    		if (cookieValue == "true") {
    			document.getElementById('erreurIdentifiantModal').style.display = "block";
    			document.cookie = "erreur=; expires=Thu, 01 Jan 1970 00:00:00 GMT";
    		} else {
    			$uibModalInstance.close();
    			document.getElementById('erreurIdentifiant').style.display = "none";
    		}
    	});
    }
});


routeAppControllers.controller("contentCtrl", function($scope, $http,$rootScope){
	
	$rootScope.req = "@";
	$rootScope.genre = "@"; 
	$rootScope.minPrix = -1;
	$rootScope.maxPrix = -1;
	$rootScope.avisMin = -1;
			
	
	$rootScope.identifiant = "@";
	$rootScope.nom = "@"; 
	$rootScope.prenom = "@";
	$rootScope.motdepasse = "@";
	$rootScope.motdepasseconfirm = "@";
	$rootScope.email = "@";
	
	$scope.breakpoints = [{
	    breakpoint: 1290, // Pc portable
	    settings: {
	      slidesToShow: 5,
	      slidesToScroll: 5
	    }
	  },
	  {
	    breakpoint: 768, // Smartphone
	    settings: {
	      slidesToShow: 3,
	      slidesToScroll: 3
	    }
	  },
	  {
	    breakpoint: 980, // Tablette
	    settings: {
	      slidesToShow: 4,
	      slidesToScroll: 4
	    }
	  }
	];

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
    
    $scope.calculPromo = calculPromo;
    
    $scope.estEnPromo = estEnPromo;
    
});







routeAppControllers.controller("inscriptionClient", function($scope, $http,$rootScope){
	

    $http.get("InscriptionClient").then(function(response) {
        $scope.message = response.data;
        
        console.log($scope.message);
        
    });

    
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
    });
    
    $scope.currentPage = 1;
    $scope.pageSizeMos = 12;
    $scope.pageSizeList = 8;
    $scope.ordonneur = "titre";
    $scope.modeAffichage = "mosaique";


    /*
	 * $scope.pageChangeHandler = function(num) { alert(num); };
	 */
    
    $scope.calculeMoyenne = function(list) {
    	var moy = 0;
    	if(list.length == 0)
    		return "Pas d'avis";
    	for(i=0; i < list.length; i++)
    		moy += list[i].note;
    	return (moy / list.length).toFixed(1) + "/5 ("+list.length+")";
    	
    }
    
    $scope.calculPromo = calculPromo;
    
    $scope.estEnPromo = estEnPromo;
});


routeAppControllers.controller("connexionCtrl", function($scope, $http,$routeParams,$rootScope){

	

});


routeAppControllers.controller("inscriptionCtrl", function($scope, $http,$routeParams,$rootScope){	
	$scope.rez=true;
	if($routeParams.identifiant==null && $routeParams.nom==null && $routeParams.prenom==null && $routeParams.motdepasse==null && $routeParams.motdepasseconfirm==null && $routeParams.email==null ){
		// $scope.rez = "Inscription";
	}else if($routeParams.identifiant==null || $routeParams.nom==null || $routeParams.prenom==null || $routeParams.motdepasse==null || $routeParams.motdepasseconfirm==null || $routeParams.email==null ){
		$scope.rez = "Tous les champs doivent etre remplis";
	}else{
		$scope.IDSave = $routeParams.identifiant;
		$scope.nomSave = $routeParams.nom;
		$scope.prenomSave = $routeParams.prenom;
		$scope.emailSave = $routeParams.email;
		
		$http.get("InscriptionClient",{params:{"identifiant":$routeParams.identifiant,"nom":$routeParams.nom,"prenom":$routeParams.prenom,"motdepasse":$routeParams.motdepasse,"motdepasseconfirm":$routeParams.motdepasseconfirm,"email":$routeParams.email}}).then(function(response) {
				var data = response.data;
				$scope.rez = data;
				
				
		});
	}

});

routeAppControllers.controller("compteClient", function($scope, $http, $routeParams, $location, $rootScope){
	
	$scope.redirectModificationMotDePasse = function() {
		window.location.href=("#/modificationMotDePasse");
    }
	
	$http.get("GetInfoClient", {params:{"pseudo": $routeParams.pseudo}}).then(function(response) {
		var data = response.data;
    	$scope.pseudo = data.pseudo;
    	$scope.nom = data.nom;
    	$scope.prenom = data.prenom;
    	$scope.mail = data.email;
    }); 
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
        	controller: 'contentCtrl'
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
        .when('/inscription/:identifiant?/:nom?/:prenom?/:motdepasse?/:motdepasseconfirm?/:email?', {
        	templateUrl: 'partials/registerView.html',
        	controller: 'inscriptionCtrl'
        })
// .when('/inscription', {
// templateUrl: 'partials/registerView.html',
// // controller: 'inscriptionCtrl'
// })
// .when('/inscriptionClient', {
// templateUrl: 'partials/inscriptionClient.html',
// controller: 'inscriptionClient'
// })
        .when('/compteClient/:pseudo',{
        	templateUrl : 'partials/CompteClient.html',
        	controller: 'compteClient'
        })
        .when('/modificationMotDePasse',{
        	templateUrl : 'partials/ModificationMotDePasse.html',
        	controller: 'modificationMotDePasse'
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
	    host: 'localhost:9200'
	  });
});