(function() {
    'use strict';

    angular
        .module('bvloginApp')
        .controller('BamsPermissionDetailController', BamsPermissionDetailController);

    BamsPermissionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'BamsPermission', 'BamsAuthority'];

    function BamsPermissionDetailController($scope, $rootScope, $stateParams, previousState, entity, BamsPermission, BamsAuthority) {
        var vm = this;

        vm.bamsPermission = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bvloginApp:bamsPermissionUpdate', function(event, result) {
            vm.bamsPermission = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
