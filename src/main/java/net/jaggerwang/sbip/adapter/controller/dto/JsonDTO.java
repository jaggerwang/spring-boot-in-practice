package net.jaggerwang.sbip.adapter.controller.dto;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class JsonDTO {
    private String code;

    private String message;

    private Map<String, Object> data;

    public JsonDTO(String code, String message, Map<String, Object> data) {
        assert data != null;

        this.code = code;
        this.message = message;
        this.data = data;
    }

    public JsonDTO(String code, String message) {
        this(code, message, new HashMap<>());
    }

    public JsonDTO(Map<String, Object> data) {
        this("ok", "", data);
    }

    public JsonDTO() {
        this("ok", "", new HashMap<>());
    }

    public JsonDTO addDataEntry(String key, Object value) {
        data.put(key, value);
        return this;
    }
}
