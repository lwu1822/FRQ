package com.nighthawk.team_backend.mvc.database.note;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nighthawk.team_backend.mvc.database.club.Club;

import java.util.*;

@RestController
@RequestMapping("/api/note")
public class NoteApiController {
    // @Autowired
    // private JwtTokenUtil jwtGen;
    /*
     * #### RESTful API ####
     * Resource: https://spring.io/guides/gs/rest-service/
     */

    // Autowired enables Control to connect POJO Object through JPA

    @Autowired
    private NoteJpaRepository notejparepository;

    /*
     * GET List of People
     */
    /*
     * GET individual Club using ID
     */

    @GetMapping("/{id}")
    public ResponseEntity<String> getNote(@PathVariable Long id) throws JsonMappingException, JsonProcessingException {
      // Backend Year Object
      Optional<Note> optional = notejparepository.findById(id);
      if (optional.isPresent()) { // Good ID
          Note note = optional.get(); // value from findByID
          return new ResponseEntity<String>(note.toString(), HttpStatus.OK); // OK HTTP response: status code, headers, and body
      }
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    
    
}