(function() {
    'use strict';

    angular
        .module('bvloginApp')
        .controller('BamsUserDeleteController',BamsUserDeleteController);

    BamsUserDeleteController.$inject = ['$uibModalInstance', 'entity', 'BamsUser'];

    function BamsUserDeleteController($uibModalInstance, entity, BamsUser) {
        var vm = this;

        vm.bamsUser = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            BamsUser.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
