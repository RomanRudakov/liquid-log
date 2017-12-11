package ru.naumen.sd40.log.parser;

import org.influxdb.dto.BatchPoints;
import org.springframework.stereotype.Component;

import ru.naumen.perfhouse.influx.InfluxDAO;

@Component
public class SdngSave implements Saver<SdngData> {
	
	public void save(SdngData data, boolean trace, InfluxDAO influxDAO, long currentKey, String influxDb, BatchPoints points)
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
	}

}
