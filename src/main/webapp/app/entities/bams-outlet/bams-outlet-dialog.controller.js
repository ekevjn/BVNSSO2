(function() {
    'use strict';

    angular
        .module('bvloginApp')
        .controller('BamsOutletDialogController', BamsOutletDialogController);

    BamsOutletDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'BamsOutlet', 'BamsOutletPro'];

    function BamsOutletDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, BamsOutlet, BamsOutletPro) {
        var vm = this;

        vm.bamsOutlet = entity;
        vm.clear = clear;
        vm.save = save;
        vm.bamsoutletpros = BamsOutletPro.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.bamsOutlet.id !== null) {
                BamsOutlet.update(vm.bamsOutlet, onSaveSuccess, onSaveError);
            } else {
                BamsOutlet.save(vm.bamsOutlet, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bvloginApp:bamsOutletUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
