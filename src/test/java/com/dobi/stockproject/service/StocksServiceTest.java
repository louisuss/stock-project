package com.dobi.stockproject.service;

import com.dobi.stockproject.domain.Stocks;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class StocksServiceTest {
    @Autowired
    StocksService stocksService;

    @Test(expected = IllegalArgumentException.class)
    public void testSearchMaxProfit() throws IOException {
        String searchedSymbol = "abcde";
        String symbol = "ABCDE";

        searchedSymbol = searchedSymbol.toUpperCase();
        assertThat(searchedSymbol.equals(symbol));

        Stocks stock = stocksService.searchMaxProfit(searchedSymbol);
    }
}
