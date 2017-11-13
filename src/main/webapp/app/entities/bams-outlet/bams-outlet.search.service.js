(function() {
    'use strict';

    angular
        .module('bvloginApp')
        .factory('BamsOutletSearch', BamsOutletSearch);

    BamsOutletSearch.$inject = ['$resource'];

    function BamsOutletSearch($resource) {
        var resourceUrl =  'api/_search/bams-outlets/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
