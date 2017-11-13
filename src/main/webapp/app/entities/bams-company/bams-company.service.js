(function() {
    'use strict';
    angular
        .module('bvloginApp')
        .factory('BamsCompany', BamsCompany);

    BamsCompany.$inject = ['$resource'];

    function BamsCompany ($resource) {
        var resourceUrl =  'api/bams-companies/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
