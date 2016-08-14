app.service('claimService', function ($rootScope, $http, ENDPOINT_URI, errorService,
                                      resourcesService,
                                      permissionsService) {

    var service = this,
        path = '/claims';

    function workFlowAction(claimId, action) {
        return $http.put(ENDPOINT_URI + path + "/" + claimId + "/" + action, {})
            .then(
                function ok(response) {
                    return response.data;
                },
                function err(error) {
                    errorService.setError(error)
                }
            )
    }

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

    service.update = function (data) {
        console.log(data);
    }

    service.approve = function (claimId) {
        return workFlowAction(claimId, "approve")
    }

    service.grant = function (claimId) {
        return workFlowAction(claimId, "grant")
    }

    service.revoke = function (claimId) {
        return workFlowAction(claimId, "revoke")
    }

    service.decline = function (claimId) {
        return workFlowAction(claimId, "decline")
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

    function getClaimsByState(state) {
        return $http.get(ENDPOINT_URI + path + (state.length > 0 ? "/" + state : ''))
            .then(
                function ok(response) {
                    return response.data;
                },
                function err(error) {
                    errorService.setError(error)
                }
            )
    }

    service.getAllClaims = function () {
        return getClaimsByState('')
    };

    service.getAllClaimed = function () {
        return getClaimsByState('claimed')
    };

    service.getAllApproved = function () {
        return getClaimsByState('approved')
    };

    service.getAllGranted = function () {
        return getClaimsByState('granted')
    };

    service.getAllRevoked = function () {
        return getClaimsByState('revoked')
    }

    service.getAllDeclined = function () {
        return getClaimsByState('declined')
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