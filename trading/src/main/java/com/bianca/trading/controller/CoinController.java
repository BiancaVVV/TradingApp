package com.bianca.trading.controller;

import com.bianca.trading.modal.Coin;
import com.bianca.trading.service.CoinService;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class CoinController {
    @Autowired
    private CoinService coinService;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping
    ResponseEntity<List<Coin>>getCoinList(@RequestParam("page") int page) throws Exception {
        List<Coin> coins= coinService.getCoinList(page);
        return new ResponseEntity<>(coins, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{coinId}/chart")
    ResponseEntity<JsonNode>getMarketChart(@PathVariable String coinId,
                                           @RequestParam("days")int days) throws Exception {
        String res= coinService.getMarketChart(coinId,days);
        JsonNode jsonNode=objectMapper.readTree(res);
        return new ResponseEntity<>(jsonNode, HttpStatus.ACCEPTED);
    }

    @GetMapping("/search")
    ResponseEntity<JsonNode> searchCoin(@RequestParam("q") String keyword) throws Exception {
        String coin=coinService.searchCoin(keyword);
        JsonNode jsonNode = objectMapper.readTree(coin);

        return ResponseEntity.ok(jsonNode);
    }

    @GetMapping("/top50")
    ResponseEntity<JsonNode> getTop50CoinByMarketCapRank() throws Exception {
        String coin=coinService.getTop500CoinsByMarketCapRank();
        JsonNode jsonNode = objectMapper.readTree(coin);

        return ResponseEntity.ok(jsonNode);
    }

    @GetMapping("/trading")
    ResponseEntity<JsonNode> getTradingCoin() throws Exception {
        String coin=coinService.GetTradingCoins();
        JsonNode jsonNode = objectMapper.readTree(coin);

        return ResponseEntity.ok(jsonNode);
    }

    @GetMapping("/details/{coinId}")
    ResponseEntity<JsonNode> getCoinDetails(@PathVariable String coinId) throws Exception {
        String coin=coinService.getCoinDetails(coinId);
        JsonNode jsonNode = objectMapper.readTree(coin);

        return ResponseEntity.ok(jsonNode);
    }
}
