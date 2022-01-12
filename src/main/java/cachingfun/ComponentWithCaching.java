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

    public String getDefaultDataWithParam() {
        Cache redisCache = cacheManager.getCache("redis");
        String cacheKey = DEFAULT_REQUEST + DEFAULT_REQUEST;
        Cache.ValueWrapper existing = redisCache.get(cacheKey);
        if (existing != null) {
            return (String) existing.get();
        }
        String fetched = getDataWithParameter(DEFAULT_REQUEST, DEFAULT_REQUEST);
        redisCache.put(cacheKey, fetched);
        return fetched;
    }

    @Cacheable(cacheNames = "redis")
    public String getDataWithParameter(String request, String param) {
        return remoteEndpoint.fetchData(request);
    }

}
