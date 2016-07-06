app.config(function ($routeProvider) {
    $routeProvider
        .when("/first", {
            templateUrl: "views/first.html",
            controller: "firstController"
        })
        .when("/second", {
            templateUrl: "views/second.html",
            controller: "secondController"
        })
        .when("/resourcetype", {
            templateUrl: "views/resourcetype.html",
            controller: "resourceTypeController"
        })
        .when("/login", {
            templateUrl: "views/login.html",
            controller: "loginController"
        })
    ;
});