describe('My first controller', function () {
    var coreService, httpBackend, resultToken;
    beforeEach(module('app'));

    beforeEach(inject(function (_coreService_, $httpBackend) {
        coreService = _coreService_
        httpBackend = $httpBackend
    }));

    afterEach(function () {
        httpBackend.verifyNoOutstandingExpectation();
        httpBackend.verifyNoOutstandingRequest();
    });

    it('should return token', function () {

        onSuccess = function (resultToken) {
            expect(resultToken).toEqual('122345332')
        }

        httpBackend.expect('POST', backend_server + "/api/v1/login")
            .respond({
                "data": {"token": "122345332"}
            });

        coreService.login({'username': 'admin', 'password': '12345'}, onSuccess)
        httpBackend.flush();
    })

})
