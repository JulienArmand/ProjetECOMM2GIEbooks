var app = angular.module("app", ['ui.bootstrap']);


app.controller("headerCtrl", function($scope){

    //...    

});


app.controller("footerCtrl", function($scope){

    //...    

});


app.controller("menuCtrl", function($scope){

    //...    

});


app.controller("contentCtrl", function($scope, $http){
    $http.get("First").then(function(response) {
        $scope.livres = response.data;
        
    });
    
    $scope.calculeMoyenne = function(list) {
    	var moy = 0;
    	if(list.length == 0)
    		return "Pas d'avis";
    	for(i=0; i < list.length; i++)
    		moy += list[i].note;
    	return (moy / list.length).toFixed(1) + "/5 ("+list.length+")";
    	
    }
});






