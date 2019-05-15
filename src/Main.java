import syntaxtree.*;
import minijava.*;

import java.io.*;

class Main {
    public static void main (String [] args){

	FileInputStream fis = null;

	if(args.length==0)							//check parameters
	{
		System.out.println("Usage: java Main [file1] [file1] [file2] [file3]..");
		System.exit(0);
	}
	try{
		for(int i=0;i<args.length;i++)						//for every file
		{
			fis = new FileInputStream(args[i]);
			System.out.println("//////////////////////"+args[i]+"//////////////////////");


			MiniJavaParser parser = new   MiniJavaParser(fis);

			STClassesVisitor STCV = new STClassesVisitor();
			Goal root = parser.Goal();						//check syntax

			System.err.println("Program parsed successfully.");
			root.accept(STCV, null);						//check class declaration
			if(STCV.GetSTD().getErrorFlag())
			{

				System.out.println("////////////////////////////////////////////"+"\n");
				continue;
			}


			STPVariablesDeclVisitor STPCTV=new STPVariablesDeclVisitor(STCV.GetSTD());
			root.accept(STPCTV, null);				//check variables declartion
			if(STPCTV.GetSTD().getErrorFlag())
			{
				System.out.println("////////////////////////////////////////////"+"\n");
				continue;
			}


			TypeCheckerVisitor TCV=new TypeCheckerVisitor(STCV.GetSTD());		//check statements and assignments
			root.accept(TCV, null);

			if(TCV.GetSTD().getErrorFlag())
			{
				System.out.println("////////////////////////////////////////////"+"\n");
				continue;
			}
			else{
				System.err.println("Program evaluated successfully.");
				TCV.GetSTD().PrintOffsets();								//Print classes offsets
			}

			System.out.println("////////////////////////////////////////////"+"\n");
		}



	}
	catch(ParseException ex){
	    System.out.println(ex.getMessage());
	}
	catch(FileNotFoundException ex){
	    System.err.println(ex.getMessage());
	}
	finally{
	    try{
		if(fis != null) fis.close();
	    }
	    catch(IOException ex){
		System.err.println(ex.getMessage());
	    }
	}
    }
}
