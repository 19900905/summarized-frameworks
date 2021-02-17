package org.zxd.cache;
/*
 * Project: idea-projects
 * DateTime: 2021/2/10 13:51
 * @author: 竺旭东
 * Version: v1.0
 * Desc: TODO
 */

import java.util.List;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "common.cache")
@Data
public class CacheConfigs {
	private List<CacheConfig> cacheConfigs;
}
