package vn.softdream.autotest.constants;

/**
 * Contains the common configuration constants for the project without security configurations
 * @author thoaidc
 */
@SuppressWarnings("unused")
public interface BaseConstants {

    String MESSAGE_SOURCE_ENCODING = "UTF-8"; // Specifies the charset for i18n messages

    // The location where the resource bundle files for i18n messages are stored
    String[] MESSAGE_SOURCE_BASENAME = { "classpath:i18n/messages" };

    // The paths that will be ignored by interceptors when processing requests
    String[] INTERCEPTOR_EXCLUDED_PATHS = {
        "**/favicon.ico",
        "/images/**",
        "/index.html",
        "**index.html",
        "**/file/**",
        "**/login",
        "/error**",
        "/i18n/**"
    };

    interface UPLOAD_RESOURCES {
        String PREFIX_PATH = "/uploads/";
        String[] VALID_IMAGE_FORMATS = { ".png", ".jpg", ".jpeg", ".gif", ".svg", ".webp", ".webm" };
        String[] COMPRESSIBLE_IMAGE_FORMATS = { ".png", ".jpg", ".jpeg", ".webp" };
        String DEFAULT_IMAGE_FORMAT = ".webp";
        String DEFAULT_IMAGE_PATH_FOR_ERROR = PREFIX_PATH + "error/error_image.webp";
        String PNG = "png";
        String WEBP = "webp";
        String JPG = "jpg";
        String JPEG = "jpeg";
    }

    /**
     * Configures the handling of static resources <p>
     * Static resource requests listed in the {@link STATIC_RESOURCES#PATHS} section will be automatically searched for
     * and mapped to the directories listed in the {@link STATIC_RESOURCES#LOCATIONS} section
     */
    interface STATIC_RESOURCES {

        String[] PATHS = {
            "/**.js",
            "/**.css",
            "/**.svg",
            "/**.png",
            "/**.ico",
            "/content/**",
            "/uploads/**",
            "/i18n/**"
        };

        String[] LOCATIONS = {
            "classpath:/static/",
            "classpath:/static/content/",
            "classpath:/static/i18n/"
        };
    }

    // Some patterns to validate data formats
    @SuppressWarnings("unused")
    interface REGEX {
        String USERNAME_PATTERN = "^[a-zA-Z0-9]{2,45}$"; // Includes only numbers and letters

        // The length is between 8 and 20 characters
        // It contains only letters (lowercase/uppercase), numbers, and special characters from a specified list
        String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!])[A-Za-z\\d@#$%^&+=!]{8,20}$";

        // The email address cannot have invalid characters, such as a dot at the beginning or end of the domain name
        String EMAIL_PATTERN = "^[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";

        // The phone number can have a plus sign (+) at the beginning (for international numbers)
        // The country code can consist of 1 to 3 digits
        // The area code must follow specific rules (e.g., starting with 3, 5, 7, 8, or 9 and followed by certain digits)
        // Spaces, dots, or hyphens can be used to separate parts of the phone number
        // After the area code, there may be one or two groups of three or four digits
        // Examples of valid phone numbers:
        // 0355034078
        // +123 456 7890
        // +84-90-123-4567
        // 091-123-4567
        // +123.456.7890
        String PHONE_PATTERN = "^\\+?(\\d{1,3})(\\s|\\.|-)?((3[2-9])|(5[689])|(7[06-9])|(8[1-689])|(9[0-46-9])|([1-9]\\d{1,2}))(\\s|\\.|-)?\\d{3}(\\s|\\.|-)?\\d{3,4}$";

        String ACCOUNT_STATUS_PATTERN = "^(ACTIVE|INACTIVE|LOCKED|DELETED)$";
    }
}
