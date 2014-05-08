package com.DzinVision.preprosteSuplence.main;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.charset.Charset;

public class Gui {
    //Function that generates error window
    //Accepts two parameter: title of window and content in window

    public static String filter="";
    private static int view;
    public static String text="";

    public void error(final String title, final String content) {
        //Set stage
        final Stage stage=new Stage();

        //Set pane root with horizontal gap=10; vertical gap=20; padding=25
        GridPane root=new GridPane();
        root.setHgap(10);
        root.setVgap(20);
        root.setPadding(new Insets(25, 25, 25, 25));

        //Set text area; its content to "content"; not editable by user, automatic line break to true,
        //not selectable by "tab" and its selected border color to transparent->not showing
        TextArea textArea=new TextArea();
        textArea.setText(content);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setStyle("-fx-focus-color: transparent;");
        textArea.setFocusTraversable(false);

        //Create new column and row size
        ColumnConstraints column1=new ColumnConstraints();
        column1.setPercentWidth(100);
        root.getColumnConstraints().addAll(column1);

        RowConstraints row1=new RowConstraints();
        row1.setPercentHeight(60);
        root.getRowConstraints().addAll(row1);

        //Add text area to root
        root.add(textArea, 0, 0, 1, 1);

        //Put buttons close and refresh on new pane for easier repositioning
        HBox btn=new HBox(25);
        btn.setAlignment(Pos.BASELINE_RIGHT);
        Button ok=new Button("Zapri");
        ok.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //If close is selected close this window and exit application
                stage.close();
                System.exit(0);
            }
        });

        Button refresh=new Button("Poskusi znova");
        refresh.setDefaultButton(true);
        refresh.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //If refresh is selected close this window
                stage.close();
            }
        });

        //Add buttons to root pane
        btn.getChildren().addAll(refresh, ok);
        root.add(btn, 0, 1, 1, 1);


        //Create a new scene with root panel, set width to 350 and height to 150
        Scene scene=new Scene(root, 350, 150);

        //Set stages scene to "scene", title to "title", icon image,
        //don't enable user to resize window, if window is closed exit the application,
        //then show window and wait for window to close before continuing with code in "start" function
        stage.setScene(scene);
        stage.setTitle(title);
        stage.getIcons().add(new Image(com.DzinVision.preprosteSuplence.main.Main.class.getResourceAsStream("icon.png")));
        stage.setResizable(false);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
        stage.showAndWait();
    }


    //Install graphics
    public void install() {
        //Create new Stage
        final Stage stage=new Stage();

        //Create new text area, set its text,
        //enable auto line break, disable tab selection,
        //set selection color to transparent->invisible
        TextArea text=new TextArea();
        text.setText("Program ste odprli prvič. Program se bo inštaliral samodejno. Z uporabo programa se strinjate, da program pošilja javne podatke na" +
                        "strežnik, kjer se bodo shranjevali in bili uporabljeni za analizo. Avtor programa ni odgovoren za napačne podatke nadomeščanj.\n\nAvtor: Vid Drobnič");
        text.setEditable(false);
        text.setWrapText(true);
        text.setStyle("-fx-focus-color: transparent;");
        text.setFocusTraversable(false);

        HBox h1=new HBox(10);
        h1.setAlignment(Pos.CENTER);
        h1.getChildren().addAll(text);

        //Add button ok and set it to default button
        Button ok=new Button("Vredu");
        ok.setDefaultButton(true);
        ok.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //When pressed make new stage
                Stage stage1 = new Stage();

                //Create text to inform the user that installation is going
                Text text = new Text("Inštalacija je v poteku.");

                //Make new horizontal panel, set its position to center,
                //set padding to 10,
                //add text to it.
                HBox h1 = new HBox(10);
                h1.setAlignment(Pos.CENTER);
                h1.getChildren().add(text);


                //Make new progress bar and set it to loading without known percentage
                ProgressBar pb = new ProgressBar();
                pb.setProgress(-1);

                //Make new progress bar indicator and set it to loading without know percentage
                ProgressIndicator pin = new ProgressIndicator();
                pin.setProgress(-1);

                //Create new horizontal box, set its position to center
                //and add text, and progress bar to it
                HBox h2 = new HBox(10);
                h2.setAlignment(Pos.CENTER);
                h2.getChildren().addAll(pb, pin);

                //Create new vertical box, set its position to center and add
                //both horizontal boxes to it
                VBox root = new VBox(10);
                root.setAlignment(Pos.CENTER);
                root.getChildren().addAll(h1, h2);

                //Create scene and set window size to 300 by 200
                Scene scene = new Scene(root, 300, 200);

                //Set stage1 scene to "scene", make title "instalacija",
                //set icon images, and show it
                stage1.setScene(scene);
                stage1.setTitle("Inštalacija");
                stage1.getIcons().add(new Image(com.DzinVision.preprosteSuplence.main.Main.class.getResourceAsStream("icon.png")));
                stage1.show();

                //Install the application, close this window, and close the window of super
                Functions.install();
                stage1.close();

                stage.close();
            }
        });

        //Create exit button that closes the application if pressed
        Button exit=new Button("Izhod");
        exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.exit(0);
            }
        });

        HBox h2=new HBox(20);
        h2.setPadding(new Insets(10, 0, 0, 0));
        h2.setAlignment(Pos.BASELINE_RIGHT);
        h2.getChildren().addAll(ok, exit);


        VBox root=new VBox(10);
        root.setPadding(new Insets(20, 20, 20, 20));
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(h1, h2);


        //Create new scene, set its panel to root,
        //set its size to 430 by 200
        Scene scene=new Scene(root, 430, 200);

        //Set sages scene to "scene", set its title,
        //set its icon image, disable user resize,
        //set that if window closed close application,
        //show the window and wait until its closed
        stage.setScene(scene);
        stage.setTitle("Preproste Suplence");
        stage.getIcons().add(new Image(com.DzinVision.preprosteSuplence.main.Main.class.getResourceAsStream("icon.png")));
        stage.setResizable(false);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
        stage.showAndWait();
    }


    public void update(final String client) {
        final Stage stage=new Stage();


        TextArea text=new TextArea("Možna je nadgradnja programa. Za pravilno delovanje je nadgradnja obvezna. V tem procesu je možno, da se bo aplikacija zaprla in jo" +
                "boste morali ponovno odpreti.");
        text.setEditable(false);
        text.setWrapText(true);
        text.setStyle("-fx-focus-color: transparent;");
        text.setFocusTraversable(false);

        HBox h1=new HBox();
        h1.setAlignment(Pos.CENTER);
        h1.getChildren().add(text);


        Button ok=new Button("Vredu");
        ok.setDefaultButton(true);
        ok.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (client.equals("main")) Functions.updateMain();
                else if(client.equals("updater")) Functions.updateUpdater();
                stage.close();
            }
        });

        Button exit=new Button("Izhod");
        exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                stage.close();
                System.exit(0);
            }
        });

        HBox h2=new HBox(15);
        h2.setAlignment(Pos.BASELINE_RIGHT);
        h2.getChildren().addAll(ok, exit);


        VBox root=new VBox(20);
        root.setPadding(new Insets(25, 25, 25, 25));
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(h1, h2);

        Scene scene=new Scene(root, 430, 200);

        stage.setScene(scene);
        stage.setTitle("Preproste Suplence");
        stage.getIcons().add(new Image(com.DzinVision.preprosteSuplence.main.Main.class.getResourceAsStream("icon.png")));
        stage.setResizable(false);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
        stage.showAndWait();
    }


    public void main() {
        Stage stage=new Stage();

        ToolBar toolBar=new ToolBar();

        Button btn1=new Button("Danes");
        btn1.setFocusTraversable(false);
        Button btn2=new Button("Jutri");
        btn2.setFocusTraversable(false);
        Button btn3=new Button("Pojutrišnjem");
        btn3.setFocusTraversable(false);

        Region spacer1=new Region();
        spacer1.setPrefWidth(30);

        Region spacer2=new Region();
        spacer2.setPrefWidth(60);

        Region spacer3=new Region();
        spacer3.setPrefWidth(30);

        Button settings=new Button("Nastavitve");
        settings.setFocusTraversable(false);

        HBox h1=new HBox(5);
        h1.getStyleClass().addAll("segmented-button-bar");
        h1.getChildren().addAll(btn1, btn2, btn3);

        toolBar.getItems().addAll(spacer1, h1, spacer2, settings);


        try {
            BufferedReader in=new BufferedReader(new InputStreamReader(new FileInputStream(Main.path+"filter.txt"), "UTF-8"));
            String ln; String out="";
            while ((ln=in.readLine())!=null) out+=ln;

            filter=out;
        } catch (Exception e) {}

        final TextArea textArea=new TextArea(Functions.text(0, filter));
        textArea.setWrapText(true);
        textArea.setEditable(false);
        textArea.setStyle("-fx-focus-color: transparent;");
        textArea.setFocusTraversable(false);



        btn1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                textArea.setText(Functions.text(0, filter));
                view=0;
            }
        });

        btn2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                textArea.setText(Functions.text(1, filter));
                view=1;
            }
        });

        btn3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                textArea.setText(Functions.text(2, filter));
                view=2;
            }
        });

        settings.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                final Stage stage1=new Stage();

                TextArea about=new TextArea();
                about.setWrapText(true);
                about.setEditable(false);
                about.setStyle("-fx-focus-color: transparent;");
                about.setFocusTraversable(false);
                about.setText("Avtor: Vid Drobnič, Gimnazija Vič; vse pravice pridržane.\n\nVerzija: " + Main.currentVersion);
                about.setMaxHeight(100);


                final TextField filter=new TextField("Filter:");
                filter.setEditable(false);
                filter.setStyle("-fx-focus-color: transparent;");
                filter.setFocusTraversable(false);
                filter.setMaxSize(55, 50);

                final TextField input=new TextField();
                input.setEditable(true);

                String filterString="";

                try {
                    BufferedReader in=new BufferedReader(new InputStreamReader(new FileInputStream(Main.path+"filter.txt"), "UTF-8"));
                    String ln; String out="";
                    while ((ln=in.readLine())!=null) out+=ln;

                    filterString=out;
                } catch (Exception e) {}

                input.setText(filterString);


                HBox h1=new HBox(10);
                h1.getChildren().addAll(filter, input);


                Button submit=new Button("Nastavi");
                submit.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        try {
                            PrintWriter out=new PrintWriter(Main.path+"filter.txt");
                            out.print(input.getText());
                            out.close();
                        } catch (Exception e) {}

                        Gui.filter=input.getText().replaceAll(" ", "");
                        textArea.setText(Functions.text(view, Gui.filter));
                        stage1.close();
                    }
                });


                HBox h2=new HBox();
                h2.setAlignment(Pos.CENTER);
                h2.getChildren().addAll(submit);

                VBox root=new VBox(15);
                root.setPadding(new Insets(25, 25, 25, 25));
                root.getChildren().addAll(about, h1, h2);

                Scene scene=new Scene(root, 500, 250);

                stage1.setScene(scene);
                stage1.setTitle("Preproste Suplence");
                stage1.getIcons().add(new Image(com.DzinVision.preprosteSuplence.main.Main.class.getResourceAsStream("icon.png")));
                stage1.show();

            }
        });


        BorderPane root=new BorderPane();
        root.setTop(toolBar);
        root.setCenter(textArea);

        Scene scene=new Scene(root, 600, 800);

        stage.setScene(scene);
        stage.setTitle("Preproste Suplence");
        stage.getIcons().add(new Image(com.DzinVision.preprosteSuplence.main.Main.class.getResourceAsStream("icon.png")));
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
        stage.showAndWait();
    }
}