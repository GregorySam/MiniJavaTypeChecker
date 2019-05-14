import visitor.GJDepthFirst;
import syntaxtree.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;

class LLWriter extends GJDepthFirst<String,ScopeType> {


    private final STDataStructure STD;
    private final PrintWriter fis;


    public STDataStructure GetSTD() {
            return STD;
         }


    public LLWriter(STDataStructure newSTD, PrintWriter fi)
    {
        STD=newSTD;
        fis=fi;
    }



    /** Goal
     * Grammar production:
     * f0 -> MainClass()
     * f1 -> ( TypeDeclaration() )*
     * f2 -> <EOF>
     */

    public String visit(Goal n, ScopeType st){

        //wrtite vtables


        ScopeType main_st=STD.GetMainVariables();

        n.f0.accept(this,main_st);
        n.f1.accept(this,null);


        return null;
    }



    public String visit(IntegerType n, ScopeType st)
    {

        return n.f0.tokenImage;
    }

    public String visit(BooleanType n, ScopeType st)
    {

        return n.f0.tokenImage;
    }

    public String visit(ArrayType n, ScopeType st)
    {
        return (n.f0.tokenImage+n.f1.tokenImage+n.f2.tokenImage);
    }





    public String visit(Identifier n,ScopeType st) { return n.f0.tokenImage;}

    public String visit(IntegerLiteral n,ScopeType st) { return "int"; }

    public String visit(TrueLiteral n,ScopeType st){return "boolean";}

    public String visit(FalseLiteral n,ScopeType st){return "boolean";}































}
