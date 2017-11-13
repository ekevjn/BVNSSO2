(function() {
    'use strict';

    angular
        .module('bvloginApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('bams-user', {
            parent: 'entity',
            url: '/bams-user?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bvloginApp.bamsUser.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/bams-user/bams-users.html',
                    controller: 'BamsUserController',
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
                    $translatePartialLoader.addPart('bamsUser');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('bams-user-detail', {
            parent: 'bams-user',
            url: '/bams-user/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bvloginApp.bamsUser.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/bams-user/bams-user-detail.html',
                    controller: 'BamsUserDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('bamsUser');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'BamsUser', function($stateParams, BamsUser) {
                    return BamsUser.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'bams-user',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('bams-user-detail.edit', {
            parent: 'bams-user-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/bams-user/bams-user-dialog.html',
                    controller: 'BamsUserDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['BamsUser', function(BamsUser) {
                            return BamsUser.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('bams-user.new', {
            parent: 'bams-user',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/bams-user/bams-user-dialog.html',
                    controller: 'BamsUserDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                uname: null,
                                passwordhash: null,
                                firstname: null,
                                lastname: null,
                                email: null,
                                gender: null,
                                imageurl: null,
                                activated: null,
                                langkey: null,
                                activationkey: null,
                                resetkey: null,
                                createdby: null,
                                createddate: null,
                                resetdate: null,
                                lastmodifiedby: null,
                                lastmodifieddate: null,
                                user_type: null,
                                outlet_id: null,
                                comp_code: null,
                                mobi_phone: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('bams-user', null, { reload: 'bams-user' });
                }, function() {
                    $state.go('bams-user');
                });
            }]
        })
        .state('bams-user.edit', {
            parent: 'bams-user',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/bams-user/bams-user-dialog.html',
                    controller: 'BamsUserDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['BamsUser', function(BamsUser) {
                            return BamsUser.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('bams-user', null, { reload: 'bams-user' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('bams-user.delete', {
            parent: 'bams-user',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/bams-user/bams-user-delete-dialog.html',
                    controller: 'BamsUserDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['BamsUser', function(BamsUser) {
                            return BamsUser.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('bams-user', null, { reload: 'bams-user' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
