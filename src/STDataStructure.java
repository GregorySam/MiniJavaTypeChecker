import java.util.*;


class ScopeType
{
    final LinkedHashMap<String, String> Variables;
    private final String scopename;


    public boolean InsertVariable(String id, String p)              //Insert viaribale if dows not exist
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

    public String GetScopeName(){               //get class-method name
        return scopename;
    }


    public ScopeType(String name)
    {
        this.scopename=name;
        Variables =new LinkedHashMap<>();
    }

    public String GetType(String id)            //return type of identifier
    {
       return Variables.get(id);
    }



}

class MethodType extends ScopeType
{
    private final String name;
    private String id;
    private final String type;
    private final List<String> ParametersTypes;
    private final ClassType ClassPertain;

    public MethodType(String name,String type,ClassType CT)
    {
        super("class "+CT.GetName()+" "+name);
        this.name=name;
        this.type=type;
        this.id=type+name;
        this.ParametersTypes=new ArrayList<>();
        this.ClassPertain=CT;

    }

    @Override
    public String GetType(String id)                        //get type of variable if type not found search in class and bas class
    {
        if(Variables.get(id)==null)
        {

            return ClassPertain.GetType(id);

        }
        else
        {
            String str_type;

            str_type=Variables.get(id);

            return str_type;
        }
    }


    public void ChangeId(String a)                  //id of function
    {
        id=id+a;
        ParametersTypes.add(a);
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

    public ClassType getClassPertain()
    {
        return ClassPertain;
    }

    public boolean CheckParametersMatch(String params,STDataStructure std){            //check if call parametrs match declared parameters

        if(params==null && ParametersTypes.size()==0)
        {
            return true;
        }
        else if(params==null )
        {
            return false;
        }
        String[] parts=params.split(",");
        int i;

        if(parts.length!=ParametersTypes.size()){
            return false;
        }

        for(i=0;i<parts.length;i++)
        {
            if(!ParametersTypes.get(i).equals(parts[i]))
            {
                if(!parts[i].equals("int") && !parts[i].equals("boolean") && !parts[i].equals("int[]")){
                    ClassType expected_base_class_type,classType;

                    classType=std.GetClass(parts[i]);
                    expected_base_class_type=std.GetClass(ParametersTypes.get(i));

                    return classType.IsTypeOf(expected_base_class_type.GetName());


                }
                else
                {
                    return false;
                }
            }
        }
        return true;

    }

}

class ClassType extends ScopeType
{
    private final String name;
    private ClassType BaseClass;
    private final LinkedHashMap<String, MethodType> Methods;

    private int var_offset;
    private int methods_offset;

    public ClassType(String n)
    {
        super("class "+n);
        name=n;
        Methods=new LinkedHashMap<>();
        BaseClass=null;
    }
    public boolean IsTypeOf(String id)
    {
        if(id.equals(name))
        {
            return true;
        }

        if(BaseClass==null)
        {
            return false;
        }
        return BaseClass.IsTypeOf(id);

    }

    static private int GetSize(String type)         //get offset size of types
    {
        if(type.equals("int"))
        {
           return 4;
        }
        else if(type.equals("int[]"))
        {
            return 8;
        }
        else if(type.equals("boolean")){
            return 1;
        }
        else
        {
            return 8;
        }
    }

    private int GetVariablesOffset()
    {
        return var_offset;
    }

    private int GetMethodsOffset()
    {
        return methods_offset;
    }



    public void PrintOffsets()          //print fields offsets
    {
        int var_offset,meth_offset;

        if(BaseClass==null)
        {
            var_offset=0;
            meth_offset=0;
        }
        else
        {
            var_offset=BaseClass.GetVariablesOffset();
            meth_offset=BaseClass.GetMethodsOffset();
        }
        System.out.println("----------------Variables----------------");

        for (Map.Entry<String, String> entry : Variables.entrySet()) {

            String id=entry.getKey();
            String type = entry.getValue();

            System.out.println(name+"."+id+": "+var_offset);

            var_offset=var_offset+GetSize(type);


        }
        this.var_offset=var_offset;


        System.out.println("----------------Methods----------------");

        for (Map.Entry<String, MethodType> entry : Methods.entrySet()) {

            String id=entry.getKey();

            if(BaseClass==null)
            {
                System.out.println(name+"."+id+": "+meth_offset);
                meth_offset=meth_offset+8;
            }
            else{
                if(BaseClass.GetMethod(id)!=null) {
                }
                else{
                    System.out.println(name+"."+id+": "+meth_offset);
                    meth_offset=meth_offset+8;
                }
            }

        }
        this.methods_offset=meth_offset;

    }

    public boolean InsertMethod(MethodType MT)      //inseret method in map
    {

        if(Methods.containsKey(MT.GetName())) {
            return false;
        }

        //if there is no base class put method else
        // check if same exists-override
        if(BaseClass==null)
        {
            Methods.put(MT.GetName(),MT);
            return true;
        }

        MethodType base_class_meth;

        base_class_meth=BaseClass.GetMethod(MT.GetName());

        if(base_class_meth==null) {
            Methods.put(MT.GetName(),MT);
            return true;
        }
        else {

            String base_funid;
            base_funid=MT.GetId();

            String base_class_funid=base_class_meth.GetId();
            if(base_funid.equals(base_class_funid))
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

    public String GetName(){return name;}

    public boolean FindMethod(String id)
    {
        return  Methods.get(id)!=null;
    }


    public void SetBaseClass(ClassType id)
    {
        BaseClass=id;
    }

    public MethodType GetMethod(String id) {            //search for method in curent class or base class

        if(Methods.get(id)==null)
        {
            if(BaseClass==null)
            {
                return null;
            }
            else
            {
                return BaseClass.GetMethod(id);
            }
        }

        return Methods.get(id);
    }
    @Override
    public String GetType(String id)                    //get type of identifier
    {
        if(Variables.get(id)==null)
        {
            if(BaseClass==null)
            {
                return null;
            }
            else
            {
                return BaseClass.GetType(id);
            }
        }

        return Variables.get(id);
    }

}


public class STDataStructure {

    private final ScopeType MainVariables;
    private final LinkedHashMap<String,ClassType> Classes;
    private boolean error_flag;


    public STDataStructure(){
        MainVariables=new ScopeType("Main");
        error_flag=false;
        Classes=new LinkedHashMap<String,ClassType>();
    }

    public boolean getErrorFlag(){
        return error_flag;
    }

    public void PrintOffsets()
    {
        for (Map.Entry<String, ClassType> entry :Classes.entrySet()) {

            ClassType type = entry.getValue();
            String class_name=entry.getKey();

            System.out.println("--------------------------Class "+class_name+"--------------------------");
            type.PrintOffsets();
        }

    }


    public ScopeType GetMainVariables()
    {
        return MainVariables;
    }

    public void SetErrorFlag(boolean f)
    {
        error_flag=f;
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
