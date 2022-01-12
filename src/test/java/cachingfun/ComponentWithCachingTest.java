package cachingfun;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
class ComponentWithCachingTest {

    @MockBean
    RemoteEndpoint mockEndpoint;

    @Autowired
    ComponentWithCaching underTest;

    @Test
    void willFetchDataFromRemoteEndpoint() {
        when(mockEndpoint.fetchData("ping"))
                .thenReturn("pong");

        assertThat(underTest.getData("ping"))
                .isEqualTo("pong");
    }

    @Test
    void cachingTest() {
        when(mockEndpoint.fetchData("ping2"))
                .thenReturn("pong2");

        assertThat(underTest.getData("ping2"))
                .isEqualTo("pong2");

        when(mockEndpoint.fetchData("ping2"))
                .thenReturn("other pong 2");

        assertThat(underTest.getData("ping2"))
                .isEqualTo("pong2");
    }

}