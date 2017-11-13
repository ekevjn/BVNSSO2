(function() {
    'use strict';

    angular
        .module('bvloginApp')
        .controller('BamsPermissionDeleteController',BamsPermissionDeleteController);

    BamsPermissionDeleteController.$inject = ['$uibModalInstance', 'entity', 'BamsPermission'];

    function BamsPermissionDeleteController($uibModalInstance, entity, BamsPermission) {
        var vm = this;

        vm.bamsPermission = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            BamsPermission.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
