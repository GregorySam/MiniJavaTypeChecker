//public class TypeChecker {
//}
//
//
//
////    static private Type OperationCheck(String operator, Type t1, Type t2)
////    {
////
////        if(!t1.equals(t2)){
////            System.out.println("error");
////            System.exit(0);
////        }
////
////        if(operator.equals("+") || operator.equals("-") || operator.equals("*"))
////        {
////            if(t1== Type.INT )
////            {
////                return Type.INT;
////            }
////            else
////            {
////                System.out.println("error");
////                System.exit(0);
////            }
////        }
////
////        if(operator.equals("<"))
////        {
////            if(t1== Type.INT )
////            {
////                return Type.BOOLEAN;
////            }
////            else
////            {
////                System.out.println("error");
////                System.exit(0);
////            }
////        }
////
////        if(operator.equals("&&"))
////        {
////            if(t1== Type.INT)
////            {
////                return Type.INT;
////            }
////            else if(t1== Type.BOOLEAN)
////            {
////                return Type.BOOLEAN;
////            }
////        }
////        System.out.println("error");
////        System.exit(0);
////        return null;
////    }
//    /**Statement
//     * Grammar production:
//     * f0 -> Block()
//     *       | AssignmentStatement()
//     *       | ArrayAssignmentStatement()
//     *       | IfStatement()
//     *       | WhileStatement()
//     *       | PrintStatement()
//     */
//
//    public Type visit(Statement n, Type s)
//    {
//        return n.f0.accept(this,null);
//
//    }
//
//
//    /////////////////////////////////////////////////////////////////////////////
//
//    /**IfStatement
//     * Grammar production:
//     * f0 -> "if"
//     * f1 -> "("
//     * f2 -> Expression()
//     * f3 -> ")"
//     * f4 -> Statement()
//     * f5 -> "else"
//     * f6 -> Statement()
//     */
//
//    public Type visit(IfStatement n,Type p)
//    {
//        Type b;
//        b=n.f2.accept(this,null);
//
//        if(b!=Type.BOOLEAN)
//        {
//            System.out.println("error");
//            System.exit(0);
//        }
//        n.f4.accept(this,null);
//        n.f6.accept(this,null);
//        return null;
//    }
//////////////////////////////////////////////////////////////////////////////////
//    /**WhileStaement
//     * Grammar production:
//     * f0 -> "while"
//     * f1 -> "("
//     * f2 -> Expression()
//     * f3 -> ")"
//     * f4 -> Statement()
//     */
//
//    public Type visit(WhileStatement n,Type p)
//    {
//        Type b;
//        b=n.f2.accept(this,null);
//
//        if(b!=Type.BOOLEAN)
//        {
//            System.out.println("error");
//            System.exit(0);
//        }
//        n.f4.accept(this,null);
//
//        return null;
//    }
//
/////////////////////////////////////////////////////////////////////
//
//
//
//
//
//
//
//    /**ArrayAssignmentStatement()
//     * Grammar production:
//     * f0 -> Identifier()
//     * f1 -> "["
//     * f2 -> Expression()
//     * f3 -> "]"
//     * f4 -> "="
//     * f5 -> Expression()
//     * f6 -> ";"
//     */
//
//    public Type visit(ArrayAssignmentStatement n,Type s)
//    {
//        Type id,expr1,expr2;
//
//        id=n.f0.accept(this,null);
//
//
//
//        if(id!= Type.INT_ARRAY)
//        {
//            System.out.println("error");
//            System.exit(0);
//        }
//
//
//        expr1= n.f2.accept(this,null);
//
//        if(expr1!= Type.INT)
//        {
//            System.out.println("error");
//            System.exit(0);
//        }
//        expr2=n.f5.accept(this,null);
//        if(expr2!= Type.INT)
//        {
//            System.out.println("error");
//            System.exit(0);
//        }
//        return null;
//
//    }
//
//
//    ///////////////////////////////////////////////
//
//
//
//
//    /**Block
//     * Grammar production:
//     * f0 -> "{"
//     * f1 -> ( Statement() )*
//     * f2 -> "}"
//     */
//
//    public Type visit(Block n, Type s)
//    {
//        return n.f1.accept(this,null);
//    }
//
//
//    /////////////////////////////////////////////////////////////////////////////
//
//
//
//
//
//    /**
//     * Grammar production:
//     * f0 -> Identifier()
//     * f1 -> "="
//     * f2 -> Expression()
//     * f3 -> ";"
//     */
//
//    public Type visit(AssignmentStatement n, Type s)
//    {
//
//        Type type1,type2;
//
//        type1=n.f0.accept(this,null);
//
//        if(type1==null){
//            System.out.println("error");
//            System.exit(0);
//        }
//
//        type2=n.f2.accept(this,null);
//
//        if(!type2.equals(type1))
//        {
//            System.out.println("error");
//            System.exit(0);
//        }
//        return null;
//
//
//    }
///////////////////////////////////////////////////////////////////////////////////
//    /**Expression
//     * Grammar production:
//     * f0 -> AndExpression()
//     *       | CompareExpression()
//     *       | PlusExpression()
//     *       | MinusExpression()
//     *       | TimesExpression()
//     *       | ArrayLookup()
//     *       | ArrayLength()
//     *       | MessageSend()
//     *       | Clause()
//     */
//
//
//    public Type visit(Expression n, Type s)
//    {
//        return n.f0.accept(this,null);
//
//    }
//
//    public Type visit(AndExpression n, Type s)
//    {
//
//        Type type1,type2;
//
//
//        type1=n.f0.accept(this,null);
//        type2=n.f2.accept(this,null);
//
//
//        return OperationCheck("&&",type1,type2);
//
//    }
//
//    public Type visit(CompareExpression n, Type s)
//    {
//
//        Type type1,type2;
//
//        type1=n.f0.accept(this,null);
//        type2=n.f2.accept(this,null);
//
//        return OperationCheck("<",type1,type2);
//
//
//    }
//    public Type visit(PlusExpression n, Type s)
//    {
//        Type type1,type2;
//
//        type1=n.f0.accept(this,null);
//        type2=n.f2.accept(this,null);
//
//        return OperationCheck("+",type1,type2);
//    }
//
//    public Type visit(MinusExpression n, Type s)
//    {
//        Type type1,type2;
//
//        type1=n.f0.accept(this,null);
//        type2=n.f2.accept(this,null);
//
//        return OperationCheck("-",type1,type2);
//    }
//
//    public Type visit(TimesExpression n, Type s)
//    {
//        Type type1,type2;
//
//        type1=n.f0.accept(this,null);
//        type2=n.f2.accept(this,null);
//
//        return OperationCheck("*",type1,type2);
//    }
//
//    public Type visit(ArrayLookup n, Type s)
//    {
//        Type type1,type2;
//
//        type1=n.f0.accept(this,null);
//        type2=n.f2.accept(this,null);
//
//        if(type1!= Type.INT_ARRAY && type2!= Type.INT)
//        {
//            System.out.println("error");
//            System.exit(0);
//        }
//
//        return Type.INT;
//    }
//
//    public Type visit(ArrayLength n, Type s)
//    {
//        Type type;
//
//        type=n.f0.accept(this,null);
//        if(type!= Type.INT_ARRAY)
//        {
//            System.out.println("error");
//            System.exit(0);
//        }
//
//        return Type.INT;
//    }
//
//    public Type visit(Clause n, Type s)
//    {
//        return n.f0.accept(this,null);
//    }
//
/////////////////////////////////////////////////////////////////////////////////
//
//    /**Clause
//     * Grammar production:
//     * f0 -> NotExpression()
//     *       | PrimaryExpression()
//     */
//
//    ///////////////////////////////////////////////////////////////////////////
//
//    /**PrimaryExpression
//     * Grammar production:
//     * f0 -> IntegerLiteral()
//     *       | TrueLiteral()
//     *       | FalseLiteral()
//     *       | Identifier()
//     *       | ThisExpression()
//     *       | ArrayAllocationExpression()
//     *       | AllocationExpression()
//     *       | BracketExpression()
//     */
//
//    public Type visit(IntegerLiteral n, Type s)
//    {
//        return Type.INT;
//    }
//
//    public Type visit(ArrayAllocationExpression n,Type s)
//    {
//        Type type;
//
//        type=n.f3.accept(this,null);
//
//        if(type!= Type.INT)
//        {
//            System.out.println("error");
//            System.exit(0);
//        }
//        return Type.INT_ARRAY;
//    }
//
//    public Type visit(TrueLiteral n, Type s)
//    {
//        return Type.BOOLEAN;
//    }
//
//    public Type visit(FalseLiteral n, Type s)
//    {
//        return Type.BOOLEAN;
//    }
//
//    public Type visit(BracketExpression n, Type s)
//    {
//        return n.f1.accept(this,null);
//    }
//
//
//
///////////////////////////////////////////////////////////////////////////////////