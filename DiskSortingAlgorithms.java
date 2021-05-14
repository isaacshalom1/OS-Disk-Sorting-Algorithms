package DiskSortingAlgos;

import java.util.*;

public class DiskSortingAlgorithms {
	
	public static class sstfNode {
		 int distance = 0;
		 boolean track = false;
	}
	
	public static void main(String[] args) {
		
		int[] requests = {4078, 153, 2819, 3294, 1433, 211, 1594,
		                  2004, 2335, 2007, 771, 1043, 3950, 2784, 1881, 2931, 
		                  3599, 1245, 4086, 520, 3901, 2866, 947, 3794, 2353, 3970,
		                  3948, 1815, 4621, 372, 2684, 3088, 827, 3126, 2083, 584, 4420,
		                  1294, 917, 3659, 2868, 100, 1581, 4581, 1664, 1001, 
		                  1213, 3439, 4706};
		
		//command line arguments 
		int head = Integer.parseInt(args[0]);
		String movement = args[1];	
		
		System.out.println("Total Head Movement For FCFS: " + FCFS(requests, head));
		System.out.println("Total Head Movement For SSTF: " + SSTF(requests, head));
		System.out.println("Total Head Movement For SCAN: " + Scan(requests, head, movement));
		System.out.println("Total Head Movement For CSCAN: " + CSCAN(requests, head, movement));
		System.out.println("Total Head Movement For LOOK: " + LOOK(requests, head, movement));
		System.out.println("Total Head Movement For CLOOK: " + CLOOK(requests, head, movement));


	}
	

	public static int FCFS(int arr[], int head) {
		//initialize total to be == to the Max of the head and first headition in arr
		// - min of head and first head of arr
		int total = Math.max(arr[0], head) - Math.min(arr[0], head); 
		
		//loop through array to add it all up 
		for(int i =1; i<arr.length; i++) {
			head = arr[i-1];
			total += (Math.max(arr[i], head) - Math.min(arr[i], head));		
		}
		
		return total; //return sum 
	}
	

	//SSTF helper function 
	public static void trackDifference(int arr[], int head, sstfNode node[]) {
		
	   for (int i = 0; i < node.length; i++) //size of node arr
	       node[i].distance = Math.abs(arr[i] - head); //calculate absolute value to track distance
	}

	
	//SSTF helper function 
	public static int findMin(sstfNode node[]) { //find track min distance from our head
		
	   int head = Integer.MIN_VALUE; 
	   int min = Integer.MAX_VALUE;

	   for (int i = 0; i < node.length; i++) {
	       if (!node[i].track && min > node[i].distance) { //if true and the min distance >node[i] distance
	            
	    	   //set values
	           min = node[i].distance; 
	           head = i;
	       }
	   }
	   return head; //return index of track 
	}

	public static int SSTF(int arr[], int head) {     
 
	   sstfNode node[] = new sstfNode[arr.length]; //size of requests
	    
	   for (int i = 0; i < node.length; i++)
	    
	       node[i] = new sstfNode(); //create nodes
	    
	     
	   int count = 0; //number of SSTF movements 
	    
      // stores sequence of disk access 
	   int[] sequence = new int[arr.length + 1];
	    
	   for (int i = 0; i < arr.length; i++) {
	       
	       sequence[i] = head;
	       trackDifference(arr, head, node); //call helper function 
	        
	       int index = findMin(node); //helper
	        
	       node[index].track = true;
	        
	       // increase the total count
	       count += node[index].distance;
	        
	       //  new head
	       head = arr[index];
	   }      
	   // for last track track
	   sequence[sequence.length - 1] = head;
	    
	   return count;    //return total                                                                                              
	}
	
	
	public static int Scan(int[] arr, int head, String direction) {
		
		Arrays.sort(arr); //sort array
		//initialize values
		int total =0;	
		int start =0;
		int minDistance = Integer.MAX_VALUE;
		
		for(int i=0; i<arr.length; i++) {
			int distance = Math.abs(arr[i] - head); //get distance 
			if(direction.equals("left")) { //if its left 
				if (distance < minDistance && head >= arr[i]) { //if distance < minimum distance && head >= requests[i]
					minDistance = distance;
					start =i;
				}
			}
			else { //otherwise 
				if(distance < minDistance && head <= arr[i]) {
					minDistance = distance;
					start = i;
				}
			}
		}
		
		
		if (direction.equals("left")) { //if left 
			for(int i=start; i>=0; i--) { //go from start downwards 
				
				//sum to be == to the head and head in arr
				// - min of head and head of arr
				total += (Math.max(head,  arr[i]) - Math.min(head,  arr[i])); 
				head = arr[i];
			}
			total += arr[0]; //add spot 0
			
			for(int i=start+1; i<arr.length; i++) {
				total += (Math.max(head,  arr[i]) - Math.min(head,  arr[i]));
				head = arr[i];
			}
		}
		else {
			for(int i=start; i<arr.length; i++) {
				//sum to be == to the head and head in arr
				// - min of head and head of arr
				total += (Math.max(head,  arr[i]) - Math.min(head,  arr[i]));
				head = arr[i];
			}
			
			total += (5000 - arr[arr.length -1]);
			for(int i= start-1; i >= 0; i--) {
				//sum to be == to the head and head in arr
				// - min of head and head of arr
				total += (Math.max(head,  arr[i]) - Math.min(head,  arr[i]));
				head = arr[i];
			}
		}
		return total;
		
	}
	public static int CSCAN(int[] requests, int head, String direction) {
		//similar to scam with modifications to improve it 
		Arrays.sort(requests); //sort array 
		
		int total = 0;
		int start = 0;
		int minDistance = Integer.MAX_VALUE;
		
		for (int i = 0; i < requests.length; i++) {			
			int distance = Math.abs(requests[i] - head); //set the distance as the requests num - head 
		    if (direction.equals( "left")) { //if left 		    	
		    	if (distance < minDistance && head >= requests[i]) { //if distance < minimum and head > requests[i]
		    		minDistance = distance; //set distance and start index
		            start = i;
		            
		    	}	    	
		    }
		    else { //else if right and not left 
		    	if (distance < minDistance && head <= requests[i]) {		    		
		    		minDistance = distance;
		    		start = i;		    		
		    	}		    	
		    }		    
		}
		
		if(direction.equals("left")) { //if left 
			for (int i = start; i >= 0; i--) { //use the starting index 
				total += (Math.max(head, requests[i]) - Math.min(head, requests[i])); //add up the total movements 
				head = requests[i]; //set the new head 
				
			}
			total += head; //add the head 
			total += 5000; //add size of the drive 
			
			for (int i = requests.length - 1; i > start; i--) {	//traverse backwards 		
				total += (Math.max(head, requests[i]) - Math.min(head, requests[i])); //add to total 
				head = requests[i];
				
			}
			
		}
		
		
		else {//if its right use the right start index and do that instead 
			for (int i = start; i < requests.length; i++) {			
				total += (Math.max(head, requests[i]) - Math.min(head, requests[i]));
				head = requests[i];			
			}
			
			total += (5000 - head);
			total += 5000;
			
			for (int i = 0; i < start; i++) {
				total += (Math.max(head, requests[i]) - Math.min(head, requests[i]));
				head = requests[i];				
			}			
		}
		
		return total; //head movements 
		
	}
	
	public static int LOOK(int[] requests, int head, String direction) {
		//similar to scan 
		Arrays.sort(requests);
		int total = 0;
		int minDistance = Integer.MAX_VALUE;
		int start =0;
		
		for( int i=0; i<requests.length; i++) { //go through the requestsay 
			
			int distance = Math.abs(requests[i] - head);//set the distance as the requests num - head 
			if(direction.equals("left")) { //if left 
				if(distance <minDistance && head >= requests[i]) {  //if distance < minimum and head > requests[i]
					minDistance = distance;
					start =i;
				}
			}
			else { //if right 
				if (distance < minDistance && head <= requests[i]) {
					minDistance = distance;
					start =i;
				}
			}
		}
		
		if (direction.equals("left")){
			for(int i=start; i>=0; i--) { //traverse from starting index that was set 
				total += (Math.max(head, requests[i]) - Math.min(head, requests[i])); //start adding up total 
				head = requests[i]; //set the head 
			}
			//do it again from start+1
			for(int i= start+1; i<requests.length; i++) {
				total += (Math.max(head, requests[i]) - Math.min(head, requests[i])); // add up
				head = requests[i];
				
			}
		}
		else { //else if direction is right 
			//loop opposite 
			for(int i=start; i<requests.length; i++) {
				total += (Math.max(head, requests[i]) - Math.min(head, requests[i]));
				head = requests[i];
			}
			for(int i= start-1; i > 0; i--) {
				total += (Math.max(head, requests[i]) - Math.min(head, requests[i])); 
				head = requests[i];
			
			}
		}
		
		return total;
	}
	
	public static int CLOOK(int[] requests, int head, String direction) {
		//algorithm is very similar to LOOK with modifications 
		Arrays.sort(requests); //sort the requestsay 
		int total =0;
		
		int start =0; 
		int minDistance = Integer.MAX_VALUE;
		
		for( int i=0; i<requests.length; i++) {
			
			int distance = Math.abs(requests[i] - head); //fidn distance 
			if(direction.equals("left")) {
				if(distance <minDistance && head >= requests[i]) { 
					minDistance = distance;
					start =i;
				}
			}
			else {
				if (distance < minDistance && head <= requests[i]) {
					minDistance = distance;  //if distance < minimum and head > requests[i] set start and distance 
					start =i;
				}
			}
		}
		if(direction.equals("left")) { //if left start looping from start -> 0 adding it up 
			for(int i = start; i>= 0; i--) {
				total += (Math.max(head, requests[i]) - Math.min(head, requests[i]));
				head = requests[i];
				
			}
			total += (5000 - requests[0]); //modification subtract requests[0] from disk drive an add to total 
			for(int i= requests.length -1; i> start; i--) {
				total += (Math.max(head, requests[i]) - Math.min(head, requests[i]));
				head = requests[i];
			}
		}
		else { //if right loop opposite way from left to set head and add up total 
			for(int i= start; i<requests.length; i++) {
				total += (Math.max(head, requests[i]) - Math.min(head, requests[i]));
				head = requests[i];
			}
			total += (5000 - requests[requests.length-1]);
			for(int i=0; i<start;i++) {
				total += (Math.max(head, requests[i]) - Math.min(head, requests[i]));
				head = requests[i];
				
			}
			
				
		}
		return total;
	}
}
	            
	            



