import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;


public class hashjoin {
	
	ArrayList< ArrayList<String> > check;
	//Vector< Vector<String> > check;
	ArrayList<FileWriter> WriterOfR = new ArrayList<FileWriter>();
	ArrayList<FileWriter> WriterOfS = new ArrayList<FileWriter>();
	FileWriter writer ;
	int numberOfBlocks;
	String inputR;
	String inputS;
	String outputFilename ;
	public void open( String blocks,String r,String s ) throws IOException
	{
		String output[] = r.split("/");
		inputR = output[output.length-1];
		output = s.split("/");
		inputS = output[output.length-1];
		outputFilename = inputR+"_"+inputS+"_join";
		numberOfBlocks = Integer.parseInt(blocks);
		check = new ArrayList< ArrayList<String> >(numberOfBlocks - 1);
		System.out.println("In open function");
		int count = 0;
		
		int filecount = 0;
		
		FileWriter writer;
		String arraylistName = "r";
		String filename = inputR;
		String filename1 = inputS;
		for( int i = 0 ; i < numberOfBlocks-1 ; i++ )
		{
			WriterOfR.add(new FileWriter(new File(filename+"_"+i)));
			WriterOfS.add(new FileWriter(new File(filename1+"_"+i)));
			check.add(i,new ArrayList<String>());
		}
		//check = new ArrayList< ArrayList<String> >(numberOfBlocks - 1);
		BufferedReader reader = new BufferedReader(new FileReader(r));
		int num;
		String line = reader.readLine();
		while( (line ) != null)
		{
			num = Integer.parseInt(line.split(",")[1])%( numberOfBlocks - 1 );
			if(check.get(num).isEmpty())
			{
				check.get(num).add(line);
			}
			else
			{
				if(check.get(num).size() < join.numberOfRecordInBlock )
				{
					check.get(num).add(line);
				}
				else
				{
					//System.out.println("In");
					int length = check.get(num).size();
					for( int q = 0 ; q < length ; q++ )
					{
						WriterOfR.get(num).write(check.get(num).get(q)+"\n");			
					}
					check.get(num).clear();
					check.get(num).add(line);
					
				}
			}
			if( (line = reader.readLine()) == null )
				break;
			
		}
		int siz = check.size();
		for( int q = 0 ; q < siz ; q++ )
		{
			if( !check.get(q).isEmpty() )
			{
				int len = check.get(q).size();
				for(int h = 0 ; h < len ; h++ )
				{
					WriterOfR.get(q).write(check.get(q).get(h)+"\n");
				}
				check.get(q).clear();
			}
			
		}
		reader.close();
		reader = new BufferedReader(new FileReader(s));
		line = reader.readLine();
		/*while( (line) != null)
		{
			num = Integer.parseInt((line.split(",")[0]))%( numberOfBlocks - 1 );
			WriterOfS.get(num).write(line+"\n");
			if( (line = reader.readLine()) == null )
				break;
		}*/
		while( (line ) != null)
		{
			num = Integer.parseInt(line.split(",")[0])%( numberOfBlocks - 1 );
			if(check.get(num).isEmpty())
			{
				check.get(num).add(line);
			}
			else
			{
				if(check.get(num).size() < join.numberOfRecordInBlock )
				{
					check.get(num).add(line);
				}
				else
				{
					//System.out.println("In");
					int length = check.get(num).size();
					for( int q = 0 ; q < length ; q++ )
					{
						WriterOfS.get(num).write(check.get(num).get(q)+"\n");			
					}
					check.get(num).clear();
					check.get(num).add(line);
					
				}
			}
			if( (line = reader.readLine()) == null )
				break;
			
		}
		siz = check.size();
		for( int q = 0 ; q < siz ; q++ )
		{
			if( !check.get(q).isEmpty() )
			{
				int len = check.get(q).size();
				for(int h = 0 ; h < len ; h++ )
				{
					WriterOfS.get(q).write(check.get(q).get(h)+"\n");
				}
				check.get(q).clear();
			}
			
		}
		reader.close();
		for( int i = 0 ; i < numberOfBlocks - 1  ; i++ )
		{
			WriterOfR.get(i).close();
			WriterOfS.get(i).close();
		}
		//close();
	}
	public void getnext() throws IOException
	{
		System.out.println("In getnext");
		String lineOfR,lineOfS;
		writer = new FileWriter(new File(outputFilename));
		BufferedReader readerOfS,readerOfR;
		for( int i = 0 ; i < numberOfBlocks-1 ; i++ )
		{
			readerOfR = new BufferedReader(new FileReader(inputR+"_"+i));
			//System.out.println(lineOfR = readerOfR.readLine());
			while( (lineOfR = readerOfR.readLine()) != null)
			{
				//System.out.println("I am in");
				readerOfS = new BufferedReader(new FileReader(inputS+"_"+i));
				while( (lineOfS = readerOfS.readLine()) != null )
				{
					//System.out.println("I am in");
					if( lineOfR.split(",")[1].equals(lineOfS.split(",")[0]))
					{
						//System.out.println("I am in");
						writer.write(lineOfR+","+lineOfS.split(",")[1]+"\n");
					}
				}
				readerOfS.close();
			}
			readerOfR.close();
			
		}
		String str1;
		for(int k = 0; k < numberOfBlocks - 1; k++)
		{
			//System.out.println("in");
			str1=inputR+"_"+k; 
			new File(str1).delete();
		}
		for(int k = 0; k < numberOfBlocks - 1 ; k++)
		{
			str1=inputS+"_"+k; 
			new File(str1).delete();
		}
	}
	
	public void close() throws IOException
	{
		System.out.println("In close");
		writer.close();
		
	}

}
