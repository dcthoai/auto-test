package vn.softdream.autotest.dto.response;

import java.io.Serial;
import java.io.Serializable;

@SuppressWarnings("unused")
public class AuditingEntityDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String createdByStr;
    private String createdDateStr;
    private String lastModifiedByStr;
    private String lastModifiedDateStr;

    public String getCreatedByStr() {
        return createdByStr;
    }

    public void setCreatedByStr(String createdByStr) {
        this.createdByStr = createdByStr;
    }

    public String getCreatedDateStr() {
        return createdDateStr;
    }

    public void setCreatedDateStr(String createdDateStr) {
        this.createdDateStr = createdDateStr;
    }

    public String getLastModifiedByStr() {
        return lastModifiedByStr;
    }

    public void setLastModifiedByStr(String lastModifiedByStr) {
        this.lastModifiedByStr = lastModifiedByStr;
    }

    public String getLastModifiedDateStr() {
        return lastModifiedDateStr;
    }

    public void setLastModifiedDateStr(String lastModifiedDateStr) {
        this.lastModifiedDateStr = lastModifiedDateStr;
    }
}
