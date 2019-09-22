# processing-fee-calculator-api
Source code repo of Processing Fee Calculator

This api will provide the processing fee reports for clients which is group by clientid, transaction type, transaction date and priority flag and again sorted by same

###Setup Repo and Pre-requisite
*  import the project into STS or Intellij
*  put the Transaction input data file into some folder in file system and update the folder path in src/main/resource/application.yml and src/test/resource/application.yml file
*  Create directory "ProcessedData" in file system to move processed file into ProcessedData directory and update the location of this folder in src/main/resource/application.yml and src/test/resource/application.yml file
*  Scheduled a task which will execute in every 15 seconds which is configurable and it load file from file system, process and persist in database(H2 In memory Database)

After above configuration, Run the application as springboot app  

### API's exposed
This api expose following endpoints to get the processing fee report
* **/reports** - will generate report for all the client in csv format
* **/reports/{clientId}** - will generate report for given client id

### How to use api's
* Run the application as springboot app and wait for 15 second(configurable)
* Access url from browser "http://localhost:8080/processing-fee-calculator/v1.0/reports" to get the report in csv format for all clients
* Access url from browser "http://localhost:8080/processing-fee-calculator/v1.0/reports/AS" to get the report in csv format for given client

