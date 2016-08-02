app.controller('userRegisterController', function ($scope, userregisterService, $http, $rootScope, $location, errorService) {

    $scope.username = '';
    $scope.password = '';
    $scope.confirm_password = '';
    $scope.email = '';
    $scope.firstname = '';
    $scope.lastname = '';
    $scope.registerError = '';

    $scope.register = function () {
        var data = {
            username: angular.copy($scope.username),
            password: angular.copy($scope.password),
            email: angular.copy($scope.email),
            firstName: angular.copy($scope.firstname),
            lastName: angular.copy($scope.lastname)
        };
        userregisterService.register(data, onSuccess, onError)
    };

    onSuccess = function (data) {
        $location.path("/login")
    };

    onError = function (data) {
        errorService.setError(data)
    };

    $scope.isFormValid = function () {
        return (
            $scope.username.length > 0
            && $scope.password.length > 0
            && $scope.password === $scope.confirm_password
        )
    }
});