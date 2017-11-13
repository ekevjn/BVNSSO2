(function() {
    'use strict';

    angular
        .module('bvloginApp')
        .controller('BamsGenerTypeDeleteController',BamsGenerTypeDeleteController);

    BamsGenerTypeDeleteController.$inject = ['$uibModalInstance', 'entity', 'BamsGenerType'];

    function BamsGenerTypeDeleteController($uibModalInstance, entity, BamsGenerType) {
        var vm = this;

        vm.bamsGenerType = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            BamsGenerType.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
