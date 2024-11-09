package com.finance.app.apiprovider;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class CommonResponse {
    private LocalDate date;
    private Map<String, Object> data = new HashMap<>();

    @JsonAnySetter
    public void setData(String key, Object value) {
        this.data.put(key, value);
    }
}
