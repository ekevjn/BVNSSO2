(function() {
    'use strict';

    angular
        .module('bvloginApp')
        .factory('BamsUserSearch', BamsUserSearch);

    BamsUserSearch.$inject = ['$resource'];

    function BamsUserSearch($resource) {
        var resourceUrl =  'api/_search/bams-users/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
