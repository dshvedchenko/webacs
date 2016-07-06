app.controller('loginController', function ($scope, coreService, $http, $rootScope, $location) {
    $scope.username = ''
    $scope.password = ''

    $scope.handleLogin = function () {
        var data = {
            username: angular.copy($scope.username),
            password: angular.copy($scope.password)
        }
        coreService.handleLogin(data, onSuccess)
    }

    onSuccess = function (token) {
        $rootScope.token = token
        $location.path("/first")
    }

})