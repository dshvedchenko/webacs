app.service('sessionService', function ($rootScope, $http) {

    this.isAdmin = function () {
        return $rootScope.isAdmin
    }

})