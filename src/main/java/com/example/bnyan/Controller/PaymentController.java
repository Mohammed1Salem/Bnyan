package com.example.bnyan.Controller;

import com.example.bnyan.DTO.PayRequestDTO;
import com.example.bnyan.DTO.PaymentResponseDTO;
import com.example.bnyan.Model.Customer;
import com.example.bnyan.Model.Specialist;
import com.example.bnyan.Repository.CustomerRepository;
import com.example.bnyan.Repository.SpecialistRepository;
import com.example.bnyan.Service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final CustomerRepository customerRepository; // Use CustomerRepository
    private final SpecialistRepository specialistRepository;

    @PostMapping("/pay-specialist")
    public ResponseEntity<PaymentResponseDTO> paySpecialist(@RequestBody PayRequestDTO request) throws Exception {

        Customer payer = customerRepository.findById(request.getPayerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Specialist specialist = specialistRepository.findById(request.getSpecialistId())
                .orElseThrow(() -> new RuntimeException("Specialist not found"));

        PaymentResponseDTO paymentResponse = paymentService.createMarketplacePayment(
                payer,
                specialist,
                request.getAmount(),
                request.getPlatformFee()
        );

        return ResponseEntity.ok(paymentResponse);
    }

}
