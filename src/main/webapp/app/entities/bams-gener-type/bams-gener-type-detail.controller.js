(function() {
    'use strict';

    angular
        .module('bvloginApp')
        .controller('BamsGenerTypeDetailController', BamsGenerTypeDetailController);

    BamsGenerTypeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'BamsGenerType', 'BamsGenerCode'];

    function BamsGenerTypeDetailController($scope, $rootScope, $stateParams, previousState, entity, BamsGenerType, BamsGenerCode) {
        var vm = this;

        vm.bamsGenerType = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bvloginApp:bamsGenerTypeUpdate', function(event, result) {
            vm.bamsGenerType = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
