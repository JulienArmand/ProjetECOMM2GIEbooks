app.controller("popoverConnexionCtrl", function($scope, $http, $rootScope){
	$scope.connexion = function (pseudo, mdp) {
		$http.get("ConnexionClient", {params:{"pseudo": pseudo, "motDePasse": mdp}}).then(function(response) {
			var login = getCookie('login');
			if (login != null && login != "") {
				$rootScope.login = login;
				$rootScope.estConnecte = true;
			}
			var erreur = getCookie('erreur');
			if (erreur == "true") {
				$rootScope.estConnecte = false;
				document.getElementById('erreurIdentifiantPopoverConnexion').style.display = "block";
			}
		});
	}
});

app.directive('hoverPopover2', function ($compile, $templateCache, $timeout, $rootScope, $compile) {
	var getTemplate = function (contentType) {
	    return $templateCache.get('templatePopoverMonCompte.html');
	};
	return {
	    restrict: 'A',
	    link: function (scope, element, attrs) {
	    	
	        var content = getTemplate();
	        var compileContent = $compile(content)(scope);
	        $rootScope.insidePopover = false;
	        $(element).popover({
	            content: compileContent,
	            placement: 'bottom',
	            html: true
	        });
            
	        $("#monCompteHeader").hover(function(){
	        	$(element).popover('show');
                scope.attachEvents(element);
	        });
	        
	    },
	    controller: function ($scope, $element) {
	        $scope.attachEvents = function (element) {
	            $('.popover').on('mouseenter', function () {
	                $rootScope.insidePopover = true;
	            });
	            $('.popover').on('mouseleave', function () {
	                $rootScope.insidePopover = false;
	                $(element).popover('hide');
	            });
	        }
	    }
	    
	};
});

app.directive('hoverPopover', function ($compile, $templateCache, $timeout, $rootScope, $compile) {
	var getTemplate = function (contentType) {
	    return $templateCache.get('templatePopoverConnexion.html');
	};
	return {
	    restrict: 'A',
	    link: function (scope, element, attrs) {
	    	
	        var content = getTemplate();
	        var compileContent = $compile(content)(scope);
	        $rootScope.insidePopover = false;
	        $(element).popover({
	            content: compileContent,
	            placement: 'bottom',
	            html: true
	        });
            
	        $(element).hover(function(){
	        	$(element).popover('show');
                scope.attachEvents(element);
	        });
	        
	    },
	    controller: function ($scope, $element) {
	        $scope.attachEvents = function (element) {
	            $('.popover').on('mouseenter', function () {
	                $rootScope.insidePopover = true;
	            });
	            $('.popover').on('mouseleave', function () {
	                $rootScope.insidePopover = false;
	                $(element).popover('hide');
	            });
	        }
	    }
	    
	};
});