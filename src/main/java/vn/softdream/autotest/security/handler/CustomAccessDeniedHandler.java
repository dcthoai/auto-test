package vn.softdream.autotest.security.handler;

import vn.softdream.autotest.common.JsonUtils;
import vn.softdream.autotest.common.MessageUtils;
import vn.softdream.autotest.constants.ExceptionConstants;
import vn.softdream.autotest.constants.HttpStatusConstants;
import vn.softdream.autotest.dto.response.BaseResponseDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Handle cases where users attempt to access resources they do not have permission for (HTTP 403 - Forbidden)
 * @author thoaidc
 */
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private static final Logger log = LoggerFactory.getLogger(CustomAccessDeniedHandler.class);
    private final MessageUtils messageUtils;

    public CustomAccessDeniedHandler(MessageUtils messageUtils) {
        this.messageUtils = messageUtils;
        log.debug("AccessDeniedHandler 'CustomAccessDeniedHandler' is configured for use");
    }

    /**
     * Directly responds to the client when they lack sufficient access rights,
     * without passing the request to further filters <p>
     * In this case, a custom JSON response is sent back <p>
     * You can add additional business logic here, such as sending a redirect or other necessary actions
     *
     * @param request that resulted in an <code>AccessDeniedException</code>
     * @param response so that the user agent can be advised of the failure
     * @param exception that caused the invocation
     */
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException exception) throws IOException {
        log.error("AccessDenied handler is active. {}: {}", exception.getMessage(), request.getRequestURL());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE); // Convert response body to JSON
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setStatus(HttpStatusConstants.FORBIDDEN);

        BaseResponseDTO responseDTO = BaseResponseDTO.builder()
            .code(HttpStatusConstants.FORBIDDEN)
            .success(HttpStatusConstants.STATUS.FAILED)
            .message(messageUtils.getMessageI18n(ExceptionConstants.FORBIDDEN))
            .build();

        response.getWriter().write(JsonUtils.toJsonString(responseDTO));
        response.flushBuffer();
    }
}
