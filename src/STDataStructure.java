import java.util.*;








class ScopeType
{
    protected HashMap<String, Object> Variables;


    public boolean InsertVariable(String id, Object p)
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
        Variables =new HashMap<>();
    }

    public HashMap<String, Object> GetVariables()
    {
        return Variables;
    }

    public String GetType(String id)
    {
        if(Variables.get(id)==null)
        {
            return null;
        }
        else
        {
            Object o_type;
            String str_type;

            o_type=Variables.get(id);
            str_type=o_type.toString();

            if(!str_type.equals("int") && !str_type.equals("int[]") && !str_type.equals("boolean"))
            {
                ClassType ct=(ClassType)o_type;
                return ct.GetName();
            }
            return str_type;
        }
    }



}

class MethodType extends ScopeType
{
    private String name;
    private String id;
    private String type;

    public MethodType(String name,String type)
    {
        this.name=name;
        this.type=type;
        this.id=type+name;


    }

    public void ChangeId(String a)
    {
        id=id+a;
    }

    public String GetName()
    {
        return name;
    }

    public String GetType()
    {
        return type;
    }


    public String GetId()
    {
        return id;
    }

}

class ClassType extends ScopeType
{
    private String name;
    private ClassType BaseClass;
    private HashMap<String, MethodType> Methods;

    public ClassType(String n)
    {
        name=n;
        Methods=new HashMap<>();
        BaseClass=null;
    }


    public boolean InsertMethod(MethodType MT)
    {


        if(Methods.containsKey(MT.GetName())) {
            return false;

        }


        if(BaseClass==null)
        {


            Methods.put(MT.GetName(),MT);
            return true;


        }
        else
        {

            if(!BaseClass.GetMethods().containsKey(MT.GetName()))
            {
                Methods.put(MT.GetName(),MT);
                return true;
            }
            else
            {
                String base_funid=BaseClass.GetMethod(MT.GetName()).GetId();
                String class_funid=MT.GetId();
                if(base_funid.equals(class_funid))
                {
                    Methods.put(MT.GetName(),MT);
                    return true;
                }
                else
                {
                    return false;
                }
            }


        }
    }

    public String GetName(){return name;}


    public void SetBaseClass(ClassType id)
    {
        BaseClass=id;
    }

    public MethodType GetMethod(String id)
    {
        return Methods.get(id);
    }

    public HashMap<String, MethodType> GetMethods()
    {
        return Methods;
    }



}


public class STDataStructure {

    private ScopeType MainVariables;
    private HashMap<String,ClassType> Classes;

    public STDataStructure(){
        MainVariables=new ScopeType();

        Classes=new HashMap<>();
    }


    public ScopeType GetMainVariables()
    {
        return MainVariables;
    }

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

    public boolean FindClass(String id)
    {
        return Classes.containsKey(id);
    }



}
