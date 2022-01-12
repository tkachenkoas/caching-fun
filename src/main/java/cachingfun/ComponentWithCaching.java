package cachingfun;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class ComponentWithCaching {

    @Autowired
    private RemoteEndpoint remoteEndpoint;

    @Cacheable(cacheNames = "redis")
    public String getData(String request) {
        return remoteEndpoint.fetchData(request);
    }

}
