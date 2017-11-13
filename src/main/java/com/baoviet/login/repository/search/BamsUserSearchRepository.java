package com.baoviet.login.repository.search;

import com.baoviet.login.domain.BamsUser;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the BamsUser entity.
 */
public interface BamsUserSearchRepository extends ElasticsearchRepository<BamsUser, Long> {
}
