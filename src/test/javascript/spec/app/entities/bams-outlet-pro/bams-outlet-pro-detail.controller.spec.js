'use strict';

describe('Controller Tests', function() {

    describe('BamsOutletPro Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockBamsOutletPro, MockBamsOutlet;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockBamsOutletPro = jasmine.createSpy('MockBamsOutletPro');
            MockBamsOutlet = jasmine.createSpy('MockBamsOutlet');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'BamsOutletPro': MockBamsOutletPro,
                'BamsOutlet': MockBamsOutlet
            };
            createController = function() {
                $injector.get('$controller')("BamsOutletProDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'bvloginApp:bamsOutletProUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
