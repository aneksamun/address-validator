
You are required to create a library that will validate addresses based on rules which are defined within a template file.
For each country there will be a different json template that will define validation rules for the address.
The template will be stored as a json file that needs to be loaded and parsed by the service.
Included in the src/main/resources directory are two templates containing the validation rules for the United Kingdom and for the United States. 
You should use these in your solution

For each address field there are 3 possible types of validation rules:
- required - indicates that a field is mandatory
- maxLength - indicates the maximum length that the value of the field may have
- pattern - defines a regular expression which the value of the field must adhere to

The library will be used by other teams to validate addresses, so you should define a suitable interface for the service.
The service should respond with a message to indicate all validation rules that have failed if the address is not valid
