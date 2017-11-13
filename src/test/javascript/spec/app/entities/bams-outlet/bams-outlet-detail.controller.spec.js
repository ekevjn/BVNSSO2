'use strict';

describe('Controller Tests', function() {

    describe('BamsOutlet Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockBamsOutlet, MockBamsOutletPro;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockBamsOutlet = jasmine.createSpy('MockBamsOutlet');
            MockBamsOutletPro = jasmine.createSpy('MockBamsOutletPro');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'BamsOutlet': MockBamsOutlet,
                'BamsOutletPro': MockBamsOutletPro
            };
            createController = function() {
                $injector.get('$controller')("BamsOutletDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'bvloginApp:bamsOutletUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
