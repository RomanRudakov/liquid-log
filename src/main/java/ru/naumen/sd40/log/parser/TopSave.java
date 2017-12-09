package ru.naumen.sd40.log.parser;

import org.influxdb.dto.BatchPoints;
import org.springframework.stereotype.Component;

import ru.naumen.perfhouse.influx.InfluxDAO;

@Component
public class TopSave implements Saver<TopData> {
	
	public void save(TopData cpuData, boolean trace, InfluxDAO influxDAO, long currentKey, String influxDb, BatchPoints points)
	{
        if (!cpuData.isNan())
        {
        	influxDAO.storeTop(points, influxDb, currentKey, cpuData);
        }
	}

}
