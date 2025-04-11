package vn.softdream.autotest.exception.handler;

import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import vn.softdream.autotest.common.MessageUtils;
import vn.softdream.autotest.constants.ExceptionConstants;
import vn.softdream.autotest.constants.HttpStatusConstants;
import vn.softdream.autotest.dto.response.BaseResponseDTO;
import vn.softdream.autotest.exception.BaseAuthenticationException;
import vn.softdream.autotest.exception.BaseBadRequestException;
import vn.softdream.autotest.exception.BaseException;

import java.util.Objects;

/**
 * Used to handle exceptions in the application centrally and return consistent responses <p>
 * Provides a standardized and centralized approach to handling common errors in Spring applications <p>
 * Helps log detailed errors, return structured responses, and easily internationalize error messages
 *
 * @author thoaidc
 */
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(CustomExceptionHandler.class);
    private final MessageUtils messageUtils;

    public CustomExceptionHandler(MessageUtils messageUtils) {
        this.messageUtils = messageUtils;
        log.debug("ResponseEntityExceptionHandler 'CustomExceptionHandler' is configured for handle exception");
    }

    /**
     * Handle exceptions when an HTTP method is not supported (ex: calling POST on an endpoint that only supports GET)
     * @param e the exception to handle
     * @param headers the headers to use for the response
     * @param status the status code to use for the response
     * @param request the current request
     * @return ResponseEntity with body is a BaseResponseDTO with custom message I18n
     */
    @Override
    public ResponseEntity<Object> handleHttpRequestMethodNotSupported(@Nullable HttpRequestMethodNotSupportedException e,
                                                                      @Nullable HttpHeaders headers,
                                                                      @Nullable HttpStatusCode status,
                                                                      @Nullable WebRequest request) {
        log.error("Handle method not allow exception. {}", Objects.nonNull(e) ? e.getMessage() : "");

        BaseResponseDTO responseDTO = BaseResponseDTO.builder()
            .code(HttpStatusConstants.METHOD_NOT_ALLOWED)
            .success(HttpStatusConstants.STATUS.FAILED)
            .message(ExceptionConstants.METHOD_NOT_ALLOW)
            .build();

        return new ResponseEntity<>(responseDTO, HttpStatus.METHOD_NOT_ALLOWED);
    }

    /**
     * Handle exceptions when a request is invalid due to data (validation error)<p>
     * For example: When using @{@link Valid} in a controller method and the incoming request data is invalid
     *
     * @param exception the exception to handle
     * @param headers the headers to be written to the response
     * @param status the selected response status
     * @param request the current request
     * @return ResponseEntity with body is a BaseResponseDTO with custom message I18n
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
                                                                  @Nullable HttpHeaders headers,
                                                                  @Nullable HttpStatusCode status,
                                                                  @Nullable WebRequest request) {
        FieldError fieldError = exception.getBindingResult().getFieldError();
        String errorKey = ExceptionConstants.INVALID_REQUEST_DATA; // Default message

        if (Objects.nonNull(fieldError))
            errorKey = fieldError.getDefaultMessage(); // If the field with an error includes a custom message key

        String reason = messageUtils.getMessageI18n(errorKey);
        log.error("Handle validate request data exception: {}. {}", reason, exception.getMessage());

        BaseResponseDTO responseDTO = BaseResponseDTO.builder()
            .code(HttpStatusConstants.BAD_REQUEST)
            .success(HttpStatusConstants.STATUS.FAILED)
            .message(errorKey)
            .build();

        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ BaseAuthenticationException.class })
    public ResponseEntity<BaseResponseDTO> handleBaseAuthenticationException(BaseAuthenticationException exception) {
        String reason = messageUtils.getMessageI18n(exception.getErrorKey(), exception.getArgs());
        log.error("[{}] Handle authentication exception: {}", exception.getEntityName(), reason);

        BaseResponseDTO responseDTO = BaseResponseDTO.builder()
            .code(HttpStatusConstants.UNAUTHORIZED)
            .success(HttpStatusConstants.STATUS.FAILED)
            .message(exception.getErrorKey())
            .build();

        return new ResponseEntity<>(responseDTO, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({ BaseBadRequestException.class })
    public ResponseEntity<BaseResponseDTO> handleBaseBadRequestException(BaseBadRequestException exception) {
        String reason = messageUtils.getMessageI18n(exception.getErrorKey(), exception.getArgs());
        log.error("[{}] Handle bad request alert exception: {}", exception.getEntityName(), reason);

        BaseResponseDTO responseDTO = BaseResponseDTO.builder()
            .code(HttpStatusConstants.BAD_REQUEST)
            .success(HttpStatusConstants.STATUS.FAILED)
            .message(exception.getErrorKey())
            .build();

        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ BaseException.class })
    public ResponseEntity<BaseResponseDTO> handleBaseException(BaseException exception) {
        String reason = messageUtils.getMessageI18n(exception.getErrorKey(), exception.getArgs());
        log.error("[{}] Handle exception: {}", exception.getEntityName(), reason, exception.getError());

        BaseResponseDTO responseDTO = BaseResponseDTO.builder()
            .code(HttpStatusConstants.BAD_REQUEST)
            .success(HttpStatusConstants.STATUS.FAILED)
            .message(exception.getErrorKey())
            .build();

        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ MaxUploadSizeExceededException.class })
    public ResponseEntity<Object> handleNullPointerException(MaxUploadSizeExceededException e, WebRequest request) {
        log.error("[{}] Maximum upload size exceeded: {}", request.getClass().getName(), e.getMessage());

        BaseResponseDTO responseDTO = BaseResponseDTO.builder()
            .code(HttpStatusConstants.BAD_REQUEST)
            .success(HttpStatusConstants.STATUS.FAILED)
            .message(ExceptionConstants.MAXIMUM_UPLOAD_SIZE_EXCEEDED)
            .build();

        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ NullPointerException.class })
    public ResponseEntity<Object> handleNullPointerException(NullPointerException exception, WebRequest request) {
        // Handle NullPointerException (include of Objects.requireNonNull())
        log.error("[{}] Null pointer exception occurred: {}", request.getClass().getName(), exception.getMessage());

        BaseResponseDTO responseDTO = BaseResponseDTO.builder()
            .code(HttpStatusConstants.INTERNAL_SERVER_ERROR)
            .success(HttpStatusConstants.STATUS.FAILED)
            .message(ExceptionConstants.NULL_EXCEPTION)
            .build();

        return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({ RuntimeException.class })
    public ResponseEntity<BaseResponseDTO> handleRuntimeException(RuntimeException exception) {
        log.error("Handle runtime exception", exception);

        BaseResponseDTO responseDTO = BaseResponseDTO.builder()
            .code(HttpStatusConstants.INTERNAL_SERVER_ERROR)
            .success(HttpStatusConstants.STATUS.FAILED)
            .message(ExceptionConstants.UNCERTAIN_ERROR)
            .build();

        return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<BaseResponseDTO> handleException(Exception exception) {
        log.error("Handle unexpected exception", exception);

        BaseResponseDTO responseDTO = BaseResponseDTO.builder()
            .code(HttpStatusConstants.INTERNAL_SERVER_ERROR)
            .success(HttpStatusConstants.STATUS.FAILED)
            .message(ExceptionConstants.UNCERTAIN_ERROR)
            .build();

        return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
