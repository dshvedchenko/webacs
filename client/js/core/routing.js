app.config(function ($routeProvider) {
    $routeProvider
        .when("/first", {
            templateUrl: "views/first.html",
            controller: "firstController"
        })
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
        .when("/users/create", {
            templateUrl: "views/admin/create_user.html",
            controller: "adminCreateUserController"
        })
    ;
});