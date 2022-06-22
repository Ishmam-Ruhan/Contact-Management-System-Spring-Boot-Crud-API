package com.ishmamruhan.DislAssignmentOne.ExternalAPI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ZipInfoAPI {

    /*
    *   - Practice Purpose -
    */


    @Autowired
    private RestTemplate restTemplate;

    public static final Logger logger = LoggerFactory.getLogger(ZipInfoAPI.class);

    public Object getData(long zipCode){
        logger.info("Rest Template: {}",restTemplate);

       String zipInfoAPI  = "https://app.zipcodebase.com/api/v1/search?codes="+zipCode+"&country=BD";


        HttpHeaders headers =new HttpHeaders();
        headers.set("apikey","c9607010-f054-11ec-9d56-3b879f23918c");

        ResponseEntity<Object> response =
            restTemplate.exchange(zipInfoAPI, HttpMethod.GET, new HttpEntity<>(headers),Object.class);

        System.out.println("Response Code: "+response.getStatusCode());

        Object ob = response.getBody();



        return response.getBody();
    }


    // Request Body
    class ZipCodeData{
        private String postal_code;
        private String country_code;
        private String latitude;
        private String longitude;
        private String city;
        private String state;
        private String state_code;

        public ZipCodeData(String postal_code, String country_code, String latitude, String longitude, String city, String state, String state_code) {
            this.postal_code = postal_code;
            this.country_code = country_code;
            this.latitude = latitude;
            this.longitude = longitude;
            this.city = city;
            this.state = state;
            this.state_code = state_code;
        }

        public ZipCodeData() {
        }

        public String getPostal_code() {
            return postal_code;
        }

        public void setPostal_code(String postal_code) {
            this.postal_code = postal_code;
        }

        public String getCountry_code() {
            return country_code;
        }

        public void setCountry_code(String country_code) {
            this.country_code = country_code;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getState_code() {
            return state_code;
        }

        public void setState_code(String state_code) {
            this.state_code = state_code;
        }
    }
}






