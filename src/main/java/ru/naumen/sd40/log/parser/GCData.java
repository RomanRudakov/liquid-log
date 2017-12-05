package ru.naumen.sd40.log.parser;

import static ru.naumen.sd40.log.parser.NumberUtils.getSafeDouble;
import static ru.naumen.sd40.log.parser.NumberUtils.roundToTwoPlaces;


import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

public class GCData {
	
	private DescriptiveStatistics ds = new DescriptiveStatistics();


    public double getCalculatedAvg()
    {
        return roundToTwoPlaces(getSafeDouble(ds.getMean()));
    }

    public long getGcTimes()
    {
        return ds.getN();
    }

    public double getMaxGcTime()
    {
        return roundToTwoPlaces(getSafeDouble(ds.getMax()));
    }

    public boolean isNan()
    {
        return getGcTimes() == 0;
    }
    
    public DescriptiveStatistics getDS()
    {
    	return ds;
    }

}
