package com.baoviet.login.repository.search;

import com.baoviet.login.domain.BamsCompany;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the BamsCompany entity.
 */
public interface BamsCompanySearchRepository extends ElasticsearchRepository<BamsCompany, Long> {
}
