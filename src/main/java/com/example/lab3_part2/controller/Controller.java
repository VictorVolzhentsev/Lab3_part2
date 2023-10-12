package com.example.lab3_part2.controller;

import com.example.lab3_part2.model.Request;
import com.example.lab3_part2.model.Response;
import com.example.lab3_part2.service.ModifyRequestService;
import com.example.lab3_part2.service.ModifyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class Controller {
    private final ModifyService ModifyService;
    private final ModifyRequestService ModifyRequestService;

    @Autowired
    public Controller(@Qualifier("ModifySystemTime") ModifyService ModifyService,
                      ModifyRequestService ModifyRequestService){
        this.ModifyService = ModifyService;
        this.ModifyRequestService = ModifyRequestService;
    }


    @PostMapping(value = "/feedback")
    public ResponseEntity<Response> feedback(@RequestBody Request request) {


        log.warn("входящий реквест: " + String.valueOf(request));
        Response response = Response.builder()
                .uid(request.getUid())
                .operationUid(request.getOperationUid())
                .systemTime(request.getSystemTime())
                .code("success")
                .errorCode("0")
                .errorMessage("None")
                .build();

        ModifyRequestService.modifyRq(request);

        Response responseAfterModify = ModifyService.modify(response);
        log.warn("исходящий реквест: " + String.valueOf(response));

        return new ResponseEntity<>(responseAfterModify, HttpStatus.OK);
    }
}
