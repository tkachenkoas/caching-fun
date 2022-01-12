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
        return redisCache.get(
                DEFAULT_REQUEST, () -> getData(DEFAULT_REQUEST)
        );
    }

    @Cacheable(cacheNames = "redis")
    public String getData(String request) {
        return remoteEndpoint.fetchData(request);
    }

    public String getDefaultDataWithParam() {
        Cache redisCache = cacheManager.getCache("redis");
        return redisCache.get(
                DEFAULT_REQUEST + DEFAULT_REQUEST,
                () -> getDataWithParameter(DEFAULT_REQUEST, DEFAULT_REQUEST)
        );
    }

    @Cacheable(cacheNames = "redis", key = "#request + #param")
    public String getDataWithParameter(String request, String param) {
        return remoteEndpoint.fetchData(request);
    }

}
