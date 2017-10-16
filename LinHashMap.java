
/************************************************************************************
 * @file LinHashMap.java
 *
 * @author  John Miller
 */

import java.io.*;
import java.lang.reflect.Array;
import static java.lang.System.out;
import java.util.*;

/************************************************************************************
 * This class provides hash maps that use the Linear Hashing algorithm.
 * A hash table is created that is an array of buckets.
 */
public class LinHashMap <K, V>
       extends AbstractMap <K, V>
       implements Serializable, Cloneable, Map <K, V>
{
    /** The number of slots (for key-value pairs) per bucket.
     */
    private static final int SLOTS = 4;

    /** The class for type K.
     */
    private final Class <K> classK;

    /** The class for type V.
     */
    private final Class <V> classV;

    /********************************************************************************
     * This inner class defines buckets that are stored in the hash table.
     */
    private class Bucket
    {
        int    nKeys;
        K []   key;
        V []   value;
        Bucket next;

        @SuppressWarnings("unchecked")
        Bucket (Bucket n)
        {
            nKeys = 0;
            key   = (K []) Array.newInstance (classK, SLOTS);
            value = (V []) Array.newInstance (classV, SLOTS);
            next  = n;
        } // constructor
    } // Bucket inner class

    /** The list of buckets making up the hash table.
     */
    private final List <Bucket> hTable;

    /** The modulus for low resolution hashing
     */
    private int mod1;

    /** The modulus for high resolution hashing
     */
    private int mod2;

    /** Counter for the number buckets accessed (for performance testing).
     */
    private int count = 0;

    /** The index of the next bucket to split.
     */
    private int split = 0;

    /********************************************************************************
     * Construct a hash table that uses Linear Hashing.
     * @param classK    the class for keys (K)
     * @param classV    the class for keys (V)
     * @param initSize  the initial number of home buckets (a power of 2, e.g., 4)
     */
    public LinHashMap (Class <K> _classK, Class <V> _classV)    // , int initSize)
    {
        classK = _classK;
        classV = _classV;
        hTable = new ArrayList <> ();
        mod1   = 4;                        // initSize;
        mod2   = 2 * mod1;
    } // constructor

    /********************************************************************************
     * Return a set containing all the entries as pairs of keys and values.
     * @return  the set view of the map
     */
    public Set <Map.Entry <K, V>> entrySet ()
    {
        Set <Map.Entry <K, V>> enSet = new HashSet <> ();

        // implementation------
        for(int i = 0; i < hTable.size();i++){//loops through initial bucket identifiers 
        	Bucket temp = hTable.get(i);//sets temp to iteration of i
        	while(temp != null){//loops through all linked value in the bucket chain
        		for (int j = 0; j < temp.nKeys; j++) {//places individual bucket sets in enSet
					enSet.add(new AbstractMap.SimpleEntry<K, V>(temp.key[j],
							temp.value[j]));
				}
        		temp = temp.next;
        	}
        }
        // implementation------
            
        return enSet;
    } // entrySet

    /********************************************************************************
     * Given the key, look up the value in the hash table.
     * @param key  the key used for look up
     * @return  the value associated with the key
     */
    public V get (Object key)
    {
        int i = h (key);
        // implementation-----
        Bucket temp = hTable.get(i);//search variable
    	while (temp != null) {//loop through all not null data    			
     		for (int x = 0; x < SLOTS; x++) {//iterate through bucket
     			if (key.equals(temp.key[x])) {//if data element is found, return that value
     				return temp.value[x];
     			}
     		}		
     		temp = temp.next;//else increment the bucket and try again
     	}
    	//method will return null if value is not found
    	// implementation-----
        return null;
    } // get

    /********************************************************************************
     * Put the key-value pair in the hash table.
     * @param key    the key to insert
     * @param value  the value to insert
     * @return  null (not the previous value)
     */
    public V put (K key, V value)
    {
        int i = h (key);
        //out.println ("LinearHashMap.put: key = " + key + ", h() = " + i + ", value = " + value);
        // implementation-----
        if(i<split){
            i=h2(key);
        }
        Bucket temp = hTable.get(i);
        if( temp.nKeys < SLOTS ){//no split 
        	temp.key[temp.nKeys] = key;
            temp.value[temp.nKeys] = value;
            temp.nKeys++;
        }
     
        else{ //split
        	hTable.add(new Bucket(null));
        	while(temp.next != null){
        		temp = temp.next;
        	}
        	//last value
        	if(temp.nKeys < SLOTS){
        		temp.key[temp.nKeys] = key;
        		temp.value[temp.nKeys] = value;
        		temp.nKeys++;
        	}
        	else{//ins in new bucket
	  		   temp.next = new Bucket(null);
	  		   temp = temp.next;
	  		   temp.key[temp.nKeys]=key;
	  		   temp.value[temp.nKeys]=value;
	  		   temp.nKeys++;
	  	   }	
	  	   Bucket rep1 = new Bucket(null); //the new split bucket
	  	   Bucket new1 = new Bucket(null); //temp
	  	   temp = hTable.get(split + 1); //the original split bucket
	  	   for(int m = 0; m<temp.nKeys; m++){
	  		   int i2 = h2(temp.key[m]);
	  		   if(i2 == split){//assign val to new split bucket
	  			   if(rep1.next ==null){
	  				   rep1.next = new Bucket(null);
	  				   rep1.next = rep1;
	  			   }   
	  			   rep1.key[rep1.nKeys] = temp.key[m];
	  			   rep1.value[rep1.nKeys] = temp.value[m];
	  			   temp.nKeys++;
	  		   }
	  		   else{//assign val to second new split bucket
	  			   if(new1.next==null){
	  				   new1.next = new Bucket(null);
	  				   new1 = new1.next;
	  			   }
	  			   new1.key[new1.nKeys] = temp.key[m];
	  			   new1.value[new1.nKeys] = temp.value[m];  	
	  		   }
	  	   }

	  	   if(split == mod1-1){//holds new split index
	  		   split = 0;
	  		   mod1 = mod1*2;
	  		   mod2 = mod1*2;
	  	   }
	  	   else{//next bucket is new split
	  		   split++;
	  	   }
        }   
        // implementation-----
    	
        return null;
    } // put

    /********************************************************************************
     * Return the size (SLOTS * number of home buckets) of the hash table. 
     * @return  the size of the hash table
     */
    public int size ()
    {
        return SLOTS * (mod1 + split);
    } // size

    /********************************************************************************
     * Print the hash table.
     */
    private void print ()
    {
        out.println ("Hash Table (Linear Hashing)");
        out.println ("-------------------------------------------");

        // implementation-----
        out.println("Key\t->\tValue");		
		Iterator<Map.Entry<K, V>> itr = this.entrySet().iterator();//iterator for this chain set
		while (itr.hasNext()) {//loops until null value
			Map.Entry<K, V> entry = itr.next();
			out.println(entry.getKey() + "\t->\t" + entry.getValue());//print data
		}
        // implementation-----

        out.println ("-------------------------------------------");
    } // print

    /********************************************************************************
     * Hash the key using the low resolution hash function.
     * @param key  the key to hash
     * @return  the location of the bucket chain containing the key-value pair
     */
    private int h (Object key)
    {
        return key.hashCode () % mod1;
    } // h

    /********************************************************************************
     * Hash the key using the high resolution hash function.
     * @param key  the key to hash
     * @return  the location of the bucket chain containing the key-value pair
     */
    private int h2 (Object key)
    {
        return key.hashCode () % mod2;
    } // h2

    /********************************************************************************
     * The main method used for testing.
     * @param  the command-line arguments (args [0] gives number of keys to insert)
     */
    public static void main (String [] args)
    {

        int totalKeys    = 30;
        boolean RANDOMLY = true;

        LinHashMap <Integer, Integer> ht = new LinHashMap <> (Integer.class, Integer.class);
        if (args.length == 1) 
        	totalKeys = Integer.valueOf (args [0]);

        if (RANDOMLY) {
            Random rng = new Random ();
            for (int i = 1; i <= totalKeys; i += 2) ht.put (rng.nextInt (2 * totalKeys), i * i);
        } else {
            for (int i = 1; i <= totalKeys; i += 2) ht.put (i, i * i);
        } // if

        ht.print ();
        for (int i = 0; i <= totalKeys; i++) {
            out.println ("key = " + i + " value = " + ht.get (i));
        } // for
        out.println ("-------------------------------------------");
        out.println ("Average number of buckets accessed = " + ht.count / (double) totalKeys);
    } // main

} // LinHashMap class
