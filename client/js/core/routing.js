app.config(function ($routeProvider) {
    $routeProvider
        .when("/resourcetype", {
            templateUrl: "views/resourcetype.html",
            controller: "resourceTypeController"
        })
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
        .when("/admin/users", {
            templateUrl: "views/admin/users.html",
            controller: "adminUserController"
        })
        .when("/claims", {
            templateUrl: "views/claims.html",
            controller: "claimController"
        })
    ;
});