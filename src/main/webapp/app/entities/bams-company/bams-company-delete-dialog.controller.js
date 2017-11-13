(function() {
    'use strict';

    angular
        .module('bvloginApp')
        .controller('BamsCompanyDeleteController',BamsCompanyDeleteController);

    BamsCompanyDeleteController.$inject = ['$uibModalInstance', 'entity', 'BamsCompany'];

    function BamsCompanyDeleteController($uibModalInstance, entity, BamsCompany) {
        var vm = this;

        vm.bamsCompany = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            BamsCompany.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
