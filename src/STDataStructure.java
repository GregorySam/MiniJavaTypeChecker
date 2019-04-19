import com.sun.source.tree.Scope;

import java.lang.reflect.Method;
import java.util.*;

enum Type
{
    INT,
    INT_ARRAY,
    BOOLEAN,
    CLASS
}



class ScopeType
{
    private HashMap<String, Type> Variables;

    public boolean InsertVariable(String id, Type p)
    {
        if(Variables.containsKey(id))
        {
            return false;
        }
        else
        {
            Variables.put(id,p);
            return true;
        }
    }
    public ScopeType()
    {
        Variables=new HashMap<>();
    }
}

class MethodType extends ScopeType
{
    private String name;
    Type t;

    public MethodType(String n,Type t)
    {
        name=n;
        this.t=t;

    }

}

class ClassType extends ScopeType
{
    private String name;
    private String BaseClass;
    private HashMap<String, MethodType> Methods;

    public ClassType(String n)
    {
        name=n;
        Methods=new HashMap<>();
    }


    public boolean InsertMethod(String id, Type p)
    {
        MethodType M=new MethodType(id,p);

        if(Methods.containsKey(id))
        {
            return false;
        }
        else
        {
            Methods.put(id,M);
            return true;
        }

    }

    public MethodType GetMethod(String id)
    {
        return Methods.get(id);
    }


}


public class STDataStructure {

    private ScopeType MainVariables;
    private HashMap<String,ClassType> Classes;

    //create connected graph of classes
    public STDataStructure(){
        MainVariables=new ScopeType();

        Classes=new HashMap<>();
    }

//    public Type GetPrimitiveType(String id)
//    {
//        Type t;
//
//        t=PrimitiveTypes.get(id);
//
//        return t;
//    }
    public ScopeType GetMainVariables()
    {
        return MainVariables;
    }

    public boolean InsertPrimitiveType(String id, Type pt)
    {
       return this.MainVariables.InsertVariable(id,pt);

    }
//    public boolean FindClass(String id)
//    {
//
//    }

    public boolean InsertClass(String id)
    {
        ClassType c=new ClassType(id);

        if(this.Classes.containsKey(id))
        {
            return false;
        }
        else
        {
            this.Classes.put(id,c);
            return true;
        }

    }

    public ClassType GetClass(String id)
    {
        return Classes.get(id);
    }
//
//    public boolean InsertClassVariable(String class_id, String v_id, Type p)
//    {
//        ClassType c=Classes.get(class_id);
//
//        return c.InsertVariable(v_id,p);
//
//    }
//
//    public boolean InsertClassMethod(String class_id, String m_id, Type p)
//    {
//        ClassType c=Classes.get(class_id);
//
//        return c.InsertMethod(m_id,p);
//
//    }
//
//    public boolean InsertClassMethodVariable(String class_id, String m_id, String v_id, Type p)
//    {
//        ClassType c=Classes.get(class_id);
//        MethodType m=c.GetMethod(m_id);
//
//        return m.InsertVariable(v_id,p);
//
//    }



}
