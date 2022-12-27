import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main extends Application {

    static File file;

    public static void main(String[] args) {
//        System.out.println(checkStructureValidity(readFromFile(new File("file1.242"))));
        String content = readFromFile(new File("file1.242"));
        String [] Expressions = getExpresions(content);
        String [] results = postFix(Expressions);
        for (int i = 0; i < results.length; i++){
            System.out.println(results[i]);
        }

        String equation = "1+2/5*(3+5)*4";
        String fixedEq = postFix(equation);
        System.out.println(fixedEq);
//        launch(args);
    }
    //method that takes in the array of expressions and returns an array of them with a postfix notation for each
    public static String[] postFix(String[] expressions){
        String[] postFix = new String[expressions.length];
        for (int i = 0; i < expressions.length; i++){ //goes through each expression
            postFix[i] = postFix(expressions[i]); // and converts it to postfix notation
        }
        return postFix; //returns the array of postfix expressions
    }
    //method to read the file and return the string with each tag and the content after displayed in a line
    private static String readFromFile(File file) {
        String content = ""; //string to store the content of the file
        try {
            Scanner fsc = new Scanner(file);
            while (fsc.hasNextLine()) { //scans by line
                content += fsc.nextLine();//.replaceAll("\\s", "");
//                content += "\n";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        content = content.replaceAll("\\s", ""); //removes all the white spaces
        StringBuilder sb = new StringBuilder(); //makes a string builder to be able to append a new line after each tag to make it easer to find them

        for (int i = 0; i < content.length(); i++) {
            if (content.charAt(i) != '>' && content.charAt(i) != '<') { //adds everything so long a new tag is not found
                sb.append(content.charAt(i));
            }
            else if (content.charAt(i) == '>') { //adds a line after a tag
                sb.append(">");
                sb.append("\n");
            }
            else if (content.charAt(i) == '<') { //adds a line before a tag
                sb.append("\n");
                sb.append("<");
            }


        }
        //removing empty lines from string builder caused by adding a new line after and before each tag
        ArrayList <String> lines = new ArrayList<>(List.of(sb.toString().split("\n"))); //converting the string builder to an array indexing each line then converting it to an array list in order to remove the empty lines
        sb = new StringBuilder(); //emptying the string builder
        for (int i =0; i<lines.size(); i++){ //looping through the lines stored in the array list
            if (lines.get(i).length() == 0) { //if the length is 0 then it is an empty line
                lines.remove(i); //removing the empty line
                i--; //decrementing i to make sure the next line is not skipped
            }
            else {
                sb.append(lines.get(i)); //if the line is not empty then it is added to the string builder
                sb.append("\n"); //adding a new line after each line
            }
        }

        System.out.println(sb.toString());
        return sb.toString(); //converting the string builder to a string and returning it
    }
    //method that extracts only the expression from the string and returns them as an array of expressions
    private static String [] getExpresions(String content){
        if (!checkStructureValidity(content)){ //checks if the structure is valid and returns null if it is not
            return null;
        }
        String [] contentArray = content.split("\n"); //splits the content by line and stores it in an array has the structure been valid
        ArrayList <String> expressions  = new ArrayList<>(); //arraylist to store only the experessions without the tags and other content
//        ArrayList <String> contentArrayList = new ArrayList<>(List.of(contentArray));
        for (int i = 0; i < contentArray.length; i++){ //looping through the array of the content to find "<equation> tag and closing tag (since the structure is valid)
            if (contentArray[i].equals("<equation>")){ //if found the tag pair, it adds the line in between to the expression array list
                if (contentArray[i+2].equals("</equation>")){
                    expressions.add(contentArray[i+1]); //the expression is stored in one line since the readFromFile method removes all the white spaces
                }
            }
        }
        String [] expressionsArray = new String[expressions.size()]; //converting the array list to an array
        for (int i = 0; i < expressions.size(); i++){
            expressionsArray[i] = expressions.get(i);
        }
        return expressionsArray; //returning the array of expressions
    }
    //method to process the string removing whitespace and placing each pair of tag beginning and ending in one line so that they are checked in a line by line basis.
//    private static String processString(String content) {
//        String processed = "";
//        String[] lines = content.split("\n");
//        for (String line : lines) {
//            line = line.replaceAll("\\s", "");
////            if (line.contains("<\\w") && line.contains("</")) {
//                processed += line + "\n";
////            }
//        }
//        return processed;
//    }
    //method to check if a string contains a tag keyword and returns an array of the tags found
//    private static LnList findTags(String content) {
//        LnList foundTags = new LnList(); //uses a linked list to store the tags as they are found
//        String[] tags = {"<242>", "<equations>","<equation>", "</equation>", "</equations>", "<files>", "<file>", "</file>", "</files>", "</242>"}; //array has to be ordered for the stack to work properly (see below)
//        for (String tag : tags) {
//            if (!content.contains(tag)) { //if one of the tags is not found at all, the content is invalid
//                return null;
//            }
//            while (content.contains(tag)) { //going over all instances of the tag
//                foundTags.addLast(tag); //adding the tag to the list
//                content = content.replaceFirst(tag, ""); //removing tag from original string
//            }
//        }
////        foundTags.print();
//        return foundTags; //list of all found tags
//    }
    //method to check if the text extracted from the file is valid
    private static boolean checkStructureValidity(String content) {
//        boolean isValid = true;
        if (!content.contains("<242>")) {
            return false;
        }
        MyStack tagsStack = new MyStack(100); //stack to store the tags as they are found of 100 size
        for(String line : content.split("\n")) {
            if (line.equals("<242>")) {
                tagsStack.push("<242>"); //pushes all opening tags to the stack
            }
            else if (line.equals("<files>")){
                tagsStack.push("<files>");
            }
            else if (line.equals("<file>")){
                tagsStack.push("<file>");
            }
            else if (line.equals("</file>")){ //once closing tag is found it looks for its opening tag in the top of the stack
                if (tagsStack.getTop().equals("<file>")) {
                    tagsStack.pop();
                }
                else {
                    return false;
                }
            }
            else if (line.equals("</files>")){
                if (tagsStack.getTop().equals("<files>")) {
                    tagsStack.pop();
                }
                else {
                    return false;
                }
            }
            else if (line.equals("<equations>")){
                tagsStack.push("<equations>");
            }
            else if (line.equals("<equation>")){
                tagsStack.push("<equation>");
            }
            else if (line.equals("</equation>")){
                if (tagsStack.getTop().equals("<equation>")) {
                    tagsStack.pop();
                }
                else {
                    return false;
                }
            }
            else if (line.equals("</equations>")){
                if (tagsStack.getTop().equals("<equations>")) {
                    tagsStack.pop();
                }
                else {
                    return false;
                }
            }
            else if (line.equals("</242>")){
                if (tagsStack.getTop().equals("<242>")) {
                    tagsStack.pop();
                }
                else {
                    return false;
                }
            }
        }
        return true;
//        LnList tagList = findTags(content);
//        if (findTags(content) == null) {//if one of the tags is not found at all
//            return false;
//        }
//        else if (tagList.getSize() % 2 != 0 ) { //if the number of tags is odd, then there is a missing tag
//            return false;
//        } else {
//            MyStack contentStack = new MyStack(tagList.getSize()); //stack to check the validity of the file structure
//            LnNode current = tagList.getFirst();
////            String sections [] = content.split("\\s");
////            for (int i = 0; i < sections.length; i++) {
//            for (int i =0; i < tagList.getSize(); i++) {
//                System.out.println(current.data);
////            System.out.println(contentStack.getTop());
//                if (current.data.equals("<242>")) {
//                    contentStack.push(current.data);
//                } else if (current.data.equals("<files>")) {
//                    contentStack.push(current.data);
//                } else if (current.data.equals("<file>")) {
//                    contentStack.push(current.data);
//                    if (current.next.data.equals("</file>")) {
//                        contentStack.pop();
//                        current = current.next;
//                    } else return false;
//                } else if (current.data.equals("<equations>")) {
//                    contentStack.push(current.data);
//                }
//                else if (current.data.equals("<equation>")) {
//                    contentStack.push(current.data);
//                    if (current.next.data.equals("</equation>")) {
//                        contentStack.pop();
//                        current = current.next;
//                    } else return false;
////                else if (current.data.equals("</equation>")) {
////                    if (contentStack.getTop().equals("<equation>")) {
////                        contentStack.pop();
////                    }else {
////                        isValid = false;
////                        break;
////                    }
//                }
//                else if (current.data.equals("</equations>")) {
//                    if (contentStack.getTop().equals("<equations>")) {
//                        contentStack.pop();
//                    }else {
//                        isValid = false;
//                        break;
//                    }
//                }
////                else if (current.data.equals("</file>")) {
////                    if (contentStack.getTop().equals("<file>")) {
////                        contentStack.pop();
////                    }else {
////                        isValid = false;
////                        break;
////                    }
////                }
//                else if (current.data.equals("</files>")) {
//                    if (contentStack.getTop().equals("<files>")) {
//                        contentStack.pop();
//                    }else {
//                        isValid = false;
//                        break;
//                    }
//                }
//                else if (current.data.equals("</242>")){
//                    if (contentStack.getTop().equals("<242>")) {
//                        contentStack.pop();
//                    } else {
//                        isValid = false;
//                        break;
//                    }
//                }
//                if (i == tagList.getSize() - 1 && !contentStack.isEmpty()) {
//                    isValid = false;
//                }
////                System.out.println(sections[i]);
//                current = current.next;
//            }
//
//        }
//
//        System.out.println(contentStack.getTop());
//        return isValid;
    }
    //method that checks the priority of the operators
    private static int checkPriorityOfOperation(char op){
        if (op == '*' || op == '/'){
            return 2;
        }
        else if (op == '+' || op == '-'){
            return 1;
        }
        else if (op == '^')
            return 3;
        return 0;

    }
    //method to check the validity of the parenthesis using stack
    private static boolean checkExpressionValidity(String equation){
        equation = equation.replaceAll("\\s", "");
        MyStack myStack = new MyStack(equation.length());
        for (int i = 0; i < equation.length(); i++) {
//            if (Character.isLetterOrDigit(equation.charAt(i)) && i < equation.length() - 2){
//                if (equation.charAt(i+1) == ' ' && Character.isLetterOrDigit(equation.charAt(i + 2))) {//found two consecutive numbers or variables without an operator in between
//                    return false;
//                }
//        } //deleted since there is no space, consecutive variables or/and numbers are considered one number/variable
            if (i < equation.length() -1 && (equation.charAt(i) == '*' || equation.charAt(i) == '/' || equation.charAt(i) == '+' || equation.charAt(i) == '-' || equation.charAt(i) == '^')) {
                if (equation.charAt(i + 1) == '*' || equation.charAt(i + 1) == '/' || equation.charAt(i + 1) == '+' || equation.charAt(i + 1) == '-' || equation.charAt(i + 1) == '^') { //found two consecutive operators
                    return false;
                }
            }
            if (equation.charAt(i) == '('){ //found an opening parenthesis
                myStack.push(i);
            }
            else if (equation.charAt(i) == ')'){
                if (myStack.isEmpty()){ //empty and still found a closing parenthesis
                    return false;
                }
                else { //not empty but found a closing parenthesis
                    myStack.pop();
                }
            }
            if (i == equation.length()-1){
                if (!myStack.isEmpty()){// reached the end of the equation but the stack is not empty
                    return false;
                }
            }
        }
        return true;
    }
    //method to convert infix to postfix of one expression using stack. It returns a string of the infix and a postfix expression with a "=>" in between
    private static String postFix(String equation) {
        if (!checkExpressionValidity(equation)) { //checking if the parenthesis are valid
            return equation + "=> " + "Invalid Equation"; //TODO: add to gui
        }
//        String [] equationArray = equation.split("\\+|-|\\*|/|\\^");
        equation += " "; // add a space to the end of the equation to make sure the last char is added to the stack
        String [] equationArray = equation.split(" "); //splitting the equation into an array of numbers or/and variables and operators
        StringBuilder postfix = new StringBuilder();//using string builder to add elements to the expression
        MyStack operationStack = new MyStack(equation.length()); //stack to hold the operators
        for (int i = 0; i < equationArray.length; i++) { //looping through the array elements (numbers or/and variables and operators)
            if (equationArray[i].equals("(")) { //if the element is an opening parenthesis, it adds to the stack
                operationStack.push(equationArray[i]);
            } else if (equationArray[i].equals(")")) {
                while (!operationStack.getTop().equals("(")) { //adds all operations in between parenthesis to the expression so long '(' is not the top of the stack
                    postfix.append(operationStack.pop()).append(" "); //operator followed by space
                }
                operationStack.pop(); //removes the opening parenthesis
            } else if (equationArray[i].equals("+") || equationArray[i].equals("-") || equationArray[i].equals("*") || equationArray[i].equals("/") || equationArray[i].equals("^")) {//if it encountered an operator
                while (!operationStack.isEmpty() && (checkPriorityOfOperation((char) operationStack.getTop()) >= checkPriorityOfOperation(equationArray[i].charAt(0)))) { //so long the top stack operator of higher or equal priority
                    postfix.append(operationStack.pop()).append(" "); //it removes that operator from the stack and adds it to the expression followed by a space
                }
//                if (checkPriorityOfOperation(equationArray[i].charAt(0)) > checkPriorityOfOperation((char)operationStack.getTop())){ //if the operator in the string is of higher priority than the one in the stack, it pushes it to the stack
//                    operationStack.push(equation.charAt(i));
//                }
                operationStack.push(equationArray[i]); //if the operator in the string is of higher priority than the one in the stack, it pushes it to the stack
            } else {
                postfix.append(equationArray[i]).append(" "); //if it is a number or variable, it adds it to the expression followed by a space
            }
        }
//        StringBuilder fixedEq = new StringBuilder(); //string builder to easily add chars to the string
//        MyStack opStack = new MyStack(100);
//        char currentChar;
//        for (int i =0; i < equation.length(); i++){ //Going over the string character by character
//            currentChar = equation.charAt(i);
//            if (Character.isLetterOrDigit(currentChar)){ //adds to string if encountered a variable or number
//                fixedEq.append(currentChar);
//            }
//            else if (currentChar == '('){ //pushes to stack if encountered a '('
//                opStack.push(currentChar);
//            }
//            else if (currentChar == ')'){
//                while ((char)opStack.getTop() != '('){ //pops from stack and adds to the string until it encounters a '('
//                    fixedEq.append(opStack.pop());
//                }
//                opStack.pop(); //pops the '(' without adding it to the string
//            }
//            else if (!opStack.isEmpty() && checkPriorityOfOperation(currentChar) > checkPriorityOfOperation((char)opStack.getTop())){ //if the operator in the string is of higher priority than the one in the stack, it pushes it to the stack
//                opStack.push(equation.charAt(i));
//            }
//            else { //if the character is an operator
//                while (!opStack.isEmpty() && checkPriorityOfOperation(currentChar) <= checkPriorityOfOperation((char)opStack.getTop())){ //pops into string so long the stack is not empty or the operation reached in the string is of less priority of the ones in the stack
//                    fixedEq.append((char) opStack.pop());
//                }
//                opStack.push(currentChar); //pushes the operator to the stack
//            }
//        }
        return equation + "=> " + postfix.toString(); //converting to string and returning
    }

    @Override
    public void start(Stage stage) throws Exception {
        //File browser Section
//        FileBrowser fileBrowser = new FileBrowser();
        Button backButton = new Button("Back");
        TextField pathField = new TextField();
        pathField.setEditable(false);
        pathField.setMinWidth(237);
        Button browseButton = new Button("Browse");
        HBox fileBrowserPane = new HBox(backButton, pathField, browseButton);
        fileBrowserPane.setAlignment(Pos.CENTER);
        fileBrowserPane.setSpacing(10);


        //Equation Section
        TextArea equationArea = new TextArea();
        equationArea.setEditable(false);
        equationArea.setPrefSize(200, 300);
        StackPane equationPane = new StackPane(equationArea);

        //Files Section
        TextArea filesArea = new TextArea();
        filesArea.setEditable(false);
        StackPane filesPane = new StackPane(filesArea);

        //Feedback Section
        TextField feedbackField = new TextField();
        feedbackField.setEditable(false);
        StackPane feedbackPane = new StackPane(feedbackField);


        // Initialize the Stage with title and size
        VBox root = new VBox(fileBrowserPane, equationPane, filesPane, feedbackPane);
        root.setSpacing(20); //Adding some space
        root.setPadding(new Insets(20, 20, 20, 20)); //Adding margins
//        root.setMargin(equationPane, new Insets(10, 10, 10, 10));
        Scene scene = new Scene(root, 100, 100);
        stage.setTitle("Equation Browser");
        stage.setWidth(400);
        stage.setHeight(500);
        stage.setResizable(false); //Fixed window size
        stage.setScene(scene);
        stage.show();

        //Button Listeners
//        browseButton.setOnAction(e -> {
//
//        });
    }
}
