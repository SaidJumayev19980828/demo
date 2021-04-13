package com.era.tofate.payload.socket;

import com.era.tofate.ws.enums.ActionType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeneralSocketResponse<T> {
    @JsonProperty(value = "action")
    private ActionType action;

    @JsonProperty(value = "result")
    private T result;
}
