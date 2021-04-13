package com.era.tofate.payload.socket;


import com.era.tofate.ws.enums.ActionType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeneralSocketRequest<T> {
    @JsonProperty(value = "action")
    private ActionType action;

    @JsonProperty(value = "object")
    private T object;
}
