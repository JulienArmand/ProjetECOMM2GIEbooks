app.controller("verifAcheteCtrl", function($scope, $http, $rootScope, ngCart){
		
	$scope.estAchete = function(id){
		b = false;
		if($rootScope.estConnecte){
			angular.forEach($rootScope.commandes, function(value, key){
				angular.forEach(value.lesVentes, function(valueVente, keyVente){
				      if(valueVente.livre.id == id){
				      	b = true;
				      } 
				   });
			   });
		}
		return b;
	}
		
});
