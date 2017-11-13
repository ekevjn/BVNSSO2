(function() {
    'use strict';

    angular
        .module('bvloginApp')
        .controller('BamsAuthorityDeleteController',BamsAuthorityDeleteController);

    BamsAuthorityDeleteController.$inject = ['$uibModalInstance', 'entity', 'BamsAuthority'];

    function BamsAuthorityDeleteController($uibModalInstance, entity, BamsAuthority) {
        var vm = this;

        vm.bamsAuthority = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            BamsAuthority.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
