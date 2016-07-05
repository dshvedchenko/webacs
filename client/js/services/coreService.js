app.service('coreService', function () {
    this.counter = 0;
    this.shares = 2000;

    this.getHelloMessage = function () {
        console.log(this.counter)
        return this.counter++;
    }
});