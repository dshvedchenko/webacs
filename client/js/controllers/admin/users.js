app.controller('adminUserController', function ($scope, adminService, $http, $rootScope, $location) {
    $scope.users = [];
    $scope.errorBox = '';
    $scope.isEditing = false;
    $scope.created = ''

    adminService.getValidRoles(
        null,
        function (error) {
            $scope.errorBox = error
        }
    )

    function getAllUsers() {
        adminService.all()
            .then(
                function (data) {
                    $scope.users = data.data;
                }
            )
    }

    function createUser(item) {
        var data = angular.copy(item);
        adminService.createUser(data, onSuccess, onError)
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

    onSuccess = function (data) {
        $scope.created = 'CREATED'
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
        $scope.newUser = null;
    }

    function cancelEditing() {
        $scope.editedItem = null;
        $scope.isEditing = false;
        getNewUserForView();
    }

    function updateItem(item) {
        var data = angular.copy(item)
        adminService.updateItem(data)
            .then(
                function ok(response) {
                    getAllUsers();
                }
            )
    }

    getAllUsers();
    getNewUserForView();

    $scope.editedItem = null;
    $scope.updateItem = updateItem;
    $scope.cancelEditing = cancelEditing;
    $scope.setEditedItem = setEditedItem;
    $scope.isCurrentItem = isCurrentItem;
    $scope.isFormValid = isFormValid;
    $scope.createUser = createUser;
    $scope.getAllUsers = getAllUsers;
});