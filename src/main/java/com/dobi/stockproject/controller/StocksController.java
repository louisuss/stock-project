package com.dobi.stockproject.controller;

import com.dobi.stockproject.service.StocksService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequiredArgsConstructor
@Controller
public class StocksController {
    @Autowired
    private final StocksService stocksService;

    // 메인 페이지에 symbol, company name 리스트 출력
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("listStocks", stocksService.getAllStocks());
        return "index";
    }

    // symbol, company name 리스트 생성하기
    @GetMapping("/showSymbolsAndCompanyName")
    public String showSymbolsAndCompanyName() throws IOException {
        stocksService.saveStocks();

        return "redirect:/";
    }

    // symbol 해당하는 180일 동안 최대 이윤 날짜 출력
    @PostMapping("/search")
    public String searchSymbol(@RequestParam("symbol") String symbol, Model model) throws IOException {
        if (symbol.isEmpty()) {
            return "redirect:/";
        }

        model.addAttribute("stocks", stocksService.searchMaxProfit(symbol));
        return "search_result";
    }
}
