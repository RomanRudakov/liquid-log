package ru.naumen.sd40.log.parser;

import org.influxdb.dto.BatchPoints;

import ru.naumen.perfhouse.influx.InfluxDAO;

public class SaveDataParser {
	
	private String influxDb;
	private InfluxDAO influxDAO;
	private DataSet data;
	private long currentKey;
	private boolean trace;
	private BatchPoints points;
	private Saver<DataSet> saver;
	private DataCreator dataCreator; 
	
	public SaveDataParser(InfluxDAO influxDAO)
	{
		this.influxDAO = influxDAO;
	}
	
	
	
	public void connect(String nameDb, boolean trace, Saver<DataSet> saver, DataCreator dataCreator)
	{
		influxDb = nameDb.replaceAll("-", "_");
		influxDAO.init();
        influxDAO.connectToDB(influxDb);
        this.trace = trace;
        if (trace)
        {
            System.out.print("Timestamp;Actions;Min;Mean;Stddev;50%%;95%%;99%%;99.9%%;Max;Errors\n");
        }
        
        this.points = influxDAO.startBatchPoints(influxDb);
        this.saver = saver;
        this.dataCreator = dataCreator;
	}
	
	
	
	public DataSet get(long key)
	{
		if (key != currentKey)
		{
			if (data != null)
			{
			 	saver.save(data, trace, influxDAO, currentKey, influxDb, points);
			 }
	            currentKey = key;
	            data = dataCreator.getData();
		
		}
		return data;
	}
	
	public void finalSave()
	{
		influxDAO.writeBatch(points);
	}

}
