/**
 * 
 */
package com.ncr.location.repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.ncr.location.model.GeoLocationResponse;
import com.ncr.location.model.GoogleGeoCodeResponse;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

/**
 * @author mdodia
 *
 */
@Component
public class LocationRepositoryImpl implements LocationRepository {

	@Value("${google.geocode.key}")
	private String googleGeoCodeKey;

	@Value("${google.geocode.url}")
	private String googleGeoCodeURI;
	
	@Autowired
	private CacheManager cacheManager;

	@Override
	public GoogleGeoCodeResponse getLocationDetails(GeoLocationResponse location) {

		String coordinates = location.getLatitude() + "," + location.getLongitude();
		MultiValueMap<String, String> uriParams = new LinkedMultiValueMap<String, String>();
		uriParams.add("latlng", coordinates);
		uriParams.add("key", googleGeoCodeKey);

		UriComponentsBuilder uri = UriComponentsBuilder.fromUriString(googleGeoCodeURI).queryParams(uriParams);
		RestTemplate restTemplate = new RestTemplate();
		GoogleGeoCodeResponse response = restTemplate.getForObject(uri.toUriString(), GoogleGeoCodeResponse.class);

		return response;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GeoLocationResponse> getCachedLocations() {

		Ehcache cache = (Ehcache) cacheManager.getCache("location").getNativeCache();
		List<GeoLocationResponse> list = new ArrayList<GeoLocationResponse>();
		Iterator<String> iterator = cache.getKeys().iterator();
		while (iterator.hasNext()) {
			Element element = cache.get(iterator.next());
			GeoLocationResponse response = (GeoLocationResponse) element.getObjectValue();
			list.add(response);
		}
		return list;
	}

}
