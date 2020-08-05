# Charity Web App
This project was an individual assignment we were given in the first semester of 2nd year. The main goal of this assignment was to build a system to process charity donations using Spring boot allowing users to donate or create sponsors. 

# Prerequisites
* Spring Boot - Web Framework used to Build the API.
* Java - Language used
* MySQL - Database System used for persistence
* Gradle - Dependency Management Tool.

# Getting Started
These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.
   
- You must gradle build the project first which can be found on the Gradle Panel.
- To run the app, either run a jar file or just simply hit bootRun from Gradle panel.


### The HTML paths are as follows:

```sh
http://localhost:8080/ - main page

/findCharity?search= : you can see only see return list of charities after hitting a search button.

/charity/1 - search for a charity using its ID number and the page should contain Charity name, top 5 sponsor, recent sponsors.

/findCharity?search= - displays list of all charities from search result

/donateToCharity/1/0 - This should take you to a charity donation form for NSPCC

/charity/1/createSponsorForm - This should take you to Sponsor form for NSPCC Charity

/sponsorship/{furl} - find a name of sponsor with given furl name.

/donateToCharity/1/883 - This should take you to a sponsor donor page to donate for Rhonda Elphey

/charity/55555 - checking if the charity ID on the list for validation and this should return and display error 404 messages.

```


