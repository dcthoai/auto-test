package vn.softdream.autotest.security.service;

import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;

import java.util.stream.Stream;

public class SecurityUtils {

    public static MvcRequestMatcher[] convertToMvcMatchers(MvcRequestMatcher.Builder mvc, String[] patterns) {
        return Stream.of(patterns)
            .map(mvc::pattern)
            .toList()
            .toArray(new MvcRequestMatcher[0]);
    }
}
