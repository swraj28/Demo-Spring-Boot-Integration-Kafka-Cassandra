package com.example.demo.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.apache.commons.collections4.map.CaseInsensitiveMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;


/**
 * JsonUtils.java - a simple class for handling all json related queries
 *
 */
public class JsonUtils {
    private static Logger logger = LogManager.getLogger(JsonUtils.class);
    private static final ObjectMapper OBJECT_MAPPER;
    private static final ObjectMapper OBJECT_MAPPER_CI;
    private static final TypeFactory TYPE_FACTORY;
    private static final ObjectMapper OBJECT_MAPPER_CUSTOM;

    public static final ObjectMapper OBJECT_MAPPER_FOR_LONG_TYPES =new ObjectMapper().configure(DeserializationFeature.USE_LONG_FOR_INTS, true);


    static {
        OBJECT_MAPPER = new ObjectMapper();
        OBJECT_MAPPER.enable(JsonGenerator.Feature.QUOTE_NON_NUMERIC_NUMBERS);
        OBJECT_MAPPER.enable(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS);
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        OBJECT_MAPPER.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        OBJECT_MAPPER.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        OBJECT_MAPPER.configure(DeserializationFeature.ACCEPT_FLOAT_AS_INT, false);

        OBJECT_MAPPER_CUSTOM = new ObjectMapper();
        OBJECT_MAPPER_CUSTOM.enable(JsonGenerator.Feature.QUOTE_NON_NUMERIC_NUMBERS);
        OBJECT_MAPPER_CUSTOM.enable(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS);
        OBJECT_MAPPER_CUSTOM.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        OBJECT_MAPPER_CUSTOM.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        OBJECT_MAPPER_CUSTOM.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        OBJECT_MAPPER_CUSTOM.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        OBJECT_MAPPER_CUSTOM.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        OBJECT_MAPPER_CUSTOM.configure(DeserializationFeature.ACCEPT_FLOAT_AS_INT, true);


        OBJECT_MAPPER_CI = new ObjectMapper();
        OBJECT_MAPPER_CI.enable(JsonGenerator.Feature.QUOTE_NON_NUMERIC_NUMBERS);
        OBJECT_MAPPER_CI.enable(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS);
        OBJECT_MAPPER_CI.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        TYPE_FACTORY = TypeFactory.defaultInstance();
    }

    public static <T> T parseJson(String data, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(data, clazz);
        } catch (IOException e) {
            logger.error("parseJson:: getting error while parsing kafka message exception {} data {}",e.getLocalizedMessage(),data);
            return null;
        }
    }

    public static <T> T parseJsonWithCustomMapper(String data, Class<T> clazz) {

        try {
            return OBJECT_MAPPER_CUSTOM.readValue(data, clazz);
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage());
            return null;
        }
    }

    public static <T> T parseMapJson(String data) {
        try {
            return (T) OBJECT_MAPPER.readValue(data, new TypeReference<HashMap<String, Object>>() {
            });
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage());
            return null;
        }
    }

    public static <T> T parseJsonCI(String data, Class<T> clazz) {
        try {
            return OBJECT_MAPPER_CI.readValue(data, clazz);
        } catch (IOException e) {
            return null;
        }
    }

    public static <T> Map<String, T> parseJsonToMapWithStringKey(String data, Class<T> valueClass) {
        try {
            return OBJECT_MAPPER.readValue(data, new TypeReference<Map<String, T>>() {
            });
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage());
            return null;
        }
    }

    public static <K, V> Map<K, V> parseJsonToMap(String data, Class<K> keyClass, Class<V> valueClass) {
        JavaType kt = TYPE_FACTORY.constructType(keyClass);
        JavaType vt = TYPE_FACTORY.constructType(valueClass);
        try {
            return OBJECT_MAPPER.readValue(data, MapType.construct(HashMap.class, null, null, null, kt, vt));
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage());
            return null;
        }
    }

    public static <T> List<T> parseJsonToList(String data, Class<T> elementClass) {
        try {
            return OBJECT_MAPPER.readValue(data, CollectionType.construct(ArrayList.class, null, null, null, TYPE_FACTORY.constructType(elementClass)));
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage());
            return null;
        }
    }

    public static <T> Set<T> parseJsonToSet(String data, Class<T> elementClass) {
        try {
            return OBJECT_MAPPER.readValue(data, CollectionType.construct(HashSet.class, null, null, null, TYPE_FACTORY.constructType(elementClass)));
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage());
            return null;
        }
    }

    public static <T> T convertMapToPojo(Map<String, ?> map, Class<T> clazz) {
        return OBJECT_MAPPER.convertValue(map, clazz);
    }

    public static <T> T convertMapToPojo(CaseInsensitiveMap<String, ?> map, Class<T> clazz)
            throws JsonParseException, JsonMappingException, IOException {

        return OBJECT_MAPPER_CI.convertValue(map, clazz);
    }


    public static String serialiseJson(Object data) throws JsonProcessingException {
        try {
            return OBJECT_MAPPER.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            logger.error(e.getLocalizedMessage());
            throw e;
        }
    }

    public static String jsonify(String field, String value) {
        if (value == null) {
            return "{\"" + field + "\":" + "null}";
        }
        return "{\"" + field + "\":" + "\"" + value + "\"}";
    }


    /**
     * This method would provide any client to transform json string to supplied model if they are compatible.
     *
     * @param p_json_string json string which need to transform  to some model.
     * @return Json node.
     */
    public static JsonNode fromStream(InputStream p_json_string) {
        JsonNode node = null;
        try {
            node = OBJECT_MAPPER.readTree(p_json_string);
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage());
        }
        return node;
    }

    public static JsonNode fromStringToJsonNode(String json) {
        JsonNode node = null;
        try {
            node = OBJECT_MAPPER.readTree(json);
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage());
        }
        return node;
    }

    public static <T> T fromInputStreamToJson(InputStream data, TypeReference<T> typeReference) {
        try {
            return OBJECT_MAPPER.readValue(data, typeReference);
        } catch (IOException ioe) {
            logger.error("error while parsing the InputStream", ioe);
            return null;
        }
    }

    public static <K, V> Map<K, V> convertObjectToMap(Object o) {

        if (o == null) {
            return null;
        }
        return OBJECT_MAPPER.convertValue(o, Map.class);

    }
}
