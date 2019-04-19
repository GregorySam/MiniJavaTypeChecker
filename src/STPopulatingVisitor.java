import syntaxtree.*;
import visitor.*;

import javax.lang.model.type.PrimitiveType;


public class STPopulatingVisitor extends GJDepthFirst<String,ScopeType>{

    static private STDataStructure STD=new STDataStructure();


    private Type StringToType(String s_type)
    {
        if(s_type.equals("int"))
        {
            return Type.INT;
        }
        else if(s_type.equals("boolean"))
        {
            return Type.BOOLEAN;
        }
        else if(s_type.equals("int[]"))
        {
            return Type.INT_ARRAY;
        }
        else
        {
            return null;
        }
    }


    /** Goal
     * Grammar production:
     * f0 -> MainClass()
     * f1 -> ( TypeDeclaration() )*
     * f2 -> <EOF>
     */

    public String visit(Goal n, ScopeType st){


        //n.f0.accept(this,null);
        n.f1.accept(this,null);

        System.out.println("Program evaluated successfully");
        return null;
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

    public String visit(MainClass n, ScopeType st)
    {

//        m.put(n.f1.accept(this,"declaration"),"class");
//        m.put(n.f11.accept(this,"declaration"),"String");

        if(n.f14.present())
        {
           n.f14.accept(this,STD.GetMainVariables());
        }
        if(n.f15.present())
        {
            n.f15.accept(this,null);
        }
        return null;

    }
    //////////////////////////////////////////////////////////////////////////////

    /** VarDeclaration
     * Grammar production:
     * f0 -> Type()
     * f1 -> Identifier()
     * f2 -> ";"
     */

    public String visit(VarDeclaration n, ScopeType ST)
    {
        String type;
        String id;
        Type t=Type.INT;

        type=n.f0.accept(this,null);
        id=n.f1.accept(this,null);

        t=StringToType(type);

        if(t==null)
        {
            return null;
        }


        if(!ST.InsertVariable(id,t))
        {
            System.out.println("Error");
            System.exit(0);
        }



        return null;
    }

    ///////////////////////////////////////////////////////////////////



    /** Type
     * Grammar production:
     * f0 -> ArrayType()
     *       | BooleanType()
     *       | IntegerType()
     *       | Identifier()
     */


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


    ////////////////////////////////////////////////////////////////////////////
    /**TypeDeclaration
     * Grammar production:
     * f0 -> ClassDeclaration()
     *       | ClassExtendsDeclaration()
     */

    public String visit(TypeDeclaration n,ScopeType st)
    {

        n.f0.accept(this,null);
        return null;
    }

    /**ClassDeclaration
     * Grammar production:
     * f0 -> "class"
     * f1 -> Identifier()
     * f2 -> "{"
     * f3 -> ( VarDeclaration() )*
     * f4 -> ( MethodDeclaration() )*
     * f5 -> "}"
     */

    public String visit(ClassDeclaration n,ScopeType st)
    {
        String id;
        id=n.f1.accept(this,null);

        STD.InsertClass(id);
        ClassType ct;
        ct=STD.GetClass(id);


        n.f3.accept(this,ct);
        n.f4.accept(this,ct);

        return null;
    }

    public String visit(MethodDeclaration n,ScopeType st)
    {
        String type;
        String id;

        ClassType ct;
        ct=(ClassType)st;

        Type t=Type.INT;

        type=n.f1.accept(this,null);
        id=n.f2.accept(this,null);

        t=StringToType(type);

        if(t==null)
        {
            return null;
        }


        if(!ct.InsertMethod(id,t))
        {
            System.out.println("Error");
            System.exit(0);
        }

        MethodType mt;
        mt=ct.GetMethod(id);

        n.f4.accept(this,mt);
        n.f7.accept(this,mt);


        return "";

    }



    ///////////////////////////////////////////////////////////////////
    /**FormalParameterList
     * Grammar production:
     * f0 -> FormalParameter()
     * f1 -> FormalParameterTail()
     */

    public String visit(FormalParameterList n,ScopeType st)
    {

        MethodType mt=(MethodType)st;

        n.f0.accept(this,mt);
        n.f1.accept(this,mt);
        return null;

    }

    public String visit(FormalParameter n,ScopeType st)
    {
        String id,type;
        Type t;

        MethodType mt=(MethodType)st;

        type=n.f0.accept(this,null);
        id=n.f1.accept(this,null);

        t=StringToType(type);

        if(t==null)
        {
            return null;
        }

        mt.InsertVariable(id,t);
        return null;
    }

    public String visit(FormalParameterTail n,ScopeType st)
    {
        MethodType mt=(MethodType)st;

        n.f0.accept(this,mt);
        return null;
    }

    public String visit(FormalParameterTerm n,ScopeType st)
    {
        MethodType mt=(MethodType)st;

        n.f1.accept(this,mt);
        return null;
    }

    ////////////////////////////////////////////////////////////////












    /**
     * Grammar production:
     * f0 -> <IDENTIFIER>
     */

    public String visit(Identifier n, ScopeType st)
    {

       return n.f0.tokenImage;


    }
















}



