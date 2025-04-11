package vn.softdream.autotest.constants;

/**
 * Please do not delete fields that are considered unused <p>
 * As it is used to reference the database permission list config for comparison <p>
 * Used in this application with @{@link vn.softdream.autotest.aop.annotation.CheckAuthorize}
 *
 * @author thoaidc
 */
@SuppressWarnings("unused")
public interface RoleConstants {

    interface System {

        String SYSTEM = "01";
        String VIEW = "0101";
        String UPDATE = "0102";
    }

    interface Account {

        String ACCOUNT = "02";
        String VIEW = "0201";
        String CREATE = "0202";
        String UPDATE = "0203";
        String DELETE = "0204";
    }

    interface Role {

        String ROLE = "03";
        String VIEW = "0301";
        String CREATE = "0302";
        String UPDATE = "0303";
        String DELETE = "0304";
    }
}
