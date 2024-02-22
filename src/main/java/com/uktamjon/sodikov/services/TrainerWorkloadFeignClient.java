package com.uktamjon.sodikov.services;

import com.uktamjon.sodikov.dtos.TrainerWorkload;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "trainer-service", url = "http://localhost:8082")
public interface TrainerWorkloadFeignClient {

    @PostMapping("/modifyworkload")
    ResponseEntity<Void> modifyWorkload(@RequestBody TrainerWorkload trainer);
}
