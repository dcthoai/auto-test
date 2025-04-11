package vn.softdream.autotest.constants;

/**
 * Messages for exceptions with internationalization (I18n) here<p>
 * The constant content corresponds to the message key in the resources bundle files in directories such as:
 * <ul>
 *   <li><a href="">resources/i18n/messages</a></li>
 * </ul>
 * These paths are defined in {@link BaseConstants#MESSAGE_SOURCE_BASENAME}
 *
 * @author thoaidc
 */
@SuppressWarnings("unused")
public interface ExceptionConstants {

    // I18n exception
    String TRANSLATE_NOT_FOUND = "exception.i18n.notFound";

    // Http exception
    String METHOD_NOT_ALLOW = "exception.http.methodNotAllow";

    // Runtime exception OR undetermined error
    String UNCERTAIN_ERROR = "exception.uncertain";
    String NULL_EXCEPTION = "exception.nullPointer";

    // Upload file request
    String MAXIMUM_UPLOAD_SIZE_EXCEEDED = "exception.upload.maximumSizeExceed";

    // Request data error
    String INVALID_REQUEST_DATA = "exception.request.data.invalid";

    // Authentication exception
    String BAD_CREDENTIALS = "exception.auth.badCredentials";
    String CREDENTIALS_EXPIRED = "exception.auth.credentialsExpired";
    String ACCOUNT_EXPIRED = "exception.auth.accountExpired";
    String ACCOUNT_LOCKED = "exception.auth.accountLocked";
    String ACCOUNT_DISABLED = "exception.auth.accountDisabled";
    String ACCOUNT_NOT_FOUND = "exception.auth.accountNotFound";
    String UNAUTHORIZED = "exception.auth.unauthorized";
    String FORBIDDEN = "exception.auth.forbidden";
    String TOKEN_INVALID_OR_EXPIRED = "exception.auth.token.invalidOrExpired";

    // OAuth2 authorization exception
    String OAUTH2_AUTHORIZATION_CODE_NOT_FOUND = "exception.oauth2.authorizationCode.notFound";
    String OAUTH2_AUTHORIZATION_CODE_CLIENT_ERROR = "exception.oauth2.authorizationCode.clientError";
    String OAUTH2_AUTHORIZATION_CODE_SERVER_ERROR = "exception.oauth2.authorizationCode.serverError";
    String OAUTH2_AUTHORIZATION_CODE_EXCEPTION = "exception.oauth2.authorizationCode.exception";
    String OAUTH2_USER_INFO_NOT_FOUND = "exception.oauth2.userinfo.notFound";
    String OAUTH2_USER_INFO_CLIENT_ERROR = "exception.oauth2.userinfo.clientError";
    String OAUTH2_USER_INFO_SERVER_ERROR = "exception.oauth2.userinfo.serverError";
    String OAUTH2_USER_INFO_EXCEPTION = "exception.oauth2.userinfo.exception";

    // Validate account info exception
    String REGISTER_FAILED = "exception.account.register.failed";
    String ACCOUNT_EXISTED = "exception.account.existed";
    String ACCOUNT_NOT_EXISTED = "exception.account.notExisted";

    // Role
    String ROLE_EXISTED = "exception.role.existed";
    String ROLE_PERMISSIONS_NOT_EMPTY = "exception.role.permissions.notEmpty";
    String ROLE_PERMISSION_INVALID = "exception.role.permission.invalidList";

    // Form data request
    String DATA_INVALID = "exception.data.invalid";
    String DATA_NOT_FOUND = "exception.data.notFound";
    String DATA_EXISTED = "exception.data.existed";
    String DATA_NOT_EXISTED = "exception.data.notExisted";
    String ID_NOT_NULL = "exception.data.id.notNull";
    String ID_INVALID = "exception.data.id.invalid";
    String NAME_NOT_BLANK = "exception.data.name.notBlank";
    String NAME_MAX_LENGTH = "exception.data.name.maxLength";
    String CODE_NOT_BLANK = "exception.data.code.notBlank";
    String USERNAME_NOT_BLANK = "exception.data.username.notBlank";
    String USERNAME_INVALID = "exception.data.username.invalid";
    String USERNAME_MIN_LENGTH = "exception.data.username.minLength";
    String USERNAME_MAX_LENGTH = "exception.data.username.maxLength";
    String PASSWORD_NOT_BLANK = "exception.data.password.notBlank";
    String PASSWORD_MIN_LENGTH = "exception.data.password.minLength";
    String PASSWORD_MAX_LENGTH = "exception.data.password.maxLength";
    String PASSWORD_INVALID = "exception.data.password.invalid";
    String EMAIL_NOT_BLANK = "exception.data.email.notBlank";
    String EMAIL_MIN_LENGTH = "exception.data.email.minLength";
    String EMAIL_MAX_LENGTH = "exception.data.email.maxLength";
    String EMAIL_INVALID = "exception.data.email.invalid";
    String PHONE_NOT_BLANK = "exception.data.phone.notBlank";
    String PHONE_MIN_LENGTH = "exception.data.phone.minLength";
    String PHONE_MAX_LENGTH = "exception.data.phone.maxLength";
    String PHONE_INVALID = "exception.data.phone.invalid";
    String DESCRIPTION_NOT_BLANK = "exception.data.description.notBlank";
    String DESCRIPTION_MAX_LENGTH = "exception.data.description.maxLength";
    String ADDRESS_NOT_BLANK = "exception.data.address.notBlank";
    String ADDRESS_MAX_LENGTH = "exception.data.address.maxLength";
    String STATUS_NOT_BLANK = "exception.data.status.notBlank";
    String STATUS_INVALID = "exception.data.status.invalid";
    String TITLE_NOT_BLANK = "exception.data.title.notBlank";
    String TITLE_MAX_LENGTH = "exception.data.title.maxLength";
    String CONTENT_NOT_BLANK = "exception.data.content.notBlank";
    String CONTENT_MAX_LENGTH = "exception.data.content.maxLength";
    String DEVICE_ID_NOT_BLANK = "exception.data.deviceId.notBlank";
}
