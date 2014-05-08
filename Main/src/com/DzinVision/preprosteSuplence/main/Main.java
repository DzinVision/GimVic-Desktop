package com.DzinVision.preprosteSuplence.main;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.File;


public class Main extends Application {

    //Ustvari spremenljivko za glavno mapo aplikacije, kjer so shranjene datoteke
    //z dodatnimi datotekami, ki so potrebne za delovanje aplikacije.
    public static String path="";
    //Ustvari spremenljivko, ki vsebuje URL do strežnika iz katerega sprejema podatke
    //o nadomeščanjih, nadgradnjah in kamor se pošilja analitiko.
    public static String ip="app.gimvic.org";
    //Ustvari novo spremenljivko iz razreda Gui, iz katere se nadzira grafiko
    private static Gui gui=new Gui();

    //Ustvari spremenljivko, ki vsebuje trenutno najnovjšo verzijo aplikacije
    public static double currentVersion=1.4;


    @Override
    public void start(Stage stage) {
        //V razredu Function kliči funkcijo, ki nastavi spremenljivko path na glavno mapo aplikacije,
        //glede na operacijski sistem, ki ga uporabnik uporablja
        Functions.setPath();

        //Preveri če ima aplikacija internetno povezavo.
        //Če aplikacija nima internetne povezavi prikaži okno, ki to napako javlja.
        //Če uporabnik želi, da se aplikacija poskusi ponovno povezati v internet, se okno zapre in aplikacija ponovno preveri internetno povezavo
        //Postopek se ponavlja, dokler uporabnik ne zapre aplikacije.
        while (true){
            if (!Functions.connection()) gui.error("Napaka!", "Ni internetne povezave!\nPoveži se v internet in poskusi znova.");
            else break;
        }

        //Iz poti, kjer se nahaja vrhovna mapa aplikacija
        //naredi spremenljivko, ki jo Java prepozna kot datoteko in ne kot niz znakov.
        File dir=new File(path);
        //Če mapa še ne obstaja program predvideva, da je aplikacija prvič zagnana, zato
        //se uporabniku prikaže okno za namestitev.
        if (!dir.exists()) gui.install();

        //Preveri če je aplikacija posodobljena na najnovejšo verzijo.
        //Če ni to javi uporabniku in mu ponudi možnost posodobitve.
        if (Functions.checkUpdatesMain()) gui.update("main");
        if (Functions.checkUpdatesUpdater()) gui.update("updater");

        //Na strežnik pošlji podatke za anlitiko
        Functions.analytics();

        //Prikaži glavno okno
        gui.main();
    }


    public static void main(String[] args){
        launch(args);
    }
}