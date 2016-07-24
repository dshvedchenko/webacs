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

        // admin UI

        .when("/admin/users", {
            templateUrl: "views/admin/users.html",
            controller: "adminUserController"
        })
        .when("/claims", {
            templateUrl: "views/claims.html",
            controller: "claimController"
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