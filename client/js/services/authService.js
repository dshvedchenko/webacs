app.service('authService', function ($rootScope, $http, ENDPOINT_URI, appUserService) {

    this.isAdmin = function () {
        return $rootScope.isAdmin
    }

    this.isLogged = function () {
        return $rootScope.token !== undefined;
    }

    this.login = function (data, onSuccess, onError) {

        var post = $http.post(ENDPOINT_URI + "/login", data);
        post.then(
            function ok(response) {
                onSuccess(response.data.data.token)
            },
            function error(response) {
                $rootScope.token = undefined
                onError(response.data !== null ? response.data.error : "UNKNOWN NETWORK ERROR")
            }
        );
        post.then(
            function setSession(response) {
                $rootScope.isAdmin = response.data.data.sysrole === "ADMIN"
                $rootScope.token = response.data.data.token
            }
        ).then(
            function loadCurrentUser() {
                appUserService.getSessionUser()
            }
        )
        ;

    }

    this.logout = function (onSuccess, onError) {
        if ($rootScope.token === undefined) return;
        var post = $http.post(ENDPOINT_URI + "/logout", {});
        post.then(
            function ok(response) {
                onSuccess(undefined)
            },
            function error(response) {

                onError(response.data.error)
            }
        );
        post.then(
            function clearSession(response) {
                delete $rootScope.token
                delete $rootScope.isAdmin
                delete $rootScope.loggedin
            }
        )
    }

})