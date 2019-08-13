package com.serverless.service;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.serverless.dal.DynoManager;
import com.serverless.dbentity.InquiryItem;
import com.serverless.dbentity.InquiryList;
import org.apache.log4j.Logger;

public class DatabaseService {

    private final Logger logger = Logger.getLogger(this.getClass());

    public void update(UpdateItemSpec updateItemSpec, String tableName) {

        DynamoDB dynamoDBClient = new DynamoDB(new DynoManager().getClient());
        Table table = dynamoDBClient.getTable(tableName);

        try {
            logger.info("Updating item for table: " + tableName);
            UpdateItemOutcome outcome = table.updateItem(updateItemSpec);
            logger.info("Update item succeeded:\n" + outcome.getItem().toJSONPretty());

        }
        catch (Exception e) {
            logger.error("Failed to update the item");
            throw new RuntimeException("Item could not be found to be updated for table: " + tableName);
        }
    }

    public void save(InquiryItem item, String tableName) {
        new DynoManager(tableName).getMapper().save(item); // was previously super.saveInquiryItem(item);
    }

    public void save(InquiryList list, String tableName) {
        new DynoManager(tableName).getMapper().save(list); // was previously super.saveList(list);
    }
}
