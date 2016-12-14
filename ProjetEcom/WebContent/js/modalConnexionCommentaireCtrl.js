app.controller("modalConnexionCommentaireCtrl", function($scope, $http, $uibModalInstance, $rootScope, note, commentaire, idLivre, userService, $cookies){
	$scope.cancel = function () {
	    $uibModalInstance.dismiss('cancel');
	};
	
	$scope.envoyerCommentaire = function () {
	    $uibModalInstance.dismiss('cancel');
	};
	
	$scope.$on('connectionStateChanged', function (pseudo, mdp) {
		if (userService.isConnected()) {
			$uibModalInstance.close();
			$http.get("AjouterCommentaire", {params:{"note": note, "commentaire": commentaire, "idLivre": idLivre, "idClient" : $cookies.get('idClient')}}).then(function(response) {
				   if(response.data !== 'dejaCommente') { 
					   $scope.livre = response.data; 
				   } else {
					   document.getElementById('erreurDejaCommente').style.display = "block"; 
				   } 
			});
		}
	});
	
	$scope.connexion = function (pseudo, mdp) {
		userService.signIn(pseudo, mdp, "erreurIdentifiantModal")
	}
});
