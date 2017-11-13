(function() {
    'use strict';
    angular
        .module('bvloginApp')
        .factory('BamsOutletPro', BamsOutletPro);

    BamsOutletPro.$inject = ['$resource', 'DateUtils'];

    function BamsOutletPro ($resource, DateUtils) {
        var resourceUrl =  'api/bams-outlet-pros/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.effectivedate = DateUtils.convertLocalDateFromServer(data.effectivedate);
                        data.ceasedate = DateUtils.convertLocalDateFromServer(data.ceasedate);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.effectivedate = DateUtils.convertLocalDateToServer(copy.effectivedate);
                    copy.ceasedate = DateUtils.convertLocalDateToServer(copy.ceasedate);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.effectivedate = DateUtils.convertLocalDateToServer(copy.effectivedate);
                    copy.ceasedate = DateUtils.convertLocalDateToServer(copy.ceasedate);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
