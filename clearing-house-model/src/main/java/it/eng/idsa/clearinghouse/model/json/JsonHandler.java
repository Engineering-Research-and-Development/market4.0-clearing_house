package it.eng.idsa.clearinghouse.model.json;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.owlike.genson.Genson;
import de.fraunhofer.iais.eis.ids.jsonld.Serializer;
import org.apache.commons.lang3.StringUtils;

import java.util.logging.Logger;

public class JsonHandler {
    private static final java.util.logging.Logger log = Logger.getLogger(JsonHandler.class.getName());
    public static final String UTF_8 = "UTF-8";

    public static String convertToJson(Object obj) throws Exception {
        return convertToJsonByJackson(obj);
    }

    public static Object convertFromJson(String json, Class clazz) throws Exception {
        return convertFromJsonByJackson(json, clazz);
    }

    public static String convertToJsonByJackson(Object obj) throws Exception {
        try {
            ObjectMapper mapper = new ObjectMapper();
            //mapper.setPropertyNamingStrategy(PropertyNamingStrategy.UPPER_CAMEL_CASE); //This property put data in upper camel case
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            log.severe(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    public static String convertToJsonByGenson(Object obj) throws Exception {
        try {
            final Genson genson = new Genson();
            return genson.serialize(obj);
        } catch (Exception e) {
            log.severe(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    public static String convertToJsonByFraunhofer(Object obj) throws Exception {
        try {
            Serializer serializer = new Serializer();
            return serializer.serialize(obj);
        } catch (Exception e) {
            log.severe(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    public static Object convertFromJsonByJackson(String json, Class clazz) throws Exception {
        try {
            if (StringUtils.isEmpty(json))
                throw new Exception("Json data is EMPTY");
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true); //This property serialize/deserialize not considering the case of fields
            return mapper.readValue(json, clazz);
        } catch (Exception e) {
            log.severe(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    public static Object convertFromJsonByGenson(String json, Class clazz) throws Exception {
        try {
            if (StringUtils.isEmpty(json))
                throw new Exception("Json data is EMPTY");
            Genson genson = new Genson();
            return genson.deserialize(json, clazz);
        } catch (Exception e) {
            log.severe(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    public static Object convertFromJsonByFraunhofer(String json, Class clazz) throws Exception {
        try {
            if (StringUtils.isEmpty(json))
                throw new Exception("Json data is EMPTY");
            Serializer serializer = new Serializer();
            return serializer.deserialize(json, clazz);
        } catch (Exception e) {
            log.severe(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

}
