package com.mycompany.semester;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;//import all of javafx again


/**
 * @author CJ
 * 10-29-25
 * GUI to order food at a skate rink
 */
/*

CUSTOMER REQUESTS:

-Interactive GUI(not a console based application)
-Buttons to select food items
-Option to remove unwanted food items
-Warning users not to select less than 0 of any items
-Option to tip
-Write the recipt to a text file
-As little room for user error as possible(no text inputs)
-Consisten way to hold the names and prices of the menu items
-A way to go back from the subtotal screen


*** added warning for users who do not order any items
*** added itemized bill at the end
*** added continuity for button placement
*/

public class App extends Application {
    public int[] cart = new int[8];//holds the amount of each item
    double total = 0;//holds the running total cost
    public double[] cost = new double[] {1,1.5,2,2,.5,1.75,1.25,.75};//holds the price of each item
    public String[] items = new String[] {"Taco","Hamburger","Cheeseburger",//holds the name of each item
        "Salad","French Fries","Nachos","Large Drink","Small Drink"};//only used for writing to txt file
    
    
    String filename = ("recipt.txt");//filename to store results
    
    @Override
    public void start(Stage stage) {//initiates the window for the entire application
    //each stage will transition to the next without closing the window
        
        GridPane pane = new GridPane();
        var scene = new Scene(pane,300,450);//size of the window
        pane.setPadding(new Insets(20,20,20,20));//space at each edge of the window
        pane.setVgap(30);
        stage.setTitle("Programming II Semester Project");
        stage.setScene(scene);
        
        
            pane.add(new Label("Welcome!"), 0, 0);
            Button exitButton = new Button("Exit");
            exitButton.setOnAction(event -> exitButtonClicked());//one method for each exit button
        
            Button next = new Button("Next");
            next.setOnAction(event->{
            stage.getScene().setRoot(menu(stage));//Transitions to the next stage
            });
        
        HBox bbox = new HBox();// houses the next and exit buttons
        bbox.setSpacing(15);
            bbox.getChildren().add(next);
            bbox.getChildren().add(exitButton);
            
        pane.add(bbox, 0,1);
        
        
        stage.show();//creates the window for the user
      
    }
    private GridPane grid;
    public GridPane menu(Stage stage){
        grid = new GridPane();
        grid.setPadding(new Insets(15,15,15,15));
        grid.setHgap(10);
        grid.setVgap(10);
        
        //ENTREES*****************
        
        
        //entree add
        grid.add(new Label("Entrees"),0,1);
        
            Button taco = new Button("Taco: $1.00");
            taco.setOnAction(e-> button(0,stage));
        
            Button burger = new Button("Hamburger: $1.40");
            burger.setOnAction(e-> button(1,stage));
        
            Button cheese = new Button("Cheeseburger: $2.00");
            cheese.setOnAction(e-> button(2,stage));
        
        VBox ents = new VBox();
            ents.getChildren().add(taco);
            ents.getChildren().add(burger);
            ents.getChildren().add(cheese);
            
        grid.add(ents, 0, 2);
        
        //entree count
            Label tacos = new Label(cart[0]+"");
            Label burgers = new Label(cart[1]+"");
            Label cheeses = new Label(cart[2]+"");
        VBox entCount = new VBox(10);
            entCount.getChildren().add(tacos);
            entCount.getChildren().add(burgers);
            entCount.getChildren().add(cheeses);
            
        grid.add(entCount,1,2);
        
        // entree removal
            Button nTaco = new Button("Remove");
            nTaco.setOnAction(e->nButton(0,stage));
        
            Button nBurger = new Button("Remove");
            nBurger.setOnAction(e->nButton(1,stage));
        
            Button nCheese = new Button("Remove");
            nCheese.setOnAction(e->nButton(2,stage));
        
        VBox nEnts= new VBox();
            nEnts.getChildren().add(nTaco);
            nEnts.getChildren().add(nBurger);
            nEnts.getChildren().add(nCheese);
        
        grid.add(nEnts, 2, 2);

        //SIDES*************************
        
        //side add
        grid.add(new Label("Sides"),0,3);
        
            Button salad = new Button("Salad: $2.00");
            salad.setOnAction(e-> button(3,stage));
        
            Button fries = new Button("Fries: $0.50");
            fries.setOnAction(e->button(4,stage));
        
            Button nachos = new Button("Nachos: $1.75");
            nachos.setOnAction(e->button(5,stage));
                
        VBox sides = new VBox();
            sides.getChildren().add(salad);
            sides.getChildren().add(fries);
            sides.getChildren().add(nachos);
        
        grid.add(sides, 0, 4);
        
        //sides count
            Label salads = new Label(cart[3]+"");
            Label frie = new Label(cart[4]+"");
            Label nacho = new Label(cart[5]+"");
        
        VBox sideCount = new VBox(10);
            sideCount.getChildren().add(salads);
            sideCount.getChildren().add(frie);
            sideCount.getChildren().add(nacho);
            
            grid.add(sideCount, 1, 4);
        
        //sides removal
            Button nSalad = new Button("Remove");
            nSalad.setOnAction(e-> nButton(3,stage));
        
            Button nFries = new Button("Remove");
            nFries.setOnAction(e->nButton(4,stage));
        
            Button nNachos = new Button("Remove");
            nNachos.setOnAction(e-> nButton(5,stage));
        
        VBox nSides = new VBox();
            nSides.getChildren().add(nSalad);
            nSides.getChildren().add(nFries);
            nSides.getChildren().add(nNachos);
        
        grid.add(nSides, 2, 4);
        
        
        // DRINKS**************
        
        //drinks add
        grid.add(new Label("Drinks"), 0, 5);
        
            Button large = new Button("Large Soda: $1.25");
            large.setOnAction(e->button(6,stage));
        
            Button small = new Button("Small Soda: $0.75");
            small.setOnAction(e->button(7,stage));
        
        VBox drinks = new VBox();
            drinks.getChildren().add(large);
            drinks.getChildren().add(small);
        
        grid.add(drinks, 0, 6);
        
        
       //drinks count
            Label larges = new Label(cart[6]+"");
            Label smalls = new Label(cart[7]+"");
        
        VBox drinkCount = new VBox(10);
            drinkCount.getChildren().add(larges);
            drinkCount.getChildren().add(smalls);
            
        grid.add(drinkCount,1,6);
        
        //drinks removal
            Button nLarge = new Button("Remove");
            nLarge.setOnAction(e-> nButton(6,stage));
        
            Button nSmall = new Button("Remove");
            nSmall.setOnAction(e-> nButton(7,stage));
            
        VBox nDrinks = new VBox();
            nDrinks.getChildren().add(nLarge);
            nDrinks.getChildren().add(nSmall);
            
        grid.add(nDrinks, 2, 6);
        
        // premade warning for empty cart
                Alert mt = new Alert(AlertType.WARNING);
                mt.setTitle("WARNING");
                mt.setHeaderText("Empty Cart Error");
                mt.setContentText("Please enter your order before continuing");
                
            //buttons    
            Button next = new Button("Next");
            if (getTotal()==0){//checks if the cart is empty by measuring the total being $0.00
                next.setOnAction(e->mt.showAndWait());//shows the error instead of going to the next screen
             
            }
            else{//if there is at least one item in the cart
            next.setOnAction(e-> stage.getScene().setRoot(subtotal(stage)));//continues to the next screen
            }
        
            Button exit = new Button("Exit");
            exit.setOnAction(e-> exitButtonClicked());
        
        HBox trim = new HBox();//houses the buttons
        trim.setSpacing(15);
            trim.getChildren().add(next);
            trim.getChildren().add(exit);
        grid.add(trim, 0,10);
        
        
        return grid;
        }
    
