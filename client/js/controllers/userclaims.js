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
        $scope.newClaimSteps = 0;
        $scope.resources = [];

        if (!authService.isLogged()) {
            $location.path("/")
        }

        get = function () {
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
            $scope.newClaimSteps++;
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
            var claimedRes = $scope.resources.filter(function (i) {
                return i.claimed
            })
            var claimedPerm = claimedRes.filter(
                function (i) {
                    return i.permissions.some(
                        function (p) {
                            return p.claimed
                        }
                    )
                }
            )
        )
            ;
        }

        function anyResourcesClaimed() {
            return $scope.resources.some(function (i) {
                return i.claimed
            });
        }

        get();

        $scope.orderBy = orderBy;
        $scope.newClaimsWizard = newClaimsWizard;
        $scope.getPermissionsByResource = getPermissionsByResource;
        $scope.submitClaims = submitClaims;
        $scope.anyResourcesClaimed = anyResourcesClaimed;

    });