/**
 * 
 */
package com.ncr.location.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.ncr.location.model.GeoLocationResponse;
import com.ncr.location.model.GoogleGeoCodeResponse;
import com.ncr.location.repository.LocationRepository;

/**
 * @author mdodia
 *
 */
@Service
public class AddressService {

	@Value("${google.geocode.key}")
	private String googleGeoCodeKey;

	@Value("${google.geocode.url}")
	private String googleGeoCodeURI;

	@Autowired
	private LocationRepository locationRepositoryImpl;

	public GoogleGeoCodeResponse getGEOCodedAddress(GeoLocationResponse location) {

		return locationRepositoryImpl.getLocationDetails(location);
	}

	@Cacheable(cacheNames = "location", keyGenerator = "SHA1KeyGenerator")
	public GeoLocationResponse getGEOLocationResponse(GeoLocationResponse location) throws Exception {

		GoogleGeoCodeResponse response = locationRepositoryImpl.getLocationDetails(location);
		return formatResponse(response);
	}

	public List<GeoLocationResponse> getCachedEntries() {

		List<GeoLocationResponse> cachedEntries = locationRepositoryImpl.getCachedLocations();
		Collections.sort(cachedEntries, new Comparator<GeoLocationResponse>() {

			@Override
			public int compare(GeoLocationResponse resp1, GeoLocationResponse resp2) {
				return resp2.getTimestamp().compareTo(resp1.getTimestamp());
			}
		});
		return cachedEntries;
	}

	private GeoLocationResponse formatResponse(GoogleGeoCodeResponse response) throws Exception {

		GeoLocationResponse formattedResponse = null;

		if (null != response) {

			if ("OK".equals(response.status)) {
				for (GoogleGeoCodeResponse.Results result : response.results) {

					if (null != result.geometry) {

						if ("ROOFTOP".equals(result.geometry.location_type)) {
							formattedResponse = new GeoLocationResponse();
							formattedResponse.setLatitude(result.geometry.location.lat);
							formattedResponse.setLongitude(result.geometry.location.lng);
							formattedResponse.setAddress(result.formatted_address);
							formattedResponse.setLookUpTimeStamp(new java.util.Date());
							formattedResponse.setMatchType("EXACT");
							formattedResponse.setTimestamp(new java.util.Date());
							formattedResponse.setAddressType(result.types);
							break;
						}
					}
				}

				if (null == formattedResponse) {
					for (GoogleGeoCodeResponse.Results result : response.results) {
						if (null != result.geometry) {

							formattedResponse = new GeoLocationResponse();
							formattedResponse.setLatitude(result.geometry.location.lat);
							formattedResponse.setLongitude(result.geometry.location.lng);
							formattedResponse.setAddress(result.formatted_address);
							formattedResponse.setLookUpTimeStamp(new java.util.Date());
							formattedResponse.setMatchType("APPROXIMATE");
							formattedResponse.setTimestamp(new java.util.Date());
							formattedResponse.setAddressType(result.types);
							break;
						}
					}
				} else {
					// TO DO
				}
			} else {
				throw new Exception("Error from Google Geo-Coding service - " +response.status);
			}
		} else {
			// TO DO
		}

		return formattedResponse;
	}

}
