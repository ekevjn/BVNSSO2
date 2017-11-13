package com.baoviet.login.service;

import com.codahale.metrics.annotation.Timed;
import com.baoviet.login.domain.*;
import com.baoviet.login.repository.*;
import com.baoviet.login.repository.search.*;
import org.elasticsearch.indices.IndexAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.List;

@Service
public class ElasticsearchIndexService {

    private final Logger log = LoggerFactory.getLogger(ElasticsearchIndexService.class);

    private final BamsAuthorityRepository bamsAuthorityRepository;

    private final BamsAuthoritySearchRepository bamsAuthoritySearchRepository;

    private final BamsCompanyRepository bamsCompanyRepository;

    private final BamsCompanySearchRepository bamsCompanySearchRepository;

    private final BamsGenerCodeRepository bamsGenerCodeRepository;

    private final BamsGenerCodeSearchRepository bamsGenerCodeSearchRepository;

    private final BamsGenerTypeRepository bamsGenerTypeRepository;

    private final BamsGenerTypeSearchRepository bamsGenerTypeSearchRepository;

    private final BamsOutletRepository bamsOutletRepository;

    private final BamsOutletSearchRepository bamsOutletSearchRepository;

    private final BamsOutletProRepository bamsOutletProRepository;

    private final BamsOutletProSearchRepository bamsOutletProSearchRepository;

    private final BamsPermissionRepository bamsPermissionRepository;

    private final BamsPermissionSearchRepository bamsPermissionSearchRepository;

    private final BamsUserRepository bamsUserRepository;

    private final BamsUserSearchRepository bamsUserSearchRepository;

    private final UserRepository userRepository;

    private final UserSearchRepository userSearchRepository;

    private final ElasticsearchTemplate elasticsearchTemplate;

    public ElasticsearchIndexService(
        UserRepository userRepository,
        UserSearchRepository userSearchRepository,
        BamsAuthorityRepository bamsAuthorityRepository,
        BamsAuthoritySearchRepository bamsAuthoritySearchRepository,
        BamsCompanyRepository bamsCompanyRepository,
        BamsCompanySearchRepository bamsCompanySearchRepository,
        BamsGenerCodeRepository bamsGenerCodeRepository,
        BamsGenerCodeSearchRepository bamsGenerCodeSearchRepository,
        BamsGenerTypeRepository bamsGenerTypeRepository,
        BamsGenerTypeSearchRepository bamsGenerTypeSearchRepository,
        BamsOutletRepository bamsOutletRepository,
        BamsOutletSearchRepository bamsOutletSearchRepository,
        BamsOutletProRepository bamsOutletProRepository,
        BamsOutletProSearchRepository bamsOutletProSearchRepository,
        BamsPermissionRepository bamsPermissionRepository,
        BamsPermissionSearchRepository bamsPermissionSearchRepository,
        BamsUserRepository bamsUserRepository,
        BamsUserSearchRepository bamsUserSearchRepository,
        ElasticsearchTemplate elasticsearchTemplate) {
        this.userRepository = userRepository;
        this.userSearchRepository = userSearchRepository;
        this.bamsAuthorityRepository = bamsAuthorityRepository;
        this.bamsAuthoritySearchRepository = bamsAuthoritySearchRepository;
        this.bamsCompanyRepository = bamsCompanyRepository;
        this.bamsCompanySearchRepository = bamsCompanySearchRepository;
        this.bamsGenerCodeRepository = bamsGenerCodeRepository;
        this.bamsGenerCodeSearchRepository = bamsGenerCodeSearchRepository;
        this.bamsGenerTypeRepository = bamsGenerTypeRepository;
        this.bamsGenerTypeSearchRepository = bamsGenerTypeSearchRepository;
        this.bamsOutletRepository = bamsOutletRepository;
        this.bamsOutletSearchRepository = bamsOutletSearchRepository;
        this.bamsOutletProRepository = bamsOutletProRepository;
        this.bamsOutletProSearchRepository = bamsOutletProSearchRepository;
        this.bamsPermissionRepository = bamsPermissionRepository;
        this.bamsPermissionSearchRepository = bamsPermissionSearchRepository;
        this.bamsUserRepository = bamsUserRepository;
        this.bamsUserSearchRepository = bamsUserSearchRepository;
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    @Async
    @Timed
    public void reindexAll() {
        reindexForClass(BamsAuthority.class, bamsAuthorityRepository, bamsAuthoritySearchRepository);
        reindexForClass(BamsCompany.class, bamsCompanyRepository, bamsCompanySearchRepository);
        reindexForClass(BamsGenerCode.class, bamsGenerCodeRepository, bamsGenerCodeSearchRepository);
        reindexForClass(BamsGenerType.class, bamsGenerTypeRepository, bamsGenerTypeSearchRepository);
        reindexForClass(BamsOutlet.class, bamsOutletRepository, bamsOutletSearchRepository);
        reindexForClass(BamsOutletPro.class, bamsOutletProRepository, bamsOutletProSearchRepository);
        reindexForClass(BamsPermission.class, bamsPermissionRepository, bamsPermissionSearchRepository);
        reindexForClass(BamsUser.class, bamsUserRepository, bamsUserSearchRepository);
        reindexForClass(User.class, userRepository, userSearchRepository);

        log.info("Elasticsearch: Successfully performed reindexing");
    }

    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    private <T, ID extends Serializable> void reindexForClass(Class<T> entityClass, JpaRepository<T, ID> jpaRepository,
                                                              ElasticsearchRepository<T, ID> elasticsearchRepository) {
        elasticsearchTemplate.deleteIndex(entityClass);
        try {
            elasticsearchTemplate.createIndex(entityClass);
        } catch (IndexAlreadyExistsException e) {
            // Do nothing. Index was already concurrently recreated by some other service.
        }
        elasticsearchTemplate.putMapping(entityClass);
        if (jpaRepository.count() > 0) {
            try {
                Method m = jpaRepository.getClass().getMethod("findAllWithEagerRelationships");
                elasticsearchRepository.save((List<T>) m.invoke(jpaRepository));
            } catch (Exception e) {
                elasticsearchRepository.save(jpaRepository.findAll());
            }
        }
        log.info("Elasticsearch: Indexed all rows for " + entityClass.getSimpleName());
    }
}
