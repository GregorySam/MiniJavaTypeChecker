import java.util.*;
import syntaxtree.*;
import visitor.*;


public class STPopulatingVisitor extends GJDepthFirst<String,String>{

    static Map m= new HashMap();



    /** Goal
     * Grammar production:
     * f0 -> MainClass()
     * f1 -> ( TypeDeclaration() )*
     * f2 -> <EOF>
     */

    public String visit(Goal n,String a){
       n.f0.accept(this,null);
       return "ok";
    }

    /////////////////////////////////////////////////////////////////////////


    /**MainClass
     * Grammar production:
     * f0 -> "class"
     * f1 -> Identifier()
     * f2 -> "{"
     * f3 -> "public"
     * f4 -> "static"
     * f5 -> "void"
     * f6 -> "main"
     * f7 -> "("
     * f8 -> "String"
     * f9 -> "["
     * f10 -> "]"
     * f11 -> Identifier()
     * f12 -> ")"
     * f13 -> "{"
     * f14 -> ( VarDeclaration() )*
     * f15 -> ( Statement() )*
     * f16 -> "}"
     * f17 -> "}"
     */

    public String visit(MainClass n,String s)
    {
        m.put(n.f1.accept(this,null),"class");
        m.put(n.f11.accept(this,null),"String");

        if(n.f14.present())
        {
            n.f14.accept(this,null);
        }
        if(n.f15.present())
        {
            n.f15.accept(this,null);
        }
        return "";

    }
    //////////////////////////////////////////////////////////////////////////////

    /** VarDeclaration
     * Grammar production:
     * f0 -> Type()
     * f1 -> Identifier()
     * f2 -> ";"
     */

    public String visit(VarDeclaration n,String s)
    {
        String s1,s2;

        s1=n.f1.accept(this,null);
        s2=n.f0.accept(this,null);

        m.put(n.f1.accept(this,null),n.f0.accept(this,null));
        return "";
    }

    ///////////////////////////////////////////////////////////////////



    /** Type
     * Grammar production:
     * f0 -> ArrayType()
     *       | BooleanType()
     *       | IntegerType()
     *       | Identifier()
     */

    public String visit(Type n,String s)
    {
        return n.f0.accept(this,null);
    }

    public String visit(IntegerType n,String s)
    {

        return n.f0.tokenImage;
    }

    public String visit(BooleanType n,String s)
    {

        return n.f0.tokenImage;
    }

    public String visit(ArrayType n,String s)
    {
        return (n.f0.tokenImage+n.f1.tokenImage+n.f2.tokenImage);
    }


    ////////////////////////////////////////////////////////////////////////////





    /**Statement
     * Grammar production:
     * f0 -> Block()
     *       | AssignmentStatement()
     *       | ArrayAssignmentStatement()
     *       | IfStatement()
     *       | WhileStatement()
     *       | PrintStatement()
     */

    public String visit(Statement n,String s)
    {
        n.f0.accept(this,null);
        return "";
    }


    /////////////////////////////////////////////////////////////////////////////

    /**ArrayAssignmentStatement()
     * Grammar production:
     * f0 -> Identifier()
     * f1 -> "["
     * f2 -> Expression()
     * f3 -> "]"
     * f4 -> "="
     * f5 -> Expression()
     * f6 -> ";"
     */

    public String visit(ArrayAssignmentStatement n,String s)
    {

        n.f0.accept(this,null);
        n.f2.accept(this,null);
        n.f5.accept(this,null);
        return "";

    }


    ///////////////////////////////////////////////




    /**Block
     * Grammar production:
     * f0 -> "{"
     * f1 -> ( Statement() )*
     * f2 -> "}"
     */

    public String visit(Block n,String s)
    {
        return n.f1.accept(this,null);
    }


    /////////////////////////////////////////////////////////////////////////////





    /**
     * Grammar production:
     * f0 -> Identifier()
     * f1 -> "="
     * f2 -> Expression()
     * f3 -> ";"
     */

    public String visit(AssignmentStatement n,String s)
    {
        //if f0 exists in map
        String id,type1;

        id=n.f0.accept(this,null);
        type1=m.get(id).toString();

        if(type1!="int" && type1!="boolean") {
            System.out.println("error");
            System.exit(0);
        }
        return "";

        //if value of f0 = value of f2


    }








    /**
     * Grammar production:
     * f0 -> <IDENTIFIER>
     */

    public String visit(Identifier n,String s)
    {
        return n.f0.tokenImage;

    }
















}



