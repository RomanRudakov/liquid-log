package ru.naumen.sd40.log.parser;

import java.io.IOException;
import java.text.ParseException;

import org.junit.Assert;
import org.junit.Test;

public class ActionDoneParserTest  {

    @Test
    public void mustParseAddAction() throws IOException, ParseException {
        //given
        ActionDoneParser parser = new ActionDoneParser();
        SdngData dataSet = new SdngData();

        //when
        parser.parseData(dataSet, "Done(10): AddObjectAction");

        //then
        Assert.assertEquals(1, dataSet.getActionsData().getAddObjectActions());
    }

    @Test
    public void mustParseFormActions() throws IOException, ParseException {
        //given
        ActionDoneParser parser = new ActionDoneParser();
        SdngData dataSet = new SdngData();

        //when
        parser.parseData(dataSet, "Done(10): GetFormAction");
        parser.parseData(dataSet, "Done(1): GetAddFormContextDataAction");

        //then
        Assert.assertEquals(2, dataSet.getActionsData().getFormActions());
    }
    
    @Test
    public void mustParseEditObject() throws IOException, ParseException {
        //given
        ActionDoneParser parser=  new ActionDoneParser();
        SdngData dataSet = new SdngData();

        //when
        parser.parseData(dataSet, "Done(10): EditObjectAction");

        //then
        Assert.assertEquals(1, dataSet.getActionsData().getEditObjectsActions());
    }

    @Test
    public void mustParseSearchObject() throws IOException, ParseException{
        //given
        ActionDoneParser parser = new ActionDoneParser();
        SdngData dataSet = new SdngData();

        //when
        parser.parseData(dataSet, "Done(10): GetPossibleAgreementsChildsSearchAction");
        parser.parseData(dataSet,"Done(10): TreeSearchAction");
        parser.parseData(dataSet,"Done(10): GetSearchResultAction");
        parser.parseData(dataSet,"Done(10): GetSimpleSearchResultsAction");
        parser.parseData(dataSet,"Done(10): SimpleSearchAction");
        parser.parseData(dataSet,"Done(10): ExtendedSearchByStringAction");
        parser.parseData(dataSet,"Done(10): ExtendedSearchByFilterAction");

        //then
        Assert.assertEquals(7, dataSet.getActionsData().getSearchActions());
    }

    @Test
    public void mustParseGetList() throws IOException, ParseException{
        //given:
        ActionDoneParser parser=  new ActionDoneParser();
        SdngData dataSet = new SdngData();

        //when:
        parser.parseData(dataSet,"Done(10): GetDtObjectListAction");
        parser.parseData(dataSet,"Done(10): GetPossibleCaseListValueAction");
        parser.parseData(dataSet,"Done(10): GetPossibleAgreementsTreeListActions");
        parser.parseData(dataSet,"Done(10): GetCountForObjectListAction");
        parser.parseData(dataSet,"Done(10): GetDataForObjectListAction");
        parser.parseData(dataSet,"Done(10): GetPossibleAgreementsListActions");
        parser.parseData(dataSet,"Done(10): GetDtObjectForRelObjListAction");

        //then:
        Assert.assertEquals(7, dataSet.getActionsData().geListActions());
    }

    @Test
    public void mustParseComment() throws IOException, ParseException{
        //given:
        ActionDoneParser parser=  new ActionDoneParser();
        SdngData dataSet = new SdngData();

        //when:
        parser.parseData(dataSet,"Done(10): EditCommentAction");
        parser.parseData(dataSet,"Done(10): ChangeResponsibleWithAddCommentAction");
        parser.parseData(dataSet,"Done(10): ShowMoreCommentAttrsAction");
        parser.parseData(dataSet,"Done(10): CheckObjectsExceedsCommentsAmountAction");
        parser.parseData(dataSet,"Done(10): GetAddCommentPermissionAction");
        parser.parseData(dataSet,"Done(10): GetCommentDtObjectTemplateAction");

        //then:
        Assert.assertEquals(6, dataSet.getActionsData().getCommentActions());
    }

    @Test
    public void mustParseDtObject() throws IOException, ParseException{
        //given:
        ActionDoneParser parser=  new ActionDoneParser();
        SdngData dataSet = new SdngData();

        //when:
        parser.parseData(dataSet,"Done(10): GetVisibleDtObjectAction");
        parser.parseData(dataSet,"Done(10): GetDtObjectsAction");
        parser.parseData(dataSet,"Done(10): GetDtObjectTreeSelectionStateAction");
        parser.parseData(dataSet,"Done(10): AbstractGetDtObjectTemplateAction");
        parser.parseData(dataSet,"Done(10): GetDtObjectTemplateAction");

        //then:
        Assert.assertEquals(5, dataSet.getActionsData().getDtObjectActions());
    }

}
