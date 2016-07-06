app.service('coreService', function ($rootScope, $http) {
    this.counter = 0;
    this.shares = 2000;

    this.getHelloMessage = function () {
        console.log(this.counter)
        return this.counter++;
    }

    this.handleLogin = function (data, onSuccess) {
        $http.post(backend_server + "/api/v1/login", data)
            .then(
                function ok(response) {
                    onSuccess(response.data.data.token)
                },
                function error(response) {
                    $rootScope.token = undefined
                    $rootScope.loginError = response.data
                }
            )
    }

});