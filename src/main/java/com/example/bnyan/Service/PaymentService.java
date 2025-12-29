package com.example.bnyan.Service;

import com.example.bnyan.DTO.PayeeDTO;
import com.example.bnyan.DTO.PayerDTO;
import com.example.bnyan.DTO.PaymentResponseDTO;
import com.example.bnyan.Model.Customer;
import com.example.bnyan.Model.Payment;
import com.example.bnyan.Model.Specialist;
import com.example.bnyan.Repository.PaymentRepository;
import com.stripe.model.PaymentIntent;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    @Value("${stripe.api.key}")
    private String stripeKey;

    @PostConstruct
    public void init() {
        com.stripe.Stripe.apiKey = stripeKey;
    }

    public PaymentResponseDTO createMarketplacePayment(
            Customer payer,           // Customer entity
            Specialist specialist,
            int amount,
            int platformFee
    ) {
        try {
            int specialistAmount = amount - platformFee;

            // Stripe payment parameters
            Map<String, Object> params = new HashMap<>();
            params.put("amount", amount); // in halalas
            params.put("currency", "sar");
            params.put("payment_method_types", List.of("card"));
            params.put("application_fee_amount", platformFee);

            Map<String, Object> transferData = new HashMap<>();
            transferData.put("destination", specialist.getStripeAccountId());
            params.put("transfer_data", transferData);

            // Create Stripe PaymentIntent
            PaymentIntent intent = PaymentIntent.create(params);

            // Create Payment
            Payment payment = new Payment();
            payment.setStripePaymentIntentId(intent.getId());
            payment.setAmount(amount);
            payment.setPlatformFee(platformFee);
            payment.setSpecialistAmount(specialistAmount);
            payment.setStatus("PENDING");
            payment.setPayer(payer);
            payment.setPayee(specialist);
            payment.setCreatedAt(LocalDateTime.now()); // ensure time is set

            // Save Payment
            paymentRepository.save(payment);

            // Build and return DTO
            return PaymentResponseDTO.builder()
                    .paymentId(payment.getId())
                    .paymentIntentId(intent.getId())
                    .clientSecret(intent.getClientSecret())
                    .status(payment.getStatus())
                    .amount(payment.getAmount())
                    .currency("SAR")
                    .platformFee(payment.getPlatformFee())
                    .specialistAmount(payment.getSpecialistAmount())
                    .gateway("STRIPE")
                    .createdAt(payment.getCreatedAt())
                    .payer(new PayerDTO(payer.getUser().getId(), payer.getUser().getFullName()))
                    .payee(new PayeeDTO(specialist.getId(), specialist.getUser().getFullName()))
                    .build();

        } catch (Exception e) {
            // for errors
            throw new RuntimeException("Failed to create payment: " + e.getMessage(), e);
        }
    }



}
