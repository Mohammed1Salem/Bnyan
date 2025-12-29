package com.example.bnyan.Controller;

import com.example.bnyan.DTO.PaymentRequestDTO;
import com.example.bnyan.DTO.PaymentResultDTO;
import com.example.bnyan.DTO.PaymentStatusDTO;
import com.example.bnyan.Service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/create")
    public ResponseEntity<PaymentResultDTO> create(@RequestBody PaymentRequestDTO request) {
        PaymentResultDTO result = paymentService.createPayment(request);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/status/{id}")
    public ResponseEntity<PaymentStatusDTO> status(@PathVariable String id) {
        PaymentStatusDTO status = paymentService.getPaymentStatus(id);
        return ResponseEntity.ok(status);
    }

}

