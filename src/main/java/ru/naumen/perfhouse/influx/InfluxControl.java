package ru.naumen.perfhouse.influx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class InfluxControl {
	
	
	public static String HOST;
	
	public static String USER;
	
	public static String PASSWORD;
	
	@Autowired
	public InfluxControl(@Value("${influx.host}") String HOST, @Value("${influx.user}") String USER, @Value("${influx.password}") String PASSWORD)
	{
		InfluxControl.HOST = HOST;
		InfluxControl.USER = USER;
		InfluxControl.PASSWORD = PASSWORD;
	}
	
}
