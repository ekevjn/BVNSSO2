(function() {
    'use strict';
    angular
        .module('bvloginApp')
        .factory('BamsGenerType', BamsGenerType);

    BamsGenerType.$inject = ['$resource', 'DateUtils'];

    function BamsGenerType ($resource, DateUtils) {
        var resourceUrl =  'api/bams-gener-types/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.typeCreatedDate = DateUtils.convertLocalDateFromServer(data.typeCreatedDate);
                        data.typeLastUpdateDate = DateUtils.convertLocalDateFromServer(data.typeLastUpdateDate);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.typeCreatedDate = DateUtils.convertLocalDateToServer(copy.typeCreatedDate);
                    copy.typeLastUpdateDate = DateUtils.convertLocalDateToServer(copy.typeLastUpdateDate);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.typeCreatedDate = DateUtils.convertLocalDateToServer(copy.typeCreatedDate);
                    copy.typeLastUpdateDate = DateUtils.convertLocalDateToServer(copy.typeLastUpdateDate);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
