package ru.naumen.sd40.log.parser;

import org.influxdb.dto.BatchPoints;
import org.springframework.stereotype.Component;

import ru.naumen.perfhouse.influx.InfluxDAO;

@Component
public class GCSave implements Saver<GCData> {
	
	public void save(GCData gc, boolean trace, InfluxDAO influxDAO, long currentKey, String influxDb, BatchPoints points)
	{
        if (!gc.isNan())
        {
        	influxDAO.storeGc(points, influxDb, currentKey, gc);
        }
	}

}
