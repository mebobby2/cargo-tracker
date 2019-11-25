# cargo-tracker

## Domain Driven Design
### Overview
The first main concept of DDD that we would need to familiarize ourselves with is the identification of the 'Problem Space' or the 'Business Domain'.

For example, an Auto Loans/Lease Management has one business domain. A retail banking service could have multiple business domains, Checking Account Management, Savings Account Management, and Credit Card Management.

Once we have identified the main Business Domain, the next step is to break the domain into its sub-domains. The identification of the sub-domains essentially involves the breaking down of the various business capabilities of your main business domain into cohesive units of business functionalities.

For example, the Auto Loans/Lease Management could have three subdomains:
* Originations Sub-Domain – This sub-domain takes care of the
business capability of issuing new auto loans/leases to customers.
* Servicing Sub-Domain – This sub-domain takes care of the business capability of servicing (e.g., monthly billing/payments) these auto loans/leases.
* Collections Sub-Domain – This sub-domain takes care of the business capability of managing these auto loans/leases if something goes wrong (e.g., customer defaults on payment).

The retail banking service's Credit Card Management domain would also have 3 subdomains:
* Products Sub-Domain – This sub-domain takes care of the business
capability of managing all types of credit card products.
* Billing Sub-Domain – This sub-domain takes care of the business capability of billing for a customer’s credit card.
* Claims Sub-Domain – This sub-domain takes care of the business capability of managing any kinds of claims for a customer’s credit card.

So what are Bounded Contexts?

We need to start creating solutions for the corresponding domains/sub-domains identified earlier, that is, we need to move from the Problem Space area to the Solution Space area, and that’s where Bounded Contexts play a central role.

*Simply put, Bounded Contexts are design solutions to our identified Business Domains/Sub-Domains.*

For example of the Auto Finance Business Domain, we could choose to have a single solution for the entire domain, that is, a single bounded context for all the sub-domains; or we could choose to have a bounded context mapped to a single sub-domain, giving rise to 3 bounded contexts.

There are no restrictions to the choice of deployment as long as the Bounded Context is treated as a single cohesive unit. You could have a monolithic deployment for the multiple bounded contexts approach (single Web Archive [WAR] file with multiple JAR files per Bounded context), you could choose a microservices deployment model with each bounded context as a separate container, or you could choose a serverless model with each bounded context deployed as a function.

### The Domain Model
The Domain Model is the implementation of the core business logic within a specific Bounded Context.

In business language, this involves identifying:
* Business Entities
* Business Rules
* Business Flows
* Business Operations
* Business Events

In technical language within the DDD world, this translates into identifying:
* Aggregates/Entities/Value Objects
* Domain Rules
* Sagas
* Commands/Queries
* Events

#### Aggregates/Entity Objects/Value Objects
The Aggregate (also known as the root aggregate) is the central business object within your Bounded Context and defines the scope of consistency within that Bounded context. Every aspect of your Bounded Context begins and ends within your root aggregate.

*Aggregate = Principal identifier of your Bounded Context*

Entity Objects have an identity of their own but cannot exist without the root aggregate, that is, they are created when the root aggregate is created and are destroyed when the root aggregate is destroyed.

*Entity Objects = Secondary identifiers of your Bounded Context*

Value Objects have no identity and are easily replaceable within an instance of a root aggregate or an entity.

As an example, let us take the Originations Bounded Context of our Auto Loans/ Lease Management Domain:

Aggregate = Application
Entity = Applicant Details
Amount = Value Object

#### Domain Rules
Domain Rules are pure business rule definitions. Modeled as Objects too, they assist the Aggregate for any kind of business logic execution within the scope of a Bounded Context.

Within our Originations Bounded Context, a good example of a Domain Rule is a 'State Applicant Compliance Validation' Business Rule. The rule basically states that depending upon the “state” of the Loan Application (e.g., CA, NY), additional validation checks could be applicable to the loan applicant.

