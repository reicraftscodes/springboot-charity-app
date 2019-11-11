# charity-assessment-1-starter



## Charity Giving 
   
  - Build the project by running the build.gradle file. Then navigate to the build/libs folder.
    From here you can run the jar file.
    
    The HTML paths are as follows: 
    
    http://localhost:8080/ - main page
    
    /findCharity?search= :  you can see only see return list of charities after hitting a search button.
    
    /charity/1 - search for a charity using its ID number and the page should contain Charity name, top 5 sponsor, recent sponsors. 
    
    /findCharity?search= - displays list of all charities from search result
    
    /donateToCharity/1/0 - This should take you to a charity donation form for NSPCC
    
    /charity/1/createSponsorForm - This should take you to Sponsor form for NSPCC Charity
    
    /sponsorship/{furl}  - find a name of sponsor with given furl name. 
    
    /donateToCharity/1/883 - This should take you to a sponsor donor page to donate for Rhonda Elphey
    
    /charity/55555 - checking if the charity ID on the list for validation and this should return and display error 404 messages.    