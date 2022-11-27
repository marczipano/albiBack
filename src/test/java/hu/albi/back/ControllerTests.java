package hu.albi.back;

import hu.albi.back.model.SubletInfo;
import hu.albi.back.service.SubletService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
class ControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SubletService subletService;

    @Test
    void subletInfoList() throws Exception {
        SubletInfo subletInfo1 = new SubletInfo();
        subletInfo1.setId(1);
        subletInfo1.setAddress("1000 Teszt utca 1.");
        subletInfo1.setEmail("test@test.com");
        subletInfo1.setRooms(3);

        SubletInfo subletInfo2 = new SubletInfo();
        subletInfo2.setId(2);
        subletInfo2.setAddress("2000 Gép utca 2.");
        subletInfo2.setEmail("gep@gep.com");
        subletInfo2.setRooms(2);

        List<SubletInfo> subletInfos = new ArrayList<>();

        subletInfos.add(subletInfo1);
        subletInfos.add(subletInfo2);

        when(subletService.getSubletInfos()).thenReturn(subletInfos);

        this.mockMvc.perform(get("/sublets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].address", is(subletInfo1.getAddress())))
                .andExpect(jsonPath("$[1].email", is(subletInfo2.getEmail())));
    }

    @Test
    void subletInfoOrder() throws Exception {
        SubletInfo subletInfo1 = new SubletInfo();
        subletInfo1.setId(1);
        subletInfo1.setAddress("1000 Teszt utca 1.");
        subletInfo1.setEmail("test@test.com");
        subletInfo1.setSize(50);

        SubletInfo subletInfo2 = new SubletInfo();
        subletInfo2.setId(2);
        subletInfo2.setAddress("2000 Gép utca 2.");
        subletInfo2.setEmail("gep@gep.com");
        subletInfo2.setSize(70);

        List<SubletInfo> subletInfos = new ArrayList<>();

        int greater;
        int less;

        if(subletInfo1.getSize() > subletInfo2.getSize()){
            subletInfos.add(subletInfo1);
            subletInfos.add(subletInfo2);
            greater = subletInfo1.getSize();
            less = subletInfo2.getSize();
        }
        else{
            subletInfos.add(subletInfo2);
            subletInfos.add(subletInfo1);
            greater = subletInfo2.getSize();
            less = subletInfo1.getSize();
        }


        when(subletService.getSubletInfos("sizeDesc", "")).thenReturn(subletInfos);

        this.mockMvc.perform(get("/sublets/find?o=sizeDesc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].size", is(greater)))
                .andExpect(jsonPath("$[1].size", is(less)));
    }

    @Test
    void subletInfoSearch() throws Exception {
        SubletInfo subletInfo1 = new SubletInfo();
        subletInfo1.setId(1);
        subletInfo1.setAddress("1000 Teszt utca 1.");
        subletInfo1.setEmail("test@test.com");
        subletInfo1.setSize(50);

        SubletInfo subletInfo2 = new SubletInfo();
        subletInfo2.setId(2);
        subletInfo2.setAddress("2000 Gép utca 2.");
        subletInfo2.setEmail("gep@gep.com");
        subletInfo2.setSize(70);

        List<SubletInfo> subletInfos = new ArrayList<>();

        subletInfos.add(subletInfo2);

        when(subletService.getSubletInfos("normal", "gép")).thenReturn(subletInfos);

        this.mockMvc.perform(get("/sublets/find?a=gép"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].address", is(subletInfo2.getAddress())));
    }
}
