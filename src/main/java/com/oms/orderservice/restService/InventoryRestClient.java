package com.oms.orderservice.restService;

import com.oms.orderservice.commondto.InventoryRequestDTO;
import com.oms.orderservice.commondto.InventoryResponseDTO;
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
    String AVAILABLE_INVENTORY_CHECK_URL = "http://localhost:8083/inventory/check/availableInventory";

    public InventoryResponseDTO checkInventory1(InventoryRequestDTO requestDTO) {
        try {
            return restTemplate.postForObject(
                    AVAILABLE_INVENTORY_CHECK_URL,
                    requestDTO,
                    InventoryResponseDTO.class
            );
        } catch (RestClientException e) {
            return null;
        }
    }

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
        }catch (RestClientException e){
            log.info("Exception while calling Check Inventory Service ", e);
            return null;
        }
    }
}
