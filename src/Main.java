import syntaxtree.*;
import minijava.*;

import java.io.*;

class Main {
    public static void main (String [] args){

	FileInputStream fis = null;
	try{
		for(int i=0;i<args.length;i++)
		{
			fis = new FileInputStream(args[i]);
			System.out.println("//////////////////////"+args[i]+"//////////////////////");
			String res;

			MiniJavaParser parser = new   MiniJavaParser(fis);

			STClassesVisitor STCV = new STClassesVisitor();
			Goal root = parser.Goal();

			System.err.println("Program parsed successfully.");
			res=root.accept(STCV, null);
			if(STCV.GetSTD().getErrorFlag())
			{

				System.out.println("////////////////////////////////////////////"+"\n");
				continue;
			}


			STPVariablesDeclVisitor STPCTV=new STPVariablesDeclVisitor(STCV.GetSTD());
			res=root.accept(STPCTV, null);
			if(STPCTV.GetSTD().getErrorFlag())
			{
				System.out.println("////////////////////////////////////////////"+"\n");
				continue;
			}


			TypeCheckerVisitor TCV=new TypeCheckerVisitor(STCV.GetSTD());
			root.accept(TCV, null);

			if(TCV.GetSTD().getErrorFlag())
			{
				System.out.println("////////////////////////////////////////////"+"\n");
				continue;
			}
			else{
				System.err.println("Program evaluated successfully.");
				TCV.GetSTD().PrintOffsets();
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
