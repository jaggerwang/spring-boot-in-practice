package net.jaggerwang.sbip.adapter.dao.jpa.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.jaggerwang.sbip.entity.FileBO;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

/**
 * @author Jagger Wang
 */
@Component
@Converter
public class FileMetaConverter implements AttributeConverter<FileBO.Meta, String> {
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public String convertToDatabaseColumn(FileBO.Meta attribute) {
        if (attribute == null) {
            return null;
        }

        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public FileBO.Meta convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, FileBO.Meta.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
