package com.oms.orderservice.restService;

import com.oms.orderservice.commondto.InventoryRequestDTO;
import com.oms.orderservice.commondto.InventoryResponseDTO;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpResponse;

@Service
@Slf4j
public class InventoryRestClient {

    @Autowired
    private RestTemplate restTemplate;

    String AVAILABLE_INVENTORY_CHECK_URL = "http://inventory-service:8083/inventory/check/availableInventory";

    public ResponseEntity<InventoryResponseDTO> checkInventory(InventoryRequestDTO requestDTO) {
        log.info("Outgoing Request for Inventory : {}",requestDTO.toString());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<InventoryRequestDTO> httprequestEntity = new HttpEntity<>(requestDTO, httpHeaders);
        try{
        ResponseEntity<InventoryResponseDTO> inventoryResponse = restTemplate.exchange(AVAILABLE_INVENTORY_CHECK_URL,
                HttpMethod.POST,
                httprequestEntity,
                InventoryResponseDTO.class);
            return inventoryResponse;
        }catch (RestClientException e) {
            log.error("Exception while calling Inventory Service", e);
            throw new RuntimeException("Inventory service is unavailable", e);
        }
    }
}
