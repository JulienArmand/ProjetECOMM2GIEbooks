function checkNumeroCB(value){
	isCorrectLength = (value.length == 16);
	isNumeric = (!isNaN(parseFloat(value)) && isFinite(value));
	return isCorrectLength && isNumeric;
}

function checkCryptogramme(value){
	isCorrectLength = (value.length == 3);
	isNumeric = (!isNaN(parseFloat(value)) && isFinite(value));
	return isCorrectLength && isNumeric;
}

function checkAnnee(value){
	isCorrectLength = (value.length == 4);
	isNumeric = (!isNaN(parseFloat(value)) && isFinite(value));
	currentTime = new Date();
	currentYear = currentTime.getFullYear();
	isFuture = (parseFloat(value)>=currentYear && parseFloat(value)<(currentYear+10));
	return isCorrectLength && isNumeric && isFuture;
}

function checkComptePaypal(value){
	if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(value)){
		return (false);
	}  
    return (true);
}

app.controller("paiementCtrl", function($scope, $http, $rootScope, ngCart, $uibModal, $resource) {

	$("#menu").hide();

	$scope.mois = [ { nom : "Janvier", valeur : "1" }, 
					{ nom : "Février", valeur : "2" }, 
					{ nom : "Mars", valeur : "3" }, 
					{ nom : "Avril", valeur : "4" }, 
					{ nom : "Mai", valeur : "5" }, 
					{ nom : "Juin", valeur : "6" }, 
					{ nom : "Juillet", valeur : "7" }, 
					{ nom : "Aout", valeur : "8" },
					{ nom : "Septembre", valeur : "9" }, 
					{ nom : "Octobre", valeur : "10" }, 
					{ nom : "Novembre", valeur : "11" }, 
					{ nom : "Décembre", valeur : "12" }, 
					{ nom : "Mois", valeur : "0", selected : "true", disabled : "true" } ];

	$scope.selectedMois = $scope.mois[0].value;
	$scope.moisSelectionne = 0;

	$scope.changeRadio = function(idForm, bool) {
		$(idForm).hidden(bool);
	}

	$scope.moyenPaiement = {
		moyen : "CB"
	}

	$scope.setMoyen = function(str) {
		$scope.moyenPaiement.moyen = str;
	}
	
	$scope.setMois = function(str){
		$scope.moisSelectionne = str;
	}

	$scope.passerPaiement = function() {
		if (document.cookie !== "") {
			window.location.href = "#/paiement";
		} else { // popup connection
			modalInstance = $uibModal.open({
				ariaLabelledBy : 'modal-title',
				ariaDescribedBy : 'modal-body',
				templateUrl : '/template/modalConnection/modalConnection.html',
				controller : 'modalConnexionPaiementCtrl'
			});
		}
	}

	$scope.creerCommande = function() {
		var idLivres = "";
		for (var i = 0; i < ngCart.getCart().items.length; i++) {
			idLivres += ((ngCart.getCart().items[i])._id);
			if (i !== (ngCart.getCart().items.length - 1))
				idLivres += ","
		}
		
		var check = true;
		document.getElementById('erreurChampVide').style.display = "none";
		document.getElementById('erreurNumeroCarte').style.display = "none";
		document.getElementById('erreurCryptogramme').style.display = "none";
		document.getElementById('erreurMoisNonSelectionne').style.display = "none";
		document.getElementById('erreurAnnee').style.display = "none";
		document.getElementById('erreurPaypal').style.display = "none";
		//Si moyen de paiment est CB :
		if($scope.moyenPaiement.moyen === "CB"){
			//Vérifier champs non vides
			if($("#titulaireCB").val().length <= 0 || $("#numeroCarte").val().length <= 0 || $("#annee").val().length <= 0 || $("#cryptogramme").val().length <= 0 || $scope.moisSelectionne == 0){
				document.getElementById('erreurChampVide').style.display = "block";
				check = false;
			}
			else{
				//Vérifier numéro CB correct
				if(!checkNumeroCB($("#numeroCarte").val())){
					document.getElementById('erreurNumeroCarte').style.display = "block";
					check = false;
				}
				else{
					//Vérifier cryptogramme correct
					if(!checkCryptogramme($("#cryptogramme").val())){
						document.getElementById('erreurCryptogramme').style.display = "block";
						check = false;
					}
					else{
						//Vérifier année fin carte correcte
						if(!checkAnnee($("#annee").val())){
							document.getElementById('erreurAnnee').style.display = "block";
							check = false;
						}
					}
				}
			}
		}
		//Si moyen de paiement est Paypal :
		else{
			//Vérifier adresse du compte correcte
			if(!checkComptePaypal($("#comptePaypal").val())){
				document.getElementById('erreurPaypal').style.display = "block";
				check = false;
			}
		}
		//Si les vérifications se sont déroulées sans problème :
		if(check){
			$http.get("GestionCommande", {
				params : {
					"action" : "post",
					"type" : $scope.moyenPaiement.moyen,
					"livres" : idLivres
				}
			}).then(function(response) {
				for (var i = ngCart.getItems().length; i >= 0; i--) {
					ngCart.removeItem(i);
				}
				$rootScope.commande = response.data;
				
				$http.get("GestionCommande", {
					params : {
						"action" : "commandeClient"
					}
				}).then(function(response) {
					$rootScope.commandes = response.data;
				});

				var Mail = $resource("/EnvoiMailBeans/confirmation/mail", {cmd:"@cmd"});
				
				var mail = Mail.get({cmd:$rootScope.commande}, function(){
					
				})
				
				window.location.href = "#/confirmation";
			});
		}
		
	}
});
