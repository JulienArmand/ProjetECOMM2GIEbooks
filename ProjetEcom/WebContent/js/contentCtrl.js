routeAppControllers.controller("contentCtrl", function($scope, $http,$rootScope){
	
	$("#menu").show();
	
	$rootScope.req = "@";
	$rootScope.genre = "@"; 
	$rootScope.minPrix = -1;
	$rootScope.maxPrix = -1;
	$rootScope.avisMin = -1;
			
	
	$rootScope.identifiant = "@";
	$rootScope.nom = "@"; 
	$rootScope.prenom = "@";
	$rootScope.motdepasse = "@";
	$rootScope.motdepasseconfirm = "@";
	$rootScope.email = "@";
	
	$scope.breakpoints = [
		{
		    breakpoint: 1600, // Pc portable++
		    settings: {
		      slidesToShow: 6,
		      slidesToScroll: 6
		    }
		  },
		{
	    breakpoint: 1290, // Pc portable
	    settings: {
	      slidesToShow: 5,
	      slidesToScroll: 5
	    }
	  },
	  {
	    breakpoint: 768, // Smartphone
	    settings: {
	      slidesToShow: 2,
	      slidesToScroll: 2
	    }
	  },
	  {
	    breakpoint: 980, // Tablette
	    settings: {
	      slidesToShow: 4,
	      slidesToScroll: 4
	    }
	  }
	];

    $http.get("Promos").then(function(response) {
        $scope.livresPromo = response.data;
        
    });
    
    $http.get("PlusVendu").then(function(response) {
        $scope.livresPlusVendu = response.data;
        
    });
    
    $http.get("Recherche").then(function(response) {
        $scope.livresRecherche = response.data;
        
    });
    
    $scope.calculeMoyenne = function(list) {
    	var moy = 0;
    	if(list.length == 0)
    		return "Pas d'avis";
    	for(i=0; i < list.length; i++)
    		moy += list[i].note;
    	return (moy / list.length).toFixed(1);
    }
    
    $scope.calculPromo = calculPromo;
    
    $scope.estEnPromo = estEnPromo;
    
});