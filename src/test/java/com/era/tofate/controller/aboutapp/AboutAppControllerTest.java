//package com.era.tofate.controller.aboutapp;
//
//import com.era.tofate.ToFateApplication;
//import com.era.tofate.controller.AboutAppController;
//import com.era.tofate.entities.aboutapp.AboutApp;
//import com.era.tofate.service.aboutapp.AboutAppService;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.context.ContextConfiguration;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@SpringBootTest
//@ContextConfiguration(classes = ToFateApplication.class)
//class AboutAppControllerTest {
//    @Mock
//    private AboutAppService service;
//
//    @InjectMocks
//    AboutAppController controller;
//
//    @Test
//    void getAboutApp() {
////        AboutApp mockAbout = new AboutApp();
////        mockAbout.setId(1L);
////        mockAbout.setAboutAppText("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
//
//        ResponseEntity<?> mock = controller.getAboutApp();
//
//        AboutApp aboutApp = service.getOne(1L);
//        when(service.findById(1L)).thenReturn(mock);
//        //verify(service).findById(1L);
//        assertEquals(aboutApp.getAboutAppText(), mock.getAboutAppText());
//    }
//}
