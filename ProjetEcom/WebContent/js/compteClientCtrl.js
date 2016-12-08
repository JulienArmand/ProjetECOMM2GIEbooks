routeAppControllers.controller("compteClient", function($scope, $http, $location, $rootScope){	
	
	$("#menu").hide();
	
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
				if(response.data === ('doubleExiste')){
					document.getElementById('erreurDoubleExiste').style.display = "block";
					document.getElementById('erreurEmailExiste').style.display = "none";
					document.getElementById('erreurPseudoExiste').style.display = "none";
					document.getElementById('confirmationModificationProfil').style.display = "none";
				}
				else{
					if(response.data === ('pseudoExiste')){
						document.getElementById('erreurPseudoExiste').style.display = "block";
						document.getElementById('erreurEmailExiste').style.display = "none";
						document.getElementById('erreurDoubleExiste').style.display = "none";
						document.getElementById('confirmationModificationProfil').style.display = "none";
					}
					else{
						if(response.data ===('emailExiste')){
							document.getElementById('monCompteHeader').textContent="Bienvenue ".concat($("#pseudo").val());
							document.getElementById('erreurEmailExiste').style.display = "block";
							document.getElementById('erreurPseudoExiste').style.display = "none";
							document.getElementById('erreurDoubleExiste').style.display = "none";
							document.getElementById('confirmationModificationProfil').style.display = "none";
						}
						else{
							document.getElementById('monCompteHeader').textContent="Bienvenue ".concat($("#pseudo").val());
							document.getElementById('confirmationModificationProfil').style.display = "block";
							document.getElementById('erreurPseudoExiste').style.display = "none";
							document.getElementById('erreurEmailExiste').style.display = "none";
							document.getElementById('erreurDoubleExiste').style.display = "none";
						}
					}	
				}	
	    });
	}
});