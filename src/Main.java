import syntaxtree.*;
import minijava.*;

import java.io.*;

class Main {
    public static void main (String [] args){
	if(args.length != 1){
	    System.err.println("Usage: java Driver <inputFile>");
	    System.exit(1);
	}
	FileInputStream fis = null;
	try{
	    fis = new FileInputStream(args[0]);
	    MiniJavaParser parser = new   MiniJavaParser(fis);

	    STClassesVisitor STPV = new STClassesVisitor();
	    Goal root = parser.Goal();

		System.err.println("Program parsed successfully.");
	    System.out.println(root.accept(STPV, null));

		STPVariablesDeclVisitor STPCTV=new STPVariablesDeclVisitor(STPV.GetSTD());
		System.out.println(root.accept(STPCTV, null));

		TypeCheckerVisitor TCV=new TypeCheckerVisitor(STPV.GetSTD());
		System.out.println(root.accept(TCV, null));


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
