package ru.naumen.perfhouse.influx;

import java.util.List;


import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.QueryResult;
import org.json.JSONObject;

import ru.naumen.sd40.log.parser.ActionDoneData;
import ru.naumen.sd40.log.parser.ErrorData;
import ru.naumen.sd40.log.parser.GCData;
import ru.naumen.sd40.log.parser.TopData;

public interface InfluxDAO {
	
	public void connectToDB(String dbName);
	
	public void destroy();
	
	public QueryResult.Series executeQuery(String dbName, String query);
	
	public List<String> getDbList();
	
	public void init();
	
	public BatchPoints startBatchPoints(String dbName);
	
	public void storeActionsFromLog(BatchPoints batch, String dbName, long date, ActionDoneData dones, ErrorData erros);
	public void storeFromJSon(BatchPoints batch, String dbName, JSONObject data);
	
	public void storeGc(BatchPoints batch, String dbName, long date, GCData gc);
	
	public void storeTop(BatchPoints batch, String dbName, long date, TopData data);
	
	public void writeBatch(BatchPoints batch);

}
