'use strict';

describe('Controller Tests', function() {

    describe('BamsGenerCode Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockBamsGenerCode, MockBamsGenerType, MockBamsUser, MockBamsAuthority;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockBamsGenerCode = jasmine.createSpy('MockBamsGenerCode');
            MockBamsGenerType = jasmine.createSpy('MockBamsGenerType');
            MockBamsUser = jasmine.createSpy('MockBamsUser');
            MockBamsAuthority = jasmine.createSpy('MockBamsAuthority');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'BamsGenerCode': MockBamsGenerCode,
                'BamsGenerType': MockBamsGenerType,
                'BamsUser': MockBamsUser,
                'BamsAuthority': MockBamsAuthority
            };
            createController = function() {
                $injector.get('$controller')("BamsGenerCodeDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'bvloginApp:bamsGenerCodeUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
