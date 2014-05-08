package com.DzinVision.preprosteSuplence.main;

public class Xml{

    public static int count =0;

    public static String[][] nadomescanja(String xml) {
        count(xml, "<nadomescanja>", 0);
        if (count==0) {
            String[][] string=new String[0][0];
            return string;
        }

        String buffXml=xml;

        String[] profesorji=new String[count];

        int[] profUre=new int[count];

        for (int i=0; i<count; i++) {
            int start=buffXml.indexOf("<nadomescanja>")+14;
            int end=buffXml.indexOf("</nadomescanja>");

            profesorji[i]=buffXml.substring(start, end).replaceAll("<nadomescanja>", "");

            buffXml=buffXml.substring(0, start)+buffXml.substring(end+15);
        }

        for (int i=0; i<count; i++) {
            int start=profesorji[i].indexOf("<stevilo_ur_nadomescanj>")+"<stevilo_ur_nadomescanj>".length();
            int end=profesorji[i].indexOf("</stevilo_ur_nadomescanj>");

            profUre[i]=Integer.parseInt(profesorji[i].substring(start, end));
        }

        int max=0;
        for (int i=0; i<profUre.length; i++) if (profUre[i]>max) max=profUre[i];

        int size=0;
        for (int i=0; i<profUre.length; i++) size+=profUre[i];

        String[][] profesorjiFull=new String[size][7];

        int buff=0;
        for (int i=0; i<count; i++) {
            for (int j=0; j<profUre[i]; j++) {
                profesorjiFull[buff][0]=get("odsoten_fullname", profesorji[i]);
                buff++;
            }
        }

        buff=0;
        for (int j=0; j<count; j++) {
            buffXml=profesorji[j];
            for (int i=0; i<profUre[j]; i++) {
                int start=buffXml.indexOf("<nadomescanja_ure>");
                int end=buffXml.indexOf("</nadomescanja_ure>");

                String nadomescanjaXml=buffXml.substring(start+"<nadomescanja_ure>".length(), end);

                profesorjiFull[buff][1]=get("ura", nadomescanjaXml);
                profesorjiFull[buff][2]=get("class_name", nadomescanjaXml);
                profesorjiFull[buff][3]=get("ucilnica", nadomescanjaXml);
                profesorjiFull[buff][4]=get("nadomesca_full_name", nadomescanjaXml);
                profesorjiFull[buff][5]=get("predmet", nadomescanjaXml);
                profesorjiFull[buff][6]=get("opomba", nadomescanjaXml);

                buff++;

                buffXml=buffXml.substring(0, start)+buffXml.substring(end+"</nadomescanja_ure>".length());
            }
        }

        return profesorjiFull;
    }

    public static String[][] menjavaPredmeta(String xml) {
        count(xml, "<menjava_predmeta>", 0);

        if (count==0) {
            String[][] string=new String[0][0];
            return string;
        }

        String[][] menjavaPredmeta=new String[count][7];

        for (int j=0; j<count; j++) {
            int start=xml.indexOf("<menjava_predmeta>");
            int end=xml.indexOf("</menjava_predmeta>");

            String buffXml=xml.substring(start+"<menjava_predmeta>".length(), end);

            menjavaPredmeta[j][0]=get("ura", buffXml);
            menjavaPredmeta[j][1]=get("class_name", buffXml);
            menjavaPredmeta[j][2]=get("ucilnica", buffXml);
            menjavaPredmeta[j][3]=get("ucitelj", buffXml);
            menjavaPredmeta[j][4]=get("original_predmet", buffXml);
            menjavaPredmeta[j][5]=get("predmet", buffXml);
            menjavaPredmeta[j][6]=get("opomba", buffXml);

            xml=xml.substring(0, start)+xml.substring(end+"</menjava_predmeta>".length());
        }

        return menjavaPredmeta;
    }

