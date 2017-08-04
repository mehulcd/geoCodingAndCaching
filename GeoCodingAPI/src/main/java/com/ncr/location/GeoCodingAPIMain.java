/**
 * 
 */
package com.ncr.location;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author mdodia
 *
 */
@SpringBootApplication
@EnableCaching
public class GeoCodingAPIMain {

	public static void main(String[] args) {
		SpringApplication.run(GeoCodingAPIMain.class, args);
	}

}
