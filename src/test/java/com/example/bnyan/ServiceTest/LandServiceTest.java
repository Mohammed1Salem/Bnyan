package com.example.bnyan.ServiceTest;

import com.example.bnyan.Api.ApiException;
import com.example.bnyan.Model.Customer;
import com.example.bnyan.Model.Land;
import com.example.bnyan.Repository.CustomerRepository;
import com.example.bnyan.Repository.LandRepository;
import com.example.bnyan.Service.LandService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LandServiceTest {

    @InjectMocks
    private LandService landService;

    @Mock
    private LandRepository landRepository;

    @Mock
    private CustomerRepository customerRepository;

    private Land land1, land2;
    private Customer customer;
    private List<Land> landList;

    @BeforeEach
    void setup() {
        customer = new Customer();
        customer.setId(1);

        land1 = new Land();
        land1.setId(10);
        land1.setLocation("Riyadh");
        land1.setSize("500");
        land1.setCustomer(customer);

        land2 = new Land();
        land2.setId(11);
        land2.setLocation("Jeddah");
        land2.setCustomer(customer);

        landList = new ArrayList<>();
        landList.add(land1);
        landList.add(land2);
    }

    @Test
    public void getAllLandsSuccessTest() {
        when(landRepository.findAll()).thenReturn(landList);

        List<Land> result = landService.getAllLands();

        assertEquals(2, result.size());
        verify(landRepository, times(1)).findAll();
    }

    @Test
    public void getAllLandsEmptyTest() {
        when(landRepository.findAll()).thenReturn(new ArrayList<>());

        assertThrows(ApiException.class, () -> {
            landService.getAllLands();
        });
    }

    @Test
    public void addLandSuccessTest() {
        when(customerRepository.getCustomerById(1)).thenReturn(customer);

        landService.add(1, land1);

        verify(customerRepository, times(1)).getCustomerById(1);
        verify(landRepository, times(1)).save(land1);
        assertNotNull(land1.getCreatedAt());
    }

    @Test
    public void updateLandUnauthorizedTest() {
        Land existingLand = new Land();
        Customer owner = new Customer();
        owner.setId(99);
        existingLand.setCustomer(owner);

        when(landRepository.getLandById(10)).thenReturn(existingLand);

        assertThrows(ApiException.class, () -> {
            landService.update(10, 1, land1);
        });
    }

    @Test
    public void deleteLandSuccessTest() {
        when(landRepository.getLandById(10)).thenReturn(land1);

        landService.delete(10, 1);

        verify(landRepository, times(1)).delete(land1);
    }

    @Test
    public void getLandsByCustomerSuccessTest() {
        when(customerRepository.getCustomerById(1)).thenReturn(customer);
        when(landRepository.getLandsByCustomerId(1)).thenReturn(landList);

        List<Land> result = landService.getLandsByCustomer(1);

        assertEquals(2, result.size());
        verify(landRepository, times(1)).getLandsByCustomerId(1);
    }

    @Test
    public void getLandByIdNotFoundTest() {
        when(landRepository.getLandById(99)).thenReturn(null);

        assertThrows(ApiException.class, () -> {
            landService.getLandById(99);
        });
    }
}