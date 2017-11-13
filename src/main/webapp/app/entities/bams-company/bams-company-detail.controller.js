(function() {
    'use strict';

    angular
        .module('bvloginApp')
        .controller('BamsCompanyDetailController', BamsCompanyDetailController);

    BamsCompanyDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'BamsCompany'];

    function BamsCompanyDetailController($scope, $rootScope, $stateParams, previousState, entity, BamsCompany) {
        var vm = this;

        vm.bamsCompany = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bvloginApp:bamsCompanyUpdate', function(event, result) {
            vm.bamsCompany = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
