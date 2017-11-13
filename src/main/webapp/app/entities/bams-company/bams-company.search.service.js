(function() {
    'use strict';

    angular
        .module('bvloginApp')
        .factory('BamsCompanySearch', BamsCompanySearch);

    BamsCompanySearch.$inject = ['$resource'];

    function BamsCompanySearch($resource) {
        var resourceUrl =  'api/_search/bams-companies/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
