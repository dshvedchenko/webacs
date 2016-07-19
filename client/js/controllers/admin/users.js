app.controller('adminUserController', function ($scope, appUserService, authService, $http, $rootScope, $location, $timeout) {
    $scope.users = [];
    $scope.errorBox = '';
    $scope.isEditing = false;
    $scope.created = ''

    if (!authService.isLogged()) {
        $location.path("/")
    }

    appUserService.getValidRoles(
        null,
        function (error) {
            $scope.errorBox = error
        }
    )

    function getAllUsers() {
        appUserService.all()
            .then(
                function (data) {
                    $scope.users = data.data;
                }
            )
    }

    function createUser(item) {
        var data = angular.copy(item);
        appUserService.createUser(data, onSuccess, onError)

    };

    function updateItem(item) {
        var data = angular.copy(item)
        appUserService.updateItem(data)
            .then(
                function ok(response) {
                    getAllUsers();
                },
                function (response) {
                    $scope.errorBox = response.data.error.message;
                    $timeout(function () {
                        $scope.errorBox = undefined;
                    }, 5000)
                }
            )
    }

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

    onSuccess = function (data) {
        $scope.created = 'CREATED'
        getAllUsers()
    };

    onError = function (data) {
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
        $scope.isCreation = false;
        $scope.newUser = null;
    }

    function cancelEditing() {
        $scope.editedItem = null;
        $scope.isEditing = false;
    }

    function setCreation() {
        cancelEditing();
        $scope.isCreation = true;
        getNewUserForView();
    }

    getAllUsers();

    $scope.editedItem = null;
    $scope.updateItem = updateItem;
    $scope.cancelEditing = cancelEditing;
    $scope.setEditedItem = setEditedItem;
    $scope.isCurrentItem = isCurrentItem;
    $scope.isFormValid = isFormValid;
    $scope.createUser = createUser;
    $scope.getAllUsers = getAllUsers;
    $scope.setCreation = setCreation;
});