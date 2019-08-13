package com.serverless.dbentity;

public class KinesisWrapper {

    private String streamAction;

    private InquiryList inquiryList;

    private InquiryItem inquiryItem;

    public KinesisWrapper() {}

    public KinesisWrapper(InquiryItem inquiryItem, String streamAction) {
        this.streamAction = streamAction;
        this.inquiryItem = inquiryItem;
    }

    public String getStreamAction() {
        return streamAction;
    }

    public InquiryItem getInquiryItem() {
        return inquiryItem;
    }

    public InquiryList getInquiryList() {
        return inquiryList;
    }
}
