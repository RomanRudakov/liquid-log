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
	
	public SaveDataParser(InfluxDAO influxDAO)
	{
		this.influxDAO = influxDAO;
	}
	
	
	
	public void connect(String nameDb, boolean trace)
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
	}
	
	
	
	public DataSet get(long key)
	{
		if (key != currentKey)
		{
			if (data != null)
			{
			 	ActionDoneData dones = data.getActionsData();
	            dones.calculate();
	            ErrorData erros = data.getErrors();
	            if (trace)
	            {
	                System.out.print(String.format("%d;%d;%f;%f;%f;%f;%f;%f;%f;%f;%d\n", currentKey, dones.getCount(),
	                        dones.getMin(), dones.getMean(), dones.getStddev(), dones.getPercent50(), dones.getPercent95(),
	                        dones.getPercent99(), dones.getPercent999(), dones.getMax(), erros.getErrorCount()));
	            }
	            if (!dones.isNan())
	            {
	            	influxDAO.storeActionsFromLog(points, influxDb, currentKey, dones, erros);
	            }

	            GCData gc = data.getGc();
	            if (!gc.isNan())
	            {
	            	influxDAO.storeGc(points, influxDb, currentKey, gc);
	            }

	            TopData cpuData = data.cpuData();
	            if (!cpuData.isNan())
	            {
	            	influxDAO.storeTop(points, influxDb, currentKey, cpuData);
	            }
			 }
	            currentKey = key;
	            data = new DataSet();
		
		}
		return data;
	}
	
	public void finalSave()
	{
		influxDAO.writeBatch(points);
	}

}
