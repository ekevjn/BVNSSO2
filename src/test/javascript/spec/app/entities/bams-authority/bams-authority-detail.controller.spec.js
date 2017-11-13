'use strict';

describe('Controller Tests', function() {

    describe('BamsAuthority Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockBamsAuthority, MockBamsPermission, MockBamsGenerCode;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockBamsAuthority = jasmine.createSpy('MockBamsAuthority');
            MockBamsPermission = jasmine.createSpy('MockBamsPermission');
            MockBamsGenerCode = jasmine.createSpy('MockBamsGenerCode');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'BamsAuthority': MockBamsAuthority,
                'BamsPermission': MockBamsPermission,
                'BamsGenerCode': MockBamsGenerCode
            };
            createController = function() {
                $injector.get('$controller')("BamsAuthorityDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'bvloginApp:bamsAuthorityUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
