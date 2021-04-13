package com.era.tofate.payload;

import com.era.tofate.ws.enums.ActionType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeneralSocketError {
    @JsonProperty(value = "action")
    private ActionType actionType;

    @JsonProperty(value = "error")
    private String error;
}
