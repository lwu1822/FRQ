package com.nighthawk.spring_portfolio.mvc.calendar;

// Prototype Implementation

public class APCalendar {

    /** Returns true if year is a leap year and false otherwise.
     * isLeapYear(2019) returns False
     * isLeapYear(2016) returns True
     */          
    public static boolean isLeapYear(int year) {
        // implementation not shown
        if(year % 4 == 0) {
            if (year % 400 == 0) {
                return true;
            }

            if (year % 100 == 0) {
                return false;
            }

            return true;
        }
        return false;
        }
        
    /** Returns the value representing the day of the week 
     * 0 denotes Sunday, 
     * 1 denotes Monday, ..., 
     * 6 denotes Saturday. 
     * firstDayOfYear(2019) returns 2 for Tuesday.
    */
    private static int firstDayOfYear(int year) {
        // implementation not shown
        int firstDay = dayOfWeek(1, 1, year);
        return firstDay; 


        /* 
        int lastTwoDigit = year % 100; 
        // before add 0 for 1900, 6 for 2000, etc. 
        int beforeYear = (lastTwoDigit/4) + 2;
        // after add 0 for 1900, 6 for 2000, etc. 
        int afterYear = 0; 

        int firstDigit = (year/100);

        int firstDayOfYear = 0;


        // add 0 for 1900, 6 for 2000, etc. 
        if (firstDigit == 20){
            afterYear += 6;
        } else if (firstDigit == 19) {
            afterYear = beforeYear; 
        } else if (firstDigit == 17) {
            afterYear += 4; 
        } else if (firstDigit == 18) {
            afterYear += 2; 
        } else {
            afterYear += 400; 
        }

        firstDayOfYear = (afterYear + lastTwoDigit)%7 - 1;
        if (firstDayOfYear == 0) {
            firstDayOfYear = 7; 
        }
        
        return firstDayOfYear;
        */ 
    }


    /** Returns n, where month, day, and year specify the nth day of the year.
     * This method accounts for whether year is a leap year. 
     * dayOfYear(1, 1, 2019) return 1
     * dayOfYear(3, 1, 2017) returns 60, since 2017 is not a leap year
     * dayOfYear(3, 1, 2016) returns 61, since 2016 is a leap year. 
    */ 
    private static int dayOfYear(int month, int day, int year) {
        // implementation not shown

        boolean leapYear = isLeapYear(year);

        int numDay = 0; 

        int[] numDaysInAMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        if (leapYear && month > 2) {
            for (int i = 0; i < month - 1; i++) {
                numDay += numDaysInAMonth[i];
            }
            numDay += 1;
        } else {
            for (int i = 0; i < month - 1; i++) {
                numDay += numDaysInAMonth[i];
            }
        }
        
        numDay += day; 

        return numDay;
        }

    /** Returns the number of leap years between year1 and year2, inclusive.
     * Precondition: 0 <= year1 <= year2
    */ 
    public static int numberOfLeapYears(int year1, int year2) {
         // to be implemented in part (a)

        int numLeapYear = 0; 
        while (year1 <= year2) {
            if (isLeapYear(year1)) {
                numLeapYear++;
            }
            year1++;
        }
        return numLeapYear;
    }

    /** Returns the value representing the day of the week for the given date
     * Precondition: The date represented by month, day, year is a valid date.
    */
    public static int dayOfWeek(int month, int day, int year) { 
        // to be implemented in part (b)
        int[] monthValue = {1, 4, 4, 0, 2, 5, 0, 3, 6, 1, 4, 6};

        int lastTwoDigit = year % 100; 
        // before add 0 for 1900, 6 for 2000, etc. 
        int beforeYear = (lastTwoDigit/4) + day + monthValue[month-1];
        if ((isLeapYear(year) && (month == 1)) || (isLeapYear(year) && (month == 2))) {
            beforeYear -= 1; 
        }
        // after add 0 for 1900, 6 for 2000, etc. 
        int afterYear = beforeYear; 

        int firstDigit = (year/100);

        int firstDayOfYear = 0;


        // add 0 for 1900, 6 for 2000, etc. 
        if (firstDigit == 20){
            afterYear += 6;
        } else if (firstDigit == 19) {
            afterYear = beforeYear; 
        } else if (firstDigit == 17) {
            afterYear += 4; 
        } else if (firstDigit == 18) {
            afterYear += 2; 
        } else {
            afterYear += 400; 
        }

        firstDayOfYear = (afterYear + lastTwoDigit)%7 - 1;
        if (firstDayOfYear == -1) {
            firstDayOfYear = 7; 
        }
        
        return firstDayOfYear;

    }

    /** Tester method */
    public static void main(String[] args) {
        // Private access modifiers
        System.out.println("firstDayOfYear: " + APCalendar.firstDayOfYear(2022));
        System.out.println("dayOfYear: " + APCalendar.dayOfYear(3, 29, 2020));

        // Public access modifiers
        System.out.println("isLeapYear: " + APCalendar.isLeapYear(2022));
        System.out.println("numberOfLeapYears: " + APCalendar.numberOfLeapYears(2000, 2022));
        System.out.println("dayOfWeek: " + APCalendar.dayOfWeek(1, 1, 2022));
    }

}