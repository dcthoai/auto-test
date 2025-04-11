package vn.softdream.autotest.interceptor;

import jakarta.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import vn.softdream.autotest.common.MessageUtils;
import vn.softdream.autotest.dto.response.BaseResponseDTO;

/**
 * {@link ResponseBodyAdvice} is an interface that allows you to intervene with the response data <p>
 * Executed before the actual data is written into the response body
 *
 * @author thoaidc
 */
@ControllerAdvice
public class BaseResponseFilter implements ResponseBodyAdvice<Object> {

    private static final Logger log = LoggerFactory.getLogger(BaseResponseFilter.class);
    private final MessageUtils messageUtils;

    public BaseResponseFilter(MessageUtils messageUtils) {
        this.messageUtils = messageUtils;
    }

    /**
     * Check if the response is a {@link BaseResponseDTO} or {@link ResponseEntity}<{@link BaseResponseDTO}> <p>
     * If true, the response will be processed by {@link BaseResponseFilter#beforeBodyWrite}
     *
     * @param returnType the return type
     * @param converterType the selected converter type
     * @return true if response type is supported by this filter
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        boolean isBaseResponseDTOType = BaseResponseDTO.class.isAssignableFrom(returnType.getParameterType());
        boolean isBasicResponseEntity = ResponseEntity.class.isAssignableFrom(returnType.getParameterType());
        log.debug("Response will be processed by: " + converterType.getSimpleName());

        return isBaseResponseDTOType || isBasicResponseEntity;
    }

    /**
     * Override the response body to add internationalization (I18n) messages before it is returned
     *
     * @param body the body of HTTP request to be written
     * @param returnType the return type of the controller method
     * @param selectedContentType Media type requested by the client, usually application/json, application/xml, etc
     * @param selectedConverterType Type of converter that Spring will use to convert the response body (to JSON, XML)
     * @param request the current request
     * @param response the current response
     * @return ResponseEntity after modification
     */
    @Override
    public Object beforeBodyWrite(Object body,
                                  @Nullable MethodParameter returnType,
                                  @Nullable MediaType selectedContentType,
                                  @Nullable Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  @Nullable ServerHttpRequest request,
                                  @Nullable ServerHttpResponse response) {
        if (body instanceof BaseResponseDTO) {
            return messageUtils.setResponseMessageI18n((BaseResponseDTO) body);
        }

        if (body instanceof ResponseEntity<?> responseEntity) {
            Object responseBody = responseEntity.getBody();

            if (responseBody instanceof BaseResponseDTO) {
                BaseResponseDTO responseDTO = messageUtils.setResponseMessageI18n((BaseResponseDTO) responseBody);
                return new ResponseEntity<>(responseDTO, responseEntity.getHeaders(), responseEntity.getStatusCode());
            }
        }

        return body;
    }
}
