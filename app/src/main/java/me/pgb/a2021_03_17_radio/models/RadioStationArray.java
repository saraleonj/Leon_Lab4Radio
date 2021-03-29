package me.pgb.a2021_03_17_radio.models;

public class RadioStationArray {
    private static String[] stringList;

    public static RadioStation[] radioStations = {
            new RadioStation("http://stream.whus.org:8000/whusfm","WHUS", "USA", "https://i2.wp.com/whus.org/wp-content/uploads/2020/03/whus.png?resize=500%2C400&ssl=1"),
            new RadioStation("http://broadcast.miami/proxy/casablanca?mp=/stream/;", "Radio Casablanca", "USA", "https://www.radio-casablanca.com/assets/imgs/record.png"),
            new RadioStation("http://pureplay.cdnstream1.com/6054_128.mp3","AirlessRadio - Smooth Grooves","USA","https://freepikpsd.com/wp-content/uploads/2019/10/jazz-png-4-Transparent-Images.png"),
    };

    public static String[] getArrayOfRadioLinks(){
        stringList = new String[radioStations.length];

        for (int i=0; i<radioStations.length; i++){
            stringList[i] = radioStations[i].getStreamLink();
        }
        return stringList;
    }

    public static String[] getArrayOfRadioNames(){
        stringList = new String[radioStations.length];

        for (int i = 0; i < radioStations.length; i++){
            stringList[i] = radioStations[i].getName();
        }

        return stringList;
    }

    public static String[] getArrayOfRadioImages(){
        stringList = new String[radioStations.length];

        for (int i = 0; i < radioStations.length; i++){
            stringList[i] = radioStations[i].getImageLink();
        }

        return stringList;
    }
}