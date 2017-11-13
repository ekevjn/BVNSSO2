(function() {
    'use strict';

    angular
        .module('bvloginApp')
        .factory('BamsOutletProSearch', BamsOutletProSearch);

    BamsOutletProSearch.$inject = ['$resource'];

    function BamsOutletProSearch($resource) {
        var resourceUrl =  'api/_search/bams-outlet-pros/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
