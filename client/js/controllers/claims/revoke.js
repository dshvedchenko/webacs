app.controller('revokeClaimController',
    function ($scope,
              $http,
              $rootScope,
              $location,
              errorService,
              claimService,
              authService) {

        $scope.claims = [];
        $scope.isEditing = false;
        $scope.created = '';

        if (!authService.isLogged()) {
            $location.path("/")
        }

        function getClaims() {
            claimService.getAllGranted()
                .then(
                    function (data) {
                        $scope.claims = data.data;
                    },
                    function (response) {
                        errorService.setError(response.data.error)
                    }
                )
        }

        function refresh() {
            getClaims()
        }

        $scope.revoke = function (item) {
            claimService.revoke(item.id)
                .then(
                    refresh()
                )
        }


        $scope.orderBy = function (prop) {
            $scope.claimOrderBy = prop
        };


        getClaims();

    });