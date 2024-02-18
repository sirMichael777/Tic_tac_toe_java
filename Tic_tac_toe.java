// Program for Tic-Tac-Toe game
// Michael Maseko
// 29-11-2022
import java.util.*;
class Tic_tac_toe {
    static String[][] matrix = new String[7][7];
    static String row;
    static String column;
    static String variable;
    static String position;
    static Scanner input = new Scanner(System.in);
    static ArrayList<String> positions_list = new ArrayList<>();
    static ArrayList<String> edges = new ArrayList<>(Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7"));
    static ArrayList<String> testers = new ArrayList<>();
    static HashMap<String, Integer> converter = new HashMap<>();
    static String[][] createGrid() {
        int row;
        int column;
        for (row = 0; row < 7; row++) {
            for (column = 0; column < 7; column++) {
                matrix[row][column] = "   ";
            }
        }
        int row2n;
        int column2n;
        for (row2n = 0; row2n < 7; row2n += 2) {
            for (column2n = 1; column2n < 6; column2n += 2) {
                Tic_tac_toe.matrix[row2n][column2n] = "---";
                Tic_tac_toe.matrix[column2n][row2n] = " | ";
            }
        }
        return matrix;
    }


    static void drawGrid(String[][] matrix) {
        System.out.println("     1     2     3 ");
        int row;
        for (row = 0; row < 7; row++) {
            if (row == 1) {
                System.out.print("A");
            } else if (row == 3) {
                System.out.print("B");
            } else if (row == 5) {
                System.out.print("C");
            } else {
                System.out.print(" ");
            }
            int column;
            for (column = 0; column < 7; column++) {
                System.out.print(matrix[row][column]);
            }
            System.out.print("\n");
        }
    }
    static void insert(String[][] matrix, String position, String variable) {
        row = String.valueOf(position.charAt(0)).toUpperCase();
        column = String.valueOf(position.charAt(1)).toUpperCase();
        if (matrix[converter.get(row)][converter.get(column)].equals(" X ") || matrix[converter.get(row)][converter.
                get(column)].equals(" O ")) {
            System.out.println("Occupied!! enter another position:");
            position = input.nextLine();
            position = check_Validity(position, variable);
            insert(matrix, position, variable);
        } else {
            matrix[converter.get(row)][converter.get(column)] = variable;
        }
    }
    static String check_Validity(String position, String variable) {
        positions_list.add(position);
        if ((position).length() != 2) {
            System.out.println("Your response should be just 2 characters, the Letter for the row followed by the " +
                    "number for column,no space or anything else eg.'B2'.\nEnter the position where you want to " +
                    "place " + variable + ':');
            position = input.nextLine();
            positions_list.add(position);
            check_Validity(position, variable);
        } else {
            row = String.valueOf(position.charAt(0));
            column = String.valueOf(position.charAt(1));
            String row_options = "ABC";
            String column_options = "123";
            if (!(row_options.contains(row.toUpperCase()) && column_options.contains(column))) {
                System.out.println("The row can only be A,B, or C and column can ony be 1,2 or 3.\nEnter the position "
                        + "where you want to place " + variable + ':');
                position = input.nextLine();
                positions_list.add(position);
                check_Validity(position, variable);
            }
        }
        return positions_list.get(positions_list.size() - 1);
    }
    static String check_left_right(String[][] matrix,int row){
        if (row <= 5) {
            if (matrix[row][1].equals(" X ") && matrix[row][1].equals(matrix[row][3]) && matrix[row][3].equals(matrix
                    [row][5])){
                return "X";
            } else if (matrix[row][1].equals(" O ") && matrix[row][1].equals(matrix[row][3]) && matrix[row][3].equals(
                    matrix[row][5])){
                return "O";
            }else return check_left_right(matrix, row + 2);
        }else return "none";
    }
    static String check_up_down(String[][] matrix,int column) {
        if (column <= 5) {
            if (matrix[1][column].equals(" X ") && matrix[1][column].equals(matrix[3][column]) && matrix[3][column].
                    equals(matrix[5][column])) {
                return "X";
            } else if (matrix[1][column].equals(" O ") && matrix[1][column].equals(matrix[3][column]) && matrix[3]
                    [column].equals(matrix[5][column])) {
                return "O";
            }else return check_up_down(matrix, column + 2);
        }else return "none";
    }
    static String check_diagonals(String[][] matrix){
        if (matrix[1][1].equals(" X ") && matrix[1][1].equals(matrix[3][3]) && matrix[3][3].equals(matrix[5][5])){
            return "X";
        } else if (matrix[1][1].equals(" O ") && matrix[1][1].equals(matrix[3][3]) && matrix[3][3].equals(matrix[5][5])){
            return "O";
        } else if (matrix[5][1].equals(" X ") && matrix[5][1].equals(matrix[3][3]) && matrix[3][3].equals(matrix[1][5])){
            return "X";
        } else if (matrix[5][1].equals(" O ") && matrix[5][1].equals(matrix[3][3]) && matrix[3][3].equals(matrix[1][5])){
            return "O";
        } else return "none";
    }
    static String check_Winner(String[][] matrix) {
        if (!(check_left_right(matrix,1).equals("none"))){
            return check_left_right(matrix, 1);
        } else if (!(check_up_down(matrix, 1).equals("none"))){
            return check_up_down(matrix, 1);
        } else if (!(check_diagonals(matrix).equals("none"))){
            return check_diagonals(matrix);
        }else return "none";
    }
    static void Stay_Leave(String decision){
        if (decision.equals("1")){
            System.out.println("Would you like to play vs Computer (1) or Multiplayer (2)\nSelect 1 or 2:");
            matrix = createGrid();
            String mode = input.nextLine();
            while (!(mode.equals("1") || mode.equals("2"))){
                System.out.println("Invalid input. Would you like to play vs Computer (1) or Multiplayer (2)\nSelect 1 " +
                        "or 2:");
                mode = input.nextLine();
            }
            if(mode.equals("1")){
                Computer_main(matrix);
            }else{
                Multiplayer_main(matrix);
            }
            if (check_Winner(matrix).equals("none")){
                System.out.println("Game over, It's a draw!! Thanks for playing!, see you again.");
            }else{
                System.out.println("Game over!! The winner is " + check_Winner(matrix) + ", Thanks for playing!, see you" +
                        " again.");
            }
            System.out.println("Select 1 to play again or any other key to exit:");
            decision = input.nextLine();
            if (decision.equals("1")){
                System.out.println("We're glad to see you back again!!");
            }
            Stay_Leave(decision);

        }else{
            System.out.println("We're sad to see you leave, hoping to see you soon again.");
            System.exit(0);
        }
    }
    static void  play_random(String[][] matrix){
        variable = " O ";
        Collections.shuffle(edges);
        String choice = edges.get(0);
        if (choice.equals("0") && matrix[1][1].isBlank()){
            matrix[1][1] = variable;
            edges.remove(choice);
            System.out.println("Played A1");
        } else if (choice.equals("1") && matrix[1][3].isBlank()) {
            matrix[1][3] = variable;
            edges.remove(choice);
            System.out.println("Played A2");
        } else if (choice.equals("2") && matrix[1][5].isBlank()) {
            matrix[1][5] = variable;
            edges.remove(choice);
            System.out.println("Played A3");
        } else if (choice.equals("3") && matrix[3][1].isBlank()) {
            matrix[3][1] = variable;
            edges.remove(choice);
            System.out.println("Played B1");
        } else if (choice.equals("4") && matrix[3][5].isBlank()) {
            matrix[3][5] = variable;
            edges.remove(choice);
            System.out.println("Played B3");
        } else if (choice.equals("5") && matrix[5][1].isBlank()) {
            matrix[5][1] = variable;
            edges.remove(choice);
            System.out.println("Played C1");
        } else if (choice.equals("6") && matrix[5][3].isBlank()) {
            matrix[5][3] = variable;
            edges.remove(choice);
            System.out.println("Played C2");
        } else if (choice.equals("7") && matrix[5][5].isBlank()) {
            matrix[5][5] = variable;
            edges.remove(choice);
            System.out.println("Played C3");
        }else{
            play_random(matrix);
        }
    }
    static void Check_play(String[][] matrix, String tester, int count, ArrayList<String> edges){
        variable = " O ";
        testers.add(tester);
        if (count == 1){
            if (matrix[3][3].isBlank()){
                matrix[3][3] = variable;
                System.out.println("Played B2");
            }else{
                play_random(matrix);
            }
        } else if (matrix[1][1].equals(matrix[1][3]) && matrix[1][3].equals(tester) && matrix[1][5].isBlank()) {
            matrix[1][5] = variable;
            edges.remove("2");
            System.out.println("Played A3");
        } else if (matrix[3][1].equals(matrix[3][3]) && matrix[3][3].equals(tester) && matrix[3][5].isBlank()) {
            matrix[3][5] = variable;
            edges.remove("4");
            System.out.println("Played B3");
        } else if (matrix[5][1].equals(matrix[5][3]) && matrix[5][3].equals(tester) && matrix[5][5].isBlank()) {
            matrix[5][5] = variable;
            edges.remove("7");
            System.out.println("Played C3");
        } else if (matrix[1][3].equals(matrix[1][5]) && matrix[1][5].equals(tester) && matrix[1][1].isBlank()) {
            matrix[1][1] = variable;
            edges.remove("0");
            System.out.println("Played A1");
        } else if (matrix[3][3].equals(matrix[3][5]) && matrix[3][5].equals(tester) && matrix[3][1].isBlank()) {
            matrix[3][1] = variable;
            edges.remove("3");
            System.out.println("Played B1");
        } else if (matrix[5][3].equals(matrix[5][5]) && matrix[5][5].equals(tester) && matrix[5][1].isBlank()) {
            matrix[5][1] = variable;
            edges.remove("5");
            System.out.println("Played C1");
        } else if (matrix[1][1].equals(matrix[3][1]) && matrix[3][1].equals(tester) && matrix[5][1].isBlank()) {
            matrix[5][1] = variable;
            edges.remove("5");
            System.out.println("Played C1");
        } else if (matrix[1][3].equals(matrix[3][3]) && matrix[3][3].equals(tester) && matrix[5][3].isBlank()) {
            matrix[5][3] = variable;
            edges.remove("6");
            System.out.println("Played C2");
        } else if (matrix[1][5].equals(matrix[3][5]) && matrix[3][5].equals(tester) && matrix[5][5].isBlank()) {
            matrix[5][5] = variable;
            edges.remove("7");
            System.out.println("Played C3");
        } else if (matrix[3][1].equals(matrix[5][1]) && matrix[5][1].equals(tester) && matrix[1][1].isBlank()) {
            matrix[1][1] = variable;
            edges.remove("0");
            System.out.println("Played A1");
        } else if (matrix[3][3].equals(matrix[5][3]) && matrix[5][3].equals(tester) && matrix[1][3].isBlank()) {
            matrix[1][3] = variable;
            edges.remove("1");
            System.out.println("Played A2");
        } else if (matrix[3][5].equals(matrix[5][5]) && matrix[5][5].equals(tester) && matrix[1][5].isBlank()) {
            matrix[1][5] = variable;
            edges.remove("2");
            System.out.println("Played A3");
        } else if (matrix[1][1].equals(matrix[1][5]) && matrix[1][5].equals(tester) && matrix[1][3].isBlank()) {
            matrix[1][3] = variable;
            edges.remove("1");
            System.out.println("Played A2");
        } else if (matrix[5][1].equals(matrix[5][5]) && matrix[5][5].equals(tester) && matrix[5][3].isBlank()) {
            matrix[5][3] = variable;
            edges.remove("6");
            System.out.println("Played C2");
        } else if (matrix[1][1].equals(matrix[5][1]) && matrix[5][1].equals(tester) && matrix[3][1].isBlank()) {
            matrix[3][1] = variable;
            edges.remove("3");
            System.out.println("Played B1");
        } else if (matrix[1][5].equals(matrix[5][5]) && matrix[5][5].equals(tester) && matrix[3][5].isBlank()) {
            matrix[3][5] = variable;
            edges.remove("4");
            System.out.println("Played B3");
        } else if (matrix[3][3].equals(matrix[1][1]) && matrix[1][1].equals(tester) && matrix[5][5].isBlank()) {
            matrix[5][5] = variable;
            edges.remove("7");
            System.out.println("Played C3");
        } else if (matrix[3][3].equals(matrix[5][5]) && matrix[5][5].equals(tester) && matrix[1][1].isBlank()) {
            matrix[1][1] = variable;
            edges.remove("0");
            System.out.println("Played A1");
        } else if (matrix[3][3].equals(matrix[1][5]) && matrix[1][5].equals(tester) && matrix[5][1].isBlank()) {
            matrix[5][1] = variable;
            edges.remove("5");
            System.out.println("Played C1");
        } else if (matrix[3][3].equals(matrix[5][1]) && matrix[5][1].equals(tester) && matrix[1][5].isBlank()) {
            matrix[1][5] = variable;
            edges.remove("2");
            System.out.println("Played A3");
        } else if (testers.get(testers.size()-1).equals(" O ")) {
            Check_play(matrix, " X ", count, edges);
        }else{
            play_random(matrix);
        }
    }
    static void Computer_main(String[][] matrix){
        testers.clear();
        edges = new ArrayList<>(Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7"));
        for (int count = 0; count < 9; count++) {
            if (count > 4 && !(Objects.equals(check_Winner(matrix), "none"))) {
                drawGrid(matrix);
                System.out.println("Game over!!The winner is " + check_Winner(matrix) + ", Thanks for playing!, see you " +
                        "again.");
                System.out.println("Select 1 to play again or 0 to exit:");
                String decision = input.nextLine();
                if (decision.equals("1")) {
                    System.out.println("We're glad to see you back!!");
                }
                Stay_Leave(decision);
                return;
            }if (count % 2 == 0 ){
                drawGrid(matrix);
                variable = " X ";
                System.out.println("Enter the position where you want to place " + variable + ":");
                position = check_Validity(input.nextLine(), variable);
                insert(matrix, position, variable);
            }else{
                System.out.println("Computer thinking");
                Check_play(matrix, " O ", count, edges);
            }
        }
        drawGrid(matrix);
    }
    static void Multiplayer_main(String[][] matrix){
        positions_list.clear();
        for (int count = 0; count < 9; count++) {
            drawGrid(matrix);
            if (count > 4 && !(check_Winner(matrix).equals("none"))) {
                System.out.println("Game over!!The winner is " + check_Winner(matrix) + ", Thanks for playing!, see you " +
                        "again.");
                System.out.println("Select 1 to play again or any other key to exit:");
                String decision = input.nextLine();
                if (decision.equals("1")) {
                    System.out.println("We're glad to see you back!!");
                }
                Stay_Leave(decision);
            }
            if (count % 2 == 0) {
                variable = " X ";
            } else {
                variable = " O ";
            }
            System.out.println("Enter the position where you want to place " + variable + ":");
            position = check_Validity(input.nextLine(), variable);
            insert(matrix, position, variable);
        }
        drawGrid(matrix);
    }
    public static void main(String[] Mike){
        converter.put("A", 1);
        converter.put("B", 3);
        converter.put("C", 5);
        converter.put("1", 1);
        converter.put("2", 3);
        converter.put("3", 5);
        System.out.println("""
                Welcome to Sir Michael's Tic-Tac-Toe Game!
                The instructions are:
                Enter the row's Alphabet followed by the column number of the position where you want to enter 'X' or
                 'O' for example 'B1'  will result in the following:""");
        matrix = createGrid();
        insert(matrix, "B1", " X ");
        drawGrid(matrix);
        Stay_Leave("1");
        if (check_Winner(matrix).equals("none")){
            System.out.println("Game over, It's a draw!! Thanks for playing!, see you again.");
        }else{
            System.out.println("Game over!! The winner is " + check_Winner(matrix) + ", Thanks for playing!, see you " +
                    "again.\n\"Select 1 to play again or any other key to exit:");
            String decision = input.nextLine();
            if (decision.equals("1")){
                System.out.println("We're glad to see you back!!");
            }
            Stay_Leave(decision);
        }
    }
}