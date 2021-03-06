app.controller('loginController', function ($scope, authService, $http, $rootScope, $location, errorService) {
    $scope.username = '';
    $scope.password = '';
    $scope.loginError = '';

    $scope.handleLogin = function () {
        var data = {
            username: angular.copy($scope.username),
            password: angular.copy($scope.password)
        };
        authService.login(data, onSuccess, onError)
    };

    onSuccess = function (token) {
        $rootScope.loggedin = true;
        $rootScope.token = token;
        $location.path("/")
    };

    onError = function (error) {
        $scope.loginError = error;
        errorService.setError(error)
    };

    $scope.logingAsAdmin = function () {
        $scope.username = 'admin';
        $scope.password = '1qaz2wsx';
        $scope.handleLogin();
    };

    $scope.logingAsUser1 = function () {
        $scope.username = 'johns';
        $scope.password = '1qaz2wsx';
        $scope.handleLogin();
    };

    $scope.logingAsUser2 = function () {
        $scope.username = 'billk';
        $scope.password = '1qaz2wsx';
        $scope.handleLogin();
    };

    $scope.badLoginAsUser1 = function () {
        $scope.username = 'johns';
        $scope.password = '1sx';
        $scope.handleLogin();
    }

});