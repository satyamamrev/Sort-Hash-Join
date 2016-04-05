import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;


public class join {

	static int numberOfRecordInBlock = 100;
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		if( args.length != 4 )
		{
			System.out.println("Check Command Line Arguments!!!!!!");
			return ;
		}
		else
		{
			if( args[2].equals("hash") )
			{
				System.out.println("Perform hash based join");
				hashjoin hash = new hashjoin();
				hash.open(args[3], args[0], args[1]);
				hash.getnext();
				hash.close();
			}
			else if( args[2].equals("sort") )
			{
				System.out.println("Perform sort based join");
				//System.out.println(args[3]);
				//System.out.println(args[1]);
				//System.out.println(args[0]);
				SortJoin sortjoin = new SortJoin();
				sortjoin.open(args[3], args[0],args[1] );
			} 
			else
			{
				System.out.println("Wrong input!!!!!!!!");
				
				return ;
			}
		}
		

	}
	
	

}
