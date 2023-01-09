package com.example.springbootrabbitmqstockprice.controllers;

import com.example.springbootrabbitmqstockprice.constants.RabbitMQConstants;
import com.example.springbootrabbitmqstockprice.dtos.StockDto;
import com.example.springbootrabbitmqstockprice.services.RabbitMQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "stock")
public class StockController {

    @Autowired
    private RabbitMQService rabbitMQService;

    @PutMapping
    private ResponseEntity updateStock(@RequestBody StockDto stockDto) {
        System.out.println(stockDto.productCode);
        this.rabbitMQService.sendMessage(RabbitMQConstants.QUEUE_STOCK, stockDto);
        return new ResponseEntity(HttpStatus.OK);
    }
}
