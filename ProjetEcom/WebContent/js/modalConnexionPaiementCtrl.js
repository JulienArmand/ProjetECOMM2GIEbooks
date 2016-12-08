app.controller("modalConnexionPaiementCtrl", function($scope, $http, $uibModalInstance, $rootScope){
	$scope.cancel = function () {
	    $uibModalInstance.dismiss('cancel');
	};
	
	$scope.envoyerCommentaire = function () {
	    $uibModalInstance.dismiss('cancel');
	};
	
	$scope.connexion = function (pseudo, mdp) {
		$http.get("ConnexionClient", {params:{"pseudo": pseudo, "motDePasse": mdp}}).then(function(response) {
			var login = getCookie('login');
			if (login != null && login != "") {
				$rootScope.login = login;
				$rootScope.estConnecte = true;
				$uibModalInstance.close();
				window.location.href = "#/paiement";
			}
			var erreur = getCookie('erreur');
			if (erreur == "true") {
				$rootScope.estConnecte = false;
				document.getElementById('erreurIdentifiantModal').style.display = "block";
			}
		});
	}
});