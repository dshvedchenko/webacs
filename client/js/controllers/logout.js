app.controller('logoutController', function ($scope, $http, $rootScope, $location, coreService) {

    //$location.path("/login")

    $scope.logout = function () {
        coreService.logout(onSuccess, onError)

    };

    onSuccess = function () {
        console.log('logged out')
        delete $rootScope.token
        $rootScope.loggedin = undefined
    };

    onError = function () {
        console.log('logged out error')
    };

})