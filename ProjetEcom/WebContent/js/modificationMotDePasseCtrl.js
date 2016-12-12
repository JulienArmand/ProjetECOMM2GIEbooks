routeAppControllers.controller("modificationMotDePasseCtrl", function($scope, $http){	
	$("#menu").hide();
	
	var modificationMotDePasse = function (){
		$http.get("ModificationMotDePasse", {params:{
			"pseudo": getCookie('login'),
			"ancienMotDePasse" : $("#ancienMotDePasse").val(),
			"nouveauMotDePasse" : $("#nouveauMotDePasse").val(),
			"confirmationNouveauMotDePasse" : $("#confirmationNouveauMotDePasse").val()
			}}).then(function(response) {
				switch(response.data){
					case('champVide'):
						document.getElementById('erreurChampVide').style.display = "block";
						document.getElementById('erreurMotDePasseActuel').style.display = "none";
						document.getElementById('erreurMotDePasseDifferents').style.display = "none";
						document.getElementById('confirmationModificationMotDePasse').style.display = "none";
						break;
					case('motsDePasseDifferents'):
						document.getElementById('erreurMotDePasseDifferents').style.display = "block";
						document.getElementById('erreurChampVide').style.display = "none";
						document.getElementById('erreurMotDePasseActuel').style.display = "none";
						document.getElementById('confirmationModificationMotDePasse').style.display = "none";
						break;
					case('mauvaisMotDePasse'):
						document.getElementById('erreurMotDePasseActuel').style.display = "block";
						document.getElementById('erreurChampVide').style.display = "none";
						document.getElementById('erreurMotDePasseDifferents').style.display = "none";
						document.getElementById('confirmationModificationMotDePasse').style.display = "none";
						break;
					default:
						document.getElementById('confirmationModificationMotDePasse').style.display = "block";
						document.getElementById('erreurMotDePasseActuel').style.display = "none";
						document.getElementById('erreurChampVide').style.display = "none";
						document.getElementById('erreurMotDePasseDifferents').style.display = "none";
						break;
				}
	    });
	}
	
});