package vn.softdream.autotest.config;

import jakarta.servlet.ServletContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.server.WebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Objects;

import static java.net.URLDecoder.decode;

/**
 * Configuration of web application with Servlet 3.0 APIs
 * @author thoaidc
 */
@Configuration
public class WebConfigurer implements ServletContextInitializer, WebServerFactoryCustomizer<WebServerFactory> {

    private final Logger log = LoggerFactory.getLogger(WebConfigurer.class);
    private final Environment env;

    public WebConfigurer(Environment env) {
        this.env = env;
    }

    @Override
    public void onStartup(ServletContext servletContext) {
        if (env.getActiveProfiles().length != 0) {
            log.info("Web application configuration, using profiles: {}", (Object[]) env.getActiveProfiles());
        }

        log.info("Web application fully configured");
    }

    /**
     * Customize the Servlet engine: Mime types, the document root, the cache.
     */
    @Override
    public void customize(WebServerFactory server) {
        // When running in an IDE or with ./mvnw spring-boot:run, set location of the static web assets
        setLocationForStaticAssets(server);
    }

    /**
     * When running the application in a development or testing environment (e.g. using Maven with the target folder),
     * static resources will be served from the <a href="">target/classes/static/</a> directory instead of the default.<p>
     * This is useful when static resources are created or changed during development
     * and need to be reflected directly on the server without recompiling the entire application.
     * @param server used to check if the server is a {@link ConfigurableServletWebServerFactory}
     *               (a factory for embedded servlet servers such as Tomcat, Jetty, or Undertow).
     */
    private void setLocationForStaticAssets(WebServerFactory server) {
        if (server instanceof ConfigurableServletWebServerFactory servletWebServer) {
            String prefixPath = resolvePathPrefix();
            File root = new File(prefixPath + "target/classes/static/");

            if (root.exists() && root.isDirectory()) {
                servletWebServer.setDocumentRoot(root);
            }
        }
    }

    /**
     * Resolve path prefix for static resources. <p>
     * Ensure that the path to static resources is determined correctly
     * even when the application is run from different locations (e.g. IDE, command line).<p>
     * Handle situations where the path may change due to directory structure or runtime environment.
     */
    private String resolvePathPrefix() {
        // Get the path where the current class is compiled and stored. Use UTF-8 standard to handle special characters
        String fullExecutablePath = decode(
                Objects.requireNonNull(this.getClass().getResource("")).getPath(),
                StandardCharsets.UTF_8
        );

        // Specifies the current project root directory path (.).
        String rootPath = Paths.get(".").toUri().normalize().getPath();
        String extractedPath = fullExecutablePath.replace(rootPath, "");

        // Find the path part from the root to the target/ directory
        // or return an empty string if the /target directory does not exist
        int extractionEndIndex = extractedPath.indexOf("target/");

        if (extractionEndIndex <= 0)
            return "";

        return extractedPath.substring(0, extractionEndIndex);
    }
}
