app.controller('declinedClaimController',
    function ($scope,
              $http,
              $rootScope,
              $location,
              errorService,
              claimService,
              authService) {

        $scope.claims = [];

        if (!authService.isLogged()) {
            $location.path("/")
        }

        function getClaims() {
            claimService.getAllDeclined()
                .then(
                    function (data) {
                        $scope.claims = data.data;
                    },
                    function (response) {
                        errorService.setError(response.data.error)
                    }
                )
        }


        $scope.orderBy = function (prop) {
            $scope.claimOrderBy = prop
        };


        getClaims();

    });