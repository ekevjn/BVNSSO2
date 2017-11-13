(function() {
    'use strict';

    angular
        .module('bvloginApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('bams-outlet-pro', {
            parent: 'entity',
            url: '/bams-outlet-pro?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bvloginApp.bamsOutletPro.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/bams-outlet-pro/bams-outlet-pros.html',
                    controller: 'BamsOutletProController',
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
                    $translatePartialLoader.addPart('bamsOutletPro');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('bams-outlet-pro-detail', {
            parent: 'bams-outlet-pro',
            url: '/bams-outlet-pro/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bvloginApp.bamsOutletPro.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/bams-outlet-pro/bams-outlet-pro-detail.html',
                    controller: 'BamsOutletProDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('bamsOutletPro');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'BamsOutletPro', function($stateParams, BamsOutletPro) {
                    return BamsOutletPro.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'bams-outlet-pro',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('bams-outlet-pro-detail.edit', {
            parent: 'bams-outlet-pro-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/bams-outlet-pro/bams-outlet-pro-dialog.html',
                    controller: 'BamsOutletProDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['BamsOutletPro', function(BamsOutletPro) {
                            return BamsOutletPro.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('bams-outlet-pro.new', {
            parent: 'bams-outlet-pro',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/bams-outlet-pro/bams-outlet-pro-dialog.html',
                    controller: 'BamsOutletProDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                sourcedata: null,
                                producttype: null,
                                effectivedate: null,
                                ceasedate: null,
                                productcode: null,
                                companycode: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('bams-outlet-pro', null, { reload: 'bams-outlet-pro' });
                }, function() {
                    $state.go('bams-outlet-pro');
                });
            }]
        })
        .state('bams-outlet-pro.edit', {
            parent: 'bams-outlet-pro',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/bams-outlet-pro/bams-outlet-pro-dialog.html',
                    controller: 'BamsOutletProDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['BamsOutletPro', function(BamsOutletPro) {
                            return BamsOutletPro.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('bams-outlet-pro', null, { reload: 'bams-outlet-pro' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('bams-outlet-pro.delete', {
            parent: 'bams-outlet-pro',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/bams-outlet-pro/bams-outlet-pro-delete-dialog.html',
                    controller: 'BamsOutletProDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['BamsOutletPro', function(BamsOutletPro) {
                            return BamsOutletPro.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('bams-outlet-pro', null, { reload: 'bams-outlet-pro' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
