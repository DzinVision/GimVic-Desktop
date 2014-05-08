package com.DzinVision.preprosteSuplence.main;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;


public class Functions {
    //Ta funkcija preveri, če ima program internetno povezavo
    public static boolean connection() {
        Socket socket=null;
        boolean reachable=false;
        //Poizkusi poslati paket do strežnika. Če se strežnik odzove,
        //Potem ima program internetno povezavo, drugače pa ne
        try {
            socket=new Socket(Main.ip, 80);
            reachable=true;
        } catch (Exception e) {}
        finally {
            if (socket!=null) try {socket.close();} catch (Exception e) {}
        }

        return reachable;
    }


    //Nastavi pot vrhovne mape glede na operacijski sistem, ki ga uporabnik uporablja
    public static void setPath() {
        //Nastavi spremenljivki v katerih je shranjen ime operacijskega sistema
        //in uporabniško ime uporabnika
        String os=System.getProperty("os.name");
        String user=System.getProperty("user.name");

        //Nastavi pot glede na operacijski sistem
        if (os.contains("Mac")) Main.path="/Users/"+user+"/Library/Application Support/Preproste Suplence/";
        else if(os.contains("Windows")) Main.path="/C:/Users/"+user+"/AppData/Roaming/Preproste Suplence/";
        else if(os.contains("Linux")) Main.path="/home/"+user+"/.Preproste Suplence/";
    }


    //Funkcija namesti program, če je ta prvič zagnan
    public static void install() {
        File root=new File(Main.path);
        root.mkdir();

        double version=0.0;


        //Iz internetnega naslova prenese številko trenutne verzije posodoljevalnika
        try {
            URL url=new URL("http://"+Main.ip+"/d0941e68da8f38151ff86a61fc59f7c5cf9fcaa2/computer/updaterVersion.html");
            BufferedReader in=new BufferedReader(new InputStreamReader(url.openStream()));

            String ln; String out="";
            while ((ln=in.readLine())!=null) out+=ln;
            in.close();

            version=Double.parseDouble(out);
        } catch (Exception e) {}

        //To številko zapiše v datotekko "version.txt"
        try {
            PrintWriter out=new PrintWriter(Main.path+"version.txt");
            out.print(version);
            out.close();
        } catch (Exception e) {}


        //Iz strežnika prenese trenutno verzijo posodobljevalnika in jo shrani v vrhovno mapo
        try {
            URL website = new URL("http://"+Main.ip+"/d0941e68da8f38151ff86a61fc59f7c5cf9fcaa2/computer/Updater.jar");
            ReadableByteChannel rbc = Channels.newChannel(website.openStream());
            FileOutputStream fos = new FileOutputStream(Main.path+"Updater.jar");
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        } catch (Exception e) {e.printStackTrace();}


        //Uporabniku dodeli unikaten niz znakov in ga zašifriraj z sistemo SHA-1
        //Ta niz je uporabljen za prepoznavo uporabnika v analitiki.
        String uuid=String.valueOf(UUID.randomUUID());
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(uuid.getBytes());
            byte byteData[] = md.digest();

            //convert the byte to hex format method 1
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }

