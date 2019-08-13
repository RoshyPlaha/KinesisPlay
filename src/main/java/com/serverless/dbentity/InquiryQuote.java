package com.serverless.dbentity;

import java.math.BigDecimal;
import java.util.UUID;

public class InquiryQuote {
    private String inquiryId;
    private BigDecimal quoteAmount;
    private int dealerId;

    public InquiryQuote() {
        inquiryId = null;
        dealerId = 0;
        quoteAmount = null;
    }

    public String getInquiryId() { return inquiryId; }
    public void setInquiryId(String inquiryId) { this.inquiryId = inquiryId; }

    public BigDecimal getQuoteAmount() { return quoteAmount; }
    public void setQuoteAmount(BigDecimal quoteAmount) { this.quoteAmount = quoteAmount; }

    public int getDealerId() { return dealerId; }
    public void setDealerId(int dealerId) { this.dealerId = dealerId; }
}
