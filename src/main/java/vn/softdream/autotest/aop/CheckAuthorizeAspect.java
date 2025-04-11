package vn.softdream.autotest.aop;

import vn.softdream.autotest.aop.annotation.CheckAuthorize;
import vn.softdream.autotest.constants.ExceptionConstants;
import vn.softdream.autotest.exception.BaseAuthenticationException;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * An AOP (Aspect-Oriented Programming) class in Spring <p>
 * AOP helps to separate business logic (e.g., access control checks) from the main logic of the application <p>
 * Used to check user access (authorization) before executing a method annotated with @{@link CheckAuthorize} <p>
 *
 * `@{@link Aspect}` mark this class as an Aspect in AOP <p>
 * `@{@link Component}` mark this class as a component for Spring to manage automatically
 *
 * @author thoaidc
 */
@Aspect
@Component
public class CheckAuthorizeAspect {

    private static final Logger log = LoggerFactory.getLogger(CheckAuthorizeAspect.class);
    private static final String ENTITY_NAME = "CheckAuthorizeAspect";

    public CheckAuthorizeAspect() {
        log.debug("Configured CheckAuthorizeAspect for handle authenticate method");
    }

    /**
     * {@link Pointcut} specifies where (in which method, class, or annotation) AOP logic will be applied<p>
     * This function only serves to name and define the pointcut, it does not execute any logic<p>
     * Reusability: If you need to use the same pointcut in multiple places
     * (for example, in @{@link Around}, @{@link Before}, or @{@link After} annotations),
     * you can simply reference this function
     */
    @Pointcut("@annotation(vn.softdream.autotest.aop.annotation.CheckAuthorize)") // Full path to CustomAnnotation class
    public void checkAuthorizeByJwt() {}

    /**
     * {@link Around} is a type of advice in AOP that allows you to surround the target method <p>
     * It can control the execution flow of the method (decide whether method should be executed or not)
     *
     * @return Forward the request to the target method for processing if the user has sufficient permissions
     * @throws BaseAuthenticationException If the user does not have the required permissions
     */
    @Around("checkAuthorizeByJwt()")
    public Object aroundCheckAuthorizeByJwt(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        // Retrieve the annotation to check the list of required permissions for the current method
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getStaticPart().getSignature();
        CheckAuthorize annotation = methodSignature.getMethod().getAnnotation(CheckAuthorize.class);

        // Check against the list of permissions of the current user in security context
        List<String> requiredAuthorities = Arrays.asList(annotation.authorities().split(","));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Set<String> userAuthorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        // If the user has sufficient permissions, allow the request to proceed
        if (userAuthorities.containsAll(requiredAuthorities))
            return proceedingJoinPoint.proceed();

        try {
            // Try to log the user's access attempt if they do not have permission to access the method
            ServletRequestAttributes request = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest httpServletRequest = Objects.requireNonNull(request).getRequest();
            String url = httpServletRequest.getRequestURL().toString();
            String username = Objects.nonNull(authentication.getName()) ? authentication.getName() : "Anonymous";
            log.error("User '{}' does not have any permission to access this function: {}", username, url);
        } catch (Exception ignore) {}

        // Throw an exception to allow CustomExceptionHandler handling and return a response to the client
        throw new BaseAuthenticationException(ENTITY_NAME, ExceptionConstants.FORBIDDEN);
    }
}
