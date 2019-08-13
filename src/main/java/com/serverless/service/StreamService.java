package com.serverless.service;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.AmazonKinesisClientBuilder;
import com.amazonaws.services.kinesis.model.PutRecordRequest;
import com.amazonaws.services.kinesis.model.PutRecordResult;
import org.apache.log4j.Logger;

import java.nio.ByteBuffer;

public class StreamService {

    private final Logger logger = Logger.getLogger(this.getClass());

    public void putData(String itemJson, int count) {

        logger.info("Going to put data into Kinesis..." + itemJson);
        AmazonKinesis amazonKinesis = AmazonKinesisClientBuilder
                .standard().withRegion(Regions.US_EAST_1)
                .build();

        String kinesisStreamName = System.getenv("ROSH_TEST_STREAM_NAME");
        PutRecordRequest putRecordRequest = new PutRecordRequest();
        putRecordRequest.setStreamName(kinesisStreamName); //name of aws stream you created
        putRecordRequest.setPartitionKey("awesome" + "-1");
        putRecordRequest.withData(ByteBuffer.wrap(itemJson.getBytes()));

        PutRecordResult putRecordResult = amazonKinesis.putRecord(putRecordRequest);

        logger.info("put record number: " + count + " onto Kinesis " + kinesisStreamName + " and got sequence number back" + putRecordResult.getSequenceNumber());

    }
}
