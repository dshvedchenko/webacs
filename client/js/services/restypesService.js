app.service('restypesService', function ($rootScope, $http, ENDPOINT_URI) {
    var service = this,
        path = '/restypes';

    service.getAll = function () {
        return $http.get(ENDPOINT_URI + path)
            .then(
                function ok(response) {
                    return response.data;
                }
            )
    };

    service.create = function (data, onSuccess, onError) {
        $http.post(ENDPOINT_URI + path, data)
            .then(
                function ok(response) {
                    onSuccess(response.data.data)
                },
                function error(response) {
                    onError(response.data.error)
                }
            )
    };

    service.update = function (data) {
        return $http.put(ENDPOINT_URI + path, data);
    };

    service.delete = function (id) {
        return $http.delete(ENDPOINT_URI + path + "/" + id);
    }
});