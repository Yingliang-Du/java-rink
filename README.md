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

#### Build Form Key Map
The Form Key Map is for making sure:
 * The form key in the front page to be uniqe among context beans
 * The form key used in the front page to be the same as the back end and 
 * The form key can be converted to bean property name for auto binding pupose
 * 
To achive this - you need to: 
 * Add a new constructor to your bean and take beanName string parameter
 * Call setBeanName(beanName) and buildFormKey() methods from constructor to build the form key map
 * Add new constructor to bean builder to build the bean for Web Form
 * Call the new bean constructor which take beanName string parameter
 * Add public static Builder formKeyValues(String beanName) method to return builder with formKey build in the bean
 * Create bean instance for Web Form in your BeanBinder's preBind() method
 * Use key in formKey map as input name in http form (ex: reference from page context in Handlebars template)
