app.service('claimService', function ($rootScope, $http, ENDPOINT_URI) {

    var service = this,
        path = '/claims';

    function createClaim(data) {
        console.log(data);
    }

    function updateClaim(data) {
        console.log(data);
    }

    function approveClaim(claimId) {
        console.log(claimId);
    }

    function grantClaim(claimId) {
        console.log(claimId);
    }

    function revokeClaim(claimId) {
        console.log(claimId);
    }

    function getAllMyClaims() {
        console.log("all claims for current user")
    }

    function getAllClaimsForUser(userId) {
        console.log(userId)
    }

    service.getAllClaims = function () {
        return $http.get(ENDPOINT_URI + path)
            .then(
                function ok(response) {
                    return response.data;
                }
            )
    }

})