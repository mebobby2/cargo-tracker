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

## Upto
Page 28

Chapter 2
