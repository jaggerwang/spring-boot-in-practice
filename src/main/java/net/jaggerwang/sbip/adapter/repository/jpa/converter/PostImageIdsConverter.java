package net.jaggerwang.sbip.adapter.repository.jpa.converter;

import java.util.Arrays;
import java.util.List;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

@Converter
public class PostImageIdsConverter implements AttributeConverter<List<Long>, String> {
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public String convertToDatabaseColumn(List<Long> attribute) {
        if (attribute == null) {
            return null;
        }

        try {
            var json = objectMapper.writeValueAsString(attribute);
            return json;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Long> convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return List.of();
        }

        try {
            var meta = objectMapper.readValue(dbData, Long[].class);
            return Arrays.asList(meta);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
