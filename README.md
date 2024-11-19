Choose one of your previous labs.
Choose Two Design Patterns.
Design and implement improvements to your prior lab submission based on your chosen Design patterns.
Provide the first Commit of this project with the commit message: "Start implementing design patterns for Lab Four"
 
In the Readme Document and/or text submission for this project:
Name the two patterns you wish to implement and explain what advantages you hope to achieve with them.
Describe how you implement the patterns, including additional interfaces, and classes, and how these will integrate with the classes that you already have.
Implement the changes.
Include a link to your repository.


1. Observer Pattern : 
   Some advantages to this would be that multiple panels can observe changes in the TablePanel, hoping for    
   more flexibility and easier maintenance.
   I implemented these patterns by adding an observer interface that uses TablePanel as the subject and once TablePanel is changed(filters) then the observer is going to notify and update the ChartPanel and StatsPanel to match with the Table Panel
   For this I implemented the tableObserver interface, then I made the StatsPanel and ChartPanel implement TableObserver. Then modified TablePanel to addObserver, notifyObservers, and updateTableData. Then in my main class, I added tablePanel.addObserver(statsPanel or chartPanel) which changes my stats and chart panel when the table panel is changed. 
2. Strategy Pattern : 
   This helps me define my calculating statistic algorithms and make them interchangeable, where there isn't hardcoding logic for calculating my different statistics(min, max, avg), then define separate strategy classes that implement a common interface(StatStrategy) that can switch between them in run time. I am hoping I will achieve a more flexible program.
   I implemented these patterns by adding a strategy interface that uses my calculations for my algorithms, which allows my statsPanel class to work with any strategy interchangeably. I use my class DataStatistics where my stream is. Then with that information, I make different classes averageStatsStrategy, minStatsStrategy, and maxStatsStrategy that implement StatStrategy. I modified the statsPnael that holds a reference to StatsStrategy and delegates the actual calculation to strategy. use to setStrategy(), which switches strategies, and update(), which calls strategy to calculate statics. Then I made a ComboBox that allows the user to select the desired strategy

Together these patterns will enhance modularity, flexibility, and maintainability 
