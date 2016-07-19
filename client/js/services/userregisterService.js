app.service('userregisterService', function ($rootScope, $http, ENDPOINT_URI) {
    this.counter = 0;
    this.shares = 2000;

    this.getHelloMessage = function () {
        console.log(this.counter)
        return this.counter++;
    }

    this.register = function (data, onSuccess, onError) {
        $http.post(ENDPOINT_URI + "/api/v1/register", data)
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