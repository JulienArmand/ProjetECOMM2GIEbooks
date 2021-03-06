function checkCaracteresInterdits(value) {
	result = false;
	if (/^[a-zA-Z0-9- ]*$/.test(value) === false) {
		result = true;
	}
	return result;
}

function validationEmail(mail) {
	if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(mail)) {
		return (true);
	}
	return (false);
}

routeAppControllers.controller("inscriptionCtrl", function($scope, $http){	
	$("#menu").hide();
	$scope.rez= true;
	$scope.emailSave = "hello";
	$scope.submit = function() {
		document.getElementById('erreurCaracteresInterdits').style.display = "none";
		document.getElementById('erreurMotDePasseTropCourt').style.display = "none";
		document.getElementById('erreurMailNonValide').style.display = "none";
		//Teste si l'un des champs contient des caractères interdits
		if(checkCaracteresInterdits(this._id) || checkCaracteresInterdits(this._nom) || checkCaracteresInterdits(this._prenom) || checkCaracteresInterdits(this._mdp) || checkCaracteresInterdits(this._mdpc)){
			document.getElementById('erreurCaracteresInterdits').style.display = "block";
		}
		else{
			//Teste si le mot de passe est trop court
			if(this._mdp.length < 8 || this._mdpc.length < 8){
				document.getElementById('erreurMotDePasseTropCourt').style.display = "block";
			}
			else{
				//Teste si l'adresse mail est valide
				if(!validationEmail(this._email)){
					document.getElementById('erreurMailNonValide').style.display = "block";
				}
				else{
					var pseudo = this._id;
					$http.get("InscriptionClient",{params:{"identifiant":this._id,"nom":this._nom,"prenom":this._prenom,"motdepasse":this._mdp,"motdepasseconfirm":this._mdpc,"email":this._email}}).then(function(response) {
						var data = response.data;
						$scope.rez = data;
						$http.get("ConfirmationInscriptionServlet", {
							params : {
								"id" : pseudo
							}
						});
					});
					$scope._email=this._email;
					this._mdp='';
					this._mdpc='';
				}
			}		
		}
     };
});