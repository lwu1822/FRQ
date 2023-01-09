package com.nighthawk.spring_portfolio.mvc.lightboard;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/lightboard")
public class LightApiController {
    
    /* 
    @GetMapping("/{expression}")
    public ResponseEntity<Calculator> getExpression(@PathVariable String expression) {
        // IMPORTANT: optional allow input null/no null value
        Calculator calculator_obj = new Calculator(expression); 
        return new ResponseEntity<>(calculator_obj.toStringJson(), HttpStatus.OK);  // OK HTTP response: status code, headers, and body
            
    }
*/
   

   
    @GetMapping("/{r}/{g}/{b}/{on}")
    public ResponseEntity<JsonNode> getIsLeapYear(@PathVariable short r, @PathVariable short g, @PathVariable short b, @PathVariable boolean on) throws JsonMappingException, JsonProcessingException {
      // Backend Year Object
      Light light_obj = new Light(r, g, b, on);

      
      // Turn Year Object into JSON
      ObjectMapper mapper = new ObjectMapper(); 
      JsonNode json = mapper.readTree(light_obj.toString()); // this requires exception handling

      return ResponseEntity.ok(json);  // JSON response, see ExceptionHandlerAdvice for throws
      
    }

    private LightBoard lightBoard;
    private JsonNode json;

    @GetMapping("/generate/{rows}/{cols}")
    public ResponseEntity<JsonNode> generateLightBoard(@PathVariable int rows, @PathVariable int cols) throws JsonMappingException, JsonProcessingException {
        lightBoard = new LightBoard(rows, cols);

        // Create objectmapper to convert String to JSON
        ObjectMapper mapper = new ObjectMapper(); 
        json = mapper.readTree(lightBoard.toString()); 

        return ResponseEntity.ok(json);
    }

    // add other methods
}






    
