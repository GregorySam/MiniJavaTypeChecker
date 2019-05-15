//
// Generated by JTB 1.3.2 DIT@UoA patched
//

package visitor;
import syntaxtree.*;
import java.util.*;

/**
 * All GJ visitors must implement this interface.
 */

public interface GJVisitor<R,A> {

   //
   // GJ Auto class visitors
   //

   R visit(NodeList n, A argu);
   R visit(NodeListOptional n, A argu);
   R visit(NodeOptional n, A argu);
   R visit(NodeSequence n, A argu);
   R visit(NodeToken n, A argu);

   //
   // User-generated visitor methods below
   //

   /**
    * f0 -> MainClass()
    * f1 -> ( TypeDeclaration() )*
    * f2 -> <EOF>
    */
   R visit(Goal n, A argu);

   /**
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
   R visit(MainClass n, A argu);

   /**
    * f0 -> ClassDeclaration()
    *       | ClassExtendsDeclaration()
    */
   R visit(TypeDeclaration n, A argu);

   /**
    * f0 -> "class"
    * f1 -> Identifier()
    * f2 -> "{"
    * f3 -> ( VarDeclaration() )*
    * f4 -> ( MethodDeclaration() )*
    * f5 -> "}"
    */
   R visit(ClassDeclaration n, A argu);

   /**
    * f0 -> "class"
    * f1 -> Identifier()
    * f2 -> "extends"
    * f3 -> Identifier()
    * f4 -> "{"
    * f5 -> ( VarDeclaration() )*
    * f6 -> ( MethodDeclaration() )*
    * f7 -> "}"
    */
   R visit(ClassExtendsDeclaration n, A argu);

   /**
    * f0 -> Type()
    * f1 -> Identifier()
    * f2 -> ";"
    */
   R visit(VarDeclaration n, A argu);

   /**
    * f0 -> "public"
    * f1 -> Type()
    * f2 -> Identifier()
    * f3 -> "("
    * f4 -> ( FormalParameterList() )?
    * f5 -> ")"
    * f6 -> "{"
    * f7 -> ( VarDeclaration() )*
    * f8 -> ( Statement() )*
    * f9 -> "return"
    * f10 -> Expression()
    * f11 -> ";"
    * f12 -> "}"
    */
   R visit(MethodDeclaration n, A argu);

   /**
    * f0 -> FormalParameter()
    * f1 -> FormalParameterTail()
    */
   R visit(FormalParameterList n, A argu);

   /**
    * f0 -> Type()
    * f1 -> Identifier()
    */
   R visit(FormalParameter n, A argu);

   /**
    * f0 -> ( FormalParameterTerm() )*
    */
   R visit(FormalParameterTail n, A argu);

   /**
    * f0 -> ","
    * f1 -> FormalParameter()
    */
   R visit(FormalParameterTerm n, A argu);

   /**
    * f0 -> ArrayType()
    *       | BooleanType()
    *       | IntegerType()
    *       | Identifier()
    */
   R visit(Type n, A argu);

   /**
    * f0 -> "int"
    * f1 -> "["
    * f2 -> "]"
    */
   R visit(ArrayType n, A argu);

   /**
    * f0 -> "boolean"
    */
   R visit(BooleanType n, A argu);

   /**
    * f0 -> "int"
    */
   R visit(IntegerType n, A argu);

   /**
    * f0 -> Block()
    *       | AssignmentStatement()
    *       | ArrayAssignmentStatement()
    *       | IfStatement()
    *       | WhileStatement()
    *       | PrintStatement()
    */
   R visit(Statement n, A argu);

   /**
    * f0 -> "{"
    * f1 -> ( Statement() )*
    * f2 -> "}"
    */
   R visit(Block n, A argu);

   /**
    * f0 -> Identifier()
    * f1 -> "="
    * f2 -> Expression()
    * f3 -> ";"
    */
   R visit(AssignmentStatement n, A argu);

   /**
    * f0 -> Identifier()
    * f1 -> "["
    * f2 -> Expression()
    * f3 -> "]"
    * f4 -> "="
    * f5 -> Expression()
    * f6 -> ";"
    */
   R visit(ArrayAssignmentStatement n, A argu);

   /**
    * f0 -> "if"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> Statement()
    * f5 -> "else"
    * f6 -> Statement()
    */
   R visit(IfStatement n, A argu);

   /**
    * f0 -> "while"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> Statement()
    */
   R visit(WhileStatement n, A argu);

   /**
    * f0 -> "System.out.println"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> ";"
    */
   R visit(PrintStatement n, A argu);

   /**
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
   R visit(Expression n, A argu);

   /**
    * f0 -> Clause()
    * f1 -> "&&"
    * f2 -> Clause()
    */
   R visit(AndExpression n, A argu);

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "<"
    * f2 -> PrimaryExpression()
    */
   R visit(CompareExpression n, A argu);

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "+"
    * f2 -> PrimaryExpression()
    */
   R visit(PlusExpression n, A argu);

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "-"
    * f2 -> PrimaryExpression()
    */
   R visit(MinusExpression n, A argu);

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "*"
    * f2 -> PrimaryExpression()
    */
   R visit(TimesExpression n, A argu);

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "["
    * f2 -> PrimaryExpression()
    * f3 -> "]"
    */
   R visit(ArrayLookup n, A argu);

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "."
    * f2 -> "length"
    */
   R visit(ArrayLength n, A argu);

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "."
    * f2 -> Identifier()
    * f3 -> "("
    * f4 -> ( ExpressionList() )?
    * f5 -> ")"
    */
   R visit(MessageSend n, A argu);

   /**
    * f0 -> Expression()
    * f1 -> ExpressionTail()
    */
   R visit(ExpressionList n, A argu);

   /**
    * f0 -> ( ExpressionTerm() )*
    */
   R visit(ExpressionTail n, A argu);

   /**
    * f0 -> ","
    * f1 -> Expression()
    */
   R visit(ExpressionTerm n, A argu);

   /**
    * f0 -> NotExpression()
    *       | PrimaryExpression()
    */
   R visit(Clause n, A argu);

   /**
    * f0 -> IntegerLiteral()
    *       | TrueLiteral()
    *       | FalseLiteral()
    *       | Identifier()
    *       | ThisExpression()
    *       | ArrayAllocationExpression()
    *       | AllocationExpression()
    *       | BracketExpression()
    */
   R visit(PrimaryExpression n, A argu);

   /**
    * f0 -> <INTEGER_LITERAL>
    */
   R visit(IntegerLiteral n, A argu);

   /**
    * f0 -> "true"
    */
   R visit(TrueLiteral n, A argu);

   /**
    * f0 -> "false"
    */
   R visit(FalseLiteral n, A argu);

   /**
    * f0 -> <IDENTIFIER>
    */
   R visit(Identifier n, A argu);

   /**
    * f0 -> "this"
    */
   R visit(ThisExpression n, A argu);

   /**
    * f0 -> "new"
    * f1 -> "int"
    * f2 -> "["
    * f3 -> Expression()
    * f4 -> "]"
    */
   R visit(ArrayAllocationExpression n, A argu);

   /**
    * f0 -> "new"
    * f1 -> Identifier()
    * f2 -> "("
    * f3 -> ")"
    */
   R visit(AllocationExpression n, A argu);

   /**
    * f0 -> "!"
    * f1 -> Clause()
    */
   R visit(NotExpression n, A argu);

   /**
    * f0 -> "("
    * f1 -> Expression()
    * f2 -> ")"
    */
   R visit(BracketExpression n, A argu);

}
