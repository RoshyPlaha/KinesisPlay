package com.serverless.service;

import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import com.serverless.dbentity.InquiryItem;
import com.serverless.dbentity.InquiryList;
import com.serverless.domain.InquiryState;
import org.apache.log4j.Logger;

import java.util.UUID;

public class SubmitNewInquiryHandler { // used to extend extends InquiryEngineHandler

    DatabaseService databaseService = new DatabaseService();

    private final Logger logger = Logger.getLogger(this.getClass());

    public void Process(InquiryItem item) {
        logger.info("Insider Processor for SubmitNewInquiryHandler");
        // only support item in the STR state
        if(item.getState().equals(InquiryState.Str.name())) {
            logger.info("State is STR");
            // assign an Id and move it to Bin
            item.setInquiryId(UUID.randomUUID().toString()); //
            item.setState(InquiryState.Bin.name());
            logger.info("try save yourself ");
            databaseService.save(item, InquiryItem.INQUIRY_ITEM_TABLE_NAME); // was previously super.saveInquiryItem(item);
            logger.info("done saving item");

            //todo roshy / rob do business logic here
            businessLogic();

            incrementListProcessedCount(item.getListId(), InquiryList.INQUIRY_LIST_TABLE_NAME);
        }
    }

    private void businessLogic() {
    }

    // list.setItemProcessedCount(list.getItemProcessedCount() + 1)
    private void incrementListProcessedCount(String listId, String inquiryListTableName) {

        UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey("listId", listId)
                .withUpdateExpression("set itemProcessedCount = itemProcessedCount + :r")
                .withValueMap(new ValueMap().withNumber(":r", 1))
                .withReturnValues(ReturnValue.UPDATED_NEW);

        logger.info("Preparing to update InquiryList listId (ensure list exists in db!): " + listId);
        databaseService.update(updateItemSpec, inquiryListTableName);
        logger.info("All done");
    }

}
