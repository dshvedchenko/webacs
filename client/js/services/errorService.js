app.service('errorService', function ($rootScope, $timeout) {

    this.setError = function (error) {
        $rootScope.errorBox = error.message;
        $timeout(function () {
            $rootScope.errorBox = undefined;
        }, 5000)
    };

})