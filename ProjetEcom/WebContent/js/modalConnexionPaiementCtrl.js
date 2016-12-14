app.controller("modalConnexionPaiementCtrl", function($scope, $http, $uibModalInstance, $rootScope, userService){
	$scope.cancel = function () {
	    $uibModalInstance.dismiss('cancel');
	};
	
	$scope.envoyerCommentaire = function () {
	    $uibModalInstance.dismiss('cancel');
	};
	
	$scope.connexion = function (pseudo, mdp) {
		userService.signIn(pseudo, mdp, "erreurIdentifiantModal");
	}
	
	$scope.$on('connectionStateChanged', function (pseudo, mdp) {
		if (userService.isConnected()) {
			$uibModalInstance.close();
			window.location.href = "#/paiement";
		}
	});
});