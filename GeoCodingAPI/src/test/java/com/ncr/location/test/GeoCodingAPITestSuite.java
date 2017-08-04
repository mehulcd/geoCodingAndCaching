package com.ncr.location.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.ncr.location.repository.test.LocationRepositoryTest;
import com.ncr.location.service.test.AddressServiceTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
  LocationRepositoryTest.class,
  AddressServiceTest.class
})
public class GeoCodingAPITestSuite {

}