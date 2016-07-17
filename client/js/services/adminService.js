app.service('adminService', function ($rootScope, $http) {
    this.counter = 0;
    $rootScope.validRoles = [];

    this.createUser = function (data, onSuccess, onError) {
        var headers = {}
        $http.post(backend_server + "/api/v1/users", data, headers)
            .then(
                function ok(response) {
                    onSuccess(response.data.data)
                },
                function error(response) {
                    onError(response.data.error)
                }
            )
    }

    this.getValidRoles = function (onSuccess, onError) {
        if ($rootScope.validRoles.length === 0) {
            $http.get(backend_server + "/api/v1/sysroles")
                .then(
                    function ok(response) {
                        $rootScope.validRoles = response.data.data
                        onSuccess()
                    },
                    function error(response) {
                        onError("can not get sysroles list")
                    }
                )
        }
    }
});