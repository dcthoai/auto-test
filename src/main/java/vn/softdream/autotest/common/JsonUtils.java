package vn.softdream.autotest.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Generic class for handling json with file and string
 * @author thoaidc
 */
@SuppressWarnings("unused")
public class JsonUtils {

    private static final Logger log = LoggerFactory.getLogger(JsonUtils.class);

    // Allow serialize even null field, auto format json before save
    private static final Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();

    /**
     * Read an object from json file
     * @param filePath Path to json file
     * @param className Class of the object to be converted from json data
     * @return Object corresponding to json data or null on error
     * @param <T> Generics type
     */
    public static <T> T readJsonFromFile(String filePath, Class<T> className) {
        try (FileReader reader = new FileReader(filePath)) {
            return gson.fromJson(reader, className);
        } catch (JsonSyntaxException e) {
            log.error("Invalid JSON format. {}", e.getMessage());
        } catch (IOException | JsonIOException e) {
            log.error("Cannot read data from file: {}. {}", filePath, e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return null;
    }

    /**
     * Read an array from json file
     * @param filePath Path to json file
     * @param className Class of the object to be converted from json data
     * @return List of objects which corresponds to json data or an empty list if an error occurs
     * @param <T> Generics type
     */
    public static <T> List<T> readJsonArrayFromFile(String filePath, Class<T> className) {
        try (FileReader reader = new FileReader(filePath)) {
            JsonElement jsonElement = gson.fromJson(reader, JsonElement.class);
            List<T> results = new ArrayList<>();

            // Check if the JSON is an array or a single object
            if (jsonElement.isJsonArray()) {
                Type type = TypeToken.getParameterized(List.class, className).getType();
                results = gson.fromJson(jsonElement, type); // Parse json data as list
            } else if (jsonElement.isJsonObject()) {
                T object = gson.fromJson(jsonElement, className); // Parse json data as an object then add to list results

                if (Objects.nonNull(object))
                    results.add(object);
            }

            return results;
        } catch (JsonSyntaxException e) {
            log.error("Invalid JSON format. {}", e.getMessage());
        } catch (IOException | JsonIOException e) {
            log.error("Cannot read data from file: {}. {}", filePath, e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return new ArrayList<>();
    }

    /**
     * Delete old data in file and overwrite a new json object
     * @param filePath Path to json file
     * @param jsonObject Data to be written to file
     */
    public static void writeJsonToFile(String filePath, Object jsonObject) {
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(jsonObject, writer);
        } catch (IOException | JsonIOException e) {
            log.error("Cannot write JSON object to file: {}", filePath, e);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /**
     * Parse JSON content into a specific class type.
     *
     * @param jsonString  JSON string to parse
     * @param typeOrClass Type or class to parse into
     * @param <T>         Type of the desired object
     * @return Parsed object of type T or null on error
     */
    private static <T> T parse(String jsonString, Object typeOrClass) {
        if (StringUtils.hasText(jsonString)) {
            try {
                return gson.fromJson(jsonString, (Type) typeOrClass);
            } catch (JsonSyntaxException e) {
                log.error("Invalid JSON format. {}", e.getMessage());
            } catch (JsonIOException e) {
                log.error("Cannot parse JSON string. {}", e.getMessage());
            }
        }

        return null;
    }

    public static <T> T parseJson(String jsonString, Class<T> className) {
        return parse(jsonString, className);
    }

    public static <T> T parseJson(String jsonString, Type type) {
        return parse(jsonString, type);
    }

    /**
     * Parses a JSON string into a specific collection type (e.g., {@link List}, {@link Set}, {@link Map}) with element types <p>
     * Ex: List<{@link String}> strings = JsonUtils.parseJsonAsCollection(jsonString, {@link List}.class, {@link String}.class)
     * @param jsonString    The JSON string to parse
     * @param collectionType The class type of the collection (e.g., {@link List}.class, {@link Map}.class)
     * @param typeParams    The class types of the elements in the collection (e.g., {@link String}, {@link Integer})
     * @param <T>           The type of the collection to be returned
     * @return              A parsed collection of objects of the specified type, or null if parsing fails
     */
    public static <T> T parseJsonAsCollection(String jsonString, Class<?> collectionType, Class<?>... typeParams) {
        try {
            Type type = TypeToken.getParameterized(collectionType, typeParams).getType();
            return parseJson(jsonString, type);
        } catch (Exception e) {
            log.error("Cannot parse JSON string to {}. {}", collectionType.getName(), e.getMessage());
        }

        return null;
    }

    /**
     * Convert object to json String
     * @param object data to convert
     * @return A json string or empty on error
     */
    public static String toJsonString(Object object) {
        try {
            return gson.toJson(object);
        } catch (JsonIOException e) {
            log.error("Cannot convert object to JSON string. {}", e.getMessage());
        }

        return "";
    }
}
