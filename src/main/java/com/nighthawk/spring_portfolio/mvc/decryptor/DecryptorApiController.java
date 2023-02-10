package com.nighthawk.spring_portfolio.mvc.decryptor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/decrypt")
public class DecryptorApiController {
    

    /* 
    @GetMapping("/{expression}")
    public ResponseEntity<Calculator> getExpression(@PathVariable String expression) {
        // IMPORTANT: optional allow input null/no null value
        Calculator calculator_obj = new Calculator(expression); 
        return new ResponseEntity<>(calculator_obj.toStringJson(), HttpStatus.OK);  // OK HTTP response: status code, headers, and body
            
    }
*/
   

   /* 
    @GetMapping("/{expression}")
    public ResponseEntity<JsonNode> getIsLeapYear(@PathVariable String expression) throws JsonMappingException, JsonProcessingException {
      // Backend Year Object
      Calculator calculator_obj = new Calculator(expression);

      
      // Turn Year Object into JSON
      ObjectMapper mapper = new ObjectMapper(); 
      JsonNode json = mapper.readTree(calculator_obj.toStringJson()); // this requires exception handling

      return ResponseEntity.ok(json);  // JSON response, see ExceptionHandlerAdvice for throws
      
    }
    */

    @GetMapping("all/{text}")
    public ResponseEntity<JsonNode> decryptText(@PathVariable String text) throws JsonMappingException, JsonProcessingException {

        Decryptor calculator_obj = new Decryptor(text);
      // Turn Decryptor object into JSON
      ObjectMapper mapper = new ObjectMapper(); 
      JsonNode json = mapper.readTree(calculator_obj.toStringJson());

      return ResponseEntity.ok(json);
    }

    // add other methods
}




/** Calendar API
 * Calendar Endpoint: /api/calendar/isLeapYear/2022, Returns: {"year":2020,"isLeapYear":false}
 */