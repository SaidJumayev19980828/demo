package com.era.tofate.service.aboutapp;

import com.era.tofate.ToFateApplication;
import com.era.tofate.entities.aboutapp.AboutApp;
import com.era.tofate.repository.aboutapp.AboutAppRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@ContextConfiguration(classes = ToFateApplication.class)
class AboutAppServiceImplTest {
    @Autowired
    private AboutAppService service;

    @Mock
    private AboutAppRepository repository;

    @Test
    void getOne() {
        AboutApp mockAbout = new AboutApp();
        mockAbout.setId(1L);
        mockAbout.setAboutAppText("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
        AboutApp aboutApp = service.getOne(1L);
        when(repository.getOne(1L)).thenReturn(mockAbout);
        assertEquals(aboutApp.getAboutAppText(), mockAbout.getAboutAppText());
    }
}