    public GridPane subtotal(Stage stage){
        grid = new GridPane();
        grid.setPadding(new Insets(15,15,15,15));
        grid.add(new Label("Subtotal is $"+getTotal()),0,0);
        grid.setVgap(10);
        grid.add(new Label("Would you like to add a tip?"),0,1);
        
        ToggleGroup tip = new ToggleGroup();
            RadioButton none = new RadioButton("No tip");
            none.setSelected(true);//preselects this option
            none.setToggleGroup(tip);
            RadioButton ten = new RadioButton("10%");
            ten.setToggleGroup(tip);
            RadioButton fifteen = new RadioButton("15%");
            fifteen.setToggleGroup(tip);
            
        VBox tips = new VBox(15);
            tips.getChildren().add(none);
            tips.getChildren().add(ten);
            tips.getChildren().add(fifteen);
        
        grid.add(tips,0,2);
        
        
        
            Button back = new Button("Back");
            back.setOnAction(e-> stage.getScene().setRoot(menu(stage)));
        
            Button complete = new Button("Finalize");
            complete.setOnAction(e->compute(tip.getSelectedToggle(),stage));
            
            Button exit = new Button("Exit");
            exit.setOnAction(e-> exitButtonClicked());
        
        HBox trim = new HBox();
        trim.setSpacing(15);
            trim.getChildren().add(complete);
            trim.getChildren().add(back); 
            trim.getChildren().add(exit);
        grid.add(trim, 0,3);
        
        return grid;
        
    }
    public GridPane end(Stage stage){
        grid = new GridPane();
        grid.setPadding(new Insets(15,15,15,15));
        
        
        grid.add(new Label("Thank you, order received!\n================="),0,0);
        grid.add(new Label("Total is $"+ String.format("%.2f",total)+"\n================="),0,1);//displays the total cost of the order
        
        /*this loop identifies each item the user has ordered and how many times it has been ordered
        to show an itemized breakdown of the cost of the order*/
        for (int i=0;i<8;i++){
                if (cart[i] >0){
                grid.add(new Label(cart[i]+" "+items[i]+" for $"+String.format("%.2f",cost[i])+
                        " === "+String.format("%.2f",cart[i]*cost[i])+"\n"),0,(2+i));
                }}
        
        
        grid.add(new Label("================="), 0, 11);
            Button finish = new Button("Finish");
            finish.setOnAction(e->finish());
        grid.add(finish,0,12);
        return grid;
    }
    
    
    public void button(int it,Stage stage){//every time a menu item is pressed to select and item to add to the cart
        cart[it]+=1;//adds 1 to the element representing the selected item in the cary array
        stage.getScene().setRoot(menu(stage));//updates the page with the item added to the order
        
    }
    
    public void nButton(int it, Stage stage){//the same as the previous button but to subtract the item
        if (cart[it]>0){//checks if there is at least 1 of that item in the cart
            cart[it]-=1;//subtracts 1 from the element representing that item in the cart array
            stage.getScene().setRoot(menu(stage));}//updates the page with the item subtracted from the order
        else{ //if there is NOT at least 1 of the selected item in the order
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("WARNING");
            alert.setHeaderText("Less than 0 error");
            alert.setContentText("You cannot order negative food.");
            alert.showAndWait();//alert the user they cannot remove what is not there
        }
    }
    
    public void finish(){//when the user finishes the program
        
        //this block creates a text file to make a receipt of the order.
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))){
            writer.write("Total: "+total+"\n");
            for (int i=0;i<8;i++){
                if (cart[i] >0){
                writer.write(cart[i]+" "+items[i]+" for $"+cost[i]+"\n");
                }}
            writer.close();
        }
        catch (IOException e){
            System.out.println("System error: "+e);
            
        }
        exitButtonClicked();//closes the program after creating the file
        
    }

    private void exitButtonClicked(){
        System.exit(0);//exitst the program
    }
    private double getTotal(){//any time the program checks for the total cost of the order
        double subt = 0;//creates a local variable
        for (int i=0;i<8;i++){//checks each item in the array( could also use array[].length() )
            subt += (cart[i]*cost[i]);//multiplies each item in the cary by its cost and how many times it was orderd and adds it to the variable
        }
        total = subt;//assigns the global variable the value of the local variable
        return total;//returns the global variable
    }
    private void compute(Toggle choice,Stage stage){//the tip percentage method
        
        //this method is a little janky, but it works.
        if (choice.toString().contains("'10%'")){//checks if the user selected the 10%radio button
            total = total * 1.1;//adds 10% to the global variable and returns it
        }
        else if (choice.toString().contains("'15%'")){//checks if the user selected the 15%radio button
            total = total*1.15;//adds 15% to the global variable and returns it
        }
        stage.getScene().setRoot(end(stage));//goes on to the next window
    }
    public static void main(String[] args) {
        launch();
    }
}

/*POSSIBLE ENHANCEMENTS: 
1: Add a variable to show the user how much they added for a tip
2: Allow for custom tip amounts
3: Change each window size to match the content of the window
4: Change the tip compute method to better reflect best practices
5: Allow the user to restart the program after completion
6: Add pictures to the menu items
7: Add a logo to the welcome screen
8: Update the file name to hold multiple recipts in different text files (i.e. filename = "recipt for: "+getTime()")
*/