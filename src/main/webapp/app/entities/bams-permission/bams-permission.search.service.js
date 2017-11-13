(function() {
    'use strict';

    angular
        .module('bvloginApp')
        .factory('BamsPermissionSearch', BamsPermissionSearch);

    BamsPermissionSearch.$inject = ['$resource'];

    function BamsPermissionSearch($resource) {
        var resourceUrl =  'api/_search/bams-permissions/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
