(function() {
    'use strict';

    angular
        .module('bvloginApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('bams-gener-type', {
            parent: 'entity',
            url: '/bams-gener-type?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bvloginApp.bamsGenerType.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/bams-gener-type/bams-gener-types.html',
                    controller: 'BamsGenerTypeController',
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
                    $translatePartialLoader.addPart('bamsGenerType');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('bams-gener-type-detail', {
            parent: 'bams-gener-type',
            url: '/bams-gener-type/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bvloginApp.bamsGenerType.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/bams-gener-type/bams-gener-type-detail.html',
                    controller: 'BamsGenerTypeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('bamsGenerType');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'BamsGenerType', function($stateParams, BamsGenerType) {
                    return BamsGenerType.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'bams-gener-type',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('bams-gener-type-detail.edit', {
            parent: 'bams-gener-type-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/bams-gener-type/bams-gener-type-dialog.html',
                    controller: 'BamsGenerTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['BamsGenerType', function(BamsGenerType) {
                            return BamsGenerType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('bams-gener-type.new', {
            parent: 'bams-gener-type',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/bams-gener-type/bams-gener-type-dialog.html',
                    controller: 'BamsGenerTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                typeCode: null,
                                typeName: null,
                                typeStatusCode: null,
                                typeCreatedBy: null,
                                typeCreatedDate: null,
                                typeLastUpdateBy: null,
                                typeLastUpdateDate: null,
                                typeVersionId: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('bams-gener-type', null, { reload: 'bams-gener-type' });
                }, function() {
                    $state.go('bams-gener-type');
                });
            }]
        })
        .state('bams-gener-type.edit', {
            parent: 'bams-gener-type',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/bams-gener-type/bams-gener-type-dialog.html',
                    controller: 'BamsGenerTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['BamsGenerType', function(BamsGenerType) {
                            return BamsGenerType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('bams-gener-type', null, { reload: 'bams-gener-type' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('bams-gener-type.delete', {
            parent: 'bams-gener-type',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/bams-gener-type/bams-gener-type-delete-dialog.html',
                    controller: 'BamsGenerTypeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['BamsGenerType', function(BamsGenerType) {
                            return BamsGenerType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('bams-gener-type', null, { reload: 'bams-gener-type' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
