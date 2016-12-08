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
	    });
	}
});