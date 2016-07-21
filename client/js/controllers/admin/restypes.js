app.controller('restypesController', function ($scope, $rootScope, restypesService, errorService, authService, $location) {

    $scope.items = [];
    $scope.isEditing = false;
    $scope.created = ''

    if (!authService.isLogged()) {
        $location.path("/")
    }

    function getAll() {
        restypesService.getAll()
            .then(
                function (data) {
                    $scope.items = data.data;
                },
                function (response) {
                    errorService.setError(response.data.error)
                }
            )
    }


    function createItem(item) {
        var data = angular.copy(item);
        restypesService.create(data, onSuccessCreation, onErrorCreation)

    };

    function updateItem(item) {
        var data = angular.copy(item)
        restypesService.update(data)
            .then(
                function ok(response) {
                    getAll();
                    leaveEditing();
                },
                function (response) {
                    errorService.setError(response.data.error)
                }
            )
    }

    function getNewItemForView() {
        $scope.newItem = {
            name: ''
        };
    }

    onSuccessCreation = function (data) {
        $scope.created = 'CREATED'
        finishCreation();
        getAll();
    };

    onErrorCreation = function (data) {
        $scope.errorBox = data
    };

    function isFormValid(data) {
        return (
            data.name.length > 0
        )
    }

    function isCurrentItem(itemId) {
        return $scope.editedItem !== null && $scope.editedItem.id === itemId;
    }

    function setEditedItem(item) {
        $scope.editedItem = angular.copy(item);
        $scope.isEditing = true;
        $scope.isCreation = false;
        $scope.newUser = null;
    }

    function leaveEditing() {
        $scope.editedItem = null;
        $scope.isEditing = false;
    }

    function setCreation() {
        leaveEditing();
        $scope.isCreation = true;
        getNewItemForView();
    }

    function finishCreation() {
        $scope.isCreation = false;
    }

    $scope.editedItem = null;
    $scope.updateItem = updateItem;
    $scope.leaveEditing = leaveEditing;
    $scope.setEditedItem = setEditedItem;
    $scope.isCurrentItem = isCurrentItem;
    $scope.isFormValid = isFormValid;
    $scope.createItem = createItem;
    $scope.getAll = getAll;
    $scope.setCreation = setCreation;


    getAll()
});