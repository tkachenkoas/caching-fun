package cachingfun;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class ComponentWithCaching {

    private static final String DEFAULT_REQUEST = "DEFAULT_REQUEST";

    @Autowired
    private RemoteEndpoint remoteEndpoint;

    @Autowired
    private CacheManager cacheManager;

    public String getDefaultData() {
        Cache redisCache = cacheManager.getCache("redis");
        Cache.ValueWrapper existing = redisCache.get(DEFAULT_REQUEST);
        if (existing != null) {
            return (String) existing.get();
        }
        String fetched = getData(DEFAULT_REQUEST);
        redisCache.put(DEFAULT_REQUEST, fetched);
        return fetched;
    }

    @Cacheable(cacheNames = "redis")
    public String getData(String request) {
        return remoteEndpoint.fetchData(request);
    }

}
