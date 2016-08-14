app.controller('claimController',
    function ($scope,
              $http,
              $rootScope,
              $location,
              errorService,
              claimService,
              authService,
              $route) {

        $scope.claims = [];
        $scope.isEditing = false;
        $scope.created = '';
        $scope.stateFilter = $route.current.stateFilter

        if (!authService.isLogged()) {
            $location.path("/")
        }

        function getClaims() {
            if ($scope.stateFilter === 'all') var func = claimService.getAllClaims;
            if ($scope.stateFilter === 'revoked') var func = claimService.getAllRevoked;
            if ($scope.stateFilter === 'declined') var func = claimService.getAllDeclined;

            func()
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