package sorting;

import java.util.Random;
import java.util.Arrays;

/**
 * A class that contains several sorting routines,
 * implemented as static methods.
 * Arrays are rearranged with smallest item first,
 * using compareTo.
 * @author Mark Allen Weiss
 */
public final class Sort2
{
    /**
     * Simple insertion sort.
     * @param a an array of Comparable items.
     */
    public static <AnyType extends Comparable<? super AnyType>>
    void insertionSort( AnyType [ ] a )
    {
        int j;

        for( int p = 1; p < a.length; p++ )
        {
            AnyType tmp = a[ p ];
            for( j = p; j > 0 && tmp.compareTo( a[ j - 1 ] ) < 0; j-- )
                a[ j ] = a[ j - 1 ];
            a[ j ] = tmp;
        }
    }

    /**
     * Shellsort, using Shell's (poor) increments.
     * @param a an array of Comparable items.
     */
    public static <AnyType extends Comparable<? super AnyType>>
    void shellsort( AnyType [ ] a )
    {
        int j;

        for( int gap = a.length / 2; gap > 0; gap /= 2 )
            for( int i = gap; i < a.length; i++ )
            {
                AnyType tmp = a[ i ];
                for( j = i; j >= gap &&
                            tmp.compareTo( a[ j - gap ] ) < 0; j -= gap )
                    a[ j ] = a[ j - gap ];
                a[ j ] = tmp;
            }
    }


    /**
     * Internal method for heapsort.
     * @param i the index of an item in the heap.
     * @return the index of the left child.
     */
    private static int leftChild( int i )
    {
        return 2 * i + 1;
    }
    
    /**
     * Internal method for heapsort that is used in deleteMax and buildHeap.
     * @param a an array of Comparable items.
     * @index i the position from which to percolate down.
     * @int n the logical size of the binary heap.
     */
    private static <AnyType extends Comparable<? super AnyType>>
    void percDown( AnyType [ ] a, int i, int n )
    {
        int child;
        AnyType tmp;

        for( tmp = a[ i ]; leftChild( i ) < n; i = child )
        {
            child = leftChild( i );
            if( child != n - 1 && a[ child ].compareTo( a[ child + 1 ] ) < 0 )
                child++;
            if( tmp.compareTo( a[ child ] ) < 0 )
                a[ i ] = a[ child ];
            else
                break;
        }
        a[ i ] = tmp;
    }
    
    /**
     * Standard heapsort.
     * @param a an array of Comparable items.
     */
    public static <AnyType extends Comparable<? super AnyType>>
    void heapsort( AnyType [ ] a )
    {
        for( int i = a.length / 2 - 1; i >= 0; i-- )  /* buildHeap */
            percDown( a, i, a.length );
        for( int i = a.length - 1; i > 0; i-- )
        {
            swapReferences( a, 0, i );                /* deleteMax */
            percDown( a, 0, i );
        }
    }


    /**
     * Mergesort algorithm.
     * @param a an array of Comparable items.
     */
    public static <AnyType extends Comparable<? super AnyType>>
    void mergeSort( AnyType [ ] a )
    {
        AnyType [ ] tmpArray = (AnyType[]) new Comparable[ a.length ];

        mergeSort( a, tmpArray, 0, a.length - 1 );
    }

    /**
     * Internal method that makes recursive calls.
     * @param a an array of Comparable items.
     * @param tmpArray an array to place the merged result.
     * @param left the left-most index of the subarray.
     * @param right the right-most index of the subarray.
     */
    private static <AnyType extends Comparable<? super AnyType>>
    void mergeSort( AnyType [ ] a, AnyType [ ] tmpArray,
               int left, int right )
    {
        if( left < right )
        {
            int center = ( left + right ) / 2;
            mergeSort( a, tmpArray, left, center );
            mergeSort( a, tmpArray, center + 1, right );
            merge( a, tmpArray, left, center + 1, right );
        }
    }

    /**
     * Internal method that merges two sorted halves of a subarray.
     * @param a an array of Comparable items.
     * @param tmpArray an array to place the merged result.
     * @param leftPos the left-most index of the subarray.
     * @param rightPos the index of the start of the second half.
     * @param rightEnd the right-most index of the subarray.
     */
    private static <AnyType extends Comparable<? super AnyType>>
    void merge( AnyType [ ] a, AnyType [ ] tmpArray, int leftPos, int rightPos, int rightEnd )
    {
        int leftEnd = rightPos - 1;
        int tmpPos = leftPos;
        int numElements = rightEnd - leftPos + 1;

        // Main loop
        while( leftPos <= leftEnd && rightPos <= rightEnd )
            if( a[ leftPos ].compareTo( a[ rightPos ] ) <= 0 )
                tmpArray[ tmpPos++ ] = a[ leftPos++ ];
            else
                tmpArray[ tmpPos++ ] = a[ rightPos++ ];

        while( leftPos <= leftEnd )    // Copy rest of first half
            tmpArray[ tmpPos++ ] = a[ leftPos++ ];

        while( rightPos <= rightEnd )  // Copy rest of right half
            tmpArray[ tmpPos++ ] = a[ rightPos++ ];

        // Copy tmpArray back
        for( int i = 0; i < numElements; i++, rightEnd-- )
            a[ rightEnd ] = tmpArray[ rightEnd ];
    }

    /**
     * Quicksort algorithm.
     * @param a an array of Comparable items.
     */
    public static <AnyType extends Comparable<? super AnyType>>
    void quicksort( AnyType [ ] a )
    {
        quicksort( a, 0, a.length - 1 );
    }

    private static final int CUTOFF = 3;

    /**
     * Method to swap to elements in an array.
     * @param a an array of objects.
     * @param index1 the index of the first object.
     * @param index2 the index of the second object.
     */
    public static <AnyType> void swapReferences( AnyType [ ] a, int index1, int index2 )
    {
        AnyType tmp = a[ index1 ];
        a[ index1 ] = a[ index2 ];
        a[ index2 ] = tmp;
    }

    /**
     * Return median of left, center, and right.
     * Order these and hide the pivot.
     */
    private static <AnyType extends Comparable<? super AnyType>>
    AnyType median3( AnyType [ ] a, int left, int right )
    {
        int center = ( left + right ) / 2;
        if( a[ center ].compareTo( a[ left ] ) < 0 )
            swapReferences( a, left, center );
        if( a[ right ].compareTo( a[ left ] ) < 0 )
            swapReferences( a, left, right );
        if( a[ right ].compareTo( a[ center ] ) < 0 )
            swapReferences( a, center, right );

            // Place pivot at position right - 1
        swapReferences( a, center, right - 1 );
        return a[ right - 1 ];
    }

    /**
     * Internal quicksort method that makes recursive calls.
     * Uses median-of-three partitioning and a cutoff of 10.
     * @param a an array of Comparable items.
     * @param left the left-most index of the subarray.
     * @param right the right-most index of the subarray.
     */
    private static <AnyType extends Comparable<? super AnyType>>
    void quicksort( AnyType [ ] a, int left, int right )
    {
        if( left + CUTOFF <= right )
        {
            AnyType pivot = median3( a, left, right );

                // Begin partitioning
            int i = left, j = right - 1;
            for( ; ; )
            {
                while( a[ ++i ].compareTo( pivot ) < 0 ) { }
                while( a[ --j ].compareTo( pivot ) > 0 ) { }
                if( i < j )
                    swapReferences( a, i, j );
                else
                    break;
            }

            swapReferences( a, i, right - 1 );   // Restore pivot

            quicksort( a, left, i - 1 );    // Sort small elements
            quicksort( a, i + 1, right );   // Sort large elements
        }
        else  // Do an insertion sort on the subarray
            insertionSort( a, left, right );
    }

    /**
     * Internal insertion sort routine for subarrays
     * that is used by quicksort.
     * @param a an array of Comparable items.
     * @param left the left-most index of the subarray.
     * @param right the right-most index of the subarray.
     */
    private static <AnyType extends Comparable<? super AnyType>>
    void insertionSort( AnyType [ ] a, int left, int right )
    {
        for( int p = left + 1; p <= right; p++ )
        {
            AnyType tmp = a[ p ];
            int j;

            for( j = p; j > left && tmp.compareTo( a[ j - 1 ] ) < 0; j-- )
                a[ j ] = a[ j - 1 ];
            a[ j ] = tmp;
        }
    }

    /**
     * Quick selection algorithm.
     * Places the kth smallest item in a[k-1].
     * @param a an array of Comparable items.
     * @param k the desired rank (1 is minimum) in the entire array.
     */     
    public static <AnyType extends Comparable<? super AnyType>>
    void quickSelect( AnyType [ ] a, int k )
    {
        quickSelect( a, 0, a.length - 1, k );
    }

    /**
     * Internal selection method that makes recursive calls.
     * Uses median-of-three partitioning and a cutoff of 10.
     * Places the kth smallest item in a[k-1].
     * @param a an array of Comparable items.
     * @param left the left-most index of the subarray.
     * @param right the right-most index of the subarray.
     * @param k the desired index (1 is minimum) in the entire array.
     */
    private static <AnyType extends Comparable<? super AnyType>>
    void quickSelect( AnyType [ ] a, int left, int right, int k )
    {
        if( left + CUTOFF <= right )
        {
            AnyType pivot = median3( a, left, right );

                // Begin partitioning
            int i = left, j = right - 1;
            for( ; ; )
            {
                while( a[ ++i ].compareTo( pivot ) < 0 ) { }
                while( a[ --j ].compareTo( pivot ) > 0 ) { }
                if( i < j )
                    swapReferences( a, i, j );
                else
                    break;
            }

            swapReferences( a, i, right - 1 );   // Restore pivot

            if( k <= i )
                quickSelect( a, left, i - 1, k );
            else if( k > i + 1 )
                quickSelect( a, i + 1, right, k );
        }
        else  // Do an insertion sort on the subarray
            insertionSort( a, left, right );
    }


    private static void checkSort( Integer [ ] a )
    {
        for( int i = 0; i < a.length-1; i++ )
            if( a[i] > a[i+1])
                System.out.println( "Error at " + i );
        System.out.println( "Finished checksort" );
    }

    private static void checkSort( String [ ] a )
    {
        for( int i = 0; i < a.length-1; i++ )
            if( a[i].compareTo(a[i+1]) > 0)
                System.out.println( "Error at " + i );
        System.out.println( "Finished checksort" );
    }

    private static void print( Integer [ ] a )
    {
        for( int i = 0; i < a.length; i++ )
        	System.out.print(a[i] + " ");
        System.out.println();
    }

    // Some tests of the sorting algorithms
    private static final int NUM_ITEMS = 100000;
    private static int theSeed = 0;
    
    public static void Random_Intgenerator(String[] a, int length)
	{
    	RandomStringGenerator rand = new RandomStringGenerator();
		for (int i=0; i<a.length; i++)
		{
			a[i] = rand.RandomString(length);
		}
	}
    
    public static long  Measure_mergeSort(String[] a)
	{
    	long time_start;
    	
    	//Random_Intgenerator(a, length);
    	time_start = System.nanoTime();
    	Sort2.mergeSort(a);
    	return System.nanoTime() - time_start;
	}
    
    public static long  Measure_quickSort(String[] a)
	{
    	long time_start;
    	
    	//Random_Intgenerator(a, length);
    	time_start = System.nanoTime();
    	Sort2.mergeSort(a);
    	return System.nanoTime() - time_start;
	}
    
    public static long  Measure_heapSort(String[] a)
	{
    	long time_start;
    	
    	//Random_Intgenerator(a, length);
    	time_start = System.nanoTime();
    	Sort2.heapsort(a);
    	return System.nanoTime() - time_start;
	}
    
    public static long  Measure_dualpivotSort(String[] a)
	{
    	long time_start;
    	
    	//Random_Intgenerator(a, length);
    	time_start = System.nanoTime();
    	Arrays.sort(a);
    	return System.nanoTime() - time_start;
	}
    
    public static void  Measure_sorttime(String[] a, int length)
   	{
    	long total_time1 = 0, total_time2 = 0, total_time3 = 0, total_time4 = 0;
       	
    	String[] a1 = new String[a.length], a2 = new String[a.length], a3 = new String[a.length], a4 = new String[a.length];
    	
    	
    	
    	
        for (int i=0; i<10; i++)
        {
        	Random_Intgenerator(a, length);
        	a1 =a.clone();
        	a2 = a.clone();
        	a3 = a.clone();
        	a4 = a.clone();
        	total_time1 += Measure_mergeSort(a1);
        	total_time2 += Measure_quickSort(a2);
        	total_time3 += Measure_heapSort(a3);
        	total_time4 += Measure_dualpivotSort(a4);
        	checkSort(a1);
        	checkSort(a2);
        	checkSort(a3);
        	checkSort(a4);
        }
        
        System.out.println("The mergesort time for length " + length + " is " + total_time1 / 10);
        System.out.println("The quicksort time for  length " + length + " is " + total_time2 / 10);
        System.out.println("The heapsort time for length " + length + " is " + total_time3 / 10);
        System.out.println("The dualpivotSort time for length " + length + " is " + total_time4 / 10);
        
   	}
    
    public static void main( String [ ] args )
    {
        String [ ] a = new String[ 100000 ];
       // Random rand = new Random(System.currentTimeMillis());
        //long total_time = 0;
        // Fill array a with random numbers
        //Random_Intgenerator(a);

//        for (int i=0; i<10; i++)
//        {
//        	total_time += Measure_mergeSort(a, 4);
//        	checkSort(a);
//        }
//        System.out.println("The mergesort time for length 4 is " + total_time / 10);
//        
//        total_time = 0;
//        for (int i=0; i<10; i++)
//        {
//        	total_time += Measure_quickSort(a, 4);
//        	checkSort(a);
//        }
//        System.out.println("The quicksort time for length 4 is " + total_time / 10);
//        
//        total_time = 0;
//        for (int i=0; i<10; i++)
//        {
//        	total_time += Measure_heapSort(a, 4);
//        	checkSort(a);
//        }
//        System.out.println("The heapsort time for length 4 is " + total_time / 10);
//        
//        total_time = 0;
//        for (int i=0; i<10; i++)
//        {
//        	total_time += Measure_dualpivotSort(a, 4);
//        	checkSort(a);
//        }
//        System.out.println("The dualpivotSort time for length 4 is " + total_time / 10);
        
        for (int i=4; i<=10; i+=2)
        {
        	Measure_sorttime(a, i);
        }
        
//        // Fill array a with random numbers
//        for( int i = 0; i < a.length; i++ )
//            a[i] = rand.nextInt(NUM_ITEMS * 5);
//        // Sort
//        Sort.quicksort(a);
//        checkSort( a );
//
//        // Fill array a with random numbers
//        for( int i = 0; i < a.length; i++ )
//            a[i] = rand.nextInt(NUM_ITEMS * 5);
//        // Sort
//        Sort.heapsort(a);
//        checkSort( a );
//
//        // Fill array a with random numbers
//        for( int i = 0; i < a.length; i++ )
//            a[i] = rand.nextInt(NUM_ITEMS * 5);
//        // Sort dual pivot sorting
//        Arrays.sort(a);
//        //Sort.print(a);
//        checkSort( a );
//
//        // Sorting String objects
//        // Fill array a with random numbers
//        String [ ] b = new String[ NUM_ITEMS];
//
//        for( int i = 0; i < a.length; i++ )
//            b[i] = rand.nextInt(NUM_ITEMS * 5)+"";
//        // Sort
//        Sort.mergeSort(b);
//        checkSort(b);
//        
//        // Fill array a with random numbers
//        for( int i = 0; i < a.length; i++ )
//            a[i] = rand.nextInt(NUM_ITEMS * 5);
//        // Quick select
//        int k = 10; // k must be greater than 0 and less than n
//        Sort.quickSelect( a, k); //NUM_ITEMS / 2 );
//        System.out.println("Quickselect:");
//        Sort.print(a);
//        System.out.println("kth smallest = " + a[k-1]);
//        
    }
}
