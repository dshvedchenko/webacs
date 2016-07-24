app.controller('claimController',
    function ($scope,
              $http,
              $rootScope,
              $location,
              errorService,
              claimService,
              authService) {

        $scope.claims = [];
        $scope.isEditing = false;
        $scope.created = ''

        if (!authService.isLogged()) {
            $location.path("/")
        }

        function getAllClaims() {
            claimService.getAllClaims()
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
        }


        getAllClaims();

})