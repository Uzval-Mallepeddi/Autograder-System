package userAssignment;

import java.util.Scanner;

public class Solution 
{
	public static void main(String args[])
	{
		Scanner sc= new Scanner(System.in);
		
		int a = sc.nextInt();
			int b = sc.nextInt();
			
			divideInt(a,b);
		
				
		sc.close();
	}
	
	public static void divideInt(int p, int q)
	{
		System.out.println(p/q);
	}
}
