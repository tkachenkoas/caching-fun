package cachingfun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CachingFunApplication {

    public static void main(String[] args) {
        SpringApplication.run(CachingFunApplication.class);
    }

}
