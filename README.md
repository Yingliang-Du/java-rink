java-rink
=========

Skating in java world!

Validation
==========

This is a Java Validation and Binding framework for web based Java application. This little framework will provide a very straight forward way to define your validation rule for each field in your domain object. The purpose is to let the application developer focus on business logic, not coding the validation logic. It is supposed to be very simple and easy to use. Here is a brief introduction of how to use it in your application:

# Getting Started
## Maven

```xml
  <dependency>
    <groupId>com.y-l-w.enterprise</groupId>
    <artifactId>Validation</artifactId>
    <version>${validation.version}</version>
  </dependency>
```

## Components to wire:

### Domain Object as Java Bean
 * Create every bean in your project as a subclass of AbstractValidationBean
 * Override the validate() method where you are going to define validate rule and validate fields in your object
