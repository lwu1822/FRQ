package com.nighthawk.spring_portfolio.mvc.calendar;

/** Simple POJO 
 * Used to Interface with APCalendar
 * The toString method(s) prepares object for JSON serialization
 * Note... this is NOT an entity, just an abstraction
 */
class Year {
   private int year;
   private int month;
   private int day; 
   private int year1;
   private int year2;
   private int month1; 
   private int month2;
   private int day1;
   private int day2;
   private int numDaysToDeadline; 
   private boolean isLeapYear;
   private int firstDay; 
   private int numDay; 
   private int numLeapYear;
   private int firstDayOfYear;

   // zero argument constructor
   public Year() {} 

   /* year getter/setters */
   public int getYear() {
      return year;
   }
   public void setYear(int year) {
      this.year = year;
      this.setIsLeapYear(year);
      this.setFirstDayOfYear(year);

   }

   public void setYear(int month, int day, int year) {
      this.year = year;
      this.month = month; 
      this.day = day; 
      this.setDayOfYear(month, day, year);
      this.setDayOfWeek(month, day, year);

   }

   public void setYear(int year1, int year2) {
      this.setNumberOfLeapYears(year1, year2);
   }

   public void setYear(int month1, int day1, int year1, int month2, int day2, int year2) {
      this.month1 = month1;
      this.day1 = day1; 
      this.year1 = year1; 
      this.month2 = month2; 
      this.day2 = day2; 
      this.year2 = year2; 
      this.setNumDaysToDeadline(month1, day1, year1, month2, day2, year2);
   }

   public int getNumDaysToDeadline(int month1, int day1, int year1, int month2, int day2, int year2) {
      return APCalendar.numDaysToDeadline(month1, day1, year1, month2, day2, year2); 
   }
   public void setNumDaysToDeadline(int month1, int day1, int year1, int month2, int day2, int year2) {
      this.numDaysToDeadline = APCalendar.numDaysToDeadline(month1, day1, year1, month2, day2, year2);
   }

   /* isLeapYear getter/setters */
   public boolean getIsLeapYear(int year) {
      return APCalendar.isLeapYear(year);
   }
   private void setIsLeapYear(int year) {  // this is private to avoid tampering
      this.isLeapYear = APCalendar.isLeapYear(year);
   }

   /* getFirstDayOfYear getter/setters */
   public int getFirstDayOfYear(int year) {
      return APCalendar.firstDayOfYear(year);
   }
   private void setFirstDayOfYear(int year) {  
      this.firstDay = APCalendar.firstDayOfYear(year);
   }

   /* getDayOfYear getter/setters */
   public int getDayOfYear(int month, int day, int year) {
      return APCalendar.dayOfYear(month, day, year);
   }
   private void setDayOfYear(int month, int day, int year) {  
      this.numDay = APCalendar.dayOfYear(month, day, year);
   }

   /* getNumberOfLeapYears getter/setters */
   public int getNumberOfLeapYears(int year1, int year2) {
      return APCalendar.numberOfLeapYears(year1, year2);
   }
   private void setNumberOfLeapYears(int year1, int year2) {  
      this.year1 = year1;
      this.year2 = year2;
      this.numLeapYear = APCalendar.numberOfLeapYears(year1, year2);
   }

   /* getDayOfWeek getter/setters */
   public int getDayOfWeek(int month, int day, int year) {
      return APCalendar.dayOfWeek(month, day, year);
   }
   private void setDayOfWeek(int month, int day, int year) {  
      this.firstDayOfYear = APCalendar.dayOfWeek(month, day, year);
   }

   

   /* isLeapYearToString formatted to be mapped to JSON */
   public String isLeapYearToString(){
      return ( "{ \"year\": "  +this.year+  ", " + "\"isLeapYear\": "  +this.isLeapYear+ " }" );
   }	

    /* firstDayOfYearToString formatted to be mapped to JSON */
    public String firstDayOfYearToString(){
      return ( "{ \"year\": "  +this.year+  ", " + "\"firstDayOfYear\": "  +this.firstDay+ " }" );
   }

   /* dayOfYearToString formatted to be mapped to JSON */
   public String dayOfYearToString(){
      return ( "{ \"month\": " +this.month+ ", " + "\"day\": " +this.day+ ", " + "\"year\": "  +this.year+  ", " + "\"dayOfYear\": "  +this.numDay+ " }" );
   }

   /* numberOfLeapYears formatted to be mapped to JSON */
   public String numberOfLeapYearsToString(){
      return ( "{ \"year1\": "  +this.year1+ ", " + "\"year2\": "  +this.year2+ ", " +"\"numberOfLeapYears\": "  +this.numLeapYear+ " }" );

      // return ( "{ \"year1\": " + this.year1+ "\"year2\": " + this.year2 + "\"numberOfLeapYears\": "  +this.numLeapYear+ " }" );
   }

   public String dayOfWeekToString(){
      return ( "{ \"month\": " +this.month+ ", " + "\"day\": " +this.day+ ", " + "\"year\": "  +this.year+  ", " + "\"dayOfWeek\": "  +this.firstDayOfYear+ " }" );
   }

   public String numDaysToDeadlineToString(){
      return ( "{ \"month1\": " +this.month1+ ", " + "\"day1\": " +this.day1+ ", " + "\"year1\": "  +this.year1+  ", " + "\"month2\": " +this.month2+ ", " + "\"day2\": " +this.day2+ ", " + "\"year2\": "  +this.year2+  ", "+ "\"numDaysToDeadline\": "  +this.numDaysToDeadline+ " }" );
   }

   /* standard toString placeholder until class is extended */
   public String toString() { 
      return isLeapYearToString(); 
   }

   public static void main(String[] args) {
      Year year = new Year();
      year.setYear(2022);
      System.out.println(year); // IMPORTANT: this calls the toString() method (woah)
   }
}