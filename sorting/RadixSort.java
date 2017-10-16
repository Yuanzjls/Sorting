package sorting;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Random;

public class RadixSort
{
    /*
     * Radix sort an array of Strings
     * Assume all are all ASCII
     * Assume all have same length
     */
    public static void radixSortA( String [ ] arr, int stringLen )
    {
        final int BUCKETS = 256;
        
        ArrayList<ArrayList<String>> buckets = new ArrayList<ArrayList<String>>(BUCKETS);
        //ArrayList<String[]> buckets = new ArrayList<String[]>(BUCKETS);
        
        for( int i = 0; i < BUCKETS; i++ )
        	//this.get(index).add(element);
        	//ArrayList<String> element = new ArrayList<String>();
        	buckets.add(new ArrayList<String>());
        	//buckets.set(i, new ArrayList<String>());
            //buckets[i] = new ArrayList<String>();
        
        for( int pos = stringLen - 1; pos >= 0; pos-- )
        {
            for( String s : arr )
                //buckets[ s.charAt( pos ) ].add( s );
            	buckets.get(s.charAt(pos)).add(s);

            int idx = 0;
            for( ArrayList<String> thisBucket : buckets )
            {
                for( String s : thisBucket )
                    arr[ idx++ ] = s;
                
                thisBucket.clear( );
            }
        }
    }
       
    /*
     * Counting radix sort an array of Strings
     * Assume all are all ASCII
     * Assume all have same length
     */
    public static void countingRadixSort( String [ ] arr, int stringLen )
    {
        final int BUCKETS = 256;
        
        int N = arr.length;
        String [ ] buffer = new String[ N ];

        String [ ] in = arr;
        String [ ] out = buffer;
        
        for( int pos = stringLen - 1; pos >= 0; pos-- )
        {
            int[ ] count = new int [ BUCKETS + 1 ];
            
            for( int i = 0; i < N; i++ )
                count[ in[ i ].charAt( pos ) + 1 ]++;

            for( int b = 1; b <= BUCKETS; b++ )
                count[ b ] += count[ b - 1 ];

            for( int i = 0; i < N; i++ )
                out[ count[ in[ i ].charAt( pos ) ]++ ] = in[ i ];
            
              // swap in and out roles
            String [ ] tmp = in;
            in = out;
            out = tmp;
        }
        
           // if odd number of passes, in is buffer, out is arr; so copy back
        if( stringLen % 2 == 1 )
            for( int i = 0; i < arr.length; i++ )
                out[ i ] = in[ i ];
    }
    
    /*
     * Radix sort an array of Strings
     * Assume all are all ASCII
     * Assume all have length bounded by maxLen
     */
    /*
    public static void radixSort( String [ ] arr, int maxLen )
    {
        final int BUCKETS = 256;
        
        ArrayList<String> [ ] wordsByLength = new ArrayList<>[ maxLen + 1 ];
        ArrayList<String> [ ] buckets = new ArrayList<>[ BUCKETS ];
        
        for( int i = 0; i < wordsByLength.length; i++ )
            wordsByLength[ i ] = new ArrayList<>( );
        
        for( int i = 0; i < BUCKETS; i++ )
            buckets[ i ] = new ArrayList<>( );
        
        for( String s : arr )
            wordsByLength[ s.length( ) ].add( s );
       
        int idx = 0;
        for( ArrayList<String> wordList : wordsByLength )
            for( String s : wordList )
                arr[ idx++ ] = s;
        
        int startingIndex = arr.length;    
        for( int pos = maxLen - 1; pos >= 0; pos-- )
        {
            startingIndex -= wordsByLength[ pos + 1 ].size( );
            
            for( int i = startingIndex; i < arr.length; i++ )
                buckets[ arr[ i ].charAt( pos ) ].add( arr[ i ] );
            
            idx = startingIndex;
            for( ArrayList<String> thisBucket : buckets )
            {
                for( String s : thisBucket )
                    arr[ idx++ ] = s;
                
                thisBucket.clear( );
            }
        }
    }*/

    // Print the array
    private static void print( String[] a)
    {
        for( int i = 0; i < a.length; i++ )
        	System.out.print(a[i] + " ");
        System.out.println();
    }
    

    // Do some tests
    // Note: radix sort works only for fixed length strings (radixSortA)
    // For variable length strings, radixSort has to be modified
    public static void main( String [ ] args )
    {
        List<String> lst = new ArrayList<>( );
        //Random r = new Random( );
        long start_time, total_time1, total_time2;
        //final int LEN = 4;
        String [ ] arr1 = new String[ lst.size( ) ];
        String [ ] arr2 = new String[ lst.size( ) ];
        
        for (int h=4; h<=10; h+=2)
        {
        	total_time1 = 0;
        	total_time2 = 0;
	        for (int j=0; j<10; j++)
	        {        	
		        RandomStringGenerator rand = new RandomStringGenerator();
		        for( int i = 0; i < 100000; i++ )
		        {
		        	String str;
		
		        	str = rand.RandomString(h);
		
		            lst.add( str );
		        }
		        lst.toArray( arr1 );
		        lst.toArray( arr2 );
		        start_time = System.nanoTime();
		        radixSortA(arr1, h);
		        total_time1 += System.nanoTime() - start_time;
		        
		        start_time = System.nanoTime();
		        countingRadixSort(arr2, h);
		        total_time2 += System.nanoTime() - start_time;
		        lst.clear();
	        }
	        System.out.println( "Radix sort for "+ h + " length : " + total_time1 /10);
	        System.out.println( "Radix count sort for "+ h + " length : " + total_time2 /10);
        }
        
//        total_time = 0;
//        for (int j=0; j<10; j++)
//        {
//        	
//	        RandomStringGenerator rand = new RandomStringGenerator();
//	        for( int i = 0; i < 100; i++ )
//	        {
//	        	String str;
//	
//	        	str = rand.RandomString(LEN);
//	
//	            lst.add( str );
//	        }
//	        lst.toArray( arr1 );
//	        start_time = System.nanoTime();
//	        radixSortA(arr1, LEN);
//	        total_time += System.nanoTime() - start_time;
//	        
//        }
//        System.out.println( "Radix sort: " + total_time /10);

        
       // lst.toArray( arr2 );
        
       // print(arr2);
        // Print unsorted array
        //print(arr1);
      
//        long start, end;
//
//        start = System.currentTimeMillis( );
//        Arrays.sort( arr1 );
//        end = System.currentTimeMillis( );
//        System.out.println( "Quicksort: " + ( end - start ) );
//
//        start = System.currentTimeMillis( );
//        radixSortA( arr2, LEN );
//        end = System.currentTimeMillis( );
//        System.out.println( "Radix sort: " + ( end - start ) );
//
//        for( int i = 0; i < arr1.length; i++ )
//            if( !arr1[ i ].equals( arr2[ i ]  ) )
//                System.out.println( "OOPS!!" );
//        
//        // Print sorted array
//        print(arr2);
    }
    
}
