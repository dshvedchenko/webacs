var app = angular.module('app', ['ngRoute', 'ngAnimate'])
    .constant('ENDPOINT_URI', "http://localhost:8080/api/v1")

    .filter("asDate", function () {
        return function (input) {
            if (input === null) return null;
            return new Date(input);
        }
    });

