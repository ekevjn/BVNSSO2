(function() {
    'use strict';
    angular
        .module('bvloginApp')
        .factory('BamsPermission', BamsPermission);

    BamsPermission.$inject = ['$resource', 'DateUtils'];

    function BamsPermission ($resource, DateUtils) {
        var resourceUrl =  'api/bams-permissions/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.createddate = DateUtils.convertLocalDateFromServer(data.createddate);
                        data.lastmodifieddate = DateUtils.convertLocalDateFromServer(data.lastmodifieddate);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.createddate = DateUtils.convertLocalDateToServer(copy.createddate);
                    copy.lastmodifieddate = DateUtils.convertLocalDateToServer(copy.lastmodifieddate);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.createddate = DateUtils.convertLocalDateToServer(copy.createddate);
                    copy.lastmodifieddate = DateUtils.convertLocalDateToServer(copy.lastmodifieddate);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
