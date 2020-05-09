# recruiting-bff
## Prerequisites
- JDK 11 (e.g AdoptOpenJDK)
## How to build and run
- To build an executable jar run:
`$ ./gradlew clean build`
- To run the application run:
`$ ./gradlew bootRun`
## Sample requests to API
- Create job offer `$ curl -XPOST -H "Content-type: application/json" http://localhost:8080/offers -d '{"jobTitle": "DevOps", "startDate": "2019-11-11T00:00:00Z"}'`
- Get list of offers `$ curl -XGET -H "Content-type: application/json" http://localhost:8080/offers`
- Get offer by id: `$ curl -XGET -H "Content-type: application/json" http://localhost:8080/offers/[OFFER_ID]`
- Apply for job offer: `$ curl -XPOST -H "Content-type: application/json" http://localhost:8080/offers/[OFFER_ID]/applications -d '{"candidateEmail": "test@test.org", "resumeText": "my resume"}'`