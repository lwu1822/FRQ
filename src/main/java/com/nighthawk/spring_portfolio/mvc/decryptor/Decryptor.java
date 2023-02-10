package com.nighthawk.spring_portfolio.mvc.decryptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


    public class Decryptor {
        private final String encryptedText;
        private final int shift;
        
        public Decryptor(String encryptedText) {
            this.encryptedText = encryptedText;
            this.shift = 3;
        }
        
        public String decrypt() {
            StringBuilder decryptedText = new StringBuilder();
            for (int i = 0; i < encryptedText.length(); i++) {
                char c = encryptedText.charAt(i);
                if (Character.isLetter(c)) {
                    int shiftValue = Character.isUpperCase(c) ? 'A' : 'a';
                    char decryptedChar = (char) (((c + shift) - shiftValue) % 26 + shiftValue);
                    decryptedText.append(decryptedChar);
                } else {
                    decryptedText.append(c);
                }
            }
            return decryptedText.toString();
        }

        public String toStringJson() {
            Map<String, Object> result = new HashMap<>();
            result.put("encryptedText", encryptedText);
            result.put("decryptedText", decrypt());
            result.put("shift", shift);
        
            ObjectMapper mapper = new ObjectMapper();
            String json = "";
            try {
                json = mapper.writeValueAsString(result);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        
            return json;
        }
   
    // Tester method
    public static void main(String[] args) {
        // Random set of test cases
        Decryptor decryptor = new Decryptor("abc");
        System.out.println("Decrypted Text: " + decryptor.decrypt());
    }
    }