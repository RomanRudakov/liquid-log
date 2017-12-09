package ru.naumen.sd40.log.parser;

public class SdngData implements DataSet {

	private ActionDoneData action;
	private ErrorData error;
	
	public SdngData()
	{
		action = new ActionDoneData();
		error = new ErrorData();
	}
	
	public ActionDoneData getActionsData()
	{
		return action;
	}
	
	public ErrorData getErrors()
	{
		return error;
	}
}
