import visitor.GJDepthFirst;
import syntaxtree.*;

public class TypeCheckerVisitor extends GJDepthFirst<String,ScopeType> {


        private STDataStructure STD;


        public TypeCheckerVisitor(STDataStructure newSTD)
        {
            STD=newSTD;
        }

        private void CheckType(String id, String type, ScopeType ST)
        {
            String id_type;

            if(!id.equals(type))
            {
                id_type=ST.GetType(id);

                if(id_type==null)
                {
                    System.out.println("Error");
                    System.exit(0);
                }

                if(!id_type.equals(type))
                {
                    System.out.println("Error");
                    System.exit(0);
                }
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
        if(id_type==null)
        {
            System.out.println("Error");
            System.exit(0);
        }

        expr_type=n.f2.accept(this,st);

        if(!id_type.equals(expr_type))
        {
            System.out.println("Error");
            System.exit(0);
        }

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
        String id1,id2;


        id1=n.f0.accept(this,st);

        CheckType(id1,"boolean",st);


        id2=n.f2.accept(this,st);


        CheckType(id2,"boolean",st);



        return "boolean";
    }

    public String visit(CompareExpression n,ScopeType st)
    {
        String pe1,pe2;


        pe1=n.f0.accept(this,st);

        CheckType(pe1,"int",st);

        pe2=n.f2.accept(this,st);

        CheckType(pe2,"int",st);

        return "boolean";
    }

    public String visit(PlusExpression n,ScopeType st)
    {
        String id1,id2;


        id1=n.f0.accept(this,st);

        CheckType(id1,"int",st);


        id2=n.f2.accept(this,st);

        CheckType(id2,"int",st);

        return "int";
    }

    public String visit(MinusExpression n,ScopeType st)
    {
        String id1,id2;


        id1=n.f0.accept(this,st);

        CheckType(id1,"int",st);


        id2=n.f2.accept(this,st);

        CheckType(id2,"int",st);

        return "int";
    }

    public String visit(TimesExpression n,ScopeType st)
    {
        String id1,id2;


        id1=n.f0.accept(this,st);

        CheckType(id1,"int",st);


        id2=n.f2.accept(this,st);

        CheckType(id2,"int",st);

        return "int";
    }

    public String visit(ArrayLookup n,ScopeType st)
    {
        String exp_intarrray,exp_int;

        exp_intarrray=n.f0.accept(this,st);

        CheckType(exp_intarrray,"int[]",st);

        exp_int=n.f2.accept(this,st);

        CheckType(exp_int,"int",st);

        return "int";
    }

    public String visit(ArrayLength n,ScopeType st)
    {
        String exp;

        exp=n.f0.accept(this,st);

        CheckType(exp,"int[]",st);

        return "int";

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
        return n.f0.accept(this,st);
    }

    public String visit(Identifier n,ScopeType st) { return n.f0.tokenImage;}

    public String visit(IntegerLiteral n,ScopeType st) { return "int"; }

    public String visit(TrueLiteral n,ScopeType st){return "boolean";}

    public String visit(FalseLiteral n,ScopeType st){return "boolean";}































}
