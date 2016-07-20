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
            errorService.setError(response.data.error)
        }
    )

    function getAllUsers() {
        appUserService.all()
            .then(
                function (data) {
                    $scope.users = data.data;
                },
                function (response) {
                    errorService.setError(response.data.error)
                }
            )
    }

    function createUser(item) {
        var data = angular.copy(item);
        appUserService.createUser(data, onSuccessCreation, onErrorCreation)

    };

    function updateItem(item) {
        var data = angular.copy(item)
        appUserService.updateItem(data)
            .then(
                function ok(response) {
                    getAllUsers();
                    leaveEditing();
                },
                function (response) {
                    errorService.setError(response.data.error)
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

    onSuccessCreation = function (data) {
        $scope.created = 'CREATED'
        finishCreation();
        getAllUsers();
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
        getNewUserForView();
    }

    function finishCreation() {
        $scope.isCreation = false;
    }

    getAllUsers();

    $scope.editedItem = null;
    $scope.updateItem = updateItem;
    $scope.leaveEditing = leaveEditing;
    $scope.setEditedItem = setEditedItem;
    $scope.isCurrentItem = isCurrentItem;
    $scope.isFormValid = isFormValid;
    $scope.createUser = createUser;
    $scope.getAllUsers = getAllUsers;
    $scope.setCreation = setCreation;
});