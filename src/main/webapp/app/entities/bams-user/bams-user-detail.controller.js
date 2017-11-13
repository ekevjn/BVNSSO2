(function() {
    'use strict';

    angular
        .module('bvloginApp')
        .controller('BamsUserDetailController', BamsUserDetailController);

    BamsUserDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'BamsUser', 'BamsGenerCode'];

    function BamsUserDetailController($scope, $rootScope, $stateParams, previousState, entity, BamsUser, BamsGenerCode) {
        var vm = this;

        vm.bamsUser = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bvloginApp:bamsUserUpdate', function(event, result) {
            vm.bamsUser = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
