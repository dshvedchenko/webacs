var app = angular.module('FApp', []);

app.controller('FController', function ($scope, $http) {
    $scope.token = ''
    var data = {
        username: 'admin',
        password: '1qaz2wsx'
    }
    $http.post(backend_server + "/api/v1/login", data)
        .then(
            function ok(response) {
                $scope.token = response.data.data.token
                setToken()
            }
        )

    function setToken() {

        var config = {
            headers: {
                'X-AUTHID': $scope.token,
                'Content-Type': 'application/json'
            }
        }
        $http.post(backend_server + "/api/v1/testdata", {}, config)
            .then(
                function ok(response) {
                    var res = response.data
                    console.log(res)
                }
            )


        var config = {
            headers: {
                'X-AUTHID': $scope.token,
                'Content-Type': 'application/json'
            }
        }
        $http.post(backend_server + "/api/v1/logout", {}, config)
            .then(
                function ok(response) {
                    var res = response.data
                    console.log(res)
                }
            )
    }
})