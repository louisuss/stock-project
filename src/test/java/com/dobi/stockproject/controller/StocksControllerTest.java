package com.dobi.stockproject.controller;

import com.dobi.stockproject.domain.StocksRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class StocksControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    StocksRepository stocksRepository;

    @Test
    public void index() throws Exception {
        mockMvc.perform(get("/").accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void showSymbolsAndCompanyName() throws Exception {
        mockMvc.perform(get("/showSymbolsAndCompanyName")
                .accept(MediaType.TEXT_HTML))
                .andReturn().getResponse();
    }

    @Test
    public void searchSymbol() throws Exception {
        mockMvc.perform(post("/search")
                .accept(MediaType.TEXT_HTML));
    }
}