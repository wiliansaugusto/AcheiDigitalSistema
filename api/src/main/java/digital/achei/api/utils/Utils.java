package digital.achei.api.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Utils {

    public String createSimpleJson(Object object){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
           return  objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }
}
