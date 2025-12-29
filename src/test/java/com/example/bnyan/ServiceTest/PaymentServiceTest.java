package com.example.bnyan.ServiceTest;

import com.example.bnyan.DTO.*;
import com.example.bnyan.Service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private PaymentService paymentService;

    private PaymentRequestDTO requestDTO;
    private CardDTO cardDTO;

    @BeforeEach
    void setup() {
        cardDTO = new CardDTO();
        cardDTO.setName("Test User");
        cardDTO.setNumber("4111111111111111");
        cardDTO.setCvc("123");
        cardDTO.setMonth("12");
        cardDTO.setYear("2025");

        requestDTO = new PaymentRequestDTO();
        requestDTO.setAmount(100);
        requestDTO.setDescription("Test Order");
        requestDTO.setCallbackUrl("https://example.com/callback");
        requestDTO.setSource(cardDTO);
    }

    @Test
    public void createPayment_Success() throws Exception {
        String mockResponseBody = "{\"id\": \"pay_123\", \"source\": {\"transaction_url\": \"https://test.url\"}}";
        ResponseEntity<String> responseEntity = new ResponseEntity<>(mockResponseBody, HttpStatus.OK);

        when(restTemplate.exchange(anyString(), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class)))
                .thenReturn(responseEntity);


        PaymentResultDTO result = paymentService.createPayment(requestDTO);
        assertNotNull(result);
        assertTrue(result.isSuccess());
        assertEquals("pay_123", result.getPaymentId());

        verify(restTemplate, times(1)).exchange(anyString(), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class));
    }

    @Test
    public void getPaymentStatus_Failure() {
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(String.class)))
                .thenThrow(new RuntimeException("Connection Timeout"));

        PaymentStatusDTO result = paymentService.getPaymentStatus("pay_123");

        assertEquals("error", result.getStatus());
        assertTrue(result.getDescription().contains("Connection Timeout"));
    }
}