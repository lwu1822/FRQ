package com.nighthawk.spring_portfolio.mvc.person;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.format.annotation.DateTimeFormat;

import com.vladmihalcea.hibernate.type.json.JsonType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.text.SimpleDateFormat;
import java.util.Locale;


/*
Person is a POJO, Plain Old Java Object.
First set of annotations add functionality to POJO
--- @Setter @Getter @ToString @NoArgsConstructor @RequiredArgsConstructor
The last annotation connect to database
--- @Entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@TypeDef(name="json", typeClass = JsonType.class)
public class Person {
    
    // automatic unique identifier for Person record
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // email, password, roles are key attributes to login and authentication
    @NotEmpty
    @Size(min=5)
    @Column(unique=true)
    @Email
    private String email;

    @NotEmpty
    private String password;

    // @NonNull, etc placed in params of constructor: "@NonNull @Size(min = 2, max = 30, message = "Name (2 to 30 chars)") String name"
    @NonNull
    @Size(min = 2, max = 30, message = "Name (2 to 30 chars)")
    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dob;
    

    /* HashMap is used to store JSON for daily "stats"
    "stats": {
        "2022-11-13": {
            "calories": 2200,
            "steps": 8000
        }
    }
    */
    @Type(type="json")
    @Column(columnDefinition = "jsonb")
    private Map<String,Map<String, Object>> stats = new HashMap<>(); 
    

    // Constructor used when building object from an API
    public Person(String email, String password, String name, Date dob) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.dob = dob;
    }

    // A custom getter to return age from dob attribute
    public int getAge() {
        if (this.dob != null) {
            // IMPORTANT: convert dob to current zone, then remove all the junk, leave behind 
            // YYYY-MM-DD
            LocalDate birthDay = this.dob.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            // Calculate num year, month, day, btw dob + today -> sh # years
            return Period.between(birthDay, LocalDate.now()).getYears(); }
        return -1;
    }

   
    @Type(type="json")
    @Column(columnDefinition = "jsonb")
    static Map<Integer,Map<String,Map<String, Object>>> thirdMap = new HashMap<>();

    public void setThirdMap(Map<String,Map<String, Object>> thirdMaps) {  

        thirdMap.put(1, thirdMaps); 
        System.out.println(thirdMap); 
    }

    /* 
    public void setStats(Map<String,Map<String, Object>> stats) {  

        this.stats = stats; 
        thirdMap.put(counter, stats);
        counter++; 
        System.out.println(thirdMap); 
    }
    */
    

/* 
    public String emailToString() {
      return (  "" + this.statsTwo );

        // return entire person object
      //return ("Person(id=" + this.id + ", email="  + this.email + ", password=" + this.password + ", name=" + this.name + ", dob=" + this.dob + ", stats=" + this.stats + ", thirdMap=" + this.thirdMap + ")" );
    }
    */ 

    
    public Map<String, String>  toStringNotDefault() { 
        return this.statsTwo;
     }

     public Map<String, Map<String, Object>> toStringNotDefaultNewStats() { 
        return this.stats; 
     }


    @Type(type="json")
    @Column(columnDefinition = "jsonb")
    Map<String, String> statsTwo = new HashMap<>();

     public void setStatsTwo(Map<String,String> statsTwo) {  

        this.statsTwo = statsTwo; 
    }
    

    public static void main(String[] args) {
 /* 
        // IMPORTANT: If only do Date dob;, will h error: the local variable (var) may not have been initialized
        //  https://stackoverflow.com/questions/2448843/variable-might-not-have-been-initialized-error
        Date dob = null; 
    
        try {
            dob = new SimpleDateFormat("MM-dd-yyyy").parse("10-21-1900");
        } catch (Exception e) {
            System.out.println("error"); 
        }

        Person person = new Person("tedison@gmail.com", "123qwerty!", "Thomas Edison", dob); 


        System.out.println("Email: " + person.getEmail()); 
        System.out.println("Password: " + person.getPassword()); 
        System.out.println("Name: " + person.getName()); 
        System.out.println("dob: " + person.getDob()); 
        System.out.println("Age: " + person.getAge());
        
         
        Map<String, Object> subMap = new HashMap<>(); 
        subMap.put("calories", 2200); 
        subMap.put("steps", 8000); 

        System.out.println(subMap); 

        Map<String,Map<String, Object>> actualMap = new HashMap<>(); 
        actualMap.put("2022-11-13", subMap);
        person.setStats(actualMap);



        Map<String, Object> subMap2 = new HashMap<>(); 
        subMap2.put("calories", 1000); 
        subMap2.put("steps", 5000); 

        System.out.println(subMap2); 

        Map<String,Map<String, Object>> actualMap2 = new HashMap<>(); 
        actualMap2.put("2022-11-14", subMap2);


        person.setStats(actualMap2);

        System.out.println(person); 
        */

/* 
       System.out.println("Stats: " + person.getStats()); 
        Map<Integer,Map<String,Map<String, Object>>> thirdMap = new HashMap<>(); 
        thirdMap.put(1, actualMap); 
        System.out.println(thirdMap); 
        */ 






    }

}