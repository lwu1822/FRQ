package com.nighthawk.spring_portfolio.mvc.calendar;

/** Simple POJO 
 * Used to Interface with APCalendar
 * The toString method(s) prepares object for JSON serialization
 * Note... this is NOT an entity, just an abstraction
 */
class Year {
   private int year;
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
      this.setDayOfYear(month, day, year);
      this.setDayOfWeek(month, day, year);

   }

   public void setYear(int year1, int year2) {
      this.setNumberOfLeapYears(year1, year2);
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
      return ( "{ \"year\": "  +this.year+  ", " + "\"dayOfYear\": "  +this.numDay+ " }" );
   }

   /* numberOfLeapYears formatted to be mapped to JSON */
   public String numberOfLeapYearsToString(){
      return ( "{ \"numberOfLeapYears\": "  +this.numLeapYear+ " }" );
   }

   public String dayOfWeekToString(){
      return ( "{ \"year\": "  +this.year+  ", " + "\"dayOfWeek\": "  +this.firstDayOfYear+ " }" );
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