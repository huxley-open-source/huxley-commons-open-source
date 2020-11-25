package com.thehuxley.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Properties;

import org.junit.Test;

public class ConfiguratorTest {

	@Test
	public void testGetProperties() {
		Properties prop = Configurator.getProperties("test.properties");
		assertNotNull(prop);
		
		assertEquals("value",prop.getProperty("key"));
		
		assertNull(Configurator.getProperties("afilethatdoesnotexist.properties"));
		
		// Testing a second call
		assertEquals("value",prop.getProperty("key"));
		
		// Testing a key that does not exist
		assertNull(prop.getProperty("doesntexist"));
		
	}

}
