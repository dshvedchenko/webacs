app.controller('permissionsController',
    function ($scope,
              $rootScope,
              resourcesService,
              permissionsService,
              errorService,
              authService,
              $location) {

        $scope.items = [];
        $scope.isEditing = false;
        $scope.created = '';

        if (!authService.isLogged()) {
            $location.path("/")
        }

        function getAll() {

            permissionsService.getAll()
                .then(
                    function (data) {
                        $scope.items = [];
                        $scope.items = data.data;
                    },
                    function (response) {
                        errorService.setError(response)
                    }
                )
        }


        function createItem(item) {
            var data = angular.copy(item);
            permissionsService.create(data, onSuccessCreation, onErrorCreation);
        }
        function getAvailableResources() {
            resourcesService.getAll()
                .then(
                    function (data) {
                        $scope.availableResources = data.data;
                    },
                    function (response) {
                        errorService.setError(response)
                    }
                )
        }

        function deleteItem(item) {
            permissionsService.delete(item.id)
                .then(function ok(response) {
                        getAll();
                        leaveDeleteItem()
                    },
                    function error(response) {
                        errorService.setError(response)
                    }
                )

        }
        function updateItem(item) {
            var data = angular.copy(item);
            permissionsService.update(data)
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
                title: '',
                description: '',
                resource: {}
            };
            getAvailableResources()

        }

        onSuccessCreation = function (data) {
            $scope.created = 'CREATED';
            leaveCreation();
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
            $scope.newItem = null;
            getAvailableResources();
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