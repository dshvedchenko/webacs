app.controller('userClaimController',
    function ($scope,
              $http,
              $rootScope,
              $location,
              errorService,
              claimService,
              authService) {

        $scope.claims = [];
        $scope.isEditing = false;
        $scope.newClaimSteps = false;
        $scope.resources = [];

        if (!authService.isLogged()) {
            $location.path("/")
        }

        refreshClaims = function () {
            claimService.getAllMyClaims()
                .then(
                    function (data) {
                        $scope.claims = data.data;
                    },
                    function (response) {
                        errorService.setError(response.data.error)
                    }
                )
        }


        function orderBy(prop) {
            $scope.claimOrderBy = prop
        };

        function newClaimsWizard() {
            getResourcesToClaim();
            $scope.newClaimSteps = true;
        }

        function getResourcesToClaim() {
            claimService.getAllResourcesForClaim()
                .then(
                    function (data) {
                        $scope.resources = data;
                    }
                    ,
                    function (error) {
                        errorService.setError(error)
                    }
                );
        }

        function getPermissionsByResource(resource) {
            claimService.getPermissionsByResourceId(resource.id)
                .then(
                    function (data) {
                        resource.permissions = data;
                    },
                    function (error) {
                        errorService.setError(error)
                    }
                );
        }

        function submitClaims() {
            claimService.createClaims(convertSelectionToPermissionIdsLists())
                .then(
                    function (data) {
                        $scope.newClaimSteps = false;
                        refreshClaims();
                    }
                );
            ;
        }

        function convertSelectionToPermissionIdsLists() {
            return $scope.resources
                .filter(function (i) {
                    return i.claimed
                })
                .reduce(function (perms, item) {
                    return perms.concat(item.permissions.filter(
                        function (permission) {
                            return permission.claimed & !permission.alreadyClaimed
                        })
                    )
                }, [])
                .map(function (permission) {
                    return {permissionId: permission.id}
                })
        }

        function isAnyResourcesClaimed() {
            return $scope.resources.some(function (i) {
                return i.claimed
            });
        }

        refreshClaims();

        $scope.orderBy = orderBy;
        $scope.newClaimsWizard = newClaimsWizard;
        $scope.getPermissionsByResource = getPermissionsByResource;
        $scope.submitClaims = submitClaims;
        $scope.isAnyResourcesClaimed = isAnyResourcesClaimed;

    });