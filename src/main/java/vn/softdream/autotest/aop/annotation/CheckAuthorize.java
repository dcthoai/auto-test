package vn.softdream.autotest.aop.annotation;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Apply Aspect-Oriented Programming (AOP) with the spring-aop framework to build a custom annotation <p>
 * Help controlling access to API based on user roles and permissions, similar to @{@link PreAuthorize} <p>
 *
 * `@{@link Target}` specifies where this annotation can be applied
 * <ul>
 *   <li>{@link ElementType#METHOD}: Allows the annotation to be applied to methods</li>
 *   <li>{@link ElementType#TYPE}: Allows the annotation to be applied to classes, interfaces, or enums</li>
 * </ul>
 *
 * `@{@link Retention}` means this annotation will be retained during runtime.
 * The annotation's information will be available while the program is running and can be accessed via reflection <p>
 * For example, this allows you to use annotations for dynamic access control checks during runtime,
 * such as when processing HTTP requests in a web application <p>
 *
 * `@{@link Inherited}` allows the annotation to be inherited by subclasses <p>
 * For example, if the parent class has the @CheckAuthorize annotation,
 * the subclass will automatically inherit and apply the rules of this annotation without needing to declare it again,
 * similar to methods in the class <p>
 *
 * `@{@link Documented}` indicates that annotation will be included in the API documentation when using tools like Javadoc
 *
 * @author thoaidc
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@SuppressWarnings("unused")
public @interface CheckAuthorize {

    // A list of roles and permissions you want to apply
    String authorities() default "";
}
