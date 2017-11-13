(function() {
    'use strict';

    angular
        .module('bvloginApp')
        .controller('BamsGenerCodeDialogController', BamsGenerCodeDialogController);

    BamsGenerCodeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'BamsGenerCode', 'BamsGenerType', 'BamsUser', 'BamsAuthority'];

    function BamsGenerCodeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, BamsGenerCode, BamsGenerType, BamsUser, BamsAuthority) {
        var vm = this;

        vm.bamsGenerCode = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.bamsgenertypes = BamsGenerType.query();
        vm.bamsusers = BamsUser.query();
        vm.bamsauthorities = BamsAuthority.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.bamsGenerCode.id !== null) {
                BamsGenerCode.update(vm.bamsGenerCode, onSaveSuccess, onSaveError);
            } else {
                BamsGenerCode.save(vm.bamsGenerCode, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bvloginApp:bamsGenerCodeUpdate', result);
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
