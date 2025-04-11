package vn.softdream.autotest.constants;

import vn.softdream.autotest.config.InterceptorConfig;
import vn.softdream.autotest.dto.auth.BaseAuthTokenDTO;
import vn.softdream.autotest.security.config.SecurityConfig;
import vn.softdream.autotest.security.jwt.JwtProvider;

/**
 * Security configuration parameters
 * @author thoaidc
 */
@SuppressWarnings("unused")
public interface SecurityConstants {

    // The encryption complexity in PasswordEncoder's algorithm (between 4 and 31)
    // Higher values mean the password is harder to attack, but too high will reduce performance
    int BCRYPT_COST_FACTOR = 12;

    /**
     * The corresponding keys to store information in the payload of a JWT token <p>
     * See {@link JwtProvider#createToken(BaseAuthTokenDTO)} for details
     */
    interface TOKEN_PAYLOAD {
        String USER_ID = "userId";
        String DEVICE_ID = "deviceId";
        String AUTHORITIES = "authorities";
    }

    /**
     * The paths for security configuration in {@link SecurityConfig#securityFilterChain} <p>
     * Requests matching the patterns below will have their own specific security rules applied <p>
     * Requests not listed will require authentication by default
     */
    interface REQUEST_MATCHERS {

        String[] PRIVATE = { "/api/admin**", "/admin**", "/api/users**", "/users**" };
        String[] PUBLIC = {
            "/",
            "/**.html",
            "/**.css",
            "/**.js",
            "/**.ico",
            "/i18n/**",
            "/content/**",
            "/uploads/**",
            "/static/**",
            "/register",
            "/login",
            "/p/**",
            "/api/p/**"
        };
    }

    /**
     * The configurations applied in the CORS filter in {@link InterceptorConfig#corsFilter()}
     */
    interface CORS {
        String APPLY_FOR = "/**"; // CORS filter is applied to all requests
        String[] ALLOWED_HEADERS = {
            "Content-Type",     // Content format
            "Authorization",    // Authentication token
            "Accept",           // Client-expected content
            "Origin",           // Origin of the request
            "X-CSRF-Token",     // Anti-CSRF token
            "X-Requested-With", // Ajax request markup
            "Access-Control-Allow-Origin", // Server response header
            "X-App-Version",    // Application version (optional)
            "X-Device-ID"       // Device ID (optional)
        };

        String[] ALLOWED_REQUEST_METHODS = {"GET", "PUT", "POST", "DELETE"};
        String[] ALLOWED_ORIGIN_PATTERNS = {"*"}; // The list of domains allowed to access the resources. * means all
        boolean ALLOW_CREDENTIALS = true; // Allow sending cookies or authentication information
    }

    interface COOKIES {
        // The key of the cookie storing the JWT token, which is HTTP-only
        // This cookie is automatically sent with requests by browser but cannot be accessed by JavaScript
        String HTTP_ONLY_COOKIE_ACCESS_TOKEN = "dct_access_token";
    }

    interface HEADER {

        // The request header storing the JWT token, used in cases where the token is not found in the HTTP-only cookies
        String AUTHORIZATION_HEADER = "Authorization";
        String AUTHORIZATION_GATEWAY_HEADER = "Authorization-Gateway";
        String TOKEN_TYPE = "Bearer "; // JWT token type

        // Configure Content-Security-Policy (CSP), which controls how resources are loaded and executed on the website
        // The main goal is to minimize the risk of attacks such as Cross-Site Scripting (XSS) or Code Injection
        String SECURITY_POLICY = "default-src 'self';" + // Only allow resources to be loaded from the same domain
                // Only allow embedded content (iframes) from the same domain (self) or from URLs with schema data
                " frame-src 'self' data:;" +
                // Only allow script execution from the same domain (self), unsafe-inline (allows inline scripts),
                // unsafe-eval (allows eval()), and from the domain https://storage.googleapis.com
                " script-src 'self' 'unsafe-inline' 'unsafe-eval' https://storage.googleapis.com;" +
                // Only allow styles from the same domain (self) and allow inline CSS (unsafe-inline)
                " style-src 'self' 'unsafe-inline';" +
                // Only allow images from the same domain (self) and from URLs with schema data
                " img-src 'self' data:;" +
                // Only allow fonts from the same domain (self) and from URLs with schema data
                " font-src 'self' data:";

        // Permissions-Policy limits access to sensitive browser features, helping protect user privacy
        String PERMISSIONS_POLICY = "camera=(), " + // Prevent any origin from using the camera
                "fullscreen=(self), " + // Only allow current domain (self) to use full screen mode
                "geolocation=(), " + // Prevent access to GPS location services
                "gyroscope=(), " + // Prevent use of gyroscope sensor
                "magnetometer=(), " + // Prevent use of magnetometer sensor
                "microphone=(), " + // Prevent microphone access
                "midi=(), " + // Prevent access to MIDI (digital musical instruments)
                "payment=(), " + // Prevent use of payment features
                "sync-xhr=()"; // Prevent use of synchronous XMLHttpRequest requests
    }

    interface OAUTH2 {

        /**
         * This script is used in the OAuth2 authentication flow after an authentication page opens in a popup window,
         * performs the authentication, and then reports the result to the parent window before closing the popup
         */
        String AUTH_SUCCESS_SCRIPT = """
            <script>
                const AUTH_SUCCESS_MESSAGE = 'auth-success';
                window.opener?.postMessage(AUTH_SUCCESS_MESSAGE, '*');
                window.close();
            </script>
        """;
    }
}
