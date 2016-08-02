app.controller('userProfileController', function ($scope, appUserService, authService, $http, $rootScope, $location, errorService) {
    $scope.item = {};
    $scope.created = '';

    if (!authService.isLogged()) {
        $location.path("/")
    }

    function getAllCurrent() {
        $scope.item = angular.copy($rootScope.sessionUser)
    }

    getAllCurrent();

});