app.controller('logoutController', function ($scope, $http, $rootScope, $location) {
    delete $http.defaults.headers.common['X-AUTHID']
    $rootScope.loggedin = undefined
    //$location.path("/login")
})