var app = angular.module('app', ['ngRoute']);

// app.controller('FController', function ($scope, $http) {
//     $scope.token = ''
//     $scope.userInfo = {}
//     var data = {
//         username: 'admin',
//         password: '1qaz2wsx'
//     }
//     $http.post(backend_server + "/api/v1/login", data)
//         .then(
//             function ok(response) {
//                 $scope.token = response.data.data.token
//                 setToken()
//             }
//         )
//
//     function setToken() {
//
//         var config = {
//             headers: {
//                 'X-AUTHID': $scope.token,
//                 'Content-Type': 'application/json'
//             }
//         }
//         $http({
//             method: 'GET',
//             url: backend_server + "/api/v1/user/1",
//             data: '',
//             headers: config.headers
//
//         })
//             .then(
//                 function ok(response) {
//                     $scope.userInfo = response.data.data
//                 }
//             )
//
//         $http.post(backend_server + "/api/v1/logout", {}, config)
//             .then(
//                 function ok(response) {
//                     var res = response.data
//                     console.log(res)
//                 }
//             )
//     }
// })