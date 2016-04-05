import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.plaf.SliderUI;


public class SortJoin {
	
	private static final int INT_MAX = 0;
	int fileCountR,fileCountS;
	public void open( String blocks,String r,String s ) throws IOException
	{
		System.out.println("In open");
		String output[] = r.split("/");
		String inputR = output[output.length-1];
		//System.out.println("inputR "+inputR);

		output = s.split("/");
		String inputS = output[output.length-1];
		//System.out.println("inputR "+inputR);
		//System.out.println("inputS "+inputS);
		FileWriter writer1 = new FileWriter(new File(inputR+"_"+inputS+"_join"));
		int numberOfMemBlocks = Integer.parseInt(blocks);
		int count = numberOfMemBlocks * join.numberOfRecordInBlock;
		ArrayList<String> tuplesInMBlocks = new ArrayList<String>();
		System.out.println("Before passing : "+r);
		BufferedReader reader = new BufferedReader(new FileReader(r));
		String line=reader.readLine();
		int filecount = 0;
		int numberOfTuplesInS = 0;
		int numberOfTuplesInR = 0;
		int i = 0;
		while( line!=null )
		{
			while( i < count && (line!=null))
			{
				tuplesInMBlocks.add( line );
				line = reader.readLine();
				i++;
				numberOfTuplesInR++;
			}
			
			// SORTING OF RELATION R 
			Collections.sort(tuplesInMBlocks, new Comparator<String>() {
		        @Override
		        public int compare(String str1, String str2)
		        {
		        	return  str1.split(",")[1].compareTo(str2.split(",")[1]);
		        }
		    });
			int length = tuplesInMBlocks.size();
			String filename = inputR+"_"+filecount;
			FileWriter writer = new FileWriter(new File(filename));
			for(int k = 0 ; k < length ; k++ )
			{				
				writer.write(tuplesInMBlocks.get(k)+"\n");
			}
			writer.close();
			i = 0;
			filecount++;
			tuplesInMBlocks.clear();
		}
		tuplesInMBlocks.clear();
		fileCountR = filecount;
		reader.close();
		reader = new BufferedReader(new FileReader(s));
		line=reader.readLine();
		//System.out.println("------->"line);
		filecount = 0;
		i = 0;
		while( line!=null )
		{
			while( i < count && line!=null)
			{
				tuplesInMBlocks.add( line );
				line = reader.readLine();
				i++;
				numberOfTuplesInS++;
			}
			// SORTING OF RELATION S
			Collections.sort(tuplesInMBlocks, new Comparator<String>() {
		        @Override
		        public int compare(String str1, String str2)
		        {
		        	return  str1.split(",")[0].compareTo(str2.split(",")[0]);
		        }
		    });
			int length = tuplesInMBlocks.size();
			String filename = inputS+"_"+filecount;
			FileWriter writer = new FileWriter(new File(filename));
			for(int k = 0 ; k < length ; k++ )
			{			
				//System.out.println("----"+tuplesInMBlocks.get(k));
				writer.write(tuplesInMBlocks.get(k)+"\n");
			}
			writer.close();
			i = 0;
			filecount++;
			tuplesInMBlocks.clear();
		}
		reader.close();
		fileCountS = filecount;
		System.out.println("In getnext ");
		System.out.println("blocks " + blocks);
		int numberOfBlocksInR = numberOfTuplesInR/join.numberOfRecordInBlock; // NUMBER OF BLOCKS IN R
		System.out.println("blocks R  " + numberOfBlocksInR);
		int numberOfBlocksInS = numberOfTuplesInS/join.numberOfRecordInBlock; // NUMBER OF BLOCKS IN S
		System.out.println("blocks S " + numberOfBlocksInS);
		int intr = ( Integer.parseInt( blocks ) * Integer.parseInt( blocks ));
		System.out.println("inter " + intr);
		if( numberOfBlocksInR + numberOfBlocksInS > intr )
		{
			System.out.println("Sort based Join is not possible ");
			
		}
		else
		{
			
			//System.out.println("Proceed to join sort based join");
			ArrayList<String> tuplesOfRForJoin = new ArrayList<String>(); // TUPLES OF R THAT IS USED FOR JOIN 
			ArrayList<String> topElementsForRSublist = new ArrayList<String>();// TOP ELEMENT FROM ALL THE SUBLIST OF R 
			ArrayList<String> tuplesOfSForJoin = new ArrayList<String>();// TUPLES OF S THAT IS USED FOR JOIN
			ArrayList<String> topElementsForSSublist = new ArrayList<String>();// TOP ELEMENT FROM ALL THE SUBLIST OF S
			ArrayList<BufferedReader> ReaderForS = new ArrayList<BufferedReader>();// bUFFERED READER FROM ALL THE SUBLIST OF S
			ArrayList<BufferedReader> ReaderForR = new ArrayList<BufferedReader>();// bUFFERED READER FROM ALL THE SUBLIST OF r
			for(int k = 0; k < fileCountR ; k++ )
			{
				ReaderForR.add(k,new BufferedReader(new FileReader(inputR+"_"+k)));
				topElementsForRSublist.add( k, ReaderForR.get(k).readLine() );
			}
			//System.out.println("Readerfor R "+ReaderForR.size());
			
			for(int k = 0; k < fileCountS ; k++ )
			{
				ReaderForS.add(k,new BufferedReader(new FileReader(inputS+"_"+k)));
				topElementsForSSublist.add( k, ReaderForS.get(k).readLine() );
			}
			
			while( !check(topElementsForRSublist) )
			{
				int min = Integer.MAX_VALUE;
				for( int k = 0 ; k < fileCountR ; k++ )
				{
					if( topElementsForRSublist.get(k)!=null && min > Integer.parseInt( topElementsForRSublist.get(k).split(",")[1]) )
					{
						min = Integer.parseInt( topElementsForRSublist.get(k).split(",")[1] );
					}
				}
				//if( min == 2147483647 )
				//{
				//	break;
				//}
				
				for( int k = 0 ; k < fileCountR ; k++ )
				{
					if( topElementsForRSublist.get(k)!=null && min == Integer.parseInt( topElementsForRSublist.get(k).split(",")[1]) )
					{
						tuplesOfRForJoin.add(topElementsForRSublist.get(k));
						String Line;
						while( (Line = ReaderForR.get(k).readLine() ) != null )
						{
							int num = Integer.parseInt(Line.split(",")[1]);
							//System.out.println(num);
							if( num == min )
								tuplesOfRForJoin.add(Line);
							else
							{
								break;
							}
						}
						topElementsForRSublist.set(k , Line);
						
						
					}
				}
				
				for( int k = 0; k < fileCountS ; k++ )
				{
					if( topElementsForSSublist.get(k)!=null && min == Integer.parseInt( topElementsForSSublist.get(k).split(",")[0]) )
					{
						tuplesOfSForJoin.add(topElementsForSSublist.get(k));
						String Line;
						while( (Line = ReaderForS.get(k).readLine() ) != null )
						{
							//System.out.println("---s----"+Line+"-----");
							int num = Integer.parseInt(Line.split(",")[0]);
							if( num == min )
								tuplesOfSForJoin.add(Line);
							else
							{
								break;
							}
						}
						topElementsForSSublist.set( k ,Line );
					}
					else if( topElementsForSSublist.get(k)!=null && min > Integer.parseInt( topElementsForSSublist.get(k).split(",")[0]) )
					{
						String Line;
						while( (Line = ReaderForS.get(k).readLine() ) != null )
						{
							int num = Integer.parseInt(Line.split(",")[0]);
							if( num == min )
								tuplesOfSForJoin.add(Line);
							else if( num > min )
							{
								break;
							}
						}
						topElementsForSSublist.set(k , Line);
					}
				}
				//System.out.println("-----"+tuplesOfRForJoin.size());
				//System.out.println(tuplesOfSForJoin.size()+"----");
				 
				for(int l = 0 ; l < tuplesOfRForJoin.size() ; l++ )
				{
					//if((tuplesOfRForJoin.get(l).split(",")[1].equals("100000005")))
						//System.out.println(tuplesOfRForJoin.get(l));
					for( int m = 0 ; m < tuplesOfSForJoin.size() ; m++ )
					{
						//if((tuplesOfSForJoin.get(m).split(",")[0].equals("100000005")))
							//System.out.println(tuplesOfSForJoin.get(m));
						writer1.write(tuplesOfRForJoin.get(l)+","+tuplesOfSForJoin.get(m).split(",")[1]+"\n");
					}
				}
				tuplesOfRForJoin.clear();
				tuplesOfSForJoin.clear();
			}
			writer1.close();
			
			
		}
		
		String str1;
		System.out.println("In close");
		for(int k = 0; k < fileCountR; k++)
		{
			str1=inputR+"_"+k; 
			new File(str1).delete();
		}
		for(int k = 0; k < fileCountS; k++)
		{
			str1=inputS+"_"+k; 
			new File(str1).delete();
		}
	}
	
	public boolean check(ArrayList<String> s)
	{
		int count = 0;
		int length = s.size();
		for( int i = 0 ; i < length ; i++ )
		{
			if( s.get(i) == null )
			{
				count++;
			}
		}
		if( count == length )
		{
			return true;
		}
		else
			return false;
	}
}
