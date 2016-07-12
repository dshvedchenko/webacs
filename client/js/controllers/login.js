app.controller('loginController', function ($scope, coreService, $http, $rootScope, $location) {
    $scope.username = ''
    $scope.password = ''
    $scope.loginError = ''

    $scope.handleLogin = function () {
        var data = {
            username: angular.copy($scope.username),
            password: angular.copy($scope.password)
        }
        coreService.login(data, onSuccess, onError)
    }

    onSuccess = function (token) {
        $http.defaults.headers.common['X-AUTHID'] = token
        $rootScope.loggedin = true
        $location.path("/first")
    }

    onError = function (error) {
        $scope.loginError = error
    }

})