app.controller('secondController', function ($scope, coreService) {
    $scope.userCounter = coreService.getHelloMessage()
    console.log("Message from SECOND controller ")
});