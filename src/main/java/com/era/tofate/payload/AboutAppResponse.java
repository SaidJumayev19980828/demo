package com.era.tofate.payload;

import com.era.tofate.entities.aboutapp.AboutApp;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AboutAppResponse {
    private String aboutAppText;

    public AboutAppResponse(AboutApp aboutApp) {
        this.aboutAppText = aboutApp.getAboutAppText();
    }
}
