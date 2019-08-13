## Install Pre-requisites for MA employees
node and npm and maven and java 8 and an aws account and sls


#### to install:

sls:
npm install -g serverless



#### If you can compile the app via: mvn clean install, then lets try to deploy to AWS:

sls deploy.

com.serverless.handler.Test submitting new inquiry item via: SubmitNewInquiry. Note this is using API gateway - which wont be here for long as it should be Kinesis
Note this assumes the ListId is passed in
curl -X POST https://j1obftzaf9.execute-api.us-east-1.amazonaws.com/dev/portfolioInquiry -d '{"streamAction":"SubmitNewInquiry","inquiryItem":{"inquiryId":"","state":"Str","instrumentName":"RoshInstrument","listId":"101010","quotes":[{"inquiryId":"","quoteAmount":"","dealerId":1}]}}'


curl -X POST https://j1obftzaf9.execute-api.us-east-1.amazonaws.com/dev/portfolioInquiry -d '{"streamAction":"SubmitNewList","inquiryList":{"listId":"","state":"Str","binTimeoutSeconds":1,"itemProcessedCount":1,"itemCount":1,"inquiries":[{"inquiryId":"","state":"Str","instrumentName":"RoshInstrument","listId":"101010","quotes":[{"inquiryId":"","quoteAmount":"","dealerId":1}]}]}}'


curl -X POST https://j1obftzaf9.execute-api.us-east-1.amazonaws.com/dev/portfolioInquiry -d '{"streamAction":"SubmitNewList","inquiryList":{"listId":"","state":"Str","binTimeoutSeconds":1,"itemProcessedCount":1,"itemCount":1,"inquiries":[{"inquiryId":"","state":"Str","instrumentName":"RoshInstrument","listId":"101010","quotes":[{"inquiryId":"","quoteAmount":"","dealerId":1}]},{"inquiryId":"","state":"Str","instrumentName":"RoshInstrument","listId":"202020","quotes":[{"inquiryId":"","quoteAmount":"","dealerId":1}]}]}}'

#### Once you're done, lets delete all resources
sls remove.

Kinesis help: https://www.testingexcellence.com/put-data-aws-kinesis-stream-java/

Work around to make POC work.
Once you have deployed using sls deploy, the KP Handler wont receive records. 
Navigate to AWS lambda dashboard for KP Handler and delete the kinesis trigger. Then re-add it.

