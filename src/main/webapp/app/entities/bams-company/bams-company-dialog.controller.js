(function() {
    'use strict';

    angular
        .module('bvloginApp')
        .controller('BamsCompanyDialogController', BamsCompanyDialogController);

    BamsCompanyDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'BamsCompany'];

    function BamsCompanyDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, BamsCompany) {
        var vm = this;

        vm.bamsCompany = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.bamsCompany.id !== null) {
                BamsCompany.update(vm.bamsCompany, onSaveSuccess, onSaveError);
            } else {
                BamsCompany.save(vm.bamsCompany, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bvloginApp:bamsCompanyUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
