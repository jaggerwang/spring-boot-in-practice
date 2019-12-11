package net.jaggerwang.sbip.adapter.repository.jpa.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import net.jaggerwang.sbip.entity.FileEntity;

@Converter
public class FileMetaConverter implements AttributeConverter<FileEntity.Meta, String> {
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public String convertToDatabaseColumn(FileEntity.Meta attribute) {
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
    public FileEntity.Meta convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, FileEntity.Meta.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
