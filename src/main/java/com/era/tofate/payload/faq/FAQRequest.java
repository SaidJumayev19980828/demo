package com.era.tofate.payload.faq;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class FAQRequest {
    @ApiModelProperty(notes="id of faq")
    private Long id;
    @ApiModelProperty(notes="faq answer")
    private String answer;
    @ApiModelProperty(notes="faq question")
    private String question;

    public FAQRequest(Long id, String answer, String question) {
        this.id = id;
        this.answer = answer;
        this.question = question;
    }
}
