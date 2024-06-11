##Project description
This project is used for showing the basics of using application programming interface, specifically to allow a java proggram to easily communicate with mySQL. This is used as an introductory assignmentto help understand the usage of APIs.

##Tecknology used
It uses spring boot application to set up a connection and is dependant on a yaml application and pom file to get the propper connection and dependences.

##Favorite features
Makes it easier to create tables and interact with mySQL from a java program.

##Code snippets
   jpa:
      hibernate:
         ddl-auto: create
      show-sql: true
      //this snippet tells the application to hibernate when the tables are created, which helps confirm that the program ran as intended

@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "pet_store_customer", joinColumns
	= @JoinColumn(name = "pet_store_id"),
	inverseJoinColumns = @JoinColumn(name = "customer_id"))
	Set<Customer> customers;
 //this snippet shows the annotation and set up for preventing recutrtion and creating a many to many relationship bwtween the class containing this snippet and another class

 ##Instalation and usage
 This project was made in eclipse, if there is another application connected to the desired port then it will likely fail. Simply pulling it will obtain the code and it also requires the use of DBeaver to witness whether or not it was successful

 ##Contribution
 //here there is room to mention buggs and requests

 ##Contact
 thebroness619@gmail.com
