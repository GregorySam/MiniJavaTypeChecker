import java.util.*;
import syntaxtree.*;
import visitor.*;


public class STPopulatingVisitor extends GJDepthFirst<String,String>{

    static private Map m= new HashMap();


    static private String OperationCheck(String operator,String t1,String t2)
    {

        if(!t1.equals(t2)){return "error";}

        if(operator.equals("+") || operator.equals("-") || operator.equals("*"))
        {
            if(t1.equals("int") && t2.equals("int"))
            {
                return "int";
            }
            else
            {
                return "error";
            }
        }

        if(operator.equals("<"))
        {
            if(t1.equals("int") && t2.equals("int"))
            {
                return "boolean";
            }
            else
            {
                return "error";
            }
        }

        if(operator.equals("&&"))
        {
            if(t1.equals("int") && t2.equals("int"))
            {
                return "int";
            }
            else if(t1.equals("boolean") && t2.equals("boolean"))
            {
                return "boolean";
            }
        }
        return "error";
    }



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
        m.put(n.f1.accept(this,"declaration"),"class");
        m.put(n.f11.accept(this,"declaration"),"String");

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

        s1=n.f1.accept(this,"declaration");
        s2=n.f0.accept(this,null);

        m.put(s1,s2);
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
        String type1,type2;

        type1=n.f0.accept(this,null);

        if(type1!="int" && type1!="boolean") {
            System.out.println("error");
            System.exit(0);
        }

        type2=n.f2.accept(this,null);

        if(type2.equals(type1))
        {
            return "";
        }
        else
        {
            //error
            System.out.println("eorro");
            return "";
        }


    }
/////////////////////////////////////////////////////////////////////////////////
    /**Expression
     * Grammar production:
     * f0 -> AndExpression()
     *       | CompareExpression()
     *       | PlusExpression()
     *       | MinusExpression()
     *       | TimesExpression()
     *       | ArrayLookup()
     *       | ArrayLength()
     *       | MessageSend()
     *       | Clause()
     */


    public String visit(Expression n,String s)
    {
        return n.f0.accept(this,null);

    }

    public String visit(AndExpression n,String s)
    {
        //if f0 type= f2 type
        String type1,type2;



        type1=n.f0.accept(this,null);
        type2=n.f2.accept(this,null);


        return OperationCheck("&&",type1,type2);

    }

    public String visit(CompareExpression n,String s)
    {

        String type1,type2;

        type1=n.f0.accept(this,null);
        type2=n.f2.accept(this,null);

        return OperationCheck("<",type1,type2);


    }
     public String visit(PlusExpression n,String s)
     {
         String type1,type2;

         type1=n.f0.accept(this,null);
         type2=n.f2.accept(this,null);

         return OperationCheck("+",type1,type2);
     }

    public String visit(MinusExpression n,String s)
    {
        String type1,type2;

        type1=n.f0.accept(this,null);
        type2=n.f2.accept(this,null);

        return OperationCheck("-",type1,type2);
    }

    public String visit(TimesExpression n,String s)
    {
        String type1,type2;

        type1=n.f0.accept(this,null);
        type2=n.f2.accept(this,null);

        return OperationCheck("*",type1,type2);
    }

    public String visit(ArrayLookup n,String s)
    {
        String type1,type2;

        type1=n.f0.accept(this,null);
        type2=n.f2.accept(this,null);

        if(!type2.equals("int"))
        {
            //error
            return "";
        }

        return type1;
    }

    public String visit(ArrayLength n,String s)
    {
        String type;

        type=n.f0.accept(this,null);
        if(!type.equals("int[]"))
        {
            //error
            return "";
        }

        return "int";
    }

    public String visit(Clause n,String s)
    {
        return n.f0.accept(this,null);
    }

///////////////////////////////////////////////////////////////////////////////

    /**Clause
     * Grammar production:
     * f0 -> NotExpression()
     *       | PrimaryExpression()
     */

 ///////////////////////////////////////////////////////////////////////////

    /**PrimaryExpression
     * Grammar production:
     * f0 -> IntegerLiteral()
     *       | TrueLiteral()
     *       | FalseLiteral()
     *       | Identifier()
     *       | ThisExpression()
     *       | ArrayAllocationExpression()
     *       | AllocationExpression()
     *       | BracketExpression()
     */

    public String visit(IntegerLiteral n,String s)
    {
        return "int";
    }

    public String visit(ArrayAllocationExpression n,String s)
    {
        String type;

        type=n.f3.accept(this,null);

        if(!type.equals("int"))
        {
            //error
            return "";
        }
        return type;
    }









    /**
     * Grammar production:
     * f0 -> <IDENTIFIER>
     */

    public String visit(Identifier n,String s)
    {
        if(s!=null && s.equals("declaration"))
        {
            return n.f0.tokenImage;
        }
        else
        {
            Object type;


            type=m.get(n.f0.tokenImage);
            if(type==null)
            {
                //errror
                System.out.println("error");
                System.exit(0);
            }
            return type.toString();

        }



    }
















}



