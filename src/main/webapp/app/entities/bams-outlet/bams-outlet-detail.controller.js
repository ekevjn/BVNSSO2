(function() {
    'use strict';

    angular
        .module('bvloginApp')
        .controller('BamsOutletDetailController', BamsOutletDetailController);

    BamsOutletDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'BamsOutlet', 'BamsOutletPro'];

    function BamsOutletDetailController($scope, $rootScope, $stateParams, previousState, entity, BamsOutlet, BamsOutletPro) {
        var vm = this;

        vm.bamsOutlet = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bvloginApp:bamsOutletUpdate', function(event, result) {
            vm.bamsOutlet = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
