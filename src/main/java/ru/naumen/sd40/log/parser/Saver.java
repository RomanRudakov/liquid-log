package ru.naumen.sd40.log.parser;

import org.influxdb.dto.BatchPoints;

import ru.naumen.perfhouse.influx.InfluxDAO;

public interface Saver<T extends DataSet> {
	
	public void save(T data, boolean trace, InfluxDAO influx, long currentKey, String influxDb, BatchPoints point);

}
