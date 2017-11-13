(function() {
    'use strict';

    angular
        .module('bvloginApp')
        .factory('BamsGenerTypeSearch', BamsGenerTypeSearch);

    BamsGenerTypeSearch.$inject = ['$resource'];

    function BamsGenerTypeSearch($resource) {
        var resourceUrl =  'api/_search/bams-gener-types/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
