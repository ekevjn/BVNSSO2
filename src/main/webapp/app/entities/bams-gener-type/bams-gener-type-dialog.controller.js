(function() {
    'use strict';

    angular
        .module('bvloginApp')
        .controller('BamsGenerTypeDialogController', BamsGenerTypeDialogController);

    BamsGenerTypeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'BamsGenerType', 'BamsGenerCode'];

    function BamsGenerTypeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, BamsGenerType, BamsGenerCode) {
        var vm = this;

        vm.bamsGenerType = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.bamsgenercodes = BamsGenerCode.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.bamsGenerType.id !== null) {
                BamsGenerType.update(vm.bamsGenerType, onSaveSuccess, onSaveError);
            } else {
                BamsGenerType.save(vm.bamsGenerType, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bvloginApp:bamsGenerTypeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.typeCreatedDate = false;
        vm.datePickerOpenStatus.typeLastUpdateDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
