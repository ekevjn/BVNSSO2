(function() {
    'use strict';

    angular
        .module('bvloginApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('bams-company', {
            parent: 'entity',
            url: '/bams-company?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bvloginApp.bamsCompany.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/bams-company/bams-companies.html',
                    controller: 'BamsCompanyController',
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
                    $translatePartialLoader.addPart('bamsCompany');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('bams-company-detail', {
            parent: 'bams-company',
            url: '/bams-company/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bvloginApp.bamsCompany.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/bams-company/bams-company-detail.html',
                    controller: 'BamsCompanyDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('bamsCompany');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'BamsCompany', function($stateParams, BamsCompany) {
                    return BamsCompany.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'bams-company',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('bams-company-detail.edit', {
            parent: 'bams-company-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/bams-company/bams-company-dialog.html',
                    controller: 'BamsCompanyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['BamsCompany', function(BamsCompany) {
                            return BamsCompany.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('bams-company.new', {
            parent: 'bams-company',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/bams-company/bams-company-dialog.html',
                    controller: 'BamsCompanyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                matinh: null,
                                tentinh: null,
                                diachi: null,
                                dienthoai: null,
                                fax: null,
                                tentinhEng: null,
                                diachiEng: null,
                                dienthoaiEng: null,
                                faxEng: null,
                                compId: null,
                                sequenced: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('bams-company', null, { reload: 'bams-company' });
                }, function() {
                    $state.go('bams-company');
                });
            }]
        })
        .state('bams-company.edit', {
            parent: 'bams-company',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/bams-company/bams-company-dialog.html',
                    controller: 'BamsCompanyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['BamsCompany', function(BamsCompany) {
                            return BamsCompany.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('bams-company', null, { reload: 'bams-company' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('bams-company.delete', {
            parent: 'bams-company',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/bams-company/bams-company-delete-dialog.html',
                    controller: 'BamsCompanyDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['BamsCompany', function(BamsCompany) {
                            return BamsCompany.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('bams-company', null, { reload: 'bams-company' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
