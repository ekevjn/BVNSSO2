(function() {
    'use strict';

    angular
        .module('bvloginApp')
        .controller('BamsAuthorityDetailController', BamsAuthorityDetailController);

    BamsAuthorityDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'BamsAuthority', 'BamsPermission', 'BamsGenerCode'];

    function BamsAuthorityDetailController($scope, $rootScope, $stateParams, previousState, entity, BamsAuthority, BamsPermission, BamsGenerCode) {
        var vm = this;

        vm.bamsAuthority = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bvloginApp:bamsAuthorityUpdate', function(event, result) {
            vm.bamsAuthority = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
