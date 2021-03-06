package cachingfun;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.CacheManager;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
@DirtiesContext
class ComponentWithCachingTest {

    @MockBean
    RemoteEndpoint mockEndpoint;
    @Autowired
    ComponentWithCaching underTest;
    @Autowired
    CacheManager cacheManager;

    @Test
    void willFetchDataFromRemoteEndpoint() {
        when(mockEndpoint.fetchData("ping"))
                .thenReturn("pong");

        assertThat(underTest.getData("ping"))
                .isEqualTo("pong");
    }

    @Test
    void cachingTest() {
        when(mockEndpoint.fetchData("ping"))
                .thenReturn("pong");

        assertThat(underTest.getData("ping"))
                .isEqualTo("pong");

        when(mockEndpoint.fetchData("ping"))
                .thenReturn("other pong");

        assertThat(underTest.getData("ping"))
                .isEqualTo("pong");
    }

    @Test
    void defaultDataCachingTest() {
        when(mockEndpoint.fetchData("DEFAULT_REQUEST"))
                .thenReturn("pong");

        assertThat(underTest.getDefaultData())
                .isEqualTo("pong");

        when(mockEndpoint.fetchData("DEFAULT_REQUEST"))
                .thenReturn("other pong");

        assertThat(underTest.getDefaultData())
                .isEqualTo("pong");
    }

    @Test
    void defaultDataCachingTrickyTest() {
        when(mockEndpoint.fetchData("DEFAULT_REQUEST"))
                .thenReturn("pong");

        assertThat(underTest.getData("DEFAULT_REQUEST"))
                .isEqualTo("pong");

        when(mockEndpoint.fetchData("DEFAULT_REQUEST"))
                .thenReturn("other pong");

        assertThat(underTest.getDefaultData())
                .isEqualTo("pong");
    }

    @Test
    void defaultDataWithParameterCachingTrickyTest() {
        when(mockEndpoint.fetchData("DEFAULT_REQUEST"))
                .thenReturn("pong");

        assertThat(underTest.getDataWithParameter("DEFAULT_REQUEST", "DEFAULT_REQUEST"))
                .isEqualTo("pong");

        // assertThat(underTest.getDefaultDataWithParam()).isEqualTo("pong");
        when(mockEndpoint.fetchData("DEFAULT_REQUEST"))
                .thenReturn("other pong");

        assertThat(underTest.getDefaultDataWithParam())
                .isEqualTo("pong");
    }

}