app.service('appUserService', function ($rootScope, $http, ENDPOINT_URI) {
    this.counter = 0;
    $rootScope.validRoles = [];
    var service = this,
        path = '/users';

    service.all = function () {
        return $http.get(ENDPOINT_URI + path)
            .then(
                function ok(response) {
                    return response.data;
                }
            )
    }

    this.createUser = function (data, onSuccess, onError) {
        $http.post(ENDPOINT_URI + path, data)
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
            $http.get(ENDPOINT_URI + "/sysroles")
                .then(
                    function ok(response) {
                        if (response.data.data !== undefined) $rootScope.validRoles = response.data.data
                        if (onSuccess instanceof Function) onSuccess()
                    },
                    function error(response) {
                        onError("can not get sysroles list")
                    }
                )
        }
    }

    this.updateItem = function (data) {
        return $http.put(ENDPOINT_URI + path, data);
    }

    this.deleteItem = function (id) {
        return $http.delete(ENDPOINT_URI + path + "/" + id);
    }
});