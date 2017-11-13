(function() {
    'use strict';
    angular
        .module('bvloginApp')
        .factory('BamsOutlet', BamsOutlet);

    BamsOutlet.$inject = ['$resource'];

    function BamsOutlet ($resource) {

        var resourceUrl =  'api/bams-outlets/:id';

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
            'update': { method:'PUT' },
            'find': {
                method: 'GET',
                isArray: true
            }
        });
    }
})();
