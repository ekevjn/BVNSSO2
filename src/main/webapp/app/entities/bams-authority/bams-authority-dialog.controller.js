(function() {
    'use strict';

    angular
        .module('bvloginApp')
        .controller('BamsAuthorityDialogController', BamsAuthorityDialogController);

    BamsAuthorityDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'BamsAuthority', 'BamsPermission', 'BamsGenerCode'];

    function BamsAuthorityDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, BamsAuthority, BamsPermission, BamsGenerCode) {
        var vm = this;

        vm.bamsAuthority = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.bamspermissions = BamsPermission.query();
        vm.bamsgenercodes = BamsGenerCode.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.bamsAuthority.id !== null) {
                BamsAuthority.update(vm.bamsAuthority, onSaveSuccess, onSaveError);
            } else {
                BamsAuthority.save(vm.bamsAuthority, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bvloginApp:bamsAuthorityUpdate', result);
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
