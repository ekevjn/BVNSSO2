(function() {
    'use strict';

    angular
        .module('bvloginApp')
        .controller('BamsOutletProDetailController', BamsOutletProDetailController);

    BamsOutletProDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'BamsOutletPro', 'BamsOutlet'];

    function BamsOutletProDetailController($scope, $rootScope, $stateParams, previousState, entity, BamsOutletPro, BamsOutlet) {
        var vm = this;

        vm.bamsOutletPro = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bvloginApp:bamsOutletProUpdate', function(event, result) {
            vm.bamsOutletPro = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
