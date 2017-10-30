package uga_db_proj1;

import static java.lang.System.out;

class unitTest
{
    public void union_test()
    {
        try{
            Table movie = new Table ("movie", "title year length genre studioName producerNo",
                                              "String Integer Integer Integer String Integer", "title year");

            Table cinema = new Table ("cinema", "title year length genre studioName producerNo",
                                                "String Integer Integer String String Integer", "title year");

                    Comparable [] film0 = { "Star_Wars", 1977, 124, "sciFi", "Fox", 12345 };
            Comparable [] film1 = { "Star_Wars_2", 1980, 124, "sciFi", "Fox", 12345 };
            Comparable [] film2 = { "Rocky", 1985, 200, "action", "Universal", 12125 };
            Comparable [] film3 = { "Rambo", 1978, 100, "action", "Universal", 32355 };
            out.println ();
            movie.insert (film0);
            movie.insert (film1);
            movie.insert (film2);
            movie.insert (film3);
    //        movie.print ();

            Comparable [] film4 = { "Galaxy_Quest", 1999, 104, "comedy", "DreamWorks", 67890 };
            out.println ();
            cinema.insert (film2);
            cinema.insert (film3);
            cinema.insert (film4);
    //        cinema.print ();

            out.println ();
            Table t_union = movie.union (cinema);
            t_union.print ();
        }
        catch(Exception e)
        {
            out.println("Tuples not matching");
        }    

    }
    
    public void natural_join_test()
    {
        Table movie = new Table ("movie", "title year length genre studioName producerNo",
                                          "String Integer Integer String String Integer", "title year");

        Table cinema = new Table ("cinema", "title year length genre studioName producerNo",
                                            "String Integer Integer String String Integer", "title length");

                Comparable [] film0 = { "Star_Wars", 1977, 124, "sciFi", "Fox", 12345 };
        Comparable [] film1 = { "Star_Wars_2", 1980, 124, "sciFi", "Fox", 12345 };
        Comparable [] film2 = { "Rocky", 1985, 200, "action", "Universal", 12125 };
        Comparable [] film3 = { "Rambo", 1978, 100, "action", "Universal", 32355 };
        out.println ();
        movie.insert (film0);
        movie.insert (film1);
        movie.insert (film2);
        movie.insert (film3);
//        movie.print ();

        Comparable [] film4 = { "Galaxy_Quest", 1999, 104, "comedy", "DreamWorks", 67890 };
        out.println ();
        cinema.insert (film2);
        cinema.insert (film3);
        cinema.insert (film4);
//        cinema.print ();

        Table t_join2 = movie.join (cinema);
        t_join2.print ();


    }
    
    public void equi_join_test()
    {
                Table movie = new Table ("movie", "title year length genre studioName producerNo",
                                          "String Integer Integer String String Integer", "title year");
                Table studio = new Table ("studio", "name address presNo",
                                            "String String Integer", "name");
                
           Comparable [] studio0 = { "Fox", "Los_Angeles", 7777 };
        Comparable [] studio1 = { "Universal", "Universal_City", 8888 };
        Comparable [] studio2 = { "DreamWorks", "Universal_City", 9999 };
        out.println ();
        studio.insert (studio0);
        studio.insert (studio1);
        studio.insert (studio2);
        studio.print ();

        Comparable [] film0 = { "Star_Wars", 1977, 124, "sciFi", "Fox", 12345 };
        Comparable [] film1 = { "Star_Wars_2", 1980, 124, "sciFi", "Fox", 12345 };
        Comparable [] film2 = { "Rocky", 1985, 200, "action", "Universal", 12125 };
        Comparable [] film3 = { "Rambo", 1978, 100, "action", "Universal", 32355 };
        out.println ();
        movie.insert (film0);
        movie.insert (film1);
        movie.insert (film2);
        movie.insert (film3);
        movie.print ();
        
                out.println ();
        Table t_join = movie.join ("genre", "name", studio);
        t_join.print ();


    }        
    
    public void select_test()
    {
                Table movieStar = new Table ("movieStar", "name address gender birthdate",
                                                  "String String Character String", "address");

            Comparable [] star0 = { "Carrie_Fisher", "Hollywood", 'F', "9/9/99" };
        Comparable [] star1 = { "Mark_Hamill", "Brentwood", 'M', "8/8/88" };
        Comparable [] star2 = { "Harrison_Ford", "Beverly_Hills", 'M', "7/7/77" };
        out.println ();
        movieStar.insert (star0);
        movieStar.insert (star1);
        movieStar.insert (star2);
            
        
        
        out.println ();
        Table t_iselect = movieStar.select (new KeyType ("Harrison_Ford"));
        t_iselect.print ();

    }        
            
    public void project_test()
    {
        
                Table movie = new Table ("movie", "title year length genre studioName producerNo",
                                          "String Integer Integer String String Integer", "title year");
        Comparable [] film0 = { "Star_Wars", 1977, 124, "sciFi", "Fox", 12345 };
        Comparable [] film1 = { "Star_Wars_2", 1980, 124, "sciFi", "Fox", 12345 };
        Comparable [] film2 = { "Rocky", 1985, 200, "action", "Universal", 12125 };
        Comparable [] film3 = { "Rambo", 1978, 100, "action", "Universal", 32355 };
        out.println ();
        movie.insert (film0);
        movie.insert (film1);
        movie.insert (film2);
        movie.insert (film3);
        movie.print ();

        out.println ();
        Table t_project = movie.project ("abc year");
        t_project.print ();

        
    }        
    
    public void minus_test()
    {

        Table movie = new Table ("movie", "title year length genre studioName producerNo",
                                          "String Integer Integer String String Integer", "title year");

        Table cinema = new Table ("cinema", "title year length genre studioName producerNo",
                                            "Integer Integer Integer String String Integer", "title year");

                Comparable [] film0 = { "Star_Wars", 1977, 124, "sciFi", "Fox", 12345 };
        Comparable [] film1 = { "Star_Wars_2", 1980, 124, "sciFi", "Fox", 12345 };
        Comparable [] film2 = { "Rocky", 1985, 200, "action", "Universal", 12125 };
        Comparable [] film3 = { "Rambo", 1978, 100, "action", "Universal", 32355 };
        out.println ();
        movie.insert (film0);
        movie.insert (film1);
        movie.insert (film2);
        movie.insert (film3);
//        movie.print ();

        Comparable [] film4 = { "Galaxy_Quest", 1999, 104, "comedy", "DreamWorks", 67890 };
        out.println ();
        cinema.insert (film2);
        cinema.insert (film3);
        cinema.insert (film4);
//        cinema.print ();
        
        
                out.println ();
        Table t_minus = movie.minus (cinema);
        t_minus.print ();

    }        
    
    public void bptree_test()
    {
        BpTreeMap <Integer, Integer> bpt = new BpTreeMap <> (Integer.class, Integer.class);
        
            	int[] vals = {1,2,3,4,5,6,7,8,13,11,12,22,16,18,45,36,27,100,28};
	
    	for (int i=0; i<vals.length; i++){
	    bpt.put(vals[i], vals[i]*vals[i]);
    	} 

        // Akshay Agashe Function Calls
        out.println("-----------------Sorted Map Starts----------");
        bpt.entrySet ().forEach(out::println);
        out.println("-----------------Sorted Map Ends----------");
        out.println("The largest Key in the tree is "+bpt.lastKey ());
        out.println("-----------------Submap From To Starts----------");
        bpt.subMap(5,11);
        out.println("-----------------Submap From To Ends----------");    
        out.println("-----------------Headmap Starts----------");
        bpt.headMap(22);
        out.println("-----------------Headmap ENDS----------");
        out.println("-----------------Tailmap Starts----------");
        bpt.tailMap(22);
        out.println("-----------------Tailmap ENDS----------");        
        // Akshay Agashe Function Call Ends
        
    }       
    
    public void lin_hash_map_test()
    {
        LinHashMap <Integer, Integer> ht = new LinHashMap <> (Integer.class, Integer.class);

        int[] vals = {1,2,3,4,5,6,7,8,13,11,12,22,16,18,45,36,27,100};

        for (int i=1; i<vals.length; i++){
	    ht.put(vals[i], vals[i]*vals[i]);
    	} 

        for (int i=1; i<vals.length; i++){
	    out.println("Key is "+vals[i]+" Value is "+ht.get(i));
    	}         
    }     
    
    public void i_join_test()
    {
               
        Table movie = new Table ("movie", "title year length genre studioName producerNo",
                                          "String Integer Integer String String Integer", "title year");

        Table studio = new Table ("studio", "name address presNo",
                                            "String String Integer", "name");
                

        Comparable [] studio0 = { "Fox", "Los_Angeles", 7777 };
        Comparable [] studio1 = { "Universal", "Universal_City", 8888 };
        Comparable [] studio2 = { "DreamWorks", "Universal_City", 9999 };
        out.println ();
        studio.insert (studio0);
        studio.insert (studio1);
        studio.insert (studio2);
        studio.print ();

        Comparable [] film0 = { "Star_Wars", 1977, 124, "sciFi", "Fox", 12345 };
        Comparable [] film1 = { "Star_Wars_2", 1980, 124, "sciFi", "Fox", 12345 };
        Comparable [] film2 = { "Rocky", 1985, 200, "action", "Universal", 12125 };
        Comparable [] film3 = { "Rambo", 1978, 100, "action", "Universal", 32355 };
        out.println ();
        movie.insert (film0);
        movie.insert (film1);
        movie.insert (film2);
        movie.insert (film3);
        movie.print ();
        
                out.println ();
        Table t_join1 = movie.i_join ("studioName", "name", studio);
        t_join1.print ();
        
    }
    
    public void h_join_test()
    {
//               
//        Table movie = new Table ("movie", "title year length genre studioName producerNo",
//                                          "String Integer Integer String String Integer", "title year");
//
//        Table studio = new Table ("studio", "name address presNo",
//                                            "String String Integer", "name");
//                
//
//        Comparable [] studio0 = { "Fox", "Los_Angeles", 7777 };
//        Comparable [] studio1 = { "Universal", "Universal_City", 8888 };
//        Comparable [] studio2 = { "DreamWorks", "Universal_City", 9999 };
//        out.println ();
//        studio.insert (studio0);
//        studio.insert (studio1);
//        studio.insert (studio2);
//        studio.print ();
//
//        Comparable [] film0 = { "Star_Wars", 1977, 124, "sciFi", "Fox", 12345 };
//        Comparable [] film1 = { "Star_Wars_2", 1980, 124, "sciFi", "Fox", 12345 };
//        Comparable [] film2 = { "Rocky", 1985, 200, "action", "Universal", 12125 };
//        Comparable [] film3 = { "Rambo", 1978, 100, "action", "Universal", 32355 };
//        out.println ();
//        movie.insert (film0);
//        movie.insert (film1);
//        movie.insert (film2);
//        movie.insert (film3);
//        movie.print ();
//        
//                out.println ();
//        Table t_join1 = movie.h_join ("studioName", "name", studio);
//        t_join1.print ();        
    }        
    
    public static void main(String args[])
    {
        unitTest ut = new unitTest();
        ut.union_test();
        ut.natural_join_test();
        ut.equi_join_test();
        ut.select_test();
        ut.project_test();
        ut.minus_test();
        ut.bptree_test();
        ut.lin_hash_map_test();
        ut.i_join_test();
      
    }       
}