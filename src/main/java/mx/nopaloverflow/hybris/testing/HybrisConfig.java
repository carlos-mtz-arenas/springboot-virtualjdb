package mx.nopaloverflow.hybris.testing;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.lang.reflect.InvocationTargetException;
import java.sql.Driver;

@Configuration
public class HybrisConfig {

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;
    @Value("${spring.datasource.url}")
    private String jdbcUrl;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    private Object getInstance(final Class<?> driverClass) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        final var constructor = ClassUtils.getConstructorIfAvailable(driverClass);
        if (constructor == null) {
            return null;
        }

        return constructor.newInstance();
    }

    @Bean
    public JdbcTemplate jdbcTemplate() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        final var driverClass = ClassUtils.resolveClassName(driverClassName, this.getClass().getClassLoader());

        final var instance = getInstance(driverClass);

        Assert.isTrue(instance instanceof Driver, "instance should be an instance of Driver");

        final var driver = (Driver) instance;
        final var dataSource = new SimpleDriverDataSource(driver, jdbcUrl, username, password);

        // and make the jdbcTemplate
        return new JdbcTemplate(dataSource);
    }
}
