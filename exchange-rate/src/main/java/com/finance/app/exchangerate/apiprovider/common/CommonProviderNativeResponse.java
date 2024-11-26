package com.finance.app.exchangerate.apiprovider.common;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class CommonProviderNativeResponse {
    private LocalDate date;
    private Map<String, Object> data = new HashMap<>();

    @JsonAnySetter
    public void setData(String key, Object value) {
        this.data.put(key, value);
    }
}
