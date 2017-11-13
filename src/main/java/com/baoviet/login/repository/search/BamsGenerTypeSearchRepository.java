package com.baoviet.login.repository.search;

import com.baoviet.login.domain.BamsGenerType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the BamsGenerType entity.
 */
public interface BamsGenerTypeSearchRepository extends ElasticsearchRepository<BamsGenerType, Long> {
}
