app.controller("menuCtrl", function($scope, $rootScope, $http){

	$scope.range = function(min, max, step) {
        step = step || 1;
        var input = [];
        for (var i = min; i <= max; i += step) {
            input.push(i);
        }
        return input;
    };
    
    $http.get("GetTous", {params:{"action": "genres"}}).then(function(response) {
		$scope.genres = response.data;
	});


	$scope.rechercheMenu = function(){
		window.location.href = "#/recherche/"+$rootScope.req+"/"+$rootScope.genre+"/"+$rootScope.minPrix+"/"+$rootScope.maxPrix+"/"+$rootScope.avisMin;
	}
	
	$scope.setGenre = function(genre, id){
		if(genre === $rootScope.genre) {
			$("#"+id).prop('checked', false);
			$rootScope.genre = "@";
		}
		else {$rootScope.genre = genre;}
	}
	
	$scope.setAvisMin = function(avisMin, id){
		
		if(avisMin === $rootScope.avisMin) {
			$("#avis"+id).prop('checked', false);
			$rootScope.avisMin = -1;
		}
		else {$rootScope.avisMin = avisMin;}
	}
	
	$scope.setPrixMin = function(p){
		if(p)
			$rootScope.minPrix = p;
		else
			$rootScope.minPrix = -1;
	}
	
	$scope.setPrixMax = function(p){
		if(p)
			$rootScope.maxPrix = p;
		else
			$rootScope.maxPrix = -1;
	}
	
});
