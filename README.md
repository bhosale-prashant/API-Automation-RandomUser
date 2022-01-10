# API-Automation-RandomUser
Sample project for Rest API Automation using RestAssured with testng.

So the task is:
1. Use https://randomuser.me/api/ and make a list of five names (first name only) from the API response.
2. Then use the following apis for each of the above five names:
* https://api.agify.io/?name=firstName
* https://api.genderize.io/?name=firstName
* https://api.nationalize.io/?name=firstName
3. In concole. print the details of each person by ascending order of age.

Dependencies required to setup and run this project are mentioned in [ProjectPath]\pom.xml file.

Once the setup is fine for you, try running the following java file as TestNG Test:
[ProjectPath]\src\test\java\com\randomuser\api\rest\scenarios\SceanriosWithApis.java


