(function() {
    'use strict';

    angular
        .module('bvloginApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('bams-permission', {
            parent: 'entity',
            url: '/bams-permission?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bvloginApp.bamsPermission.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/bams-permission/bams-permissions.html',
                    controller: 'BamsPermissionController',
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
                    $translatePartialLoader.addPart('bamsPermission');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('bams-permission-detail', {
            parent: 'bams-permission',
            url: '/bams-permission/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bvloginApp.bamsPermission.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/bams-permission/bams-permission-detail.html',
                    controller: 'BamsPermissionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('bamsPermission');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'BamsPermission', function($stateParams, BamsPermission) {
                    return BamsPermission.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'bams-permission',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('bams-permission-detail.edit', {
            parent: 'bams-permission-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/bams-permission/bams-permission-dialog.html',
                    controller: 'BamsPermissionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['BamsPermission', function(BamsPermission) {
                            return BamsPermission.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('bams-permission.new', {
            parent: 'bams-permission',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/bams-permission/bams-permission-dialog.html',
                    controller: 'BamsPermissionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                description: null,
                                createdby: null,
                                createddate: null,
                                lastmodifiedby: null,
                                lastmodifieddate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('bams-permission', null, { reload: 'bams-permission' });
                }, function() {
                    $state.go('bams-permission');
                });
            }]
        })
        .state('bams-permission.edit', {
            parent: 'bams-permission',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/bams-permission/bams-permission-dialog.html',
                    controller: 'BamsPermissionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['BamsPermission', function(BamsPermission) {
                            return BamsPermission.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('bams-permission', null, { reload: 'bams-permission' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('bams-permission.delete', {
            parent: 'bams-permission',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/bams-permission/bams-permission-delete-dialog.html',
                    controller: 'BamsPermissionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['BamsPermission', function(BamsPermission) {
                            return BamsPermission.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('bams-permission', null, { reload: 'bams-permission' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
