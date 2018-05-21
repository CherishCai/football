public class Menu {

    public static final String A = "A";
    public static final String B = "B";
    public static final String C = "C";
    public static final String D = "D";
    public static final String E = "E";
    public static final String X = "X";
//    A,
//    B,
//    C,
//    D,
//    E,
//    X
//    ;


    private static String menu =
            "A:\tPlay Preliminary Stage \n" +
            "B:\tPlay Final \n" +
            "C:\tDisplay Teams \n" +
            "D:\tDisplay Players \n" +
            "E:\tDisplay Cup Result\n" +
            "X:\tClose";

    public static void printMenu() {
        System.out.println();
        System.out.println(menu);
    }


}
