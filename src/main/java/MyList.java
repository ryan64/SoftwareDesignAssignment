import org.apache.commons.cli.*;
import java.util.Arrays;

public class MyList {
    private String[] args;
    private Options options;

    private Integer int_key;
    private Integer[] int_array;
    private String string_key;
    private String[] string_array;
    private boolean flag;

    Integer get_intKey(){ return int_key;}
    Integer[] get_intArray(){ return int_array;}
    String get_stringKey(){ return string_key;}
    String[] get_stringArray(){ return string_array;}
    boolean get_flag(){ return flag;}

    @SuppressWarnings("unchecked")
    public int binSearch(Comparable[] List, Comparable key){
        int beginning = 0;
        int middle = 0;
        int end = List.length - 1;

        if (List.length == 0){return -1;}
        for (int i = 0; i < List.length / 2; i++){
            middle = (beginning + (end - beginning)) / 2;
            if (List[middle].compareTo(key) == 0){return 1;}
            else if (List[middle].compareTo(key) > 0){end = middle - 1;}
            else {beginning = middle + 1;}
        }
        return 0;
    }

    public static void main(String args[]){
        MyList list = new MyList();
        int returned_value;
        list.resolve(args);
        if (list.get_flag()){returned_value = list.binSearch(list.get_intArray(), list.get_intKey());}
        else{returned_value = list.binSearch(list.get_stringArray(), list.get_stringKey());}
        System.out.println(returned_value);
    }

    public void resolve(String args[]){
        options = new Options();
        this.args = args;
        options.addOption("h", "help", false, "show help.");
        options.addOption("t","type", true,"Set type: 'i' for integer and 's' for string.");
        options.addOption("k", "key", true, "Set search key.");
        Option option = new Option("l", "list", true, "Set your list of integers or strings.");
        option.setArgs(Option.UNLIMITED_VALUES);
        options.addOption(option);

        CommandLineParser parser = new DefaultParser();
        CommandLine command;
        try{
            command = parser.parse(options, args);
            if (command.hasOption("type")){setType(command.getOptionValue("type")); }
            if (command.hasOption("key")){setKey(command.getOptionValue("key")); }
            if (command.hasOption("list")){
                if (flag){
                    int_array = Arrays.stream(command.getOptionValues("list")).mapToInt(Integer::parseInt).boxed().toArray(Integer[]::new);
                }
                else {string_array = command.getOptionValues("list"); }
            }
        }
        catch (ParseException e){
            System.out.println("Failed to parse command line. Try again later.")
        }
    }

    private void setType(String type) {
        if (type.equals("i")){flag = true;}
        else if (type.equals("s")){flag = false;}
        else {
            System.out.println("Input does not equal 'i' or 's'... ");
            System.exit(0);
        }
    }

    private void setKey(String key){
        if (flag){int_key = new Integer(key);}
        else {string_key = new String(key);}
    }
}