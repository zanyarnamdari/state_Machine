Here, we will develop a prototype application that demonstrates the payment processing state functionality.
Step 1: Create the Demo spring boot app
Create a Spring Boot project and include the following dependencies. If using IntelliJ IDEA, you can create a new project from Spring Initializr. Please note that the Spring State Machine dependency must be added separately.
•	spring-statemachine-core
•	lombok
•	h2
•	devtools
•	spring web
•	spring jpa
Step 2: Create the repository objects
Step 3: State configuration
The state machine can be configured using the annotation @EnableStateMachineFactory. By overriding the configure method, we can define the various states. Additionally, we can explore the different classes within the state-machine-core JAR.

In the overridden configure methods, we have defined the state transitions. For example, if the event is PRE_AUTH_APPROVED, then the state changes from NEW to PRE_AUTH.

The unit test demonstrates the expected output.
Step 4: Payment Service
The state machine can also be retrieved from the database. The build() method in the service implementation class accomplishes this.
Step 5: Sending events to State Machine & State Change interceptor
The state machine supports standard Spring messages. We can now invoke the sendEvent() method.

Step 6: Progressing from NEW to PRE_AUTH state
We can test this by writing a unit test for the service implementation class and checking the logger statements.

Step 7: State Machine Actions
 The pre-auth action method can be created in the Config class itself.

Step 8: State Machine Guards
If the payment_id header is null, the transaction will not proceed.