            uuid=sb.toString();
        } catch (Exception e) {}

        //Ta niz shrani v datoteko "uuid.txt".
        try {
            PrintWriter uuidOut=new PrintWriter(Main.path+"uuid.txt");
            uuidOut.print(uuid);
            uuidOut.close();
        } catch (Exception e) {}
    }


    //Poglej za možne nadgradnje glavne aplikacije
    public static boolean checkUpdatesMain() {
        //Nastavi številko za najnovejšo verzijo iz strežnika
        double version=0;
        try {
            URL url=new URL("http://"+Main.ip+"/d0941e68da8f38151ff86a61fc59f7c5cf9fcaa2/computer/mainVersion.html");
            BufferedReader in=new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));

            String ln; String out="";
            while ((ln=in.readLine())!=null) out+=ln;
            in.close();

            version=Float.parseFloat(out);
        } catch (Exception e) {e.printStackTrace();}

        //Zaradi problemov pri shranjevanju decimalnih številk v binarni sistem,
        //jih zaokroži na 1 decimalno mesto.
        version=(float)Math.round(version*10.0)/10.0;

        //Če je najnovejša verzija programa večja od trenutne verzija,
        //vrni "true", drugače pa vrni "false"
        if (version>Main.currentVersion) return true;
        else return false;
    }


    //Poglej za možne nadgradnje posodobljevalnika
    public static boolean checkUpdatesUpdater() {
        double currentVerison=0;
        double version=0;

        //Nastavi trenutno verzijo posodobljevalnika na verzijo, ki
        //je zapisana v datoteki "version.txt"
        try {
            BufferedReader in=new BufferedReader(new InputStreamReader(new FileInputStream(Main.path+"version.txt"), "UTF-8"));
            String ln; String out="";
            while ((ln=in.readLine())!=null) out+=ln;

            currentVerison=Float.parseFloat(out);
        } catch (Exception e) {}

        //Nastavi trenutno najnovejšvo verzijo posodobljevalnika na
        //trenutno najnovejšo verzijo na strežniku
        try {
            URL url=new URL("http://"+Main.ip+"/d0941e68da8f38151ff86a61fc59f7c5cf9fcaa2/computer/updaterVersion.html");
            BufferedReader in=new BufferedReader(new InputStreamReader(url.openStream()));

            String ln; String out="";
            while ((ln=in.readLine())!=null) out+=ln;
            in.close();

            version=Float.parseFloat(out);
        } catch (Exception e) {}

        //Zaradi problemov pri shranjevanju decimalnih številk, zaokroži
        //številke na 1 decimalno mesto
        version=(float)Math.round(version*10.0)/10.0;

        //Če je trenunta najnovejša verzija na strežniku novejša od lokalne vrni "true",
        //drugače vrni "false"
        if (version>currentVerison) return true;
        else return false;
    }


    //Funkcija ki nadgradi aplikacijo
    public static void updateMain() {
        //V spremenljivko zapiši trenutno lokacijo aplikacije in jo shrani v datoteko,
        //katero bo kasneje prebral posodobljevalnik, da bo vedel kam
        //shraniti novo verzijo.
        File file=new File(com.DzinVision.preprosteSuplence.main.Main.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        String path=file.getPath();
        path=path.replaceAll("%20", " ");

        try {
            PrintWriter out=new PrintWriter(Main.path+"path.txt");
            out.print(path);
            out.close();
        } catch (Exception e) {}


        //Iz strežnika prenesi najnovejšo verzijo aplikacije
        //in jo začason shrani v vrhovno mapo aplikacije.
        try {
            URL website = new URL("http://"+Main.ip+"/d0941e68da8f38151ff86a61fc59f7c5cf9fcaa2/computer/PreprosteSuplence.jar");
            ReadableByteChannel rbc = Channels.newChannel(website.openStream());
            FileOutputStream fos = new FileOutputStream(Main.path+"Preproste Suplence.jar");
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        } catch (Exception e) {e.printStackTrace();}


        //Odpri posodobljevalnik
        ProcessBuilder pb=new ProcessBuilder("java", "-jar", "Updater.jar");
        pb.directory(new File(Main.path));
        try {
            Process p=pb.start();
        } catch (Exception e) {e.printStackTrace();}
        //Ko je posodobljevalnik odprt, zapri aplikacijo.
        System.exit(0);
    }


    public static void updateUpdater() {
        File root=new File(Main.path);
        root.mkdir();

        double version=0.0;


        //Get current updater jar version from web
        try {
            URL url=new URL("http://"+Main.ip+"/d0941e68da8f38151ff86a61fc59f7c5cf9fcaa2/computer/updaterVersion.html");
            BufferedReader in=new BufferedReader(new InputStreamReader(url.openStream()));

            String ln; String out="";
            while ((ln=in.readLine())!=null) out+=ln;
            in.close();

            version=Double.parseDouble(out);
        } catch (Exception e) {}

        File vers=new File(Main.path+"version.txt");
        vers.delete();

        try {
            PrintWriter out=new PrintWriter(Main.path+"version.txt");
            out.print(version);
            out.close();
        } catch (Exception e) {}


        File updater=new File(Main.path+"Updater.jar");
        updater.delete();

        try {
            URL website = new URL("http://"+Main.ip+"/d0941e68da8f38151ff86a61fc59f7c5cf9fcaa2/computer/Updater.jar");
            ReadableByteChannel rbc = Channels.newChannel(website.openStream());
            FileOutputStream fos = new FileOutputStream(Main.path+"Updater2.jar");
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        } catch (Exception e) {e.printStackTrace();}
    }


    public static String text(int in, String filter) {
        String date="";

        GregorianCalendar calander=new GregorianCalendar();
        DateFormat fmt=new SimpleDateFormat("yyyy-MM-dd");

        Date today=calander.getTime();

        calander.add(calander.DAY_OF_MONTH, 1);
        Date tomorrow=calander.getTime();

        calander.add(calander.DAY_OF_MONTH, 1);
        Date dayAfter=calander.getTime();

        if (in==0) date=fmt.format(today);
        else if (in==1) date=fmt.format(tomorrow);
        else if (in==2) date=fmt.format(dayAfter);

        String xml="";

        try {
            URL url=new URL("http://"+Main.ip+"/f5f5d4903e9686b21f49cd417d24779001b432a5/index.php?datum="+date);
            BufferedReader inn=new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));

            String ln; String out="";
            while ((ln=inn.readLine())!=null) out+=ln;
            inn.close();
            xml=out;
        } catch (Exception e) {e.printStackTrace();}

        xml=xml.replaceAll("  ", "");


        String[][] nadomescanaja=filter(Xml.nadomescanja(xml), filter);
        String[][] menjavaPredmeta=filter(Xml.menjavaPredmeta(xml), filter);
        String[][] menjvaUr=filter(Xml.menjavaUr(xml), filter);
        String[][] menjavaUcilnic=filter(Xml.menjavaUcilnic(xml), filter);
        String[][] rezervacijaUcilnic=filter(Xml.rezervacijaUcilnic(xml), filter);
        String[][] vecUciteljev=filter(Xml.vecUciteljev(xml), filter);


        String text="NADOMEŠČANJA:\n\n";
        if (nadomescanaja.length==0) text+="Ni nadomeščanj.\n\n\n";
        else {
            for (int i=0; i<nadomescanaja.length; i++) {
                text+="Odsoten: "+nadomescanaja[i][0]+"\n";
                text+="Ura: "+nadomescanaja[i][1]+"\n";
                text+="Razred: "+nadomescanaja[i][2]+"\n";
                text+="Učilnica: "+nadomescanaja[i][3]+"\n";
                text+="Nadomešča: "+nadomescanaja[i][4]+"\n";
                text+="Predmet: "+nadomescanaja[i][5]+"\n";
                text+="Opomba: "+nadomescanaja[i][6]+"\n\n";
            }
            text+="\n";
        }
        text+="MENJAVA PREDMETA:\n\n";
        if (menjavaPredmeta.length==0) text+="Ni menjav premeta.\n\n\n";
        else {
            for (int i=0; i<menjavaPredmeta.length; i++) {
                text+="Ura: "+menjavaPredmeta[i][0]+"\n";
                text+="Razred: "+menjavaPredmeta[i][1]+"\n";
                text+="Učilnica: "+menjavaPredmeta[i][2]+"\n";
                text+="Učitelj: "+menjavaPredmeta[i][3]+"\n";
                text+="Prvotni predmet: "+menjavaPredmeta[i][4]+"\n";
                text+="Predmet: "+menjavaPredmeta[i][5]+"\n";
                text+="Opomba: "+menjavaPredmeta[i][6]+"\n\n";
            }
            text+="\n";
        }
        text+="MENJAVA UR:\n\n";
        if (menjvaUr.length==0) text+="Ni menjav ur.\n\n\n";
        else {
            for (int i=0; i<menjvaUr.length; i++) {
                text+="Razred: "+menjvaUr[i][0]+"\n";
                text+="Ura: "+menjvaUr[i][1]+"\n";
                text+="Zamenjava učiteljev: "+menjvaUr[i][2]+"\n";
                text+="Predmet: "+menjvaUr[i][3]+"\n";
                text+="Učilnica: "+menjvaUr[i][4]+"\n";
                text+="Opomba: "+menjvaUr[i][5]+"\n\n";
            }
            text+="\n";
        }
        text+="MENJAVA UČILNIC:\n\n";
        if (menjavaUcilnic.length==0) text+="Ni menjav učilnic.\n\n\n";
        else {
            for (int i=0; i<menjavaUcilnic.length; i++) {
                text+="Razred: "+menjavaUcilnic[i][0]+"\n";
                text+="Ura: "+menjavaUcilnic[i][1]+"\n";
                text+="Učitelj: "+menjavaUcilnic[i][2]+"\n";
                text+="Predmet: "+menjavaUcilnic[i][3]+"\n";
                text+="Iz učilnice: "+menjavaUcilnic[i][4]+"\n";
                text+="V učilnico: "+menjavaUcilnic[i][5]+"\n";
                text+="Opomba: "+menjavaUcilnic[i][6]+"\n\n";
            }
            text+="\n";
        }
        text+="REZERVACIJA UČILNIC:\n\n";
        if (rezervacijaUcilnic.length==0) text+="Ni rezervacij učilnic.\n\n\n";
        else {
            for (int i=0; i<rezervacijaUcilnic.length; i++) {
                text+="Ura: "+rezervacijaUcilnic[i][0]+"\n";
                text+="Učilnica: "+rezervacijaUcilnic[i][1]+"\n";
                text+="Rezervator: "+rezervacijaUcilnic[i][2]+"\n";
                text+="Opomba: "+rezervacijaUcilnic[i][3]+"\n\n";
            }
            text+="\n";
        }
        text+="VEČ UČITELJEV V RAZREDU:\n\n";
        if (vecUciteljev.length==0) text+="Ni več učiteljev v razredu.\n\n\n";
        else {
            for (int i=0; i<vecUciteljev.length; i++) {
                text+="Ura: "+vecUciteljev[i][0]+"\n";
                text+="Učitelj: "+vecUciteljev[i][1]+"\n";
                text+="Razred: "+vecUciteljev[i][2]+"\n";
                text+="Učilnica: "+vecUciteljev[i][3]+"\n";
                text+="Opomba: "+vecUciteljev[i][4]+"\n";
            }
        }

        return text;
    }


    private static String[][] filter(String[][] input, String filter) {
        if (filter.equals("") || input.length==0) return input;
        else {
            ArrayList<Integer> selected = new ArrayList<Integer>();

            for (int j = 0; j<input.length; j++) {
                for (int i = 0; i < input[0].length; i++) {
                    String check=input[j][i].replaceAll(" ", "");
                    check.toLowerCase();
                    if (check.contains(filter.toLowerCase())) {
                        selected.add(j);
                    }
                }
            }


            int size=input[0].length;
            String[][] result = new String[selected.size()][size];

            int index = 0;
            for (int i = 0; i < input.length; i++) {
                if (selected.contains(i)) {
                    for (int j = 0; j < input[0].length; j++) {
                        result[index][j] = input[i][j];
                    }

                    index++;
                }
            }

            return result;
        }
    }


    public static void analytics() {
        String uuid="";

        try {
            BufferedReader in=new BufferedReader(new InputStreamReader(new FileInputStream(Main.path+"uuid.txt"), "UTF-8"));
            String ln; String out="";
            while ((ln=in.readLine())!=null) out+=ln;

            uuid=out;
        } catch (Exception e) {}

        if (uuid.equals("")) {
            install();
        }

        try {
            BufferedReader in=new BufferedReader(new InputStreamReader(new FileInputStream(Main.path+"uuid.txt"), "UTF-8"));
            String ln; String out="";
            while ((ln=in.readLine())!=null) out+=ln;

            uuid=out;
        } catch (Exception e) {}

        String os=System.getProperty("os.name");
        os=os.replaceAll(" ", "");
        String version=System.getProperty("os.version");
        version=version.replaceAll(" ", " ");

        String url="http://"+Main.ip+"/d0941e68da8f38151ff86a61fc59f7c5cf9fcaa2/analityc_for_desktop/analitika.php?mac_hash="+uuid+"&os="+os+"&osVersion="+version+"&appVersion="+Main.currentVersion;

        try {
            URL url1=new URL(url);
            BufferedReader inn=new BufferedReader(new InputStreamReader(url1.openStream()));
            inn.close();
        } catch (Exception e) {}
    }
}