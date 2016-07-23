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
                    errorService.setError(response)
                }
            )
    }


    function createItem(item) {
        var data = angular.copy(item);
        restypesService.create(data, onSuccessCreation, onErrorCreation)

    };

    function deleteItem(item) {
        restypesService.delete(item.id)
            .then(function ok(response) {
                    getAll()
                    leaveDeleteItem()
                },
                function error(response) {
                    errorService.setError(response)
                }
            )

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
                    errorService.setError(response)
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
        leaveCreation();
        leaveDeleteItem();
        $scope.newUser = null;
    }

    function leaveEditing() {
        $scope.editedItem = null;
        $scope.isEditing = false;
    }

    function setDeleteItem(item) {
        leaveEditing();
        leaveCreation();
        $scope.deletedItem = angular.copy(item);
        $scope.isDelete = true;
    }

    function leaveDeleteItem() {
        $scope.isDelete = false;
    }

    function setCreation() {
        leaveEditing();
        leaveDeleteItem();
        $scope.isCreation = true;
        getNewItemForView();
    }

    function leaveCreation() {
        $scope.isCreation = false;
    }

    $scope.editedItem = null;
    $scope.updateItem = updateItem;
    $scope.leaveEditing = leaveEditing;
    $scope.setDeleteItem = setDeleteItem;
    $scope.setEditedItem = setEditedItem;
    $scope.isCurrentItem = isCurrentItem;
    $scope.isFormValid = isFormValid;
    $scope.createItem = createItem;
    $scope.getAll = getAll;
    $scope.setCreation = setCreation;
    $scope.deleteItem = deleteItem;

    getAll()
});