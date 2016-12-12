routeAppControllers.controller("recherche", function($scope, $http, $routeParams){
	    
	$scope.calculPromo = function(prix, promo) {
    	return roundPrix(prix-(prix*promo)/100);
    }
	
	$http.get("RechercheViaBarre", {params:{"req": $routeParams.req, "genre": $routeParams.genre, "pmin": $routeParams.pmin, "pmax": $routeParams.pmax, "avisMin": $routeParams.avisMin}}).then(function(response){
        
    	var data = response.data;
		$scope.livres=[];
    	for(var i=0; i<data.length;i++){
    		var l = data[i].l
    		l.ventes = data[i].v;
    		l.prixPromo = (l.promotion) ? $scope.calculPromo(l.prix,l.promotion.tauxReduc) : l.prix;
    		$scope.livres.push(l);

    	}        
    });
    
    $scope.currentPage = 1;
    $scope.pageSizeMos = 12;
    $scope.pageSizeList = 8;
    $scope.ordonneur = "titre";
    $scope.modeAffichage = "mosaique";
    
    $scope.calculeMoyenne = function(list) {
    	var moy = 0;
    	if(list.length === 0)
    		return "Pas d'avis";
    	for(var i = 0; i < list.length; i++)
    		moy += list[i].note;
    	return (moy / list.length).toFixed(1);
    	
    }
    
    $scope.calculPromo = calculPromo;
    
    $scope.estEnPromo = estEnPromo;
});
