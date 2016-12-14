app.controller("verifAcheteCtrl", function($scope, userService, $http, $rootScope, ngCart){
		
	$scope.estAchete = function(id){
		var b = false;
		if(userService.isConnected()){
			angular.forEach($rootScope.commandes, function(value){
				angular.forEach(value.lesVentes, function(valueVente){
				      if(valueVente.livre.id == id){
				      	b = true;
				      } 
				   });
			   });
		}
		return b;
	}
		
});