#### Commands/Queries
Commands and Queries represent any kind of operations within the Bounded Context which either affect the state of the aggregate/entity or query the state of the aggregate/ entity.

some examples of Commands within the Originations Bounded Context include 'Open a Loan Account' and 'Modify Loan Applicant Details', while examples of queries include 'View Loan Account Details' and 'View Loan Applicant Details'.

#### Events
Events capture any kind of state change either with an aggregate or an entity within the Bounded Context.

E.g: Loan Application Opened, Loan Application Approved, Loan Application Cancelled

#### Sagas
The final aspect of the DDD model is to flush out any kind of business processes/ workflows within your Business Domain. In the DDD terminology, these are termed as sagas. As stated, sagas are the only artifact that is not restricted to a single Bounded Context and may span across multiple Bounded Contexts, and in most of the cases it will span across Bounded Contexts.

The Bounded Context or specifically the aggregate within a Bounded Context acts as a Saga participant. Sagas react to multiple business events across Bounded Contexts and 'orchestrate the business process' by coordinating interactions among these Bounded Contexts.

Let us look at an example of a Saga within our Auto Finance Business Domain – opening a Loan Account.

If we lay out the business process for the opening of a Loan Account:
1. Customer puts in a Loan Application to X Auto Finance Company to purchase a new auto.
2. X Auto Finance Company validates the Loan Application details to determine the best Loan Product for the customer.
3. X Auto Finance Company either approves the Loan Application or rejects the Loan Application.
4. If the Loan Application is approved, X Auto Finance Company presents the Loan Product Terms to the customer including interest rate, tenure, and so on.
5. Customer accepts the Loan Product Terms.
6. X Auto Finance Company approves the Loan Application post acceptance.
7. X Auto Finance Company opens a new Loan Account for the customer.

It is quite evident that this business process involves multiple Bounded Contexts, that is, it starts with the Originations Bounded Context (approving a Loan Application) and ends within the Servicing Bounded Context (opening of a Loan Account).

### Domain Modelling Process

1. Identify Sub-Domains
2. Identify Bounded Context
3. Identify Domain Model
4. Identify Domain Model Operations
5. Identify Long Running Transactions (Sagas)
6. Identify Domain Model Services

Step 1 & 2 are high level artefacts. The high-level artifacts have a low degree of implementation required, that is, these are more design concepts with minimal physical artifacts required. On the other hand, the low-level artifacts have a high degree of implementation, that is, they will be the actual physical artifacts of our implementation.

#### Cargo Tracker Example
##### Sub-Domains/Bounded Contexts
In the Cargo Tracker Domain, we have four main business areas:
* Booking - Booking of cargos, Assigning of routes to cargos, Modification of cargos (e.g. change of destination of a booked cargo), Cancellation of cargos
* Routing - Optimal Itinerary allocation for cargos based on their Route Specification, Voyage Maintenance for the carriers that will carry cargos (e.g. addition of new routes)
* Handling - As the cargo progresses along its assigned route, it will need to be inspected/handled at the various ports of transit. This area covers all operations related to the Handling activity of cargos.
* Tracking - Customers need comprehensive, detailed, and up-to- date information of their booked cargos. The Tracking business area provides this capability.

Each of these Business Areas can be classified as Sub-Domain(s) within the DDD paradigm. While identifying Sub-Domains is part of the problem space identification, we need solutions for them too. As we have seen in the previous chapter, we use the concept of Bounded Contexts. Bounded Contexts are design solutions to our main problem space, and each Bounded Context could have a single sub-domain or multiple sub-domains mapped to it.

*For all our implementations, we assume that each Bounded Context is mapped to a single Sub-Domain.*

The idea of capturing the sub-domains is to ensure that at the end of the exercise, we have clearly separated our core domain into different business areas which are independent and can have their own business language recognizable within that specific business area/sub-domain.

The design solution of these sub-domains is done via Bounded Contexts deployed either as modules within a monolithic architecture or as separate microservices in our microservices-based architecture.

