(function() {
    'use strict';

    angular
        .module('bvloginApp')
        .controller('BamsUserDialogController', BamsUserDialogController);

    BamsUserDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'BamsUser', 'BamsGenerCode', 'JhiLanguageService'];

    function BamsUserDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, BamsUser, BamsGenerCode, JhiLanguageService) {
        var vm = this;

        vm.bamsUser = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.bamsgenercodes = BamsGenerCode.query();

        JhiLanguageService.getAll().then(function (languages) {
                    vm.languages = languages;
                });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.bamsUser.id !== null) {
                if(vm.bamsUser.passwordhash !== null){
                }
                BamsUser.update(vm.bamsUser, onSaveSuccess, onSaveError);
            } else {
                BamsUser.save(vm.bamsUser, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bvloginApp:bamsUserUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.createddate = false;
        vm.datePickerOpenStatus.resetdate = false;
        vm.datePickerOpenStatus.lastmodifieddate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
