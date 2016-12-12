app.controller("paiementCtrl", function($scope, $http, $rootScope, ngCart, $uibModal) {

	$("#menu").hide();

	$scope.mois = [ 
		{nom : "Janvier", valeur : "1"}, 
		{nom : "Février", valeur : "2"}, 
		{nom : "Mars", valeur : "3"}, 
		{nom : "Avril",	valeur : "4"}, 
		{nom : "Mai", valeur : "5"}, 
		{nom : "Juin", valeur : "6"}, 
		{nom : "Juillet", valeur : "7"}, 
		{nom : "Aout", valeur : "8"}, 
		{nom : "Septembre", valeur : "9" }, 
		{nom : "Octobre", valeur : "10"}, 
		{nom : "Novembre", valeur : "11"}, 
		{nom : "Décembre", valeur : "12"}, 
		{nom : "Mois", valeur : "0", selected : "true", disabled : "true"} ];

	$scope.selectedMois = $scope.mois[0].value;

	$scope.changeRadio = function(idForm, bool) {
		$(idForm).hidden(bool);
	}

	$scope.moyenPaiement = {
		moyen : "CB"
	}

	setMoyen = function(str) {
		$scope.moyenPaiement.moyen = str;
	}

	$scope.passerPaiement = function() {
		if(ngCart.getItems().length == 0){
			alert("Votre panier est vide.");
		}
		else{
			if(document.cookie != "") {			
				window.location.href = "#/paiement";
	    	} else { // popup connection
	    		var modalInstance = $uibModal.open({
	    		      ariaLabelledBy: 'modal-title',
	    		      ariaDescribedBy: 'modal-body',
	    		      templateUrl: '/template/modalConnection/modalConnection.html',
	    		      controller: 'modalConnexionPaiementCtrl'
	    		    });
	    	}
		}
	}

	creerCommande = function() {
		idLivres = "";
		for (i = 0; i < ngCart.getCart().items.length; i++) {
			idLivres += ((ngCart.getCart().items[i])._id);
			if (i != ngCart.getCart().items.length - 1)
				idLivres += ","
		}
		$http.get("GestionCommande", {
			params : {
				"action" : "post",
				"type" : $scope.moyenPaiement.moyen,
				"livres" : idLivres
			}
		}).then(function(response) {
			for (i = ngCart.getItems().length; i >= 0; i--) {
				ngCart.removeItem(i);
			}
			$rootScope.commande = response.data;
			$http.get("GestionCommande", {
				params:{"action" :"commandeClient"}}).then(function(response) {
					$rootScope.commandes = response.data;
				});	

			window.location.href = "#/confirmation";
		});
	}
});
