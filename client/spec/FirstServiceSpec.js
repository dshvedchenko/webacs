// describe('My first controller', function () {
//     var $controller, httpBackend, scope;
//
//     beforeEach(module('app'));
//
//     beforeEach(inject(function (_$controller_, $httpBackend, $rootScope) {
//         $controller = _$controller_;
//         httpBackend = $httpBackend;
//         scope = $rootScope.$new();
//
//         createController = function () {
//             return $controller('firstController', {
//                 '$scope': scope
//             });
//         };
//
//     }));
//
//     afterEach(function () {
//         httpBackend.verifyNoOutstandingExpectation();
//         httpBackend.verifyNoOutstandingRequest();
//     });
//
//
//     it('should return token', function () {
//         var $scope = {};
//         createController();
//
//         httpBackend.expect('POST', backend_server + "/api/v1/login")
//             .respond({
//                 "data": {"token": "122345332"}
//             });
//         httpBackend.expect("GET", backend_server + "/api/v1/user/list")
//             .respond({
//                     "data": [1, 2, 3, 4]
//                 }
//             );
//
//         // scope.$apply(function() {
//         //     scope.runTest();
//         // });
//         httpBackend.flush()
//         expect(scope.token).toEqual('122345332');
//         expect(scope.userInfo).toEqual([1, 2, 3, 4]);
//     })
//
// });