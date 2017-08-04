/**
 * 
 */
package com.ncr.location.repository.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import com.ncr.location.model.GeoLocationResponse;
import com.ncr.location.model.GoogleGeoCodeResponse;
import com.ncr.location.repository.LocationRepository;

/**
 * @author mdodia
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LocationRepositoryTest {
 
    @Autowired
    private LocationRepository locationRepositoryImpl;
 
    @Test
    public void testGetLocationDetails() {
    	
    	GeoLocationResponse response = new GeoLocationResponse();
    	response.setLatitude("33.969601");
    	response.setLongitude("-84.100033");
    	
    	GoogleGeoCodeResponse testResponse = locationRepositoryImpl.getLocationDetails(response);
    	Assert.notNull(testResponse, "Response sent from GeoCoding service is null");
    }
    
    @Test
    public void testGetCachedLocations1() {
      	
    	List<GeoLocationResponse> cachedList = locationRepositoryImpl.getCachedLocations();
    	Assert.notNull(cachedList, "Response sent from caching service is null");
    	Assert.isTrue(cachedList.isEmpty(), "Cache should have been empty");
    }
	
}
