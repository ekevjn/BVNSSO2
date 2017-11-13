package com.baoviet.login.repository.search;

import com.baoviet.login.domain.BamsOutlet;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the BamsOutlet entity.
 */
public interface BamsOutletSearchRepository extends ElasticsearchRepository<BamsOutlet, Long> {
}
