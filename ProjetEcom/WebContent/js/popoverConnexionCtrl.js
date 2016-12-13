app.controller("popoverConnexionCtrl", function($scope, $http, $rootScope){
	$scope.connexion = function (pseudo, mdp) {
		$http.get("ConnexionClient", {params:{"pseudo": pseudo, "motDePasse": mdp}}).then(function() {
			var login = getCookie('login');
			if (login !== null && login !== "") {
				$rootScope.login = login;
				$rootScope.estConnecte = true;
				$http.get("GestionCommande", {
					params:{"action" :"commandeClient"}}).then(function(response) {
						$rootScope.commandes = response.data;
					});	
			}
			var erreur = getCookie('erreur');
			if (erreur === "true") {
				$rootScope.estConnecte = false;
				document.getElementById('erreurIdentifiantPopoverConnexion').style.display = "block";
			}
		});
	}
});

app.directive('hoverPopover2', function ($compile, $templateCache, $timeout, $rootScope) {
	var getTemplate = function () {
	    return $templateCache.get('templatePopoverMonCompte.html');
	};
	return {
	    restrict: 'A',
	    link: function (scope, element) {
	    	
	        var content = getTemplate();
	        var compileContent = $compile(content)(scope);
	        $rootScope.insidePopover = false;
	        $(element).popover({
	            content: compileContent,
	            placement: 'bottom',
	            html: true,
	            trigger: 'click'
	        });
	    }
	    
	};
});

app.directive('hoverPopover', function ($compile, $templateCache, $timeout, $rootScope) {
	var getTemplate = function () {
	    return $templateCache.get('templatePopoverConnexion.html');
	};
	return {
	    restrict: 'A',
	    link: function (scope, element) {
	    	
	        var content = getTemplate();
	        var compileContent = $compile(content)(scope);
	        $rootScope.insidePopover = false;
	        $(element).popover({
	            content: compileContent,
	            placement: 'bottom',
	            html: true,
	            trigger: 'click'
	        }); 
	    }
	};
});