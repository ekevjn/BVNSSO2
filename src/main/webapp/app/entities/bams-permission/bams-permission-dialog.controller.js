(function() {
    'use strict';

    angular
        .module('bvloginApp')
        .controller('BamsPermissionDialogController', BamsPermissionDialogController);

    BamsPermissionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'BamsPermission', 'BamsAuthority'];

    function BamsPermissionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, BamsPermission, BamsAuthority) {
        var vm = this;

        vm.bamsPermission = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.bamsauthorities = BamsAuthority.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.bamsPermission.id !== null) {
                BamsPermission.update(vm.bamsPermission, onSaveSuccess, onSaveError);
            } else {
                BamsPermission.save(vm.bamsPermission, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bvloginApp:bamsPermissionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.createddate = false;
        vm.datePickerOpenStatus.lastmodifieddate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
