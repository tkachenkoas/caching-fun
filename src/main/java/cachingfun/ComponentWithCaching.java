package cachingfun;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class ComponentWithCaching {

    private static final String DEFAULT_REQUEST = "DEFAULT_REQUEST";

    @Autowired
    private RemoteEndpoint remoteEndpoint;

    @Lazy
    @Autowired
    private ComponentWithCaching self;

    public String getDefaultData() {
        return self.getData(DEFAULT_REQUEST);
    }

    @Cacheable(cacheNames = "redis")
    public String getData(String request) {
        return remoteEndpoint.fetchData(request);
    }

}
