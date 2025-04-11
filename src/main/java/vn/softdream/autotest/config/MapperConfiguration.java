package vn.softdream.autotest.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Create beans to handle the conversion of values between objects and between objects and JSON
 * @author thoaidc
 */
@Configuration
public class MapperConfiguration {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        // Avoid errors when encountering undefined properties in JSON that are not present in the Java class being converted
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // Do not serialize (convert to JSON) fields with empty or null values
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

        objectMapper.registerModule(new JavaTimeModule()); // To support Instant datatype of Java 8
        objectMapper.registerModule(new Jdk8Module());

        return objectMapper;
    }

    @Bean
    public ModelMapper modelMapper() {
        // Create object ModelMapper and config mapping
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }
}
