app.service('errorService', function ($rootScope, $timeout) {

    this.setError = function (error) {
        var message = "n/a";
        if (error.data !== undefined && error.data.error !== undefined) {
            message = error.data.error.message
        } else {
            message = error.message
        }

        if (message === undefined) {
            message = error
        }

        $rootScope.errorBox = message;
        $timeout(function () {
            $rootScope.errorBox = undefined;
        }, 5000)
    };

});