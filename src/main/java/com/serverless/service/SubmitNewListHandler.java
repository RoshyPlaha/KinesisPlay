package com.serverless.service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.serverless.dal.DynoManager;
import com.serverless.dbentity.InquiryItem;
import com.serverless.dbentity.InquiryList;
import com.serverless.dbentity.KinesisWrapper;
import com.serverless.domain.InquiryState;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.UUID;

import static com.serverless.domain.StreamActions.SubmitNewInquiry;

public class SubmitNewListHandler {  // used to extend extends InquiryEngineHandler

    DatabaseService databaseService = new DatabaseService();

    StreamService streamService = new StreamService();

    private final Logger logger = Logger.getLogger(this.getClass());

    public void Process(InquiryList list) {
        logger.info("Insider Processor for SubmitNewListHandler");

        // only support lists in the STR state
        if(list.getState().equals(InquiryState.Str.name())) {
            // assign an Id
            String listId = UUID.randomUUID().toString();
            list.setListId(listId);
            logger.info("Attempting to save list now");
            databaseService.save(list, InquiryList.INQUIRY_LIST_TABLE_NAME);
            logger.info("Saved dat list");

            // update inquiryItems with newly generated list id above
            list.getInquiries().forEach(item -> item.setListId(listId));

            //todo implement the below logic when we've set it up in sls
            // set a timer for the list timeout
//            super.setTimer(StreamActions.ListLive, list.getListId(), list.getBinTimeoutSeconds() * 1000);
//            // set a timer to do the check of the list submit complete
//            super.setTimer(StreamActions.SubmitNewListComplete, list.getListId(), 500);
//            // now place the calls to transition the inquiries over to bin onto kinesis

            pushToStream(list.getInquiries()); //super.putOntoStream(StreamActions.SubmitNewInquiry, item);

            logger.info("All done and finished for list handler");
        } else {
            throw new RuntimeException("Only Str states are supported for Submitting a new List");
        }
    }

    private void pushToStream(List<InquiryItem> inquiries) {

        int i = 0;
        for(InquiryItem item :inquiries) {
            try {
                streamService.putData(new ObjectMapper()
                        .writeValueAsString(new KinesisWrapper(item, SubmitNewInquiry.name())), i);
                i++;
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Something went wrong when putting a record onto kinesis for item: " + item);
            }
        };
    }
}