##### Domain Model
The Bounded Context’s Domain Model is the foundational piece of any DDD-based architecture and is used to express the Business Intent of the Bounded Context. Identification of the Domain Model involves two main sets of artifacts:
* Core Domain Model – Aggregates, Aggregate Identifiers, Entities, and Value Objects
* Domain Model Operations – Commands, Queries, and Events

###### Aggregates
The most fundamental and important aspect of designing the domain model is the identification of Aggregates within a Bounded Context. The aggregate defines the scope of consistency within your Bounded Context, that is, the aggregate consists of a root entity and a set of entity/value objects. You can consider the aggregate as a single unit wherein any operation updates the state of the aggregate as a whole. Aggregates are responsible for capturing all State and Business Rules associated with the Bounded Context.

Bounded Context | Aggregates
--- | ---
Booking | Cargo
Handling | HandlingActivity
Routing | Voyage
Tracking | TrackingActivity

###### Aggregate Identifiers
Each Aggregate needs to be uniquely identified using an Aggregate Identifier. The Aggregate Identifier is implemented using a Business key.

Aggregates | Identifier
--- | ---
Cargo | BookingId
HandlingActivity | ActivityId
Voyage | VoyageNumber
TrackingActivity | TrackingId

###### Entities
Entities within a Bounded Context have an identity of their own but cannot exist without the Aggregate. In addition to that, Entities within an Aggregate cannot be replaced.

Within the Cargo Aggregate (Booking Bounded Context), as part of the Booking process, the booking clerk needs to specify the origin of the cargo. This is mapped as an Entity object, that is, Location which clearly has an identity of its own but also cannot exist on its own without the Cargo Aggregate.

###### Value Objects
Value Objects within a Bounded Context have no identity of their own and are replaceable in any instance of an aggregate.

The Cargo Aggregate has the following Value Objects:
* Booking Amount of the cargo.
* Route specification (Origin Location, Destination Location,
Destination Arrival Deadline).
* Itinerary that the cargo is assigned to based on the Route Specification. The Itinerary consists of multiple Legs that the cargo might be routed through to get to the destination.
* Delivery Progress of the cargo against its Route Specification and Itinerary assigned to it. The Delivery Progress provides details on the Routing Status, Transport Status, Current Voyage of the cargo, Last Known Location of the cargo, Next Expected Activity, and the Last Activity that occurred on the cargo.

Let us walk through the scenarios and the rationale why we have these as value objects and not as entities because it is an important domain modeling decision:

* When a new cargo is booked, we will have a new Route Specification, an empty Cargo Itinerary, and no delivery progress.
* As the cargo is assigned an itinerary, the empty Cargo Itinerary is replaced by an allocated Cargo Itinerary.
* As the cargo progresses through multiple ports as part of its itinerary, the Delivery progress is updated and replaced within the Cargo Aggregate.
* Finally, if the customer chooses to change the delivery location of the cargo or the deadline for delivery, the Route Specification changes, a new Cargo Itinerary will be assigned, the Delivery is recalculated, and the Booking Amount changes.

They have no identity of their own, and they are replaceable within the Cargo Aggregate and thus modeled as Value Objects. That is the thumb rule for identifying Value Objects.

###### Domain Model Operations
The next step is to capture the Domain Model Operations that occur within a Bounded Context.

Operations within a Bounded Context might be
* Commands that request a change of state within the Bounded Context
* Queries that request the state of the Bounded Context
* Events that notify the state change of the Bounded Context

##### Sagas
Sagas are used primarily when we adopt the microservices architectural style for developing our applications. The distributed nature of microservices application requires us to implement a mechanism to maintain data consistency for use cases that may span across multiple microservices.

Sagas can be implemented in two ways either via Event Choreography or via Event Orchestration:

