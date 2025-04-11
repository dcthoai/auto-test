package vn.softdream.autotest.config.properties;

import vn.softdream.autotest.constants.PropertiesConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Contains the list of locations where JPA will scan to find and manage entities and repositories<p>
 * When the application starts, Spring will automatically create an instance of this class
 * and load the values from configuration files like application.properties or application.yml <p>
 *
 * {@link ConfigurationProperties} helps Spring map config properties to fields,
 * instead of using @{@link Value} for each property individually <p>
 *
 * {@link PropertiesConstants#DATASOURCE} decides the prefix for the configurations that will be mapped <p>
 *
 * See <a href="">application-dev.yml</a> for detail
 *
 * @author thoaidc
 */
@Component
@ConfigurationProperties(prefix = PropertiesConstants.JPA_PROPERTIES)
public class JpaConfig {

    private List<String> baseRepositoryPackages;
    private List<String> baseEntityPackages;
    private String persistenceUnitName;

    public List<String> getBaseRepositoryPackages() {
        return baseRepositoryPackages;
    }

    public void setBaseRepositoryPackages(List<String> baseRepositoryPackages) {
        this.baseRepositoryPackages = baseRepositoryPackages;
    }

    public List<String> getBaseEntityPackages() {
        return baseEntityPackages;
    }

    public void setBaseEntityPackages(List<String> baseEntityPackages) {
        this.baseEntityPackages = baseEntityPackages;
    }

    public String getPersistenceUnitName() {
        return persistenceUnitName;
    }

    public void setPersistenceUnitName(String persistenceUnitName) {
        this.persistenceUnitName = persistenceUnitName;
    }
}
