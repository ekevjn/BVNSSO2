(function() {
    'use strict';

    angular
        .module('bvloginApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('bams-outlet', {
            parent: 'entity',
            url: '/bams-outlet?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bvloginApp.bamsOutlet.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/bams-outlet/bams-outlets.html',
                    controller: 'BamsOutletController',
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
                    $translatePartialLoader.addPart('bamsOutlet');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('bams-outlet-detail', {
            parent: 'bams-outlet',
            url: '/bams-outlet/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bvloginApp.bamsOutlet.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/bams-outlet/bams-outlet-detail.html',
                    controller: 'BamsOutletDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('bamsOutlet');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'BamsOutlet', function($stateParams, BamsOutlet) {
                    return BamsOutlet.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'bams-outlet',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('bams-outlet-detail.edit', {
            parent: 'bams-outlet-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/bams-outlet/bams-outlet-dialog.html',
                    controller: 'BamsOutletDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['BamsOutlet', function(BamsOutlet) {
                            return BamsOutlet.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('bams-outlet.new', {
            parent: 'bams-outlet',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/bams-outlet/bams-outlet-dialog.html',
                    controller: 'BamsOutletDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                dailyId: null,
                                tendaily: null,
                                cvdl: null,
                                motaCvdl: null,
                                diachi: null,
                                dienthoai: null,
                                matinh: null,
                                activated: null,
                                vphd: null,
                                diachivphd: null,
                                maphongban: null,
                                tenphongban: null,
                                manhom: null,
                                tennhom: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('bams-outlet', null, { reload: 'bams-outlet' });
                }, function() {
                    $state.go('bams-outlet');
                });
            }]
        })
        .state('bams-outlet.edit', {
            parent: 'bams-outlet',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/bams-outlet/bams-outlet-dialog.html',
                    controller: 'BamsOutletDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['BamsOutlet', function(BamsOutlet) {
                            return BamsOutlet.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('bams-outlet', null, { reload: 'bams-outlet' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('bams-outlet.delete', {
            parent: 'bams-outlet',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/bams-outlet/bams-outlet-delete-dialog.html',
                    controller: 'BamsOutletDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['BamsOutlet', function(BamsOutlet) {
                            return BamsOutlet.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('bams-outlet', null, { reload: 'bams-outlet' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
