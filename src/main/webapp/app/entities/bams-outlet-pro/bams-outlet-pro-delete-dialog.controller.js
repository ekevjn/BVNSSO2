(function() {
    'use strict';

    angular
        .module('bvloginApp')
        .controller('BamsOutletProDeleteController',BamsOutletProDeleteController);

    BamsOutletProDeleteController.$inject = ['$uibModalInstance', 'entity', 'BamsOutletPro'];

    function BamsOutletProDeleteController($uibModalInstance, entity, BamsOutletPro) {
        var vm = this;

        vm.bamsOutletPro = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            BamsOutletPro.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
