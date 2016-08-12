app.service('claimService', function ($rootScope, $http, ENDPOINT_URI, errorService,
                                      resourcesService,
                                      permissionsService) {

    var service = this,
        path = '/claims';

    service.createClaims = function (data) {
        return $http.post(ENDPOINT_URI + path, data)
            .then(
                function ok(response) {
                    return response.data;
                },
                function err(error) {
                    errorService.setError(error)
                }
            )
    }

    function updateClaim(data) {
        console.log(data);
    }

    function approveClaim(claimId) {
        console.log(claimId);
    }

    function grantClaim(claimId) {
        console.log(claimId);
    }

    function revokeClaim(claimId) {
        console.log(claimId);
    }

    service.getAllMyClaims = function () {
        return $http.get(ENDPOINT_URI + path + "/own")
            .then(
                function ok(response) {
                    return response.data;
                },
                function err(error) {
                    errorService.setError(error)
                }
            )
    };

    function getAllClaimsForUser(userId) {
        console.log(userId)
    }

    service.getAllClaims = function () {
        return $http.get(ENDPOINT_URI + path)
            .then(
                function ok(response) {
                    return response.data;
                },
                function err(error) {
                    errorService.setError(error)
                }
            )
    };

    service.getAllClaimed = function () {
        return $http.get(ENDPOINT_URI + path + "/claimed")
            .then(
                function ok(response) {
                    return response.data;
                },
                function err(error) {
                    errorService.setError(error)
                }
            )
    };

    service.getAllApproved = function () {
        return $http.get(ENDPOINT_URI + path + "/approved")
            .then(
                function ok(response) {
                    return response.data;
                },
                function err(error) {
                    errorService.setError(error)
                }
            )
    };

    service.getAllGranted = function () {
        return $http.get(ENDPOINT_URI + path + "/granted")
            .then(
                function ok(response) {
                    return response.data;
                },
                function err(error) {
                    errorService.setError(error)
                }
            )
    };

    service.getAllRevoked = function () {
        return $http.get(ENDPOINT_URI + path + "/revoked")
            .then(
                function ok(response) {
                    return response.data;
                },
                function err(error) {
                    errorService.setError(error)
                }
            )
    }

    service.getAllDeclined = function () {
        return $http.get(ENDPOINT_URI + path + "/declined")
            .then(
                function ok(response) {
                    return response.data;
                },
                function err(error) {
                    errorService.setError(error)
                }
            )
    }

    service.getAllResourcesForClaim = function () {
        return resourcesService.getAll()
            .then(function (resp) {
                    var resourcesToClaim = resp.data.map(function (res) {
                        res['claimed'] = false;
                        return res
                    })
                    return resourcesToClaim;
                },
                function (error) {
                    return "can not get resources";
                }
            )

    }

    service.getPermissionsByResourceId = function (id) {
        return permissionsService.getAllByResourceId(id)
            .then(function (reponse) {
                    var permissions = reponse.data;
                    return permissions;
                },
                function (error) {
                    return "can not regt permissions";
                }
            )
    }
});