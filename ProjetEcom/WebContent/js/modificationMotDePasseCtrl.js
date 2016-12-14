function checkCaracteresInterdits(value){
	result = false;
	if(/^[a-zA-Z0-9- ]*$/.test(value) === false) {
		result = true;
	}
	return result;
}

routeAppControllers.controller("modificationMotDePasseCtrl", function($scope, $http, $cookies){	
	$("#menu").hide();
	
	$scope.modificationMotDePasse = function (){
		document.getElementById('erreurChampVide').style.display = "none";
		document.getElementById('erreurMotDePasseActuel').style.display = "none";
		document.getElementById('erreurMotDePasseDifferents').style.display = "none";
		document.getElementById('confirmationModificationMotDePasse').style.display = "none";
		document.getElementById('erreurCaracteresInterdits').style.display = "none";
		document.getElementById('erreurMotDePasseTropCourt').style.display = "none";
		//Teste que le mot de passe ne contient pas de caractères interdits
		if(checkCaracteresInterdits($("#nouveauMotDePasse").val())){
			document.getElementById('erreurCaracteresInterdits').style.display = "block";
		}
		else{
			//Teste que le mot de passe est de 8 caractères ou plus
			if($("#nouveauMotDePasse").val().length < 8 || $("#confirmationNouveauMotDePasse").val().length < 8){
				document.getElementById('erreurMotDePasseTropCourt').style.display = "block";
			}
			else{
				$http.get("ModificationMotDePasse", {params:{
					"pseudo": $cookies.get('login'),
					"ancienMotDePasse" : $("#ancienMotDePasse").val(),
					"nouveauMotDePasse" : $("#nouveauMotDePasse").val(),
					"confirmationNouveauMotDePasse" : $("#confirmationNouveauMotDePasse").val()
					}}).then(function(response) {
						switch(response.data){
							case('champVide'):
								document.getElementById('erreurChampVide').style.display = "block";
								break;
							case('motsDePasseDifferents'):
								document.getElementById('erreurMotDePasseDifferents').style.display = "block";
								break;
							case('mauvaisMotDePasse'):
								document.getElementById('erreurMotDePasseActuel').style.display = "block";
								break;
							default:
								document.getElementById('confirmationModificationMotDePasse').style.display = "block";
								break;
						}
			    });
			}
		}
	}
	
});