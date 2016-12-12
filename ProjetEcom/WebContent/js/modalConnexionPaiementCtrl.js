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
				$http.get("GestionCommande", {
					params:{"action" :"commandeClient"}}).then(function(response) {
						$rootScope.commandes = response.data;
					});	
				$uibModalInstance.close();
				window.location.href = "#/paiement";
			}
			var erreur = getCookie('erreur');
			if (erreur == "true") {
				$rootScope.estConnecte = false;
				$rootScope.commandes = null;
				document.getElementById('erreurIdentifiantModal').style.display = "block";
			}
		});
	}
});