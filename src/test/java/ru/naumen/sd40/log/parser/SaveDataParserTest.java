package ru.naumen.sd40.log.parser;

import java.io.IOException;
import java.text.ParseException;

import org.influxdb.dto.BatchPoints;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import org.junit.Assert;
import ru.naumen.perfhouse.influx.InfluxDAO;

public class SaveDataParserTest {
	private InfluxDAO influxDAOMock;
	private SaveDataParser saveData;
	private BatchPoints batchPoints;
	
	@Before
	public void initSaveDataParserTest()
	{
		influxDAOMock = Mockito.mock(InfluxDAO.class);
		batchPoints = BatchPoints.database("test").build();
		Mockito.when(influxDAOMock.startBatchPoints("test")).thenReturn(batchPoints);
		saveData = new SaveDataParser(influxDAOMock);
		saveData.connect("test", false);
	}
	
	@Test
	public void returnEqualsSetTest()
	{
		//given
		
		//when
		DataSet firstSet = saveData.get(1);
		DataSet secondSet = saveData.get(1);
		
		//then
		Assert.assertEquals(firstSet, secondSet);
	}
	
	@Test
	public void returnNotEqualsSetTest()
	{
		//given
		
		//when
		DataSet firstSet = saveData.get(1);
		DataSet secondSet = saveData.get(2);
		
		//then
		Assert.assertNotEquals(firstSet, secondSet);
	}
	
	@Test
	public void mustSaveTest()
	{
		//given
		
		//when
		saveData.finalSave();
		
		//then
		Mockito.verify(influxDAOMock).writeBatch(batchPoints);
	}
	
	@Test
	public void saveActionErrorTest() throws IOException, ParseException
	{
		//given
		String line = "Done(10): AddObjectAction";
		String error = "16088 [localhost-startStop-1 - -] (07 сен 2017 04:58:22,723) ERROR pool.PoolBase - dbConnectionPool - You cannot use the same pool name for separate pool instances./n";
		DataParser dataParser = new ComplexParser(new ErrorParser(), new ActionDoneParser());
		
		//when
		DataSet firstSet = saveData.get(1);
		dataParser.parseData(firstSet, error);
		dataParser.parseData(firstSet, line);
		saveData.get(2);
		
		//then
		Mockito.verify(influxDAOMock).storeActionsFromLog(batchPoints, "test", 1, firstSet.getActionsData(), firstSet.getErrors());
		
	}
	
	@Test
	public void saveGCTest() throws IOException, ParseException
	{
		//given
		String line = "2017-11-03T10:41:03.724+0000: 6.549: [GC (Allocation Failure) [PSYoungGen: 655360K->58520K(764416K)] 655360K->58592K(2512384K), 0.0612895 secs] [Times: user=0.08 sys=0.01, real=0.06 secs]/n";
		DataParser dataParser = new GCParser();
		
		//when
		DataSet firstSet = saveData.get(1);
		dataParser.parseData(firstSet, line);
		saveData.get(2);
		
		//then
		Mockito.verify(influxDAOMock).storeGc(batchPoints, "test", 1, firstSet.getGc());
		
	}
	
	@Test
	public void saveTopTest() throws IOException, ParseException
	{
		//given
		String line = "18321 adminis+  20   0 4472716 3.214g      0  24476 S   0.0 83.3 281:56.26 java";
		DataParser dataParser = new TopParser();
		
		//when
		DataSet firstSet = saveData.get(1);
		dataParser.parseData(firstSet, line);
		saveData.get(2);
		
		//then
		Mockito.verify(influxDAOMock).storeTop(batchPoints, "test", 1, firstSet.cpuData());
	}

}
