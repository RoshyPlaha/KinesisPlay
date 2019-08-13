package com.serverless.dal;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import org.apache.log4j.Logger;

public class DynoManager {

    protected static DynamoDBAdapter db_adapter;
    protected final AmazonDynamoDB client;
    protected final DynamoDBMapper mapper;

    private Logger logger = Logger.getLogger(this.getClass());

    public DynoManager(String tableName) {
        DynamoDBMapperConfig mapperConfig = DynamoDBMapperConfig.builder()
                .withTableNameOverride(new DynamoDBMapperConfig.TableNameOverride(tableName))
                .build();
        // get the db adapter
        this.db_adapter = DynamoDBAdapter.getInstance();
        this.client = this.db_adapter.getDbClient();
        // create the mapper with config
        this.mapper = this.db_adapter.createDbMapper(mapperConfig);
    }

    public DynoManager() {
        DynamoDBMapperConfig mapperConfig = DynamoDBMapperConfig.builder().build();
        // get the db adapter
        this.db_adapter = DynamoDBAdapter.getInstance();
        this.client = this.db_adapter.getDbClient();
        // create the mapper with config
        this.mapper = this.db_adapter.createDbMapper(mapperConfig);
    }

    public DynamoDBMapper getMapper() {
        return mapper;
    }

    public AmazonDynamoDB getClient() {
        return client;
    }

    public static DynamoDBAdapter getDb_adapter() {
        return db_adapter;
    }


}
