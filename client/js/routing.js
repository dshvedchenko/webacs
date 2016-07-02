app.config(function ($routeProvider) {
    $routeProvider
        .when("/first", {
            templateUrl: "views/first.html",
            controller: "firstController"
        })
        .when("/second", {
            templateUrl: "views/second.html",
            controller: "secondController"
        });
});