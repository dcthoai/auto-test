package vn.softdream.autotest.constants;

/**
 * Contains the custom response codes for HTTP responses
 * @author thoaidc
 */
@SuppressWarnings("unused")
public interface HttpStatusConstants {

    // The status indicating successful processing of the request (in the case of valid input data and no system errors)
    interface STATUS {
        boolean SUCCESS = true;
        boolean FAILED = false;
    }

    int OK = 200; // Request success
    int CREATED = 201; // Resource created successfully
    int ACCEPTED = 202; // Request accepted and being processed
    int NO_CONTENT = 204; // Successful but no return content
    int BAD_REQUEST = 400; // Invalid request
    int UNAUTHORIZED = 401; // Not authenticated
    int PAYMENT_REQUIRED = 402; // Payment requested
    int FORBIDDEN = 403; // Access denied
    int NOT_FOUND = 404; // Resource not found
    int METHOD_NOT_ALLOWED = 405; // Method not allowed
    int CONFLICT = 409; // Conflicting state, often encountered when updating resources
    int UNPROCESSABLE_ENTITY = 422; // Request could not be processed (authentication or data error)
    int INTERNAL_SERVER_ERROR = 500; // Internal server error
    int BAD_GATEWAY = 502; // Gateway unavailable
    int SERVICE_UNAVAILABLE = 503; // Service temporarily unavailable
    int GATEWAY_TIMEOUT = 504; // Gateway timeout
    int NOT_IMPLEMENTED = 501; // Not implemented
}
