import java.util.Scanner;

public class Cat {
    public static void main(String[] args) {
//        String logo = " ____        _        \n"
//                + "|  _ \\ _   _| | _____ \n"
//                + "| | | | | | | |/ / _ \\\n"
//                + "| |_| | |_| |   <  __/\n"
//                + "|____/ \\__,_|_|\\_\\___|\n";
//        System.out.println("Hello from\n" + logo);
        Scanner scanner = new Scanner(System.in);
        System.out.println("____________________________________________________________\n");
        System.out.println(" Hello :) I'm Cat\n");
        System.out.print(" What can I do for you?\n");
        System.out.println("____________________________________________________________\n");
        String input = scanner.nextLine();
        while (!input.equals("bye")){
            System.out.println("____________________________________________________________\n");
            System.out.println(input);
            System.out.println("____________________________________________________________\n");
            input = scanner.nextLine();
        }

        System.out.println(" Bye. Hope to see you again soon!\n");
        System.out.println("____________________________________________________________");
    }
}
