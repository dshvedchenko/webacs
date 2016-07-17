app.controller('firstController', function ($scope, coreService, sessionService, $http, $rootScope) {
    $scope.token = ''
    $scope.userInfo = []
    $scope.rt = coreService
    // $scope.isAdmin = sessionService.isAdmin()


    fillData = function () {
        $http.get(backend_server + "/api/v1/users")
            .then(
                function ok(response) {
                    $scope.userInfo = response.data.data
                }
            )
    }

    $scope.handleClick = function (evt) {
        console.log(evt)
        var index = $scope.userInfo.indexOf(evt)
        $scope.userInfo.splice(index, 1)
    }

    fillData()

})