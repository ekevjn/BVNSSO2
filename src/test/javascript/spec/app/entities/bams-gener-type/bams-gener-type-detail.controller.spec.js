'use strict';

describe('Controller Tests', function() {

    describe('BamsGenerType Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockBamsGenerType, MockBamsGenerCode;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockBamsGenerType = jasmine.createSpy('MockBamsGenerType');
            MockBamsGenerCode = jasmine.createSpy('MockBamsGenerCode');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'BamsGenerType': MockBamsGenerType,
                'BamsGenerCode': MockBamsGenerCode
            };
            createController = function() {
                $injector.get('$controller')("BamsGenerTypeDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'bvloginApp:bamsGenerTypeUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
