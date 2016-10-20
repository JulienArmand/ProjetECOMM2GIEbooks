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
    $http.get("First")
    .then(function(response) {
        $scope.livres = response.data;        
    });
});