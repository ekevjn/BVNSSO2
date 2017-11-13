(function() {
    'use strict';

    angular
        .module('bvloginApp')
        .factory('BamsAuthoritySearch', BamsAuthoritySearch);

    BamsAuthoritySearch.$inject = ['$resource'];

    function BamsAuthoritySearch($resource) {
        var resourceUrl =  'api/_search/bams-authorities/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
