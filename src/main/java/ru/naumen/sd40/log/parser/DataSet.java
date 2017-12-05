package ru.naumen.sd40.log.parser;

/**
 * Created by doki on 22.10.16.
 */
public class DataSet
{
    private ActionDoneData actionsData;
    private ErrorData errors;
    private GCData gc;
    private TopData cpuData = new TopData();

    public DataSet()
    {
        actionsData = new ActionDoneData();
        errors = new ErrorData();
        gc = new GCData();
    }

    public ActionDoneData getActionsData()
    {
        return actionsData;
    }

    public ErrorData getErrors()
    {
        return errors;
    }

    public GCData getGc()
    {
        return gc;
    }

    public TopData cpuData()
    {
        return cpuData;
    }
}
