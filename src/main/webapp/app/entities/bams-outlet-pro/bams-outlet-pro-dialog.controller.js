(function() {
    'use strict';

    angular
        .module('bvloginApp')
        .controller('BamsOutletProDialogController', BamsOutletProDialogController);

    BamsOutletProDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'BamsOutletPro', 'BamsOutlet', 'ParseLinks', 'AlertService', 'BamsOutletSearch'];

    function BamsOutletProDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, BamsOutletPro, BamsOutlet, ParseLinks, AlertService, BamsOutletSearch) {
        var vm = this;

        vm.bamsOutletPro = entity;
        vm.bamsOut = {};
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.bamsoutlets = BamsOutlet.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.bamsOutletPro.id !== null) {
                BamsOutletPro.update(vm.bamsOutletPro, onSaveSuccess, onSaveError);
            } else {
                BamsOutletPro.save(vm.bamsOutletPro, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bvloginApp:bamsOutletProUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.effectivedate = false;
        vm.datePickerOpenStatus.ceasedate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }



        $scope.bamcomplete = function(string){
            $scope.hidethis = false;
            BamsOutletSearch.query({
                query: string,
                size: vm.itemsPerPage,
            }, onSuccess, onError);
            function onSuccess(data, headers) {
                $scope.filterOutlet = data;
            }

            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        $scope.fillTextboxBams = function(string){
            $scope.country = string.tendaily;
            //vm.bamsOut.bamsOutlet = string.tendaily;
            vm.bamsOutletPro.bamsOutlet = string;
            $scope.hidethis = true;
        }

    }
})();
