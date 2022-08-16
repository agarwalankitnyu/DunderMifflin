package com.ankit.dundermifflin;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.ankit.dundermifflin.persistence.model.Customer;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import org.apache.http.client.utils.URIBuilder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Test;

public class CustomerApiTests {

    private static final String API_ROOT = "http://localhost:8080/customer";

    @Test
    public void whenGetCustomer_thenOK() throws URISyntaxException {
        Customer customer = createRandomCustomer();
        createCustomerAsUri(customer);

        URIBuilder ub = new URIBuilder(API_ROOT + "/get/name/");
        ub.addParameter("customerName", customer.getName());
        final URI uri = ub.build();

        final Response response = RestAssured.get(uri);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        Customer customerResponse = response.as(Customer.class);
        assertEquals(customerResponse.getName(), customer.getName());
        assertEquals(customerResponse.getAddress(), customer.getAddress());
        assertEquals(customerResponse.getEmail(), customer.getEmail());
        assertEquals(customerResponse.getContactNumber(), customer.getContactNumber());
    }

    @Test
    public void whenGetCreatedCusotmerById_thenOK() throws URISyntaxException {
        Customer customer = createRandomCustomer();
        Long id = createCustomerAsUri(customer);

        URIBuilder ub = new URIBuilder(API_ROOT + "/get/id/");
        ub.addParameter("id", id.toString());
        final URI uri = ub.build();

        final Response response = RestAssured.get(uri);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        Customer customerResponse = response.as(Customer.class);
        assertEquals(customerResponse.getName(), customer.getName());
        assertEquals(customerResponse.getAddress(), customer.getAddress());
        assertEquals(customerResponse.getEmail(), customer.getEmail());
        assertEquals(customerResponse.getContactNumber(), customer.getContactNumber());
    }

    @Test
    public void whenGetNotExistCustomerById_thenNotFound() throws URISyntaxException {
        URIBuilder ub = new URIBuilder(API_ROOT + "/get/id/");
        ub.addParameter("id", randomNumeric(2));
        final URI uri = ub.build();
        final Response response = RestAssured.get(uri);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
        assertEquals(response.asString(), "No Customer is found for the customer id provided.");
    }

    @Test
    public void whenGetNotExistCustomerByName_thenNotFound() throws URISyntaxException {
        URIBuilder ub = new URIBuilder(API_ROOT + "/get/name/");
        ub.addParameter("customerName", randomAlphabetic(10));
        final URI uri = ub.build();
        final Response response = RestAssured.get(uri);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
        assertEquals(response.asString(), "No Customer is found for the customer name provided.");
    }

    @Test
    public void whenCreateNewCustomer_thenBadRequest() {
        Customer customer = createRandomCustomer();
        createCustomerAsUri(customer);

        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(customer)
                .post(API_ROOT + "/create");
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
        assertEquals("Customer Already Exists with this name.", response.asString());
    }

    @Test
    public void whenCreateNewCustomerWithExistingName_thenCreated() {
        Customer customer = createRandomCustomer();

        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(customer)
                .post(API_ROOT + "/create");
        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
        Customer customerResponse = response.as(Customer.class);
        assertEquals(customerResponse.getName(), customer.getName());
        assertEquals(customerResponse.getAddress(), customer.getAddress());
        assertEquals(customerResponse.getEmail(), customer.getEmail());
        assertEquals(customerResponse.getContactNumber(), customer.getContactNumber());
        assertNotNull(customerResponse.getId());
    }

    @Test
    public void whenInvalidCustomer_thenError() {
        Customer customer = createRandomCustomer();
        customer.setName(null);

 
        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(customer)
                .post(API_ROOT + "/create");
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
    }

    @Test
    public void whenUpdateCreatedCustomer_thenUpdated() throws URISyntaxException {
        Customer customer = createRandomCustomer();
        Long id = createCustomerAsUri(customer);

        customer.setId(id);
        customer.setName(randomAlphabetic(20));
        
        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(customer)
                .put(API_ROOT + "/update");
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        URIBuilder ub = new URIBuilder(API_ROOT + "/get/id/");
        ub.addParameter("id", id.toString());
        URI uri = ub.build();

        response = RestAssured.get(uri);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertEquals(customer.getName(), response.as(Customer.class).getName());

    }

    @Test
    public void whenUpdateCustomerIdDoesNotExist_thenNotFound() throws URISyntaxException {
        Customer customer = createRandomCustomer();
        createCustomerAsUri(customer);

        customer.setId(123456);
        customer.setName("newName");
        
        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(customer)
                .put(API_ROOT + "/update");
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());

    }

    @Test
    public void whenUpdateCustomerNameExists_thenBadRequest() throws URISyntaxException {
        Customer customer = createRandomCustomer();
        Long id = createCustomerAsUri(customer);

        customer.setId(id);
        
        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(customer)
                .put(API_ROOT + "/update");
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());

    }

    @Test
    public void whenDeleteCreatedCustomer_thenOk() throws URISyntaxException {
        Customer customer = createRandomCustomer();
        Long id = createCustomerAsUri(customer);

        URIBuilder ub = new URIBuilder(API_ROOT + "/delete");
        ub.addParameter("id", id.toString());
        URI uri = ub.build();

        Response response = RestAssured.delete(uri);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        ub = new URIBuilder(API_ROOT + "/get/id/");
        ub.addParameter("id", id.toString());
        uri = ub.build();

        response = RestAssured.get(uri);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
    }
    @Test
    public void whenDeleteCustomerDoesNotExist_thenNotFound() throws URISyntaxException {

        URIBuilder ub = new URIBuilder(API_ROOT + "/delete");
        ub.addParameter("id", randomNumeric(3));
        URI uri = ub.build();

        Response response = RestAssured.delete(uri);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());

    }

    private Customer createRandomCustomer() {
        final Customer customer = new Customer();
        customer.setName(randomAlphabetic(10));
        customer.setEmail(randomAlphabetic(10) + "@" + randomAlphabetic(4) + ".com");
        customer.setAddress(randomAlphabetic(30));
        customer.setContactNumber(randomNumeric(10));
        return customer;
    }

    private long createCustomerAsUri(Customer customer) {
        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(customer)
                .post(API_ROOT + "/create");

        return response.as(Customer.class).getId();
    }

}
