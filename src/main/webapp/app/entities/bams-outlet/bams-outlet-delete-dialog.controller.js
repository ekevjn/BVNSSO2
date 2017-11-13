(function() {
    'use strict';

    angular
        .module('bvloginApp')
        .controller('BamsOutletDeleteController',BamsOutletDeleteController);

    BamsOutletDeleteController.$inject = ['$uibModalInstance', 'entity', 'BamsOutlet'];

    function BamsOutletDeleteController($uibModalInstance, entity, BamsOutlet) {
        var vm = this;

        vm.bamsOutlet = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            BamsOutlet.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
