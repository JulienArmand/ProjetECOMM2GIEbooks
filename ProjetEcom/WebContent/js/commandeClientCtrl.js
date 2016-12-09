app.controller("commandeClientCtrl", function($scope, $http, $rootScope, ngCart){
	
	$http.get("GestionCommande", {
		params:{"action" :"commandeClient"}}).then(function(response) {
			$rootScope.commandes = response.data;
		});	
});
