Iteration 1:

For iteration one, we completed the user stories "Experience RVA", "Restaurant Rating", and "Organized Notes." Major parts of this iteration included being able to add restaurant entries to an SQLLite database and retrieve them on the main page. We used Espresso testing in order to test the UI of our app. For many of the testing attempts, there were errors with the source pathing that prevented the tests from compiling. We revised some of the initial user stories to accomodate these issues. As a whole, we delivered on the implementation we had planned for iteration one.

Iteration 2: 

For interation two, we completed the user stories "save Time", "Sort by Price", and "Tag Restuarants". Major parts of this iteration included building additional tables within the database that interact with each other to make a linked tag system, and creating a responsive UI. At the end of the iteration we decided to implement chips to display the tags when viewing and editing restaurants. We used Espresso testing in order to test the UI of our app. Although there are still some small things we would like to add, we completed everything that we had planned for iteration two, including setting up framework for an easier iteration three. 

The Design Pattern we Implemented was the Strategy Design pattern. We created an interface called DatabaseInterface in case we wanted to use another database system other than SQLite, such as Oracle. The DatabaseInterface contains the necessary methods to create a table of restaurants, tags, and price settings. Those methods are implemented in the DatabaseHelper class which implements DatabaseInterface.
