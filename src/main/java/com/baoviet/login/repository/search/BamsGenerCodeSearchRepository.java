package com.baoviet.login.repository.search;

import com.baoviet.login.domain.BamsGenerCode;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the BamsGenerCode entity.
 */
public interface BamsGenerCodeSearchRepository extends ElasticsearchRepository<BamsGenerCode, Long> {
}
