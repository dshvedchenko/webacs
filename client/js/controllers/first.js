app.controller('firstController', function ($scope, coreService, $http, $rootScope) {
    $scope.token = ''
    $scope.userInfo = {}
    $scope.rt = coreService


    setToken = function () {
        var config = {
            headers: {
                'X-AUTHID': $rootScope.token,
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

    setToken()

})