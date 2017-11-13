package com.baoviet.login.repository.search;

import com.baoviet.login.domain.BamsOutletPro;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the BamsOutletPro entity.
 */
public interface BamsOutletProSearchRepository extends ElasticsearchRepository<BamsOutletPro, Long> {
}
