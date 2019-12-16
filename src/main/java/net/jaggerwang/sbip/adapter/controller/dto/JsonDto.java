package net.jaggerwang.sbip.adapter.controller.dto;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class JsonDto {
    private String code;

    private String message;

    private Map<String, Object> data;

    public JsonDto(String code, String message, Map<String, Object> data) {
        assert data != null;

        this.code = code;
        this.message = message;
        this.data = data;
    }

    public JsonDto(String code, String message) {
        this(code, message, new HashMap<>());
    }

    public JsonDto(Map<String, Object> data) {
        this("ok", "", data);
    }

    public JsonDto() {
        this("ok", "", new HashMap<>());
    }

    public JsonDto addDataEntry(String key, Object value) {
        data.put(key, value);
        return this;
    }
}
