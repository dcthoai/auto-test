package vn.softdream.autotest.constants;

import vn.softdream.autotest.dto.response.BaseResponseDTO;

/**
 * Message in api response with internationalization (I18n) here <p>
 * Use when you want to create a detailed response message for the client in {@link BaseResponseDTO} <p>
 * The constant content corresponds to the message key in the resources bundle files in directories such as:
 * <ul>
 *   <li><a href="">resources/i18n/messages</a></li>
 * </ul>
 * These paths are defined in {@link BaseConstants#MESSAGE_SOURCE_BASENAME}
 *
 * @author thoaidc
 */
public interface ResultConstants {

    // Get data success
    String GET_DATA_SUCCESS = "result.data.success";
    String SUCCESS = "result.success";

    // Get data failed
    String DATA_NOT_FOUND = "result.failed.data.notFound";

    // Account information processing result messages
    String REGISTER_SUCCESS = "result.account.register.success";

    // Authenticate account result messages
    String LOGIN_SUCCESS = "result.auth.login.success";
}