    public static String[][] menjavaUr(String xml) {
        count(xml, "<menjava_ur>", 0);

        if (count==0) {
            String[][] string=new String[0][0];
            return string;
        }

        String[][] menjavaUr=new String[count][6];

        xml=xml.replaceAll("&gt;", ">");

        for (int i=0; i<count; i++) {
            int start=xml.indexOf("<menjava_ur>");
            int end=xml.indexOf("</menjava_ur>");

            String buffXml=xml.substring(start+"<menjava_ur>".length(), end);

            menjavaUr[i][0]=get("class_name", buffXml);
            menjavaUr[i][1]=get("ura", buffXml);
            menjavaUr[i][2]=get("zamenjava_uciteljev", buffXml);
            menjavaUr[i][3]=get("predmet", buffXml);
            menjavaUr[i][4]=get("ucilnica", buffXml);
            menjavaUr[i][5]=get("opomba", buffXml);

            xml=xml.substring(0, start)+xml.substring(end+"</menjava_ur>".length());
        }

        return menjavaUr;
    }

    public static String[][] menjavaUcilnic(String xml) {
        count(xml, "<menjava_ucilnic>", 0);

        if (count==0) {
            String[][] string=new String[0][0];
            return string;
        }

        String[][] menjavaUcilnic=new String[count][7];

        for (int i=0; i<count; i++) {
            int start=xml.indexOf("<menjava_ucilnic>");
            int end=xml.indexOf("</menjava_ucilnic>");

            String buffXml=xml.substring(start+"<menjava_ucilnic>".length(), end);

            menjavaUcilnic[i][0]=get("class_name", buffXml);
            menjavaUcilnic[i][1]=get("ura", buffXml);
            menjavaUcilnic[i][2]=get("ucitelj", buffXml);
            menjavaUcilnic[i][3]=get("predmet", buffXml);
            menjavaUcilnic[i][4]=get("ucilnica_from", buffXml);
            menjavaUcilnic[i][5]=get("ucilnica_to", buffXml);
            menjavaUcilnic[i][6]=get("opomba", buffXml);

            xml=xml.substring(0, start)+xml.substring(end+"</menjava_ucilnic>".length());
        }

        return menjavaUcilnic;
    }

    public static String[][] rezervacijaUcilnic(String xml) {
        count(xml, "<rezerviranje_ucilnice>", 0);

        if (count==0) {
            String[][] string=new String[0][0];
            return string;
        }

        String[][] rezervacijaUcilnic=new String[count][4];

        for(int i=0; i<count; i++) {
            int start=xml.indexOf("<rezerviranje_ucilnice>");
            int end=xml.indexOf("</rezerviranje_ucilnice>");

            String buffXml=xml.substring(start+"<rezerviranje_ucilnice>".length(), end);

            rezervacijaUcilnic[i][0]=get("ura", buffXml);
            rezervacijaUcilnic[i][1]=get("ucilnica", buffXml);
            rezervacijaUcilnic[i][2]=get("rezervator", buffXml);
            rezervacijaUcilnic[i][3]=get("opomba", buffXml);

            xml=xml.substring(0, start)+xml.substring(end+"</rezerviranje_ucilnice>".length());
        }

        return rezervacijaUcilnic;
    }

    public static String[][] vecUciteljev(String xml) {
        count(xml, "<vec_uciteljev_v_razredu>", 0);

        if (count==0) {
            String[][] string=new String[0][0];
            return string;
        }

        String[][] vecUciteljev=new String[count][5];

        for (int i=0; i<count; i++) {
            int start=xml.indexOf("<vec_uciteljev_v_razredu>");
            int end=xml.indexOf("</vec_uciteljev_v_razredu");

            String buffXml=xml.substring(start+"<vec_uciteljev_v_razredu>".length(), end);

            vecUciteljev[i][0]=get("ura", buffXml);
            vecUciteljev[i][1]=get("ucitelj", buffXml);
            vecUciteljev[i][2]=get("class_name", buffXml);
            vecUciteljev[i][3]=get("ucilnica", buffXml);
            vecUciteljev[i][4]=get("opomba", buffXml);

            xml=xml.substring(0, start)+xml.substring(end+"</vec_uciteljev_v_razredu>".length());
        }

        return vecUciteljev;
    }

    public static String get(String tag, String xml) {
        if (xml.contains("<"+tag+"/>")) return "";
        else {
            int start=xml.indexOf("<"+tag+">")+tag.length()+2;
            int end=xml.indexOf("</"+tag+">");

            String result=xml.substring(start, end);

            return result;
        }
    }

    public static void count(String main, String tag, int current) {
        if (!main.contains(tag)) count=current;
        else count(main.substring(main.indexOf(tag)+tag.length()), tag, current+1);
    }
}