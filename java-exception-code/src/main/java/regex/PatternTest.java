package regex;

import java.util.regex.Pattern;

public class PatternTest {
    public static void main(String[] args) {
        String regex = "^(table_a|table_b|table_c)$";
        Pattern p = Pattern.compile(regex);
        String tablename = "table_c";
        boolean b = p.matcher(tablename).matches();
        System.out.println(b);
    }
}
