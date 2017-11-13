(function() {
    'use strict';

    angular
        .module('bvloginApp')
        .factory('BamsGenerCodeSearch', BamsGenerCodeSearch);

    BamsGenerCodeSearch.$inject = ['$resource'];

    function BamsGenerCodeSearch($resource) {
        var resourceUrl =  'api/_search/bams-gener-codes/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
