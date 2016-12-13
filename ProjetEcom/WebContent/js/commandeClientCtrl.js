app.controller("commandeClientCtrl", function($scope, $http, $rootScope){
	
	$http.get("GestionCommande", {
		params:{"action" :"commandeClient"}}).then(function(response) {
			$rootScope.commandes = response.data;
		});	
});
