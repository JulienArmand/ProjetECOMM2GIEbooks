
routeAppControllers.controller("inscriptionCtrl", function($scope, $http){	
	
	$("#menu").hide();
	
	$scope.rez= true;
	$scope.emailSave = "hello";
	$scope.submit = function() {
		$http.get("InscriptionClient",{params:{"identifiant":this._id,"nom":this._nom,"prenom":this._prenom,"motdepasse":this._mdp,"motdepasseconfirm":this._mdpc,"email":this._email}}).then(function(response) {
			var data = response.data;
			$scope.rez = data;
			
		});
		$scope._email=this._email;
		this._mdp='';
		this._mdpc='';
      };
	
});