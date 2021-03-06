/**
 * 
 */
package com.ncr.location.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ncr.location.model.GeoLocationResponse;
import com.ncr.location.model.GoogleGeoCodeResponse;
import com.ncr.location.service.AddressService;

/**
 * @author mdodia
 *
 */
@RestController
@RequestMapping("/GeoCodingAPI")
public class AddressController {
	
	@Autowired
	private AddressService addressService;

	@RequestMapping(value = "/address", 
			method = RequestMethod.GET)
	public GeoLocationResponse getLocation(@RequestParam("latitude") String latitude, 
			@RequestParam("longitude") String longitude) throws Exception {
		
		GeoLocationResponse location = new GeoLocationResponse();
		location.setLatitude(latitude);
		location.setLongitude(longitude);
		return addressService.getGEOLocationResponse(location);
	}
	
	@RequestMapping(value = "/cachedEntries", 
			method = RequestMethod.GET)
	public List<GeoLocationResponse> getCachedEntries() {
		
		return addressService.getCachedEntries();
	}
	
	@RequestMapping(value = "/geoAddress", 
			method = RequestMethod.GET)
	public GoogleGeoCodeResponse getGEOLocation(@RequestParam("latitude") String latitude, @RequestParam("longitude") String longitude) {
		
		GeoLocationResponse location = new GeoLocationResponse();
		location.setLatitude(latitude);
		location.setLongitude(longitude);
		return addressService.getGEOCodedAddress(location);
	}

}
