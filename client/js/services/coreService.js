app.service('coreService', function ($rootScope, $http) {
    this.counter = 0;

    this.getHelloMessage = function () {
        console.log(this.counter)
        return this.counter++;
    }

    this.login = function (data, onSuccess, onError) {

        var post = $http.post(backend_server + "/api/v1/login", data);
        post.then(
                function ok(response) {
                    onSuccess(response.data.data.token)
                },
                function error(response) {
                    $rootScope.token = undefined
                    onError(response.data.error)
                }
        );
        post.then(
            function setSession(response) {
                $rootScope.isAdmin = response.data.data.sysrole === "ADMIN"
                $rootScope.token = response.data.data.token
            }
        );

    }

    this.logout = function (onSuccess, onError) {

        var post = $http.post(backend_server + "/api/v1/logout", {});
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
                }
            )
    }
});