app.service('userregisterService', function ($rootScope, $http) {
    this.counter = 0;
    this.shares = 2000;

    this.getHelloMessage = function () {
        console.log(this.counter)
        return this.counter++;
    }

    this.register = function (data, onSuccess, onError) {
        $http.post(backend_server + "/api/v1/register", data)
            .then(
                function ok(response) {
                    onSuccess(response.data.data)
                },
                function error(response) {
                    onError(response.data.error)
                }
            )
    }
});