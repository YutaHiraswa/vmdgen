import java.lang.StringBuilder;

public class Main{

    static final String[] TYPES = 
        {"String", "Fixnum", "Flonum", "Special", "SimpleObject", "Array", "Function", "Builtin", "Iterator", "Regexp", "StringObject", "NumberOject", "BooleanObject"};
    int ndvSize = 100;
    StringBuilder builder = new StringBuilder();
    public Main(int size){
        ndvSize = size;
        makeExternC(builder);
        builder.append("ins : JSValue -> JSValue\n");
        builder.append("ins (v1) {\n");
        makeDecralate();
        builder.append("  top: match (v1) {\n");
        for(String s : TYPES){
            makeCase(builder, s);
        }
        builder.append("    case (true) {\n");
        builder.append("      return v1;\n");
        builder.append("    }\n");
        builder.append("  }\n}\n");
        System.out.println(builder.toString());
    }
    private void makeExternC(StringBuilder builder){
        for(String s : TYPES){
            builder.append("externC (needContext, triggerGC) to"+s+": JSValue -> "+s+"\n");
        }
    }

    private void makeDecralate(){
        for(int i=0; i<ndvSize; i++){
            builder.append("  JSValue ndv"+i+";\n");
        }
    }

    private void makeCase(StringBuilder builder, String type){
        builder.append("    case ("+type+" v1) {\n");
        for(int i=0; i<ndvSize; i++){
            builder.append("      ndv"+i+" <- to"+type+"();\n");
            
        }
        builder.append("      rematch top(ndv0);\n");
        builder.append("    }\n");
    }

    public static void main(String[] args){
        int size = 0;
        if(args.length==1){
            try{
                size = Integer.parseInt(args[0]);
            }catch(NumberFormatException e){
                e.printStackTrace();
                size = 100;
            }
        }
        new Main(size);
    }
}