package com.era.tofate.controller.virt;

import com.era.tofate.ToFateApplication;
import com.era.tofate.entities.virt.Virt;
import com.era.tofate.service.user.UserService;
import com.era.tofate.service.virt.VirtService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ContextConfiguration(classes = ToFateApplication.class)
public class VirtControllerTest {
    @Mock
    private UserService userService;
    @Mock
    private VirtService virtService;
    @Test
    void findAllTest(){
        Page<Virt> virts = Page.empty();
        Mockito.when(virtService.findAll(0,10)).thenReturn(virts);
        assertEquals(virtService.findAll(0,10),virts);

    }
}
