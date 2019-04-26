import syntaxtree.*;
import visitor.*;


public class STClassesVisitor extends GJDepthFirst<String,ScopeType>{

    private STDataStructure STD=new STDataStructure();

    public STDataStructure GetSTD()
    {
        return STD;
    }




    /** Goal
     * Grammar production:
     * f0 -> MainClass()
     * f1 -> ( TypeDeclaration() )*
     * f2 -> <EOF>
     */

    public String visit(Goal n, ScopeType st){


        n.f1.accept(this,null);

        System.out.println("Program evaluated successfully");
        return null;
    }

    /////////////////////////////////////////////////////////////////////////


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
    ////////////////////////////////////////////////////////////////////////////



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

        if(!STD.InsertClass(id))
        {
            System.out.println("Error");
            System.exit(0);
        }



        return null;
    }


/////////////////////////////////////////////////////////////////

    /**ClassExtendsDeclaration
     * Grammar production:
     * f0 -> "class"
     * f1 -> Identifier()
     * f2 -> "extends"
     * f3 -> Identifier()
     * f4 -> "{"
     * f5 -> ( VarDeclaration() )*
     * f6 -> ( MethodDeclaration() )*
     * f7 -> "}"
     */

    public String visit(ClassExtendsDeclaration n,ScopeType st)
    {

        String id,base_id;
        id=n.f1.accept(this,null);
        base_id=n.f3.accept(this,null);

        if(!STD.InsertClass(id))
        {
            System.out.println("Error");
            System.exit(0);
        }

        ClassType ct,base_ct;

        ct=STD.GetClass(id);


        if(!STD.FindClass(base_id)){
            System.out.println("Error");
            System.exit(0);
        }

        base_ct=STD.GetClass(base_id);



        ct.SetBaseClass(base_ct);


        return null;

    }

    //////////////////////////////////////////////////////////////////////





    /**
     * Grammar production:
     * f0 -> <IDENTIFIER>
     */

    public String visit(Identifier n, ScopeType st)
    {

       return n.f0.tokenImage;


    }
















}



