package com.example.waiterservice.swagger_client.api;

import com.example.waiterservice.swagger_client.invoker.ApiClient;

import com.example.waiterservice.model.CoffeeOrder;
import com.example.waiterservice.model.OrderRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2019-06-25T20:53:40.634+08:00")
@Component("com.example.waiterservice.swagger_client.api.CoffeeOrderControllerApi")
public class CoffeeOrderControllerApi {
    private ApiClient apiClient;

    public CoffeeOrderControllerApi() {
        this(new ApiClient());
    }

    @Autowired
    public CoffeeOrderControllerApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * createOrder
     * 
     * <p><b>201</b> - Created
     * @param orderRequest orderRequest
     * @return CoffeeOrder
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public CoffeeOrder createOrderUsingPOST(OrderRequest orderRequest) throws RestClientException {
        Object postBody = orderRequest;
        
        // verify the required parameter 'orderRequest' is set
        if (orderRequest == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'orderRequest' when calling createOrderUsingPOST");
        }
        
        String path = UriComponentsBuilder.fromPath("/order/").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = { 
            "*/*"
        };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "application/json"
        };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<CoffeeOrder> returnType = new ParameterizedTypeReference<CoffeeOrder>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * getOrder
     * 
     * <p><b>200</b> - OK
     * @param id id
     * @return CoffeeOrder
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public CoffeeOrder getOrderUsingGET(Long id) throws RestClientException {
        Object postBody = null;
        
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'id' when calling getOrderUsingGET");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("id", id);
        String path = UriComponentsBuilder.fromPath("/order/{id}").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = { 
            "*/*"
        };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<CoffeeOrder> returnType = new ParameterizedTypeReference<CoffeeOrder>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
