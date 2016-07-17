app.controller('adminCreateUserController', function ($scope, adminService, $http, $rootScope, $location) {
    $scope.registerError = '';
    $scope.created = ''
    $scope.data = {
        username: '',
        password: '',
        confirm_password: '',
        email: '',
        firstname: '',
        lastname: '',
        sysrole: {id: 1, name: 'GENERIC'}
    };

    adminService.getValidRoles(
        function () {
        },
        function (error) {
            alert(error)
        }
    )

    $scope.register = function () {
        var data = {
            username: angular.copy($scope.data.username),
            password: angular.copy($scope.data.password),
            email: angular.copy($scope.data.email),
            firstname: angular.copy($scope.data.firstname),
            lastname: angular.copy($scope.data.lastname),
            sysrole: angular.copy($scope.data.sysrole.id),
            enabled: true
        }
        adminService.createUser(data, onSuccess, onError)
    };

    onSuccess = function (data) {
        $scope.created = 'CREATED'
    };

    onError = function (data) {
        $scope.registerError = data
    };

    $scope.isFormValid = function () {
        return (
            $scope.data.username.length > 0
            && $scope.data.password.length > 0
            && $scope.data.password === $scope.data.confirm_password
            && $scope.data.sysrole !== undefined
        )
    }
});