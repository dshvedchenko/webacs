app.controller('approveClaimController',
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


        $scope.orderBy = function (prop) {
            $scope.claimOrderBy = prop
        };


        getAllClaimed();

    });