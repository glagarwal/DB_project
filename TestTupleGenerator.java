package uga_db_proj1; 
/*****************************************************************************************
 * @file  TestTupleGenerator.java
 *
 * @author   Sadiq Charaniya, John Miller
 */

import static java.lang.System.out;

/*****************************************************************************************
 * This class tests the TupleGenerator on the Student Registration Database defined in the
 * Kifer, Bernstein and Lewis 2006 database textbook (see figure 3.6).  The primary keys
 * (see figure 3.6) and foreign keys (see example 3.2.2) are as given in the textbook.
 */
public class TestTupleGenerator
{
    /*************************************************************************************
     * The main method is the driver for TestGenerator.
     * @param args  the command-line arguments
     */
    public static void main (String [] args)
    {
        TupleGenerator test = new TupleGeneratorImpl ();

        test.addRelSchema ("Student",
                           "id name address status",
                           "Integer String String String",
                           "id",
                           null);
        
        test.addRelSchema ("Professor",
                           "id name deptId",
                           "Integer String String",
                           "id",
                           null);
        
        test.addRelSchema ("Course",
                           "crsCode deptId crsName descr",
                           "String String String String",
                           "crsCode",
                           null);
        
        test.addRelSchema ("Teaching",
                           "crsCode semester profId",
                           "String String Integer",
                           "crcCode semester",
                           new String [][] {{ "profId", "Professor", "id" },
                                            { "crsCode", "Course", "crsCode" }});
        
        test.addRelSchema ("Transcript",
                           "studId crsCode semester grade",
                           "Integer String String String",
                           "studId crsCode semester",
                           new String [][] {{ "studId", "Student", "id"},
                                            { "crsCode", "Course", "crsCode" },
                                            { "crsCode semester", "Teaching", "crsCode semester" }});

//        String [] tables = { "Student", "Professor", "Course", "Teaching", "Transcript" };
//        
//        int tups [] = new int [] { 10000, 1000, 2000, 50000, 5000 };
//    
//        Comparable [][][] resultTest = test.generate (tups);
//        
//        for (int i = 0; i < resultTest.length; i++) {
//            out.println (tables [i]);
//            for (int j = 0; j < resultTest [i].length; j++) {
//                for (int k = 0; k < resultTest [i][j].length; k++) {
//                    out.print (resultTest [i][j][k] + ",");
//                } // for
//                out.println ();
//            } // for
//            out.println ();
//        } // for
        
        // Code by Gaurav Agarwal for creating new tables
        String [] tables = { "Student", "Professor", "Course", "Teaching", "Transcript" };
        
	Table student_table = new Table("Student", "id name address status", "Integer String String String", "id");
        Table transcript_table = new Table("Transcript","studId crsCode semester grade","Integer String String String","studId crsCode semester");
		
        int tups [] = new int [] { 10000, 1000, 2000, 3000, 5000 };
//        int tups [] = new int [] { 10, 10, 20, 30, 50 };   //for testing with small records.
    
        Comparable [][][] resultTest = test.generate (tups);
        
        //for (int i = 0; i < resultTest.length;) {
		for (int i = 0; i < tups.length;) {
            //out.println (tables [i]);
            for (int j = 0; j < resultTest [i].length; j++) {
				if(i!=0 && i!=4)	//just to compare only the Student table which is at index 0 and Transcript table which is at index 4
					break;
				if(i==0)
					student_table.insert(resultTest[i][j]);
				if(i==4)
					transcript_table.insert(resultTest[i][j]);
                                
            } // for
			i+=4;
        }
        // Code by Gaurav Agarwal Ends
        
        
        // For indexed select
        Long start_time_selindx = System.currentTimeMillis();
        Table t_iselect = student_table.select(new KeyType(resultTest[0][0][0]));
        Long end_time_selindx = System.currentTimeMillis();
        System.out.print("Time taken by indexed select: "+ (end_time_selindx-start_time_selindx)+" ms.");
        System.out.println();
        
        // For Loop join
        Long start_time_loopjoin = System.currentTimeMillis();
        Table t_join = student_table.join ("id", "studId", transcript_table);
        Long end_time_loopjoin = System.currentTimeMillis();
        System.out.print("Time taken by for loop Join: "+ (end_time_loopjoin-start_time_loopjoin)+" ms.");
        System.out.println();
        
        // for indexed Join
        Long start_time_indxjoin = System.currentTimeMillis();
        Table it_join = student_table.i_join ("id", "studId", transcript_table);
        Long end_time_indxjoin = System.currentTimeMillis();
        System.out.print("Time taken by indexed Join: "+ (end_time_indxjoin-start_time_indxjoin)+" ms.");   
        System.out.println();
        
        // for Hash Join
        Long start_time_hashjoin = System.currentTimeMillis();
        Table ht_join = student_table.h_join ("id", "studId", transcript_table);
        Long end_time_hashjoin = System.currentTimeMillis();
        System.out.print("Time taken by Hash Join: "+ (end_time_hashjoin-start_time_hashjoin)+" ms.");
        System.out.println();
   
    } // main

} // TestTupleGenerator

