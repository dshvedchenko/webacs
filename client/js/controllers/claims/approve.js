app.controller('approveClaimController',
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

        function getAllClaimed() {
            claimService.getAllClaimed()
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
            getAllClaimed();
        }

        $scope.orderBy = function (prop) {
            $scope.claimOrderBy = prop
        };

        $scope.approve = function (item) {
            claimService.approve(item.id)
                .then(refresh)
        }

        $scope.decline = function (item) {
            claimService.decline(item.id)
                .then(refresh)
        }

        getAllClaimed();

    });