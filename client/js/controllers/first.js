app.controller('firstController', function ($scope, coreService, $http) {
    $scope.token = ''
    $scope.userInfo = {}
    $scope.rt = coreService
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

    setToken = function () {
        var config = {
            headers: {
                'X-AUTHID': $scope.token,
                'Content-Type': 'application/json'
            }
        }
        $http({
            method: 'GET',
            url: backend_server + "/api/v1/user/list",
            data: '',
            headers: config.headers

        })
            .then(
                function ok(response) {
                    $scope.userInfo = response.data.data
                }
            )
    }

    $scope.handleClick = function (evt) {
        console.log(evt)
        $scope.userInfo.pop();
    }


})