(function() {
    'use strict';

    angular
        .module('bvloginApp')
        .controller('BamsGenerCodeDeleteController',BamsGenerCodeDeleteController);

    BamsGenerCodeDeleteController.$inject = ['$uibModalInstance', 'entity', 'BamsGenerCode'];

    function BamsGenerCodeDeleteController($uibModalInstance, entity, BamsGenerCode) {
        var vm = this;

        vm.bamsGenerCode = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            BamsGenerCode.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
