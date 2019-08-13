package com.serverless.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.serverless.dbentity.KinesisWrapper;

import java.io.File;
import java.io.IOException;

public class Test {

    public static void main(String[] args) throws IOException {

        Test test = new Test();
        test.go();
    }

    private void go() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        File file = new File(
                getClass().getClassLoader().getResource("ExampleJsonStructures/KinesisInquiryList.json").getFile()
        );

        KinesisWrapper kinesisWrapper = objectMapper.readValue(file, KinesisWrapper.class);
        kinesisWrapper.getInquiryList();
    }
}
