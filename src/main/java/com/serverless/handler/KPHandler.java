package com.serverless.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.KinesisEvent;
import com.amazonaws.services.lambda.runtime.events.KinesisEvent.KinesisEventRecord;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.serverless.dbentity.KinesisWrapper;
import com.serverless.domain.StreamActions;
import com.serverless.service.SubmitNewInquiryHandler;
import com.serverless.service.SubmitNewListHandler;
import org.apache.log4j.Logger;

public class KPHandler implements RequestHandler<KinesisEvent, Void> {

    private ObjectMapper objectMapper = new ObjectMapper();

    private final Logger logger = Logger.getLogger(this.getClass());

    @Override
    public Void handleRequest(KinesisEvent event, Context context) {

        logger.info("Made it into the Kinesis Handler");

        for (KinesisEventRecord rec : event.getRecords()) {
            String input = new String(rec.getKinesis().getData().array());
            logger.info("New record block to be processed looks like: " + input);

            try {

                KinesisWrapper kinesisWrapper = objectMapper.readValue(input, KinesisWrapper.class); // this throws the error
                StreamActions streamAction = StreamActions.valueOf(kinesisWrapper.getStreamAction());

                logger.info("Stream action is " + streamAction.name());
                // ugly switch statement for now...
                switch (streamAction) {
                    case SubmitNewList:
                        logger.info("Matched action: SubmitNewList");
                        SubmitNewListHandler newList = new SubmitNewListHandler();
//                        InquiryList inquiryList = objectMapper.readValue(body.get("inquiryList").toString(), InquiryList.class);
                        logger.info("InquiryList has been cast to " + kinesisWrapper.getInquiryList());
                        newList.Process(kinesisWrapper.getInquiryList());
                        logger.info("Done with Stream Action: SubmitNewList");
                        break;
//                    case SubmitNewListComplete:
//                          SubmitNewListCompleteHandler completeNewList = new SubmitNewListCompleteHandler();
//                          completeNewList.Process((UUID) data);
//                          break;
                    case SubmitNewInquiry:
                        logger.info("Matched action: SubmitNewInquiry");
                        SubmitNewInquiryHandler newInq = new SubmitNewInquiryHandler();
//                        InquiryItem inquiryItem = objectMapper.readValue(body.get("inquiryItem").toString(), InquiryItem.class); // use util Convert
                        logger.info("Inquiry item has been cast to " + kinesisWrapper.getInquiryItem());
                        newInq.Process(kinesisWrapper.getInquiryItem());
                        break;
//                    case ItemBin:
//                        ItemBinHandler bin = new ItemBinHandler();
//                        bin.Process((InquiryQuote) data);
//                        break;
                    default:
                        logger.error("no state found  :/ ");
                        throw new RuntimeException("no state found  not going to process :/ ");
                }
            } catch (Exception ex) {
                logger.error("Error in saving item: " + ex);
            }
        }
        logger.info("All done processing record block");
        return null;
    }
}