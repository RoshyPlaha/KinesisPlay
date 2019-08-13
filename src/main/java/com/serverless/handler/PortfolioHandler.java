package com.serverless.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.serverless.ApiGatewayResponse;
import org.apache.log4j.Logger;

import java.util.Collections;
import java.util.Map;

public class PortfolioHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

	private final Logger logger = Logger.getLogger(this.getClass());

	@Override
	public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) { // this used to be the `process` method

        logger.info("Please keep me warm Market Axess :) ");

        return ApiGatewayResponse.builder()
                .setStatusCode(200)
                .setObjectBody("Toasty")
                .setHeaders(Collections.singletonMap("X-Powered-By", "AWS Lambda & Serverless"))
                .build();
	}
}