* Implementation of choreography-based sagas is straightforward in the sense that microservices participating in a particular Saga will raise and subscribe to events directly.
* On the other hand, in orchestration-based Sagas, the lifecycle coordination happens through a central component. This central component is responsible for Saga creation, coordination of the flow across the various Bounded Contexts participating in the Saga, and finally the Saga Termination itself.

The Booking Saga involves the business operations within Cargo Booking, Cargo Routing, and Cargo Tracking. It starts with the cargo being booked to its subsequent routing and finally ends with the Tracking Identifier allocated to the booked cargo. This Tracking Identifier is used by the customers to track the progress of the cargo.

The Handling Saga involves the business operations within Cargo Handling, Inspection, Claims, and Final Settlement. It starts with the cargo being handled at the ports where it undergoes a voyage and claimed by the customer at the final destination and ends with the final settlement of the cargo (e.g., penalty for late delivery).

Both these Sagas span across multiple Bounded Contexts/Microservices, and the transactional consistency needs to be maintained across all these Bounded Contexts at the end of the Sagas.

##### Domain Model Services
Domain Model Services are used for two primary reasons. The first is to enable the Bounded Context’s Domain Model to be made available to external parties through well-defined Interfaces. The second is interacting with external parties be it to persist the Bounded Context’s state to Datastores (Databases), publish the Bounded Context’s state change events to external Message Brokers, or communicate with other Bounded Contexts.

There are three types of Domain Model Services for any Bounded Context:
* Inbound Services where we implement well-defined interfaces which enable external parties to interact with the Domain Model
* Outbound Services where we implement all interactions with External Repositories/other Bounded Contexts
* Application Services which act as the façade layer between the Domain Model and both Inbound and Outbound services

So how do we design these services? Which architectural pattern can we follow to implement these supporting services?

The hexagonal architectural pattern is a perfect fit to help us model/design and implement the Domain Model supporting services. The Hexagonal architecture uses the concept of ports and adaptors to implement the Domain Model Services. Let us expand on this concept a bit.

A port in the hexagonal architectural pattern could either be an inbound port or an outbound port:
* An inbound port provides an interface to the business operations of our domain model. This is typically implemented via the Application Services.
* An outbound port provides an interface to the technical operations required by our domain model. The Domain Model uses these interfaces to store or publish any kind of state from the sub-domain.

An adaptor in the hexagonal architectural pattern could either be an Inbound Adaptor or an Outbound Adaptor:
* An inbound adaptor uses the inbound port to provide the capabilities for external clients to consume the domain model. These are implemented through REST API(s), Native Web API(s), or Event API(s).
* An outbound adaptor is an implementation of the outbound port for that specific repository.

To summarize, a “Domain Model” needs a set of supporting services also known as “Domain Model Services.” These supporting services enable external clients to consume our domain model and at the same time also enable the domain model to store and publish states of the sub-domain in multiple repositories. These supporting services are modeled using the Hexagonal Architectural Pattern wherein these services are mapped either as an “inbound/outbound port” or an “inbound/outbound adaptor.” The hexagonal architectural pattern enables the “Domain Model” to be independent of these supporting services.

## Monoliths
The main focus points for a monolithic architecture are the following:
* Strong transactional consistency
* Easier maintainability
* Centralized data management
* Shared responsibilities

The microservices architectural style provides teams with
a high degree of independence in terms of development, testing, and deployment of applications; but due care needs to be taken before you start dismantling a monolith and move it to a microservices-based architecture. Microservices are essentially distributed systems which in turn require a lot of investment in automation, monitoring, and compromises for consistency. Monoliths have considerable value for complex business applications.

Structuring these Bounded Contexts as separate modules within a monolith and using domain events to communicate between them help us achieve loose coupling enabling 'true modularity' or termed 'modular monoliths'. It helps maintain a level of independence which helps us transition to microservices down the line if required.

## Source Code
https://github.com/Apress/practical-ddd-in-enterprise-java



## Upto
Page 67

Aggregate Class Implementation

Before that: copy source code chapter 3 folder
