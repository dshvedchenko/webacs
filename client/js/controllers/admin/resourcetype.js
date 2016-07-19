app.controller('resourceTypeController', function ($scope, $rootScope, coreService) {
    $scope.userCounter = coreService.getHelloMessage()
    console.log("Message from SECOND controller " + $rootScope.token)
});