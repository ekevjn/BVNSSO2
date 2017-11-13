package com.baoviet.login.repository.search;

import com.baoviet.login.domain.BamsPermission;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the BamsPermission entity.
 */
public interface BamsPermissionSearchRepository extends ElasticsearchRepository<BamsPermission, Long> {
}
