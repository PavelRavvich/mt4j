package pro.laplacelab.mt4j;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import lombok.SneakyThrows;

public class JsonMapper {

    private final ObjectMapper mapper = new ObjectMapper();

    public JsonMapper() {
        mapper.registerModule(new Jdk8Module());
        mapper.registerModule(new JavaTimeModule());
        mapper.registerModule(new ParameterNamesModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    }

    @SneakyThrows
    public String toJson(final Object obj) {
        return mapper.writer().withDefaultPrettyPrinter().writeValueAsString(obj);
    }

    @SneakyThrows
    public <T> T readValue(final String json, final Class<?> clazz) {
        @SuppressWarnings("unchecked")
        final T obj = (T) mapper.readValue(json, clazz);
        return obj;
    }

}
