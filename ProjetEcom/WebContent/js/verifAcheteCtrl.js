app.controller("verifAcheteCtrl", function($scope, $http, $rootScope, ngCart){
		
	$scope.estAchete = function(id){
		console.log("estAchete");
		var b = false;
		if($rootScope.estConnecte){
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
