package com.finance.app.auth.boundary.request;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record TelegramRequest(@JsonAlias("auth_date") Long authDate,
                              String hash,
                              @JsonAlias("query_id") String queryId,
                              Map<String, String> user) {
}
