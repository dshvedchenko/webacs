app.config(function ($routeProvider) {
    $routeProvider
        .when("/login", {
            templateUrl: "views/login.html",
            controller: "loginController"
        })
        .when("/userregister", {
            templateUrl: "views/userregister.html",
            controller: "userRegisterController"
        })
        .when("/logout", {
            templateUrl: "views/logout.html",
            controller: "logoutController"
        })

        .when("/userprofile", {
            templateUrl: "views/userprofile.html",
            controller: "userProfileController"
        })

        .when("/claims", {
            templateUrl: "views/claims.html" //,
            // controller: "claimController"
        })

        .when("/claims/list", {
            templateUrl: "views/claims/list.html",
            controller: "claimController",
            stateFilter: "all"
        })
        .when("/claims/grant", {
            templateUrl: "views/claims/grant.html",
            controller: "grantClaimController"
        })
        .when("/claims/revoke", {
            templateUrl: "views/claims/revoke.html",
            controller: "revokeClaimController"
        })
        .when("/claims/revoked", {
            templateUrl: "views/claims/list.html",
            controller: "claimController",
            stateFilter: "revoked"
        })
        .when("/claims/declined", {
            templateUrl: "views/claims/list.html",
            controller: "claimController",
            stateFilter: "declined"
        })
        .when("/claims/approve", {
            templateUrl: "views/claims/approve.html",
            controller: "approveClaimController"
        })
        .when("/claims/myclaims", {
            templateUrl: "views/claims/myclaims.html",
            controller: "userClaimController"
        })

        // admin UI

        .when("/admin/users", {
            templateUrl: "views/admin/users.html",
            controller: "adminUserController"
        })

        .when("/admin/restypes", {
            templateUrl: "views/admin/restypes.html",
            controller: "restypesController"
        })
        .when("/admin/resources", {
            templateUrl: "views/admin/resources.html",
            controller: "resourcesController"
        })
        .when("/admin/permissions", {
            templateUrl: "views/admin/permissions.html",
            controller: "permissionsController"
        })
    ;
});