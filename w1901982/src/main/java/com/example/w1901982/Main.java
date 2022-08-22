package com.example.w1901982;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import static javafx.application.Application.launch;


public class Main extends Application {
    static int fullFuelStock = 6600;
    static int income;
    static Scanner input = new Scanner(System.in);
    static FuelQueue[][] queue = new FuelQueue[5][6];

    static FuelQueue customer = new FuelQueue();

    public static void main(String[] args) {

        System.out.println("""
                Filling Station
                -------------------------------------------------------------------
                100 or VFQ => View all Fuel Queues.
                101 or VEQ => View all Empty Queues.
                102 or ACQ => Add customer to a Queue.
                103 or RCQ => Remove a customer from a Queue.
                104 or PCQ => Remove a served customer.
                105 or VCS => View Customers Sorted in alphabetical order.
                106 or SPD => Store Program Data into file.
                107 or LPD => Load Program Data from file.
                108 or STK => View Remaining Fuel Stock.
                109 or AFS => Add Fuel Stock.
                110 or IFQ => Income of the Queue;
                111 or GUI => Load GUI
                999 or EXT => Exit the Program.
                -------------------------------------------------------------------
                """);

        while (true) {
            System.out.println("Enter the Option:");
            String Option = input.next();

            if (Option.equals("100") || Option.equalsIgnoreCase("VFQ")) { // view all fuel queues
                view();
            }
            if (Option.equals("101") || Option.equalsIgnoreCase("VEQ")) {  // view all empty queues
                q_empty();
            }
            if (Option.equals("102") || Option.equalsIgnoreCase("ACQ")) {  // add customers to queues
                addCustomer();
            }
            if (Option.equals("103") || Option.equalsIgnoreCase("RCQ")) {  // remove customer from queue
                removeCustomer();
            }
            if (Option.equals("104") || Option.equalsIgnoreCase("PCQ")) {  // remove reserved customer from queues
                removeSerCustomer();
            }
            if (Option.equals("105") || Option.equalsIgnoreCase("VCS")) {  // View Customers Sorted in alphabetical order
                viewCustomerInAl_Order();
            }
            if (Option.equals("106") || Option.equalsIgnoreCase("SPD")) {  // Store Program Data into file.
                storeData();
            }
            if (Option.equals("107") || Option.equalsIgnoreCase("LPD")) {  // Load Program Data from file.
                loadData();
            }
            if (Option.equals("108") || Option.equalsIgnoreCase("STK")) {  // View Remaining Fuel Stock.
                remainingStock();
            }
            if (Option.equals("109") || Option.equalsIgnoreCase("AFS")) {  // Add Fuel Stock
                addFuelStock();
            }
            if (Option.equals("110") || Option.equalsIgnoreCase("IFQ")) {  //Income of the fuel Queue
                income();
            }
            if (Option.equals("111") || Option.equalsIgnoreCase("GUI")) {  //GUI for the fuel Queue
                gui();
            }
            if (Option.equals("999") || Option.equalsIgnoreCase("EXT")) {  //Exit the Program
                exit();
            }
        }

    }

    private static void gui() { //method to load GUI. If there is no gui it throws exception as no GUI
        try {
            launch();
        }catch (Exception exception){
            System.out.println("No any GUI");
        }
    }
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Fuel Queue Management");
        stage.setScene(scene);
        stage.show();
        /*Override start method.create new object from FXMLLoader and create object
        called scene and set the scene and load that UI*/
    }

    static void q_empty() {   // view all empty queues
        for (int i = 0; i < 5; i++) {
            if (queue[i][0] == null) {
                System.out.println("Line number " + (i + 1) + " is still empty");
            } else {
                System.out.println("Line number " + (i + 1) + " is not empty");
            }
        }
        /*check that specific position is empty or not and
        In here it is run until all positions get filled  in each row.*/
    }

    static void view() {    // view all fuel queues
        System.out.println("These are the Queues");
        for (int i = 0; i < 5; i++) {
            System.out.println("Queue No:" + (i + 1));
            for (int j = 0; j < 6; j++) {
                if (queue[i][j] == null) {
                    System.out.println("empty/empty......");
                }
                else {
                    System.out.println("Already occupied");
                }
                /*five fuel queues with 6 customers for each. print Queue no by adding one to the index.*/
            }
        }
    }

    static void income() {      //income of the queue.
        System.out.println("Income  "+income);
    }

    static void exit() {
        System.out.println("Thank you!!!"); //to exit from the programme
        System.exit(0);
    }

    static void addFuelStock() {        //add new fuel stock to current stock and print new stock
        System.out.println("Amount of fuel in liters added:  ");
        int newFuelStock=input.nextInt();
        fullFuelStock+=newFuelStock; //take new variable and add full stock availble in current situation.
        System.out.println("New fuel Stock"+" "+fullFuelStock);

    }

    static void remainingStock() {      //prints remaining stock after serve for customers
        System.out.println("Remaining Stock"+" "+fullFuelStock);
    }

    static void loadData() {        //load data to the text file called fuel_Queue_Details
        try{
            File txt = new File("fuel_Queue_Details.txt");
            Scanner read = new Scanner(txt);
            for(int i = 0; i < 5; i++){         //looping for 30 positions
                for(int j = 0; j < 6; j++){
                    if(read.hasNextLine()){
                        String readingLine = read.nextLine();
                        if( readingLine.equals("null")){
                            queue[i][j] = null;             //If there is empty record, null is assign to it
                        }
                        else {
                            queue[i][j] = new FuelQueue();
                            queue[i][j].passenger.setFirstName(readingLine);      //take the passenger details from line by line.
                            queue[i][j].passenger.setSecondName(read.nextLine());
                            queue[i][j].passenger.setVehicleNo(read.nextLine());
                            queue[i][j].passenger.setLitersRequired(Integer.parseInt(read.nextLine()));

                        }
                    }
                }
            }
            read.close();
            System.out.println("Data loaded successful.");
        }
        catch(IOException e){
            System.out.println("File cannot be found.");
        }
        /*make new object as File and naming as txt.after taking details, it will call  close() method. If data loaded successfully, It prints Data loaded successfully
        * and if not it prints file can not be found */
    }


    static void storeData() {
        try{
            FileWriter writer = new FileWriter("fuel_Details.txt");     //store data into text file
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 6; j++) {
                    if (queue[i][j]==null) {
                        writer.write("null");
                    }
                    /*looping for 30 occurences. If one record is null, it takes only one line and if it is not null it prints customer details.
                    * fro one customer , it takes four lines */
                    else {
                        writer.write(queue[i][j].passenger.getFirstName() + "\n");
                        writer.write(queue[i][j].passenger.getSecondName() + "\n");
                        writer.write(queue[i][j].passenger.getVehicleNo() + "\n");
                        writer.write(queue[i][j].passenger.getLitersRequired() + "\n");

                    }
                }

            }
            writer.close();
            System.out.println("Data was successfully stored");
        }
        catch (IOException e){
            System.out.println("Data couldn't be stored.");
        }
        /*make new object as File and naming as txt.after taking details, it will call  close() method. If data loaded successfully, It prints Data loaded successfully
         * and if not it prints file can not be found */
    }

    static void viewCustomerInAl_Order() {
        FuelQueue[][] customerOrder = new FuelQueue[5][6];      //make new array called customerOrder
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 6; j++) {
                customerOrder[i][j] = queue[i][j];      //current array assign to new array called customerOrder
                if (customerOrder[i][j] == null) {
                    customerOrder[i][j] = new FuelQueue();
                    customerOrder[i][j].passenger.setFirstName("empty");
                }
                /*If */
            }
        }
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 6; j++) {
                for (int x = 1; x < 6 - j; x++) {
                    if ((customerOrder[i][x - 1].passenger.getFirstName().compareToIgnoreCase(customerOrder[i][x].passenger.getFirstName())) > 0) {
                        FuelQueue tempElement = customerOrder[i][x - 1];
                        customerOrder[i][x - 1] = customerOrder[i][x];
                        customerOrder[i][x] = tempElement;
                    }
                }
                /*Sorted using bubble sort.*/
            }
        }
        System.out.println("The sorted queue is viewed below");
        for (int i = 0; i < 5; i++) {
            System.out.println("Queue " + (i + 1));
            for (int j = 0; j < 6; j++) {
                if (customerOrder[i][j].passenger.getFirstName().equals("empty")) {
                    System.out.println("still empty");
                    /*If the first name equals empty, it prints that position is empty*/
                } else {
                    System.out.println(customerOrder[i][j].passenger.getFirstName());
                }
            }
            System.out.println();
        }
    }

    static void removeSerCustomer() {
        System.out.print("Enter Queue number: ");
        int que_Num = input.nextInt();
        System.out.println("Served Customer removed from the queue");
        queue[que_Num - 1][0] = null;
        fullFuelStock -=10;
        System.out.println(fullFuelStock+" ");
        for (int i = 1; i < 6; i++) {
            if (queue[que_Num - 1][i - 1] == null) {
                queue[que_Num - 1][i - 1] = queue[que_Num - 1][i];
                queue[que_Num - 1][i] = null;
            }
            /*If queue number -1 and i-1 equals to null.when front customer removed, next customer goes to front place.
        Then next iteration null assigned to person in next position.In here fuel stock also get reduce */
        }
        for (int i = 0; i < 6; i++){
            if(queue[que_Num - 1][i] == null){
                queue[que_Num - 1][i] = customer.deQueue();
            }
        }
         /*If one position get blanked in the queue, it filled by customer in waiting list.So deQueue from waiting list*/

    }

    static void removeCustomer() {
        System.out.print("Enter Queue number: ");
        int que_Num = input.nextInt();
        int posNum;
        System.out.print("Enter the position number: ");
        posNum = input.nextInt();
        /*customer can enter queue number and position in that queue.*/
        queue[que_Num - 1][posNum - 1] = null;
        System.out.println("Customer is removed from the queue");
        fullFuelStock +=10;
        System.out.println(fullFuelStock+"\n ");
        /*when customer removed fuel stock get increase by 10  */
        for(int i = posNum; i< 6;i++)

        {
            if (queue[que_Num - 1][i - 1] == null) {
                queue[que_Num - 1][i - 1] = queue[que_Num - 1][i];
                queue[que_Num - 1][i] = null;
            }
        /*If queue number -1 and i-1 equals to null.when front customer removed, next customer goes to front place.
        Then next iteration null assigned to person in next position.*/

        }
        for (int i = 0; i < 6; i++){
            if(queue[que_Num - 1][i] == null){
                queue[que_Num - 1][i] = customer.deQueue();
            }
        }
        /*If one position get blanked in the queue, it filled by customer in waiting list.So deQueue from waiting list*/
    }

    static void addCustomer() {
        if (fullFuelStock <= 500) {
            System.out.println("Warning! Fuel Stock is low " + fullFuelStock);
            /*gave the warning when full stock get decrease more than the 500*/
        }
        int maxSpaces = 0;
        int queueNumber = minQueue();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 6; j++) {
                if (queue[i][j] != null) {
                    maxSpaces += 1;
                }
            }
        }
        /*take the variable as maxSpaces. then take the queue number by minQueue() method.
        * If position is not null maxSpaces increament by 1*/
        if (maxSpaces==30) {
            System.out.println("The Queues are full\nSo you have to wait in waiting queue");
            System.out.print("Enter the Customer's First Name: ");
            customer.passenger.setFirstName(input.next());
            System.out.print("Enter the Customer's Last Name: ");
            customer.passenger.setSecondName(input.next());
            System.out.print("Enter the Vehicle Number: ");
            customer.passenger.setVehicleNo(input.next());
            System.out.print("Enter the No.of Litres: ");
            customer.passenger.setLitersRequired(input.nextInt());
            customer.enQueue(customer);
            /*when maxSpaces equals to 30, customers will goes to waiting list.*/
        } else {
            System.out.print("Enter the Customer's First Name: ");
            String firstName = input.next();
            firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1).toLowerCase();
            System.out.print("Enter the Customer's Last Name: ");
            String secondName = input.next();
            secondName = secondName.substring(0, 1).toUpperCase() + secondName.substring(1).toLowerCase();
            System.out.print("Enter the Vehicle Number: ");
            String vehicleNumber = input.next();
            System.out.print("Enter the No.of Litres: (only from number)");
            int litersRequired = input.nextInt();
            /*Until maxSpaces get into 30, customers enter to the queues.*/
            for (int j = 0; j < 6; j++) {
                if (queue[queueNumber][j] == null) {
                    queue[queueNumber][j] = new FuelQueue();
                    queue[queueNumber][j].passenger.setFirstName(firstName);
                    queue[queueNumber][j].passenger.setSecondName(secondName);
                    queue[queueNumber][j].passenger.setVehicleNo(vehicleNumber);
                    queue[queueNumber][j].passenger.setLitersRequired(litersRequired);
                    /*take the customer details from passenger class, if the position is empty.*/
                    fullFuelStock -= litersRequired;
                    income = income + (litersRequired * 430);
                    /*updated fuel stock and income*/
                    System.out.println("\n" + firstName + " " + secondName + " added to the queue " + (queueNumber + 1) + " successfully");
                    System.out.println(income);
                    System.out.println(fullFuelStock);
                    break;
                }
            }
        }
    }

    static int minQueue() {
        int maxCount = 6;
        int minimumQueueNo = 0;
        for (int i=0;i<5;i++)
        {
            int count = 0;
            for (int j=0;j<6;j++)
            {
                if (queue[i][j] != null)
                {
                    count ++;
                }
            }
            if (count<maxCount)
            {
                maxCount = count;
                minimumQueueNo = i ;
            }
        }
        return minimumQueueNo;
    }
    /*getting the queue with maximum spaces.*/
    /*start from first queue to enter customers and it goes to 2,3,4,5 in order
    * first enter customers to first index */
}

