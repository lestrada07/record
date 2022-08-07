package com.nisum.api.record.controller;

import com.nisum.api.record.transfer.RecordInputModel;
import com.nisum.api.record.transfer.ResponseModel;
import com.nisum.api.record.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping(value = "/", headers = "Accept=application/json")
public class RecordController {

    private static final Logger logger = LoggerFactory.getLogger(RecordController.class);

    @Autowired
    UserService userService;

    @PostMapping("recordUser")
    public ResponseEntity<?> recordUser(@RequestBody RecordInputModel request) {
        logger.info("-- RecordController.recordUser: ");
        ResponseModel response = userService.recordUser(request);
        return ResponseEntity.ok().body(response);
    }
}
