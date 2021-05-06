package com.era.tofate.controller.virt;

import com.era.tofate.entities.virt.Virt;
import com.era.tofate.enums.Sex;
import com.era.tofate.repository.virt.VirtRepository;
import com.era.tofate.service.user.UserService;
import com.era.tofate.service.virt.VirtService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@WebMvcTest(VirtController.class)
public class VirtControllerTest {
    @Mock
    private UserService userService;
    @Mock
    private VirtService virtService;
    @MockBean
    private VirtRepository repository;
    @Autowired
    private MockMvc mockMvc;
    @Test
    void findAllTest(){
        Page<Virt> virts = Page.empty();
        Mockito.when(virtService.findAll(0,10)).thenReturn(virts);
        assertEquals(virtService.findAll(0,10),virts);
    }
    @Test
    void findAllBySexTest(){
        Page<Virt> virts = Page.empty();
        Mockito.when(virtService.findAllBySex(Sex.MALE,0,10)).thenReturn(virts);
        assertEquals(virtService.findAllBySex(Sex.MALE,0,10),virts);
    }
}
