(function() {
    'use strict';

    angular
        .module('bvloginApp')
        .controller('BamsGenerCodeDetailController', BamsGenerCodeDetailController);

    BamsGenerCodeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'BamsGenerCode', 'BamsGenerType', 'BamsUser', 'BamsAuthority'];

    function BamsGenerCodeDetailController($scope, $rootScope, $stateParams, previousState, entity, BamsGenerCode, BamsGenerType, BamsUser, BamsAuthority) {
        var vm = this;

        vm.bamsGenerCode = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bvloginApp:bamsGenerCodeUpdate', function(event, result) {
            vm.bamsGenerCode = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
