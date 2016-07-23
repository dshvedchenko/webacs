describe('authService : ', function () {
    var authService, httpBackend, resultToken, ENDPOINT_URI;
    beforeEach(module('app'));

    beforeEach(inject(function (_authService_, $httpBackend, _ENDPOINT_URI_) {
        authService = _authService_
        httpBackend = $httpBackend
        ENDPOINT_URI = _ENDPOINT_URI_;
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

})
