app.controller('logoutController', function ($scope, $http, $rootScope, $location, authService) {

    function logout() {
        authService.logout(onSuccess, onError);
        $location.path("/login")
    }
    onSuccess = function () {
        console.log('logged out')
    };

    onError = function () {
        console.log('logged out error')
    };

    logout()

});