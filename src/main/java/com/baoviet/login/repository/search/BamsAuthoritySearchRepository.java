package com.baoviet.login.repository.search;

import com.baoviet.login.domain.BamsAuthority;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the BamsAuthority entity.
 */
public interface BamsAuthoritySearchRepository extends ElasticsearchRepository<BamsAuthority, Long> {
}
