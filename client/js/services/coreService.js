app.service('coreService', function () {
    this.counter = 0;

    this.getHelloMessage = function () {
        return this.counter++;
    }
});