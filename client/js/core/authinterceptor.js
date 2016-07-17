app.factory('authinterceptor', function ($q, $rootScope) {
    return {
        'request': function (config) {
            if ($rootScope.token) config.headers['X-AUTHID'] = $rootScope.token
            config.headers['Content-Type'] = "application/json";
            return config;
        }

    };
});

app.config(['$httpProvider', function ($httpProvider) {
    $httpProvider.interceptors.push('authinterceptor');
}]);