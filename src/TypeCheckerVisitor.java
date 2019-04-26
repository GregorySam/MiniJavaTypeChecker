import visitor.GJDepthFirst;
import syntaxtree.*;

public class TypeCheckerVisitor extends GJDepthFirst<String,ScopeType> {


        private STDataStructure STD;


        public TypeCheckerVisitor(STDataStructure newSTD)
        {
            STD=newSTD;
        }

        private void CheckTypes(String exp1, String type1, String exp2, String type2)
        {
            if(exp1==null || exp2==null || type1==null || type2==null)
            {
                System.out.println("Error");
                System.exit(0);
            }

            if(!exp1.equals(type1) || !exp2.equals(type2)){
                System.out.println("Error");
                System.exit(0);
            }
        }




    /** Goal
     * Grammar production:
     * f0 -> MainClass()
     * f1 -> ( TypeDeclaration() )*
     * f2 -> <EOF>
     */

    public String visit(Goal n, ScopeType st){

        ScopeType main_st=STD.GetMainVariables();

        n.f0.accept(this,main_st);
        n.f1.accept(this,null);

        System.out.println("Program evaluated successfully");
        return null;
    }

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

        if(n.f15.present())
        {
            n.f15.accept(this,st);
        }
        return null;

    }

    public String visit(ClassDeclaration n,ScopeType st)
    {
        String class_id;
        ClassType ct;

        class_id=n.f1.accept(this,null);
        ct=STD.GetClass(class_id);
        n.f4.accept(this,ct);
        return null;

    }

    public String visit(ClassExtendsDeclaration n,ScopeType st)
    {
        ClassType ct=STD.GetClass(n.f1.accept(this,null));

        n.f6.accept(this,ct);

        return null;
    }


    public String visit(MethodDeclaration n,ScopeType st)
    {
        String expr_type,id,return_type;
        MethodType mt;
        ClassType ct;

        ct=(ClassType)st;

        return_type=n.f1.accept(this,st);

        id=n.f2.accept(this,null);
        mt=ct.GetMethod(id);

        n.f8.accept(this,mt);



        expr_type=n.f10.accept(this,mt);

        if(!expr_type.equals(return_type))
        {
            if(!expr_type.equals("int") && !expr_type.equals("int[]") && !expr_type.equals("boolean") )
            {

                ClassType base_class;


                base_class=STD.GetClass(expr_type).GetBaseClass();
                if(base_class==null)
                {
                    System.out.println("Error");
                    System.exit(0);
                }
                if(!return_type.equals(base_class.GetName()))
                {
                    System.out.println("Error");
                    System.exit(0);
                }

            }
            else
            {
                System.out.println("Error");
                System.exit(0);
            }
        }


        return null;


    }

    //////////////////////////////////////////////////////////////////////////////
    /**Statement
     * Grammar production:
     * f0 -> Block()
     *       | AssignmentStatement()
     *       | ArrayAssignmentStatement()
     *       | IfStatement()
     *       | WhileStatement()
     *       | PrintStatement()
     */

    public String visit(IfStatement n,ScopeType st)
    {
        String bool_exp;

        bool_exp=n.f2.accept(this,st);

        if(!bool_exp.equals("boolean")){
            System.out.println("Error");
            System.exit(0);
        }

        n.f4.accept(this,st);
        n.f6.accept(this,st);
        return null;
    }

    public String visit(WhileStatement n,ScopeType st)
    {
        String bool_exp;

        bool_exp=n.f2.accept(this,st);

        if(!bool_exp.equals("boolean")){
            System.out.println("Error");
            System.exit(0);
        }

        n.f4.accept(this,st);
        return null;

    }

    public String visit(PrintStatement n,ScopeType st)
    {
        n.f2.accept(this,st);
        return null;
    }

    public String visit(Block n,ScopeType st)
    {
        n.f1.accept(this,st);
        return null;
    }

    /**Asssignement Statement
     * Grammar production:
     * f0 -> Identifier()
     * f1 -> "="
     * f2 -> Expression()
     * f3 -> ";"
     */

    public String visit(AssignmentStatement n,ScopeType st)
    {
        String id;
        String id_type,expr_type;

        id=n.f0.accept(this,null);
        id_type=st.GetType(id);


        expr_type=n.f2.accept(this,st);

        CheckTypes(id_type,expr_type,expr_type,id_type);

        return  null;

    }

    ////////////////////////////////////////////////////////////
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

    public String visit(Expression n,ScopeType st)
    {
        return n.f0.accept(this,st);
    }

    public String visit(AndExpression n,ScopeType st)
    {
        String exp1,exp2;


        exp1=n.f0.accept(this,st);



        exp2=n.f2.accept(this,st);

        CheckTypes(exp1,"boolean",exp2,"boolean");


        return "boolean";
    }

    public String visit(CompareExpression n,ScopeType st)
    {
        String exp1,exp2;


        exp1=n.f0.accept(this,st);



        exp2=n.f2.accept(this,st);

        CheckTypes(exp1,"int",exp2,"int");

        return "boolean";
    }

    public String visit(PlusExpression n,ScopeType st)
    {
        String exp1,exp2;


        exp1=n.f0.accept(this,st);


        exp2=n.f2.accept(this,st);


        CheckTypes(exp1,"int",exp2,"int");


        return "int";
    }

    public String visit(MinusExpression n,ScopeType st)
    {
        String exp1,exp2;


        exp1=n.f0.accept(this,st);


        exp2=n.f2.accept(this,st);


        CheckTypes(exp1,"int",exp2,"int");


        return "int";
    }

    public String visit(TimesExpression n,ScopeType st)
    {
        String exp1,exp2;


        exp1=n.f0.accept(this,st);


        exp2=n.f2.accept(this,st);


        CheckTypes(exp1,"int",exp2,"int");


        return "int";
    }

    public String visit(ArrayLookup n,ScopeType st)
    {
        String exp_intarrray,exp_int;

        exp_intarrray=n.f0.accept(this,st);


        exp_int=n.f2.accept(this,st);


        CheckTypes(exp_intarrray,"int[]",exp_int,"int");


        return "int";
    }

    public String visit(ArrayLength n,ScopeType st)
    {
        String exp;

        exp=n.f0.accept(this,st);

        if(!exp.equals("int[]")){
            System.out.println("Error");
            System.exit(0);
        }

        return "int";

    }

    /**ArrayAssignementStatement
     * Grammar production:
     * f0 -> Identifier()
     * f1 -> "["
     * f2 -> Expression()
     * f3 -> "]"
     * f4 -> "="
     * f5 -> Expression()
     * f6 -> ";"
     */

    public String visit(ArrayAssignmentStatement n,ScopeType st)
    {
        String id,expr1,expr2,id_type;
        id=n.f0.accept(this,st);

        id_type=st.GetType(id);

        expr1=n.f2.accept(this,st);

        CheckTypes(id_type,"int[]",expr1,"int");

        expr2=n.f5.accept(this,st);

        if(!expr2.equals("int"))
        {
            System.out.println("Error");
            System.exit(0);
        }



        return "";
    }

    /**BracketExpression
     * Grammar production:
     * f0 -> "("
     * f1 -> Expression()
     * f2 -> ")"
     */

    public String visit(BracketExpression n,ScopeType st)
    {
        return n.f1.accept(this,st);
    }

    /**MessageSend
     * Grammar production:
     * f0 -> PrimaryExpression()
     * f1 -> "."
     * f2 -> Identifier()
     * f3 -> "("
     * f4 -> ( ExpressionList() )?
     * f5 -> ")"
     */



    public String visit(MessageSend n,ScopeType st)
    {
        String pe,id,parameters;
        ClassType ct;
        MethodType mt=null;

        pe=n.f0.accept(this,st);

        id=n.f2.accept(this,st);



        if(pe.equals("int") || pe.equals("int[]") || pe.equals("boolean"))
        {
            System.out.println("Error");
            System.exit(0);
        }

        if(pe.equals("this"))
        {

            if(st==STD.GetMainVariables())
            {
                System.out.println("Error");
                System.exit(0);
            }
            else
            {
                mt=(MethodType)st;
                ct=mt.getClassPertain();
                if(!ct.FindMethod(id))
                {
                    System.out.println("Error");
                    System.exit(0);
                }
            }

        }
        else
        {

            ct=STD.GetClass(pe);
            if(ct==null )
            {
                System.out.println("Error");
                System.exit(0);
            }
            mt=ct.GetMethod(id);
            if(mt==null)
            {
                System.out.println("Error");
                System.exit(0);
            }
        }

        parameters=n.f4.accept(this,st);


        //mt.Checkparametersmatch
        if(!mt.CheckParametersMatch(parameters,STD))
        {
            System.out.println("Error");
            System.exit(0);
        }

        return mt.GetType();

    }

    /**This
     * Grammar production:
     * f0 -> "this"
     */


    public String visit(ThisExpression n,ScopeType st)
    {
        return n.f0.tokenImage;
    }

    /**ExpressionList
     * Grammar production:
     * f0 -> Expression()
     * f1 -> ExpressionTail()
     */

    public String visit(ExpressionList n,ScopeType st)
    {

        String type,typel;

        type=n.f0.accept(this,st);


        typel=n.f1.accept(this,st);

        return (type+typel);
    }

    /**
     * Grammar production:
     * f0 -> ( ExpressionTerm() )*
     */

    public String visit(ExpressionTail n,ScopeType st)
    {
        String expr_t="";
        int s,i;
        s=n.f0.size();

        for(i=0;i<s;i++)
        {
            expr_t=expr_t+","+n.f0.elementAt(i).accept(this,st);
        }


        return expr_t;
    }
    /**ExpressionTerm
     * Grammar production:
     * f0 -> ","
     * f1 -> Expression()
     */

    public String visit(ExpressionTerm n,ScopeType st)
    {
        String expr;
        expr=n.f1.accept(this,st);
        if(expr==null)
        {
            return "";
        }


        return expr;



    }






    public String visit(Clause n,ScopeType st)
    {
        return n.f0.accept(this,st);
    }

    public String visit(NotExpression n,ScopeType st)
    {
        return n.f1.accept(this,st);
    }



    public String visit(PrimaryExpression n,ScopeType st)
    {
        String pex,type;
        pex=n.f0.accept(this,st);


        if(pex.equals("int") || pex.equals("boolean") || pex.equals("int[]"))
        {
            return pex;
        }
        type=st.GetType(pex);
        if(type==null)
        {
            System.out.println("Error");
            System.exit(0);
        }
        return type;
    }

    public String visit(Identifier n,ScopeType st) { return n.f0.tokenImage;}

    public String visit(IntegerLiteral n,ScopeType st) { return "int"; }

    public String visit(TrueLiteral n,ScopeType st){return "boolean";}

    public String visit(FalseLiteral n,ScopeType st){return "boolean";}































}
