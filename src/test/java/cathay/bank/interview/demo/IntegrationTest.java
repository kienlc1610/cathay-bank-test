package cathay.bank.interview.demo;

import cathay.bank.interview.demo.config.AsyncConfiguration;
import cathay.bank.interview.demo.config.JacksonConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Base composite annotation for integration tests.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(classes = { DemoApplication.class, JacksonConfiguration.class, AsyncConfiguration.class })
@EmbeddedSQL
public @interface IntegrationTest {
}
