describe('authService : ', function () {
    var authService, httpBackend, ENDPOINT_URI, $rootScope;
    beforeEach(module('app'));

    beforeEach(inject(function (_authService_, $httpBackend, _ENDPOINT_URI_, _$rootScope_) {
        authService = _authService_
        httpBackend = $httpBackend
        ENDPOINT_URI = _ENDPOINT_URI_;
        $rootScope = _$rootScope_;
    }));

    afterEach(function () {
        httpBackend.verifyNoOutstandingExpectation();
        httpBackend.verifyNoOutstandingRequest();
    });

    it('should return token', function () {

        onSuccess = function (resultToken) {
            expect(resultToken).toEqual('122345332')
        }

        httpBackend.expect('POST', ENDPOINT_URI + "/login")
            .respond({
                "data": {"token": "122345332"}
            });

        authService.login({'username': 'admin', 'password': '12345'}, onSuccess)
        httpBackend.flush();
    })

    it('should clear token', function () {
        $rootScope.token = '12345w334344';

        function onSuccess(data) {
            expect(data).toBeUndefined()
        }

        httpBackend.expect('POST', ENDPOINT_URI + "/logout")
            .respond({});

        authService.logout(onSuccess)
        httpBackend.flush();

        expect($rootScope.token).toBeUndefined()
    })

})
