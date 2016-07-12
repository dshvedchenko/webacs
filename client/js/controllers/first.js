app.controller('firstController', function ($scope, coreService, $http, $rootScope) {
    $scope.token = ''
    $scope.userInfo = {}
    $scope.rt = coreService


    setToken = function () {
        $http.get(backend_server + "/api/v1/users")
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