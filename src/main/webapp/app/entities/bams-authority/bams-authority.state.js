(function() {
    'use strict';

    angular
        .module('bvloginApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('bams-authority', {
            parent: 'entity',
            url: '/bams-authority?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bvloginApp.bamsAuthority.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/bams-authority/bams-authorities.html',
                    controller: 'BamsAuthorityController',
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
                    $translatePartialLoader.addPart('bamsAuthority');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('bams-authority-detail', {
            parent: 'bams-authority',
            url: '/bams-authority/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bvloginApp.bamsAuthority.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/bams-authority/bams-authority-detail.html',
                    controller: 'BamsAuthorityDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('bamsAuthority');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'BamsAuthority', function($stateParams, BamsAuthority) {
                    return BamsAuthority.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'bams-authority',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('bams-authority-detail.edit', {
            parent: 'bams-authority-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/bams-authority/bams-authority-dialog.html',
                    controller: 'BamsAuthorityDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['BamsAuthority', function(BamsAuthority) {
                            return BamsAuthority.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('bams-authority.new', {
            parent: 'bams-authority',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/bams-authority/bams-authority-dialog.html',
                    controller: 'BamsAuthorityDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                description: null,
                                activated: null,
                                createdby: null,
                                createddate: null,
                                lastmodifiedby: null,
                                lastmodifieddate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('bams-authority', null, { reload: 'bams-authority' });
                }, function() {
                    $state.go('bams-authority');
                });
            }]
        })
        .state('bams-authority.edit', {
            parent: 'bams-authority',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/bams-authority/bams-authority-dialog.html',
                    controller: 'BamsAuthorityDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['BamsAuthority', function(BamsAuthority) {
                            return BamsAuthority.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('bams-authority', null, { reload: 'bams-authority' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('bams-authority.delete', {
            parent: 'bams-authority',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/bams-authority/bams-authority-delete-dialog.html',
                    controller: 'BamsAuthorityDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['BamsAuthority', function(BamsAuthority) {
                            return BamsAuthority.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('bams-authority', null, { reload: 'bams-authority' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
