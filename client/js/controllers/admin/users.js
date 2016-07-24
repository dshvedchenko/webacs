app.controller('adminUserController', function ($scope, appUserService, authService, $http, $rootScope, $location, errorService) {
    $scope.users = [];
    $scope.isEditing = false;
    $scope.created = ''

    if (!authService.isLogged()) {
        $location.path("/")
    }

    appUserService.getValidRoles(
        null,
        function (response) {
            errorService.setError(response.data)
        }
    )

    function getAll() {
        appUserService.all()
            .then(
                function (data) {
                    $scope.users = data.data;
                },
                function (response) {
                    errorService.setError(response.data)
                }
            )
    }

    function createUser(item) {
        var data = angular.copy(item);
        appUserService.createUser(data, onSuccessCreation, onErrorCreation)

    };

    function updateItem(item) {
        var data = angular.copy(item)
        appUserService.update(data)
            .then(
                function ok(response) {
                    getAll();
                    leaveEditing();
                },
                function (response) {
                    errorService.setError(response.data)
                }
            )
    }

    function disableItem(item) {
        appUserService.delete(item.id)
            .then(function ok(response) {
                    getAll()
                    leaveDisableItem()
                },
                function error(response) {
                    errorService.setError(response)
                }
            )

    };

    function getNewUserForView() {
        $scope.newUser = {
            username: '',
            password: '',
            confirm_password: '',
            email: '',
            firstname: '',
            lastname: '',
            sysrole: 'GENERIC',
            enabled: true
        };
    }

    onSuccessCreation = function (data) {
        $scope.created = 'CREATED'
        leaveCreation();
        getAll();
    };

    onErrorCreation = function (data) {
        $scope.errorBox = data
    };

    function isFormValid(data) {
        return (
            data.username.length > 0
            && data.password.length > 0
            && data.password === data.confirm_password
            && data.sysrole !== undefined
        )
    }

    function isCurrentItem(itemId) {
        return $scope.editedItem !== null && $scope.editedItem.id === itemId;
    }

    function setEditedItem(user) {
        $scope.editedItem = angular.copy(user);
        $scope.isEditing = true;
        leaveDisableItem();
        leaveCreation();
    }

    function leaveEditing() {
        $scope.editedItem = null;
        $scope.isEditing = false;
    }

    function setCreation() {
        leaveEditing();
        $scope.isCreation = true;
        getNewUserForView();
    }

    function leaveCreation() {
        $scope.isCreation = false;
    }


    function setDisableItem(item) {
        leaveEditing();
        leaveCreation();
        $scope.disableItem = angular.copy(item);
        $scope.isDisable = true;
    }

    function leaveDisableItem() {
        $scope.isDisable = false;
    }

    getAll();

    $scope.editedItem = null;
    $scope.updateItem = updateItem;
    $scope.leaveEditing = leaveEditing;
    $scope.setEditedItem = setEditedItem;
    $scope.isCurrentItem = isCurrentItem;
    $scope.isFormValid = isFormValid;
    $scope.createUser = createUser;
    $scope.getAll = getAll;
    $scope.setCreation = setCreation;
    $scope.setDisableItem = setDisableItem;
    $scope.disableItem = disableItem;
});