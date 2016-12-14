app.controller("popoverConnexionCtrl", function($scope, $http, $rootScope, userService){
	
	$scope.connexion = function (pseudo, mdp) {
		userService.signIn(pseudo, mdp, "erreurIdentifiantPopoverConnexion");
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