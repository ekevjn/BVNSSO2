(function() {
    'use strict';

    angular
        .module('bvloginApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('bams-gener-code', {
            parent: 'entity',
            url: '/bams-gener-code?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bvloginApp.bamsGenerCode.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/bams-gener-code/bams-gener-codes.html',
                    controller: 'BamsGenerCodeController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('bamsGenerCode');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('bams-gener-code-detail', {
            parent: 'bams-gener-code',
            url: '/bams-gener-code/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bvloginApp.bamsGenerCode.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/bams-gener-code/bams-gener-code-detail.html',
                    controller: 'BamsGenerCodeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('bamsGenerCode');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'BamsGenerCode', function($stateParams, BamsGenerCode) {
                    return BamsGenerCode.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'bams-gener-code',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('bams-gener-code-detail.edit', {
            parent: 'bams-gener-code-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/bams-gener-code/bams-gener-code-dialog.html',
                    controller: 'BamsGenerCodeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['BamsGenerCode', function(BamsGenerCode) {
                            return BamsGenerCode.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('bams-gener-code.new', {
            parent: 'bams-gener-code',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/bams-gener-code/bams-gener-code-dialog.html',
                    controller: 'BamsGenerCodeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                code: null,
                                name: null,
                                description: null,
                                activated: null,
                                createdby: null,
                                createddate: null,
                                lastmodifiedby: null,
                                lastmodifieddate: null,
                                versionId: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('bams-gener-code', null, { reload: 'bams-gener-code' });
                }, function() {
                    $state.go('bams-gener-code');
                });
            }]
        })
        .state('bams-gener-code.edit', {
            parent: 'bams-gener-code',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/bams-gener-code/bams-gener-code-dialog.html',
                    controller: 'BamsGenerCodeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['BamsGenerCode', function(BamsGenerCode) {
                            return BamsGenerCode.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('bams-gener-code', null, { reload: 'bams-gener-code' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('bams-gener-code.delete', {
            parent: 'bams-gener-code',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/bams-gener-code/bams-gener-code-delete-dialog.html',
                    controller: 'BamsGenerCodeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['BamsGenerCode', function(BamsGenerCode) {
                            return BamsGenerCode.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('bams-gener-code', null, { reload: 'bams-gener-code' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
