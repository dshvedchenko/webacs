app.controller('grantClaimController',
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

        function getAllClaims() {
            claimService.getAllApproved()
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
            getAllClaims();
        }

        $scope.decline = function (item) {
            claimService.decline(item.id)
                .then(refresh)
        }

        $scope.grant = function (item) {
            claimService.grant(item.id)
                .then(refresh)
        }

        $scope.orderBy = function (prop) {
            $scope.claimOrderBy = prop
        };


        getAllClaims();

    });