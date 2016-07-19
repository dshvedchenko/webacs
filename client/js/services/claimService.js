app.service('claimService', function ($rootScope, $http, ENDPOINT_URI) {

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
})