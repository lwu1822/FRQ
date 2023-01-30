package com.nighthawk.spring_portfolio.mvc.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.text.SimpleDateFormat;
import java.util.Map.Entry;

@RestController
@RequestMapping("/login")
public class PersonApiController {
    /*
    #### RESTful API ####
    Resource: https://spring.io/guides/gs/rest-service/
    */

    // Autowired enables Control to connect POJO Object through JPA
    @Autowired
    private PersonJpaRepository repository;

    /*
    GET List of People
     */

    // IMPORTANT: ResponseEntity: conf HTTP response (status code, header, body)
    // Method name c be anything!??
    @GetMapping("/")
    public ResponseEntity<List<Person>> getPeople() {
        return new ResponseEntity<>( repository.findAllByOrderByNameAsc(), HttpStatus.OK);
    }

    /*
    GET individual Person using ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Person> getPerson(@PathVariable long id) {
        // IMPORTANT: optional allow input null/no null value
        Optional<Person> optional = repository.findById(id);
        if (optional.isPresent()) {  // Good ID
            Person person = optional.get();  // value from findByID
            // IMPORTANT: why use person, c't use optional?
            return new ResponseEntity<>(person, HttpStatus.OK);  // OK HTTP response: status code, headers, and body
        }
        // Bad ID
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);       
    }

    /*
    DELETE individual Person using ID
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Person> deletePerson(@PathVariable long id) {
        Optional<Person> optional = repository.findById(id);
        if (optional.isPresent()) {  // Good ID
            Person person = optional.get();  // value from findByID
            repository.deleteById(id);  // value from findByID
            return new ResponseEntity<>(person, HttpStatus.OK);  // OK HTTP response: status code, headers, and body
        }
        // Bad ID
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
    }

    /*
    POST Aa record by Requesting Parameters from URI
     */
    @PostMapping( "/post")
    public ResponseEntity<Object> postPerson(@RequestParam("email") String email,
                                             @RequestParam("password") String password,
                                             @RequestParam("name") String name,
                                             @RequestParam("dob") String dobString, 
                                             @RequestParam("height") Double height) {
        Date dob;
        password = BCrypt.hashpw(password, BCrypt.gensalt());
        // IMPORTANT: convert dobString f string to Date
        // try catch: if code in try statement mk error, x catch statement
        // c only do SimpleDataFormat in try catch statement!! 
        // https://alvinalexander.com/java/simpledateformat-convert-string-to-date-formatted-parse/
        try {
            dob = new SimpleDateFormat("MM-dd-yyyy").parse(dobString);
        } catch (Exception e) {
            return new ResponseEntity<>(dobString +" error; try MM-dd-yyyy", HttpStatus.BAD_REQUEST);
        }
        // A person object WITHOUT ID will create a new record with default roles as student
        Person person = new Person(email, password, name, dob, height);
        repository.save(person);
        return new ResponseEntity<>(email +" is created successfully", HttpStatus.CREATED);
    }

    /*
    The personSearch API looks across database for partial match to term (k,v) passed by RequestEntity body
     */
    // IMPORTANT: allow return JSON
    @PostMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    // IMPORTANT: Map<String,String>: Mk map w/ key = str, value = str
    public ResponseEntity<Object> personSearch(@RequestBody final Map<String,String> map) {
        // extract term from RequestEntity
        // IMPORTANT: refer to screenshot on tech talk, .get: Retrive value in map that h key = "term"
        String term = (String) map.get("term");

        // JPA query to filter on term
        // IMPORTANT: search db for searched keyword in both name + email
        List<Person> list = repository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(term, term);

        // return resulting list and status, error checking should be added
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /*
    The personStats API adds stats by Date to Person table 
    */

     
    @PostMapping(value = "/setStats", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> personStats(@RequestBody final Map<String,Object> stat_map) {
        // find ID
        // IMPORTANT: change f string to long
        long id=Long.parseLong((String)stat_map.get("id"));  
        Optional<Person> optional = repository.findById((id));
        if (optional.isPresent()) {  // Good ID
            Person person = optional.get();  // value from findByID

            // Extract Attributes from JSON
            Map<String, Object> attributeMap = new HashMap<>();
            // IMPORTANT: entrySet: Change map to set (set looks like: [], map looks like: {})
            // Map.Entry: Combine key value pair together
            for (Map.Entry<String,Object> entry : stat_map.entrySet())  {
                // Add all attribute other thaN "date" to the "attribute_map"
                // IMPORTANT: getKey = take the key, rm value
                // getValue = take the value, rm key
                if (!entry.getKey().equals("date") && !entry.getKey().equals("id"))
                    attributeMap.put(entry.getKey(), entry.getValue());
            }

            //************************************************************************ 

            Map<String, Map<String, Object>> returnedMap = new HashMap<>(); 
            returnedMap = person.toStringNotDefault(); 
            System.out.println("returnMap:  " + returnedMap);


            // Set Date and Attributes to SQL HashMap
            Map<String, Map<String, Object>> date_map = new HashMap<>();
            date_map.put( (String) stat_map.get("date"), attributeMap );
            // IMPORTANT: setStats works b/c of lombok (see stats hashmap in Person.java) (i think)
            // c confirm by type get/set in Person.java!!!
            person.setStats(date_map);  // BUG, needs to be customized to replace if existing or append if new

             
            Map<String, Map<String, Object>> returnedMap2 = new HashMap<>(); 
            returnedMap2 = person.toStringNotDefaultNewStats(); 
            //returnedMap2.put("stats", person.toStringNotDefaultNewStats());
            System.out.println("returnMap2:  " + returnedMap2);

            
            //******************************************************************************
             
            Map<String, Map<String, Object>> returnedMap3 = new LinkedHashMap<>();
            for(Entry<String, Map<String, Object>> entry: returnedMap.entrySet()) {
                returnedMap3.put(entry.getKey(), entry.getValue()); 
            }
            
            System.out.println("returnmap 3.1 " + returnedMap3);

            for(Entry<String, Map<String, Object>> entry: returnedMap2.entrySet()) {
                returnedMap3.put(entry.getKey(), entry.getValue()); 
            }

            System.out.println("returnmap 3.2 " + returnedMap3);

            person.setStatsTwo(returnedMap3);

            
            System.out.println("Person    " + person); 
            System.out.println("*************");

           repository.save(person);  // conclude by writing the stats updates

            // return Person with update Stats
            return new ResponseEntity<>(person, HttpStatus.OK);
        }
        // return Bad ID
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
        
    } 

    @DeleteMapping("/deleteAllStats/{id}")
    public ResponseEntity<Person> deletePersonAllStats(@PathVariable long id) {
        Optional<Person> optional = repository.findById(id);
        if (optional.isPresent()) {  // Good ID
            Person person = optional.get();  // value from findByID

            Map<String, Map<String, Object>> returnedMap3 = new LinkedHashMap<>();
           
            person.setStatsTwo(returnedMap3);
            
            System.out.println(returnedMap3); 
            System.out.println("Person:  " + person); 

            System.out.println("*************");

            repository.save(person);


            return new ResponseEntity<>(person, HttpStatus.OK);  // OK HTTP response: status code, headers, and body

        }
        // Bad ID
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
    }

    @DeleteMapping("/deleteSingleStats/{id}/{date}")
    public ResponseEntity<Person> deletePersonSingleStats(@PathVariable long id, @PathVariable String date) {
        Optional<Person> optional = repository.findById(id);
        if (optional.isPresent()) {  // Good ID
            Person person = optional.get();  // value from findByID

            Map<String, Map<String, Object>> returnedMap3 = new LinkedHashMap<>();

            returnedMap3 = person.getStatsTwo();
            System.out.println(person.getStatsTwo()); 
 
            System.out.println(returnedMap3.size()); 

            String dateRemove = ""; 
            /* 
            returnedMap3.forEach((key, value) -> {
                if(key.equals(date)) {
                    dateRemove = date;  
                }
            });
            */ 

            
            for(Entry<String, Map<String, Object>> entry: returnedMap3.entrySet()) {
                System.out.println(entry.getKey() + "  " + entry.getValue()); 
                if (entry.getKey().equals(date)) {
                    System.out.println("matched"); 
                    System.out.println("no rm returnMap3 " + returnedMap3); 
                    dateRemove = date; 
                    // IMPORTANT: c't rm inside this loop, will sh error
                    // see https://stackoverflow.com/questions/29226989/java-util-concurrentmodificationexception-arises
                    // returnedMap3.remove(date); 
                    System.out.println("removed returnMap3:  " + returnedMap3); 
                }
            }
            System.out.println(dateRemove); 
            returnedMap3.remove(dateRemove); 
            

            person.setStatsTwo(returnedMap3); 

            repository.save(person); 

            return new ResponseEntity<>(person, HttpStatus.OK);  // OK HTTP response: status code, headers, and body

        }
        // Bad ID
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
    }

    @PostMapping(value = "/setWorkout", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> personWorkout(@RequestBody final Map<String,Object> workout_map) {
        // find ID
        // IMPORTANT: change f string to long
        long id=Long.parseLong((String)workout_map.get("id"));  
        Optional<Person> optional = repository.findById((id));
        if (optional.isPresent()) {  // Good ID
            String date = ""; 

            Person person = optional.get();  // value from findByID

            // Extract Attributes from JSON
            Map<String, Object> workoutList = new LinkedHashMap<>();
            // IMPORTANT: entrySet: Change map to set (set looks like: [], map looks like: {})
            // Map.Entry: Combine key value pair together
            for (Map.Entry<String,Object> entry : workout_map.entrySet())  {
                // Add all attribute other thaN "date" to the "attribute_map"
                // IMPORTANT: getKey = take the key, rm value
                // getValue = take the value, rm key
                if (!entry.getKey().equals("date") && !entry.getKey().equals("id")) {
                    workoutList.put(entry.getKey(), entry.getValue());
                }
                if (entry.getKey().equals("date")) {
                    date = (String)entry.getValue(); 
                }
            }

            //************************************************************************ 

            Map<String, Map<String, Object>> workoutList2 = new LinkedHashMap<>(); 
            workoutList2 = person.getWorkout(); 
            System.out.println("workoutList2:  " + workoutList2);


            try {
                workoutList2.put(date, workoutList); 
            } catch (Exception e) {
                workoutList2 = new LinkedHashMap<>(); 
                workoutList2.put(date, workoutList);  
            }

            System.out.println("workoutList: " + workoutList); 
           // workoutList2.put(date, workoutList); 
            System.out.println("workoutList2: " + workoutList2); 

            person.setWorkout(workoutList2);

            
            System.out.println("Person    " + person); 
            System.out.println("*************");

           repository.save(person);

/* 
            // Set Date and Attributes to SQL HashMap
            Map<String, Map<String, Object>> date_map = new HashMap<>();
            date_map.put( (String) stat_map.get("date"), attributeMap );
            // IMPORTANT: setStats works b/c of lombok (see stats hashmap in Person.java) (i think)
            // c confirm by type get/set in Person.java!!!
            person.setStats(date_map);  // BUG, needs to be customized to replace if existing or append if new

             
            Map<String, Map<String, Object>> returnedMap2 = new HashMap<>(); 
            returnedMap2 = person.toStringNotDefaultNewStats(); 
            //returnedMap2.put("stats", person.toStringNotDefaultNewStats());
            System.out.println("returnMap2:  " + returnedMap2);

            
            //******************************************************************************
             
            Map<String, Map<String, Object>> returnedMap3 = new LinkedHashMap<>();
            for(Entry<String, Map<String, Object>> entry: returnedMap.entrySet()) {
                returnedMap3.put(entry.getKey(), entry.getValue()); 
            }
            
            System.out.println("returnmap 3.1 " + returnedMap3);

            for(Entry<String, Map<String, Object>> entry: returnedMap2.entrySet()) {
                returnedMap3.put(entry.getKey(), entry.getValue()); 
            }

            System.out.println("returnmap 3.2 " + returnedMap3);

            person.setStatsTwo(returnedMap3);

            
            System.out.println("Person    " + person); 
            System.out.println("*************");

           repository.save(person);  // conclude by writing the stats updates
*/
            // return Person with update Stats
            return new ResponseEntity<>(person, HttpStatus.OK);
        }
        // return Bad ID
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
        
    } 

}