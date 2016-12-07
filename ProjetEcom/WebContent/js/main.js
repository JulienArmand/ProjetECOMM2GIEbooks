var app = angular.module("app", ['ui.bootstrap', 'ngRoute', 'ngCart', 'routeAppControllers', 'slick', 'angularUtils.directives.dirPagination', 'elasticsearch']);

var routeAppControllers = angular.module('routeAppControllers', []);


app.controller("headerCtrl", function($scope, ngCart, $rootScope, elasticSearchSuggestion, $http){
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
	
	var login = getCookie('login');
	if (login != null && login != "") {
		$rootScope.login = login;
		$rootScope.estConnecte = true;
		
	}
	var erreur = getCookie('erreur');
	if (erreur == "true") {
		$rootScope.estConnecte = false;
	}
	
	
	$scope.redirection = function(){
		window.location.href = "index.html";
	}
	
	$scope.deconnexion = function () {
		$rootScope.estConnecte = false;
		document.cookie = "login=; expires=Thu, 01 Jan 1970 00:00:00 GMT";
		document.cookie = "idClient=; expires=Thu, 01 Jan 1970 00:00:00 GMT";
		document.cookie = "erreur=; expires=Thu, 01 Jan 1970 00:00:00 GMT";
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
    
    $http.get("GetTous", {params:{"action": "genres"}}).then(function(response) {				
		$scope.genres = response.data;
	});


	$scope.rechercheMenu = function(){
		
		window.location.href = "#/recherche/"+$rootScope.req+"/"+$rootScope.genre+"/"+$rootScope.minPrix+"/"+$rootScope.maxPrix+"/"+$rootScope.avisMin;
		
	}
	$scope.setGenre = function(genre, id){
		if(genre == $rootScope.genre) {
			$("#"+id).prop('checked', false);
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


app.controller("commandeClientCtrl", function($scope, $http, $rootScope, ngCart){
	$http.get("GestionCommande", {
		params:{"action" :"commandeClient"}}).then(function(response) {
			$scope.commandes = response.data;
		});
});



app.controller("paiementCtrl", function($scope, $http, $rootScope, ngCart){
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
	
	setMoyen = function(str){
		$scope.moyenPaiement.moyen = str;
	}
	
	creerCommande = function(){
		idLivres = "";
		for (i = 0; i < ngCart.getCart().items.length;i++){
			idLivres += ((ngCart.getCart().items[i])._id);
			if(i != ngCart.getCart().items.length-1)
				idLivres += ","
		};
		$http.get("GestionCommande", {
			params:{"action" :"post",
			"type" : $scope.moyenPaiement.moyen,
			"livres" : idLivres }}).then(function(response) {				
				for(i = ngCart.getItems().length; i >= 0; i--){
					ngCart.removeItem(i);
				}
				$rootScope.commande = response.data;
				
				
				window.location.href="#/confirmation";
		});
	}
});

app.controller("ajoutLivreCtrl", function($scope, $http, $rootScope){
	
	toggle_div = function (bouton, id) { // On déclare la fonction toggle_div qui prend en param le bouton et un id
		  var div = document.getElementById(id); // On récupère le div ciblé grâce à l'id
		  if(div.style.display=="none") { // Si le div est masqué...
		    div.style.display = "block"; // ... on l'affiche...
		    bouton.innerHTML = "-"; // ... et on change le contenu du bouton.
		  } else { // S'il est visible...
		    div.style.display = "none"; // ... on le masque...
		    bouton.innerHTML = "+"; // ... et on change le contenu du bouton.
		  }
		}
		
	creerLivre = function(){
		$http.get("AjouterLivre", {
			params:{
				"titre" : $("#titre").val(),
				"editeur" : $scope.selectedEditeur,
				"genre" : $scope.selectedGenre,
				"isbn" : $("#isbn").val(),
				"nbPage" : $("#nbPage").val(),
				"prix" : $("#prix").val(),
				"lang" : $("#langue").val(),
				"langOrig" : $("#langOrig").val(),
				"couverture" : $("#couverture").val(),
				"resume" : $("#resume").val(),
				"datePub" : $("#datePub").val()
				}}).then(function(response) {				
				console.log("Livre ajoutée");
		});
	}
	
	$scope.changeRadio = function(idForm, bool){
		$(idForm).hidden(bool);
	}
	
	$scope.formulaires = {
		genre : "creation"
	}
	
	setGenre = function(str){
		$scope.formulaires.genre = str;
	}
	
	$scope.selectedGenreToModify = null;
	
	creerGenre = function() {
		$http.get("CreerGenreServlet", {
			params:{
				"action" : "creer",
				"nom" : $("#newGenre").val()
				}}).then(function(response) {				
				console.log("Genre ajoutée");
		});
	}
	
	modifierGenre = function() {
		$http.get("CreerGenreServlet", {
			params:{
				"action" : "modif",
				"id" : $scope.selectedGenreToModify,
				"nom" : $("#modifyGenre").val()
				}}).then(function(response) {				
				console.log("Genre modifier");
		});
	}
	
	creerAuteur = function() {
		$http.get("CreerAuteurServlet", {
			params:{
				"action" : "creer",
				"nom" : $("#newNomAuteur").val(),
				"prenom" : $("#newPrenomAuteur").val()
				}}).then(function(response) {				
				console.log("Auteur ajoutée");
		});
	}
	
	modifierAuteur = function() {
		$http.get("CreerAuteurServlet", {
			params:{
				"action" : "modif",
				"id" : $scope.selectedAuteurToModify,
				"nom" : $("#modifyNomAuteur").val(),
				"prenom" : $("#modifyPrenomAuteur").val()
				}}).then(function(response) {				
				console.log("Auteur modifier");
		});
	}
	
	creerEditeur = function() {
		$http.get("CreerEditeurServlet", {
			params:{
				"action" : "creer",
				"nom" : $("#newEditeur").val()
				}}).then(function(response) {				
				console.log("Editeur ajoutée");
		});
	}
	
	modifierEditeur = function() {
		$http.get("CreerEditeurServlet", {
			params:{
				"action" : "modif",
				"id" : $scope.selectedEditeurToModify,
				"nom" : $("#modifyEditeur").val()
				}}).then(function(response) {				
				console.log("Editeur modifier");
		});
	}
	
	creerPromotion = function() {
		$http.get("AjoutPromoServlet", {
			params:{
				"id" : $scope.selectedLivre,
				"taux" : $("#newTaux").val(),
				"dateD" : $("#newDateD").val(),
				"dateF" : $("#newDateF").val()
				}}).then(function(response) {				
				console.log("Promotion ajoutée");
		});
	}
	
		
	$http.get("GetTous", {params:{"action": "genres"}}).then(function(response) {				
		$scope.genres = response.data;
	});
	
	$scope.selectedGenre = null;
	$scope.selectedGenreToModify = null;
	
	$http.get("GetTous", {params:{"action": "auteurs"}}).then(function(response) {				
			$scope.auteurs = response.data;
	});
	
	$scope.selectedAuteur = null;
	$scope.selectedAuteurToModify = null;
	
	$http.get("GetTous", {params:{"action": "editeurs"}}).then(function(response) {				
		$scope.editeurs = response.data;
	});
	
	$scope.selectedEditeur = null;
	$scope.selectedEditeurToModify = null;
	
	$http.get("GetTous", {params:{"action": "promotions"}}).then(function(response) {				
		$scope.promotions = response.data;
	});
	
	$scope.selectedPromotion = null;
	$scope.selectedPromotionToModify = null;
	
	$http.get("GetTous", {params:{"action": "livres"}}).then(function(response) {				
		$scope.livres = response.data;
	});
	
	$scope.selectedLivre = null;
	
});

app.controller("pageChange", function($scope){
	/*
	 * $scope.pageChangeHandler = function(num) { alert(num); };
	 */
});

routeAppControllers.controller("infoCtrl", function($scope, $routeParams, $http, $document, $uibModal, $location, $anchorScroll){
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
    
    $scope.allerAAvis = function() {
    	var newHash = 'avisClient';
    	if ($location.hash() !== newHash) {
        $location.hash('avisClient');
      } else {
        $anchorScroll();
      }
    }
    
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
    		$http.get("AjouterCommentaire", {params:{"note": note, "commentaire": commentaire, "idLivre": $scope.livre.id, "idClient" : getCookie('idClient')}}).then(function(response) {
    			if(response.data != 'dejaCommente') {
    				$scope.livre = response.data;
    			}
    			else {
    				document.getElementById('erreurDejaCommente').style.display = "block";
    			}
    		});
    	} else if ( note == undefined || commentaire == undefined ) {
    		document.getElementById('erreurPosterUnCommentaire').style.display = "block";
    	}
    	else { // popup connection
    		var modalInstance = $uibModal.open({
    		      ariaLabelledBy: 'modal-title',
    		      ariaDescribedBy: 'modal-body',
    		      templateUrl: '/template/modalConnection/modalConnection.html',
    		      controller: 'modalConnexionCtrl'
    		    });
    	}
    }
    
    $scope.nombreAvis = function(list) {
    	return list.length;
    }
});

app.controller("modalConnexionCtrl", function($scope, $http, $uibModalInstance, $rootScope){
	$scope.connexion = function (pseudo, mdp) {
		$http.get("ConnexionClient", {params:{"pseudo": pseudo, "motDePasse": mdp}}).then(function(response) {
			var login = getCookie('login');
			if (login != null && login != "") {
				$rootScope.login = login;
				$rootScope.estConnecte = true;
				$uibModalInstance.close();
				$http.get("AjouterCommentaire", {params:{"note": note, "commentaire": commentaire, "idLivre": $scope.livre.id, "idClient" : getCookie('idClient')}}).then(function(response) {
	    			if(response.data != 'dejaCommente') {
	    				$scope.livre = response.data;
	    			}
	    			else {
	    				document.getElementById('erreurDejaCommente').style.display = "block";
	    			}
	    		});
			}
			var erreur = getCookie('erreur');
			if (erreur == "true") {
				$rootScope.estConnecte = false;
			}
		});
	}
});

app.controller("popoverConnexionCtrl", function($scope, $http, $rootScope){	
	$scope.connexion = function (pseudo, mdp) {
		$http.get("ConnexionClient", {params:{"pseudo": pseudo, "motDePasse": mdp}}).then(function(response) {
			var login = getCookie('login');
			if (login != null && login != "") {
				$rootScope.login = login;
				$rootScope.estConnecte = true;
			}
			var erreur = getCookie('erreur');
			if (erreur == "true") {
				$rootScope.estConnecte = false;
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
	
	$scope.breakpoints = [
		{
		    breakpoint: 1600, // Pc portable++
		    settings: {
		      slidesToShow: 6,
		      slidesToScroll: 6
		    }
		  },
		{
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
	$scope.rez= true;
	$scope.emailSave = "hello";
	$scope.submit = function() {
		$http.get("InscriptionClient",{params:{"identifiant":this._id,"nom":this._nom,"prenom":this._prenom,"motdepasse":this._mdp,"motdepasseconfirm":this._mdpc,"email":this._email}}).then(function(response) {
			var data = response.data;
			$scope.rez = data;
			
		});
		$scope._email=this._email;
		this._mdp='';
		this._mdpc='';
      };
	
});

routeAppControllers.controller("compteClient", function($scope, $http, $location, $rootScope){	
	$http.get("GetInfoClient", {params:{"pseudo": getCookie('login')}}).then(function(response) {
		var data = response.data;
    	$scope.pseudo = data.pseudo;
    	$scope.nom = data.nom;
    	$scope.prenom = data.prenom;
    	$scope.mail = data.email;
    }); 
	
	modificationProfil = function (){
		$http.get("ModificationProfile", {params:{
			"pseudo" : $("#pseudo").val(),
			"nom" : $("#nom").val(),
			"prenom" : $("#prenom").val(),
			"email" : $("#email").val()
			}}).then(function(response) {
	    });
	}
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

app.directive('hoverPopover2', function ($compile, $templateCache, $timeout, $rootScope, $compile) {
	var getTemplate = function (contentType) {
	    return $templateCache.get('templatePopoverMonCompte.html');
	};
	return {
	    restrict: 'A',
	    link: function (scope, element, attrs) {
	    	
	        var content = getTemplate();
	        var compileContent = $compile(content)(scope);
	        $rootScope.insidePopover = false;
	        $(element).popover({
	            content: compileContent,
	            placement: 'bottom',
	            html: true
	        });
            
	        $("#monCompteHeader").hover(function(){
	        	$(element).popover('show');
                scope.attachEvents(element);
	        });
	        
	    },
	    controller: function ($scope, $element) {
	        $scope.attachEvents = function (element) {
	            $('.popover').on('mouseenter', function () {
	                $rootScope.insidePopover = true;
	            });
	            $('.popover').on('mouseleave', function () {
	                $rootScope.insidePopover = false;
	                $(element).popover('hide');
	            });
	        }
	    }
	    
	};
});


app.directive('hoverPopover', function ($compile, $templateCache, $timeout, $rootScope, $compile) {
	var getTemplate = function (contentType) {
	    return $templateCache.get('templatePopoverConnexion.html');
	};
	return {
	    restrict: 'A',
	    link: function (scope, element, attrs) {
	    	
	        var content = getTemplate();
	        var compileContent = $compile(content)(scope);
	        $rootScope.insidePopover = false;
	        $(element).popover({
	            content: compileContent,
	            placement: 'bottom',
	            html: true
	        });
            
	        $(element).hover(function(){
	        	$(element).popover('show');
                scope.attachEvents(element);
	        });
	        
	    },
	    controller: function ($scope, $element) {
	        $scope.attachEvents = function (element) {
	            $('.popover').on('mouseenter', function () {
	                $rootScope.insidePopover = true;
	            });
	            $('.popover').on('mouseleave', function () {
	                $rootScope.insidePopover = false;
	                $(element).popover('hide');
	            });
	        }
	    }
	    
	};
});

function connexion ($rootScope, $http, pseudo, mdp) {
	$http.get("ConnexionClient", {params:{"pseudo": pseudo, "motDePasse": mdp}}).then(function(response) {
		var login = getCookie('login');
		if (login != null && login != "") {
			$rootScope.login = login;
			$rootScope.estConnecte = true;
		}
		var erreur = getCookie('erreur');
		if (erreur == "true") {
			$rootScope.estConnecte = false;
		}
	});
}

function deconnexion ($rootScope) {
	$rootScope.estConnecte = false;
	document.cookie = "";
}

function getCookie(sName) {
    var oRegex = new RegExp("(?:; )?" + sName + "=([^;]*);?");
    if (oRegex.test(document.cookie)) {
    	return decodeURIComponent(RegExp["$1"]);
    } else {
    	return null;
    }
}

