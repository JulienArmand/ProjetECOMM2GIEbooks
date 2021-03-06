routeAppControllers.controller("infoCtrl", function($scope, $routeParams, $http, $document, $uibModal, $location, $anchorScroll, userService, $cookies, $rootScope){
	
	$("#menu").show();
	
	if($rootScope.req == "@" && $rootScope.genre == "@" && $rootScope.minPrix == -1 && $rootScope.maxPrix == -1 && $rootScope.avisMin == -1 ){
		$scope.retourUrl = "";
	}else{
		$scope.retourUrl = "#/recherche/"+$rootScope.req+"/"+$rootScope.genre+"/"+$rootScope.minPrix+"/"+$rootScope.maxPrix+"/"+$rootScope.avisMin;
	}
		
		
    $http.get("LivreAvecId", {params:{"id": $routeParams.id}}).then(function(response) {
    	$scope.livre = response.data;
    	$scope.moyenne = $scope.calculeMoyenne($scope.livre.lesAvis);
    	$scope.livre.dateDePublication = formatDateDMY($scope.livre.dateDePublication);
    	var premierePartieResume = $scope.livre.resume.substring(0, 350);
    	var deuxiemePartieResume = $scope.livre.resume.substring(350, $scope.livre.resume.length);
    	
    	var index = deuxiemePartieResume.search('\\.|!|\\?') + 1;
    	
    	
    	
    	var tmp = deuxiemePartieResume.substr(0, index);
    	$scope.premierePartieResume = premierePartieResume + tmp;
    	$scope.deuxiemePartieResume = deuxiemePartieResume.substr(index, deuxiemePartieResume.length);
    });
    
    
    $scope.init = function(moy, id){
    	var str = '#input-'+id;
    	$(str).rating({displayOnly: true, step: 0.1, size:'xl'});
    	$(str).rating('update', moy);
    }
    
    $scope.range = function(min, max, step) {
        step = step || 1;
        var input = [];
        for (var i = min; i <= max; i += step) {
            input.push(i);
        }
        return input;
    };
    
    $scope.allerAAvis = function() {
    	var newHash = 'avisClient';
    	if ($location.hash() !== newHash) {
        $location.hash('avisClient');
      } else {
        $anchorScroll();
      }
    }
    
    $scope.formatDateDMY = formatDateDMY;
    
    $scope.calculPromo = calculPromo;
    
    $scope.estEnPromo = estEnPromo;
    
    $scope.calculeMoyenne = function(list) {
    	var moy = 0;
    	for(var i = 0; i < list.length; i++)
    		moy += list[i].note;
    	return (moy / list.length).toFixed(1);
    }
    
    $scope.posterCommentaire = function(note, commentaire) {
    	if(userService.isConnected()) {
    		$http.get("AjouterCommentaire", {params:{"note": note, "commentaire": commentaire, "idLivre": $scope.livre.id, "idClient" : $cookies.get('idClient')}}).then(function(response) {
    			if(response.data === 'dejaCommente') {
    				document.getElementById('erreurDejaCommente').style.display = "block";
    			} else if (response.data === 'pasAchete') {
    				document.getElementById('erreurLivrePasAchete').style.display = "block";
    			}
    			else {
    				$scope.livre = response.data;
    			}
    		});
    	} else if ( note === undefined || commentaire === undefined ) {
    		document.getElementById('erreurPosterUnCommentaire').style.display = "block";
    	}
    	else { // popup connection
    		modalInstance = $uibModal.open({
    		      ariaLabelledBy: 'modal-title',
    		      ariaDescribedBy: 'modal-body',
    		      templateUrl: '/template/modalConnection/modalConnection.html',
    		      controller: 'modalConnexionCommentaireCtrl',
    		      resolve : {
    		          note: function() {
    		              return note;
    		          },
    		          commentaire: function(){
    		              return commentaire;
    		          },
    		          idLivre: function(){
    		              return $scope.livre.id;
    		          }
    		      }
    		    });
    	}
    }
    
    $scope.nombreAvis = function(list) {
    	return list.length;
    }
});
