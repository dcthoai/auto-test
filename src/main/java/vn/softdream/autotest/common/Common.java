package vn.softdream.autotest.common;

import vn.softdream.autotest.constants.DatetimeConstants;
import vn.softdream.autotest.dto.response.AuditingEntityDTO;
import vn.softdream.autotest.entity.AbstractAuditingEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class Common {

    private static final Logger log = LoggerFactory.getLogger(Common.class);
    private static final String ENTITY_NAME = "Common";

    public static void setAuditingInfo(AbstractAuditingEntity entity, AuditingEntityDTO auditingDTO) {
        auditingDTO.setCreatedByStr(entity.getCreatedBy());
        auditingDTO.setLastModifiedByStr(entity.getLastModifiedBy());

        try {
            String createdDate = DateUtils.ofInstant(entity.getCreatedDate())
                .toString(DatetimeConstants.Formatter.DD_MM_YYYY_HH_MM_SS_DASH);

            String lastModifiedDate = DateUtils.ofInstant(entity.getLastModifiedDate())
                .toString(DatetimeConstants.Formatter.DD_MM_YYYY_HH_MM_SS_DASH);

            auditingDTO.setCreatedDateStr(createdDate);
            auditingDTO.setLastModifiedDateStr(lastModifiedDate);
        } catch (Exception e) {
            log.error("[{}] - Could not set entity auditing info. {}", ENTITY_NAME, e.getMessage());
        }
    }

    public static <T> Map<String, Class<?>> getObjectFields(Class<T> mappingClass) {
        Map<String, Class<?>> fieldMap = new HashMap<>();

        for (Field field : mappingClass.getDeclaredFields()) {
            fieldMap.put(field.getName(), field.getType());
        }

        return fieldMap;
    }
}
