package com.rosalind.framework.base;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.*;

public class RestUnitTest {
	
	private static Logger LOG = LoggerFactory.getLogger(RestUnitTest.class);
	
	@BeforeClass
    public static void setup(){
		LOG.info("before class");
	}
	
	
	@Before
    public void setupForMethod(){
		LOG.info("setupForMethod");
	}
	
	@Test
	public void testThis(){
		assertThat(true).isEqualTo(true);
	}
	
	@Test
	public void testThat(){
		assertThat(true).isEqualTo(true);
	}
}
