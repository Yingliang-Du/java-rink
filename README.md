java-rink
=========

Skating in java world!
A set of Java utilities:
 * Validation

# Validation

This is a Java Validation, Binding and Formatting framework for web based Java application. This little framework will provide a very straight forward way to define your validation rule for each field in your domain object. The purpose is to let the application developer focus on business logic, not coding the validation logic. It is supposed to be very simple and easy to use. Here is a brief introduction of how to use it in your application:

## Getting Started
### Maven

```xml
  <dependency>
    <groupId>com.y-l-w.enterprise</groupId>
    <artifactId>Validation</artifactId>
    <version>${validation.version}</version>
  </dependency>
```

### Components to wire:

#### Validate Domain Object as Java Bean
 * Create every bean in your project as a subclass of AbstractValidationBean
 * Override the validate() method 
 * Specify validation rule and validate each field that need to be validated

#### Dealing with errors
 * AbstractValidationBean defined a Set of BeanError and a Map of FieldError
 * The framework will populate the Set and Map with validation errors
 * Create an Enum XXXErrorCode implements ErrorMessage interface to categorize error message in your project
 * Create a class XXXError extends BeanError to represent customized error
 * Add a new instance of XXXError to error Set for each error happened in your project
 
#### Auto bind Java Bean properties with Form data
 * Create a Binder class for each bean which need to be binded with form data
 * Make the Binder class a subclass of AbstractBeanBinder
 * Override preBind() method to put pre bind logic like populate bean with default values from data source
 * Override buildParameterMap() method to build parameter map, or get it from HTTP request for auto binding
 * Override postBind() method to put customized post bind process logic
