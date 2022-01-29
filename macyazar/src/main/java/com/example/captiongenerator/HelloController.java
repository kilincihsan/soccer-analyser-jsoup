package com.example.captiongenerator;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import javafx.scene.control.TextField;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HelloController {
    public TextField minText;
    public TextField maxText;
    public Button selectFileButton;
    public TextField urlTextBox;
    @FXML
    private Label welcomeText;


    //this arraylist will contain all of the things needed for txt
    ArrayList<String> allFatherText;
    //home team records
    ArrayList<String> playerNamesHomeTeam;
    ArrayList<String> playerSurnamesHomeTeam;
    ArrayList<String> playerPositionsHomeTeam;
    ArrayList<String> playerHeightHomeTeam;
    ArrayList<String> playerKilogramHomeTeam;
    ArrayList<String> playerFootHomeTeam;
    ArrayList<String> playerOldHomeTeam;
    //away team records
    ArrayList<String> playerNamesAwayTeam;
    ArrayList<String> playerSurnamesAwayTeam;
    ArrayList<String> playerPositionsAwayTeam;
    ArrayList<String> playerHeightAwayTeam;
    ArrayList<String> playerKilogramAwayTeam;
    ArrayList<String> playerFootAwayTeam;
    ArrayList<String> playerOldAwayTeam;

    //links
    ArrayList<String> playerLinksHomeArray;
    ArrayList<String> playerLinksAwayArray;


    @FXML
    protected void onHelloButtonClick() throws IOException {

        //home
        allFatherText = new ArrayList<>();
        playerNamesHomeTeam = new ArrayList<>();
        playerSurnamesHomeTeam = new ArrayList<>();
        playerPositionsHomeTeam = new ArrayList<>();
        playerHeightHomeTeam = new ArrayList<>();
        playerKilogramHomeTeam = new ArrayList<>();
        playerFootHomeTeam = new ArrayList<>();
        playerOldHomeTeam = new ArrayList<>();

        //away
        playerNamesAwayTeam = new ArrayList<>();
        playerSurnamesAwayTeam = new ArrayList<>();
        playerPositionsAwayTeam = new ArrayList<>();
        playerHeightAwayTeam = new ArrayList<>();
        playerKilogramAwayTeam = new ArrayList<>();
        playerFootAwayTeam = new ArrayList<>();
        playerOldAwayTeam = new ArrayList<>();

        //links
        playerLinksHomeArray = new ArrayList<>();
        playerLinksAwayArray = new ArrayList<>();

        //String
        Document doc = Jsoup.connect(urlTextBox.getText()).get();
        String title = doc.title();
        welcomeText.setText(title+".txt dosyasına çıkarıldı.");


        //getting match score and team names from banner text
        Elements matchScoreElement = doc.getElementsByClass("score-and-time");
        Elements matchHomeTeam = doc.select("div.home-team-section div.team a");
        Elements matchAwayTeam = doc.select("div.away-team-section div.team a");

        //league
        Elements matchLeague = doc.select("body main div.game-info-teams div.league a");
        String savedMatchLeagueName = matchLeague.text();

        //stadium
        Elements matchStadium = doc.select("body main div.game-info-teams div.match-time div");
        String savedMatchStadiumName = matchStadium.text();

        //time
        //stadium
        Elements matchTime = doc.select("body main div.game-info-teams div.match-time span");
        String savedMatchTime = matchTime.text();

        //this statement used for changing old team names
        String savedHomeTeamName;
        String savedAwayTeamName;
        if(matchHomeTeam.text()=="Belediye Vanspor"){
            savedHomeTeamName = "Vanspor FK";
        }
        else{
            savedHomeTeamName = matchHomeTeam.text();
        }
        //this statement used for changing old away team names
        if(matchAwayTeam.text().equalsIgnoreCase("Belediye Vanspor")){
            savedAwayTeamName = "Vanspor FK";
        }
        else{
            savedAwayTeamName = matchAwayTeam.text();
        }


        Elements matchScore = doc.select("div.score-and-time div.score");
        String savedMatchScore = matchScore.text().replace(":","-");
        //out
        System.out.println(savedHomeTeamName+" "+savedMatchScore+" "+savedAwayTeamName);


        //getting winner team and loser team or draw
        String[] scoreList = savedMatchScore.replace(" ","").split("-");
        Integer savedHomeTeamScore = Integer.parseInt(scoreList[0]);
        Integer savedAwayTeamScore = Integer.parseInt(scoreList[1]);

        //match conditions win lose draw
        //lose
        if(savedHomeTeamScore<savedAwayTeamScore){
            System.out.println(savedHomeTeamName+ " bilmemne stadyumunda "+savedAwayTeamName+" takımıyla karşılaşarak evindeki maçı kaybetti.");
        }
        //win
        else if(savedHomeTeamScore>savedAwayTeamScore){
            System.out.println(savedHomeTeamName+ " bilmemne stadyumunda "+savedAwayTeamName+" takımıyla karşılaşarak evindeki maçı kazandı.");
        }
        //draw
        else {
            System.out.println(savedHomeTeamName+" ve "+savedAwayTeamName+" berabere kaldı.");
        }

        //write match score for title
        allFatherText.add(savedHomeTeamName.toUpperCase(Locale.ROOT)+": "+savedHomeTeamScore+" - "+savedAwayTeamName.toUpperCase(Locale.ROOT)+": "+savedAwayTeamScore);

        //write match status for title
        if(savedHomeTeamScore<savedAwayTeamScore){
            allFatherText.add(savedMatchLeagueName+ " "+ savedMatchTime +" "+savedMatchStadiumName+" Stadı'nda gerçekleşen maç sonucunda ev sahibi takım  "+ savedHomeTeamName + ", deplasman takım "+savedAwayTeamName+" karşısında "+ savedMatchScore + " mağlup oldu." );
        }
        //win
        else if(savedHomeTeamScore>savedAwayTeamScore){
            allFatherText.add(savedMatchLeagueName+ " "+ savedMatchTime +" "+savedMatchStadiumName+" Stadı'nda gerçekleşen maç sonucunda ev sahibi takım  "+ savedHomeTeamName + ", deplasman takım "+savedAwayTeamName+" karşısında "+ savedMatchScore + " galip oldu." );
        }
        //draw
        else {
            allFatherText.add(savedMatchLeagueName+ " "+ savedMatchTime +" "+savedMatchStadiumName+" Stadı'nda gerçekleşen maç sonucunda ev sahibi takım  "+ savedHomeTeamName + ", deplasman takım "+savedAwayTeamName+" ile "+ savedMatchScore + " berabere kaldı." );
        }

        //time info
        allFatherText.add(" ");
        allFatherText.add(savedHomeTeamName+ " - " +savedAwayTeamName+" maçı saat kaçta? "+savedHomeTeamName+ " - " +savedAwayTeamName+" maçı ne zaman?");
        allFatherText.add(" ");
        allFatherText.add(savedHomeTeamName+" - "+savedAwayTeamName+" maçı "+savedMatchTime+" tarihinde gerçekleşti.");

        //stadium info
        allFatherText.add(" ");
        allFatherText.add(savedHomeTeamName+ " - " +savedAwayTeamName+" maçı hangi statta oynanacak? "+savedHomeTeamName+ " - " +savedAwayTeamName+" maçı hangi statta oynandı?");
        allFatherText.add(" ");
        allFatherText.add(savedHomeTeamName+" - "+savedAwayTeamName+" maçı "+savedMatchTime+" tarihinde "+ savedMatchStadiumName+" Stadı'nda oynandı.");

        //channel info
        allFatherText.add(" ");
        allFatherText.add(savedHomeTeamName+ " - " +savedAwayTeamName+" maçı hangi kanalda? "+savedHomeTeamName+ " - " +savedAwayTeamName+" maçı hangi kanalda yayınlanıyor? "+savedHomeTeamName+ " - " +savedAwayTeamName+" maçı hangi kanalda? Saat kaçta yayınlanıyor?");
        allFatherText.add(" ");
        if(savedMatchLeagueName.contains("Türkiye-1. Lig")){
            allFatherText.add(savedHomeTeamName+" - "+savedAwayTeamName+" maçı "+savedMatchTime+" tarihinde TRT Spor kanalında yayınlandı. TFF "+savedMatchLeagueName+ " maçları TRT Spor kanalında yayınlanmaktadır. Keyifli seyirler.");
        }
        else if(savedMatchLeagueName.contains("Türkiye-2. Lig")){
            allFatherText.add(savedHomeTeamName+" - "+savedAwayTeamName+" maçı "+savedMatchTime+" tarihinde oynandı fakat televizyonda yayınlanmadı. TFF "+ savedMatchLeagueName +" maçları genellikle TV'de yayınlanmamaktadır. Nadiren yerel kanallardan izleyebilirsiniz. Keyifli seyirler.");
        }
        else if(savedMatchLeagueName.contains("Türkiye-3. Lig")){
            allFatherText.add(savedHomeTeamName+" - "+savedAwayTeamName+" maçı "+savedMatchTime+" tarihinde oynandı fakat televizyonda yayınlanmadı. TFF "+ savedMatchLeagueName +" maçları genellikle TV'de yayınlanmamaktadır. Nadiren yerel kanallardan izleyebilirsiniz. Keyifli seyirler.");
        }
        else if(savedMatchLeagueName.contains("Türkiye-Süper Lig")){
            allFatherText.add(savedHomeTeamName+" - "+savedAwayTeamName+" maçı "+savedMatchTime+" tarihinde Lig TV kanalında yayınlandı. TFF "+ savedMatchLeagueName +" maçları Lig TV kanalında yayınlanmaktadır. Keyifli seyirler.");
        }
        else{
            allFatherText.add(savedHomeTeamName+" - "+savedAwayTeamName+" maçı "+savedMatchTime+" tarihinde oynandı fakat televizyonda yayınlanmadı. TFF "+ savedMatchLeagueName +" maçları genellikle TV'de yayınlanmıyor. Nadiren yerel kanallardan izleyebilirsiniz. Keyifli seyirler.");
        }




        //getting player links to get much more data
        Elements playerData = doc.getElementsByClass("inner no-after clear");
        Elements technicalDirectorHome = doc.getElementById("kadro").select("div.left-team div span.captain");
        Elements technicalDirectorAway = doc.getElementById("kadro").select("div.right-team div span.captain");
        Elements homeTeamLink = doc.getElementById("kadro").select("div.left-team").select("a");
        Elements awayTeamLink = doc.getElementById("kadro").select("div.right-team").select("a");
        Elements playerLinksHome = doc.getElementById("kadro").select("div div.left-team ul li span.player-name a");
        Elements playerLinksAway = doc.getElementById("kadro").select("div div.right-team ul li span.player-name a");
        //technical director names
        System.out.println(technicalDirectorHome.text());
        System.out.println(technicalDirectorAway.text());
        //home team link
        System.out.println(homeTeamLink.attr("abs:href"));
        //away team link
        System.out.println(awayTeamLink.attr("abs:href"));
        //player links
        System.out.println(playerLinksHome.text());

        //hom team links
        System.out.println("---------home team ------------");
        for (Element playerLinksElement:playerLinksHome) {
            playerLinksHomeArray.add(playerLinksElement.attr("abs:href"));
            System.out.println(playerLinksElement.attr("abs:href"));
        }
        System.out.println(playerLinksHomeArray.size());
        //away team links
        System.out.println("---------away team ------------");
        for (Element playerLinksElement:playerLinksAway) {
            playerLinksAwayArray.add(playerLinksElement.attr("abs:href"));
            System.out.println(playerLinksElement.attr("abs:href"));
        }
        System.out.println(playerLinksAwayArray.size());


        //extracting home players
        System.out.println("-------ev sahibi oyuncular------");
        for(int i=0;i<playerLinksHome.size();i++){
            Document playerDoc = Jsoup.connect(playerLinksHomeArray.get(i)).get();
            Elements playerNameNode = playerDoc.select("body main section div div div.playerDetails ul li");
            for (Element x: playerNameNode) {
                if(x.text().startsWith("ADI ")){
                    playerNamesHomeTeam.add(x.text().replaceFirst("ADI ",""));
                }
                else if(x.text().startsWith("SOYADI ")){
                    playerSurnamesHomeTeam.add(x.text().replaceFirst("SOYADI ",""));
                }
                else if(x.text().startsWith("MEVKİ ")){
                    playerPositionsHomeTeam.add(x.text().replaceFirst("MEVKİ ",""));
                }
                else if(x.text().startsWith("BOYU ")){
                    playerHeightHomeTeam.add(x.text().replaceFirst("BOYU ",""));
                }
                else if(x.text().startsWith("KİLOSU ")){
                    playerKilogramHomeTeam.add(x.text().replaceFirst("KİLOSU ",""));
                }
                else if(x.text().startsWith("KULLANDIĞI AYAK ")){
                    playerFootHomeTeam.add(x.text().replaceFirst("KULLANDIĞI AYAK ",""));
                }
                else if(x.text().startsWith("DOĞUM TARİHİ ")){
                    int year = Calendar.getInstance().get(Calendar.YEAR);
                    String[] birthdate = x.text().replace("DOĞUM TARİHİ ","").split("-");
                    playerOldHomeTeam.add(String.valueOf(year-Integer.parseInt(birthdate[0])));
                }
            }
        }
        //testing home players
        System.out.println(playerNamesHomeTeam);
        System.out.println(playerSurnamesHomeTeam);
        System.out.println(playerPositionsHomeTeam);
        System.out.println(playerHeightHomeTeam);
        System.out.println(playerKilogramHomeTeam);
        System.out.println(playerFootHomeTeam);
        System.out.println(playerOldHomeTeam);

        //extracting away players
        System.out.println("-------deplasman oyuncular------");
        for(int i=0;i<playerLinksAway.size();i++){
            Document playerDoc = Jsoup.connect(playerLinksAwayArray.get(i)).get();
            Elements playerNameNode = playerDoc.select("body main section div div div.playerDetails ul li");
            for (Element x: playerNameNode) {
                if(x.text().startsWith("ADI ")){
                    playerNamesAwayTeam.add(x.text().replaceFirst("ADI ",""));
                }
                else if(x.text().startsWith("SOYADI ")){
                    playerSurnamesAwayTeam.add(x.text().replaceFirst("SOYADI ",""));
                }
                else if(x.text().startsWith("MEVKİ ")){
                    playerPositionsAwayTeam.add(x.text().replaceFirst("MEVKİ ",""));
                }
                else if(x.text().startsWith("BOYU ")){
                    playerHeightAwayTeam.add(x.text().replaceFirst("BOYU ",""));
                }
                else if(x.text().startsWith("KİLOSU ")){
                    playerKilogramAwayTeam.add(x.text().replaceFirst("KİLOSU ",""));
                }
                else if(x.text().startsWith("KULLANDIĞI AYAK ")){
                    playerFootAwayTeam.add(x.text().replaceFirst("KULLANDIĞI AYAK ",""));
                }
                else if(x.text().startsWith("DOĞUM TARİHİ ")){
                    int year = Calendar.getInstance().get(Calendar.YEAR);
                    String[] birthdate = x.text().replace("DOĞUM TARİHİ ","").split("-");
                    playerOldAwayTeam.add(String.valueOf(year-Integer.parseInt(birthdate[0])));
                }
            }
        }

        //texting away players
        System.out.println(playerNamesAwayTeam);
        System.out.println(playerSurnamesAwayTeam);
        System.out.println(playerPositionsAwayTeam);
        System.out.println(playerHeightAwayTeam);
        System.out.println(playerKilogramAwayTeam);
        System.out.println(playerFootAwayTeam);
        System.out.println(playerOldAwayTeam);




        allFatherText.add(" ");
        //write home captain
        allFatherText.add(savedHomeTeamName+ " Takımı'nın Teknik Direktörü: ");
        allFatherText.add(" ");
        allFatherText.add(technicalDirectorHome.text());
        //write home first 11
        allFatherText.add(" ");
        allFatherText.add(savedHomeTeamName+ " Takımı'nın Kadrosu: ");
        allFatherText.add(" ");
        for (int i = 0; i < playerNamesHomeTeam.size(); i++) {
            if(i<11){
                allFatherText.add(playerNamesHomeTeam.get(i)+" "+playerSurnamesHomeTeam.get(i)+" - "+playerPositionsHomeTeam.get(i)+" - Boy: "+playerHeightHomeTeam.get(i)+"- Kilo: "+playerKilogramHomeTeam.get(i)+" Yaş: "+playerOldHomeTeam.get(i));
            }
            else if(i==11){
                allFatherText.add(" ");
                allFatherText.add(savedHomeTeamName+" Yedekleri: ");
                allFatherText.add(" ");
                allFatherText.add(playerNamesHomeTeam.get(i)+" "+playerSurnamesHomeTeam.get(i)+" - Yedek "+playerPositionsHomeTeam.get(i)+" - Boy: "+playerHeightHomeTeam.get(i)+"- Kilo: "+playerKilogramHomeTeam.get(i)+" Yaş: "+playerOldHomeTeam.get(i));
            }
            else{
                allFatherText.add(playerNamesHomeTeam.get(i)+" "+playerSurnamesHomeTeam.get(i)+" - Yedek "+playerPositionsHomeTeam.get(i)+" - Boy: "+playerHeightHomeTeam.get(i)+"- Kilo: "+playerKilogramHomeTeam.get(i)+" Yaş: "+playerOldHomeTeam.get(i));
            }
        }
        allFatherText.add(" ");
        //write away captain
        allFatherText.add(savedAwayTeamName+ " Takımı'nın Teknik Direktörü: ");
        allFatherText.add(" ");
        allFatherText.add(technicalDirectorAway.text());
        //write away first 11
        allFatherText.add(" ");
        allFatherText.add(savedAwayTeamName+ " Takımı'nın Kadrosu: ");
        allFatherText.add(" ");
        for (int i = 0; i < playerNamesAwayTeam.size(); i++) {
            if(i<11){
                allFatherText.add(playerNamesAwayTeam.get(i)+" "+playerSurnamesAwayTeam.get(i)+" - "+playerPositionsAwayTeam.get(i)+" - Boy: "+playerHeightAwayTeam.get(i)+"- Kilo: "+playerKilogramAwayTeam.get(i)+" Yaş: "+playerOldAwayTeam.get(i));
            }
            else if(i==11){
                allFatherText.add(" ");
                allFatherText.add(savedAwayTeamName+" Yedekleri: ");
                allFatherText.add(" ");
                allFatherText.add(playerNamesAwayTeam.get(i)+" "+playerSurnamesAwayTeam.get(i)+" - Yedek "+playerPositionsAwayTeam.get(i)+" - Boy: "+playerHeightAwayTeam.get(i)+"- Kilo: "+playerKilogramAwayTeam.get(i)+" Yaş: "+playerOldAwayTeam.get(i));
            }
            else{
                allFatherText.add(playerNamesAwayTeam.get(i)+" "+playerSurnamesAwayTeam.get(i)+" - Yedek "+playerPositionsAwayTeam.get(i)+" - Boy: "+playerHeightAwayTeam.get(i)+"- Kilo: "+playerKilogramAwayTeam.get(i)+" Yaş: "+playerOldAwayTeam.get(i));
            }
        }

        //write important moments //body > main > div.main-wrapper > div.section-center.section-right > div > div > div:nth-child(2) > div > div > div > div > div:nth-child(15)
        allFatherText.add(" ");
        allFatherText.add(savedMatchLeagueName+" "+savedHomeTeamName+" - "+savedAwayTeamName+ " Maçı Özeti: ");
        allFatherText.add(" ");
        //
        Elements importantMomentsType = doc.select("body main div.main-wrapper div.section-center.section-right div div div div div div div div.m-tr div div i");
        Elements importantMoments = doc.select("body main div.main-wrapper div.section-center.section-right div div div div div div div div.m-tr div span.player");
        Elements importantMomentsAsistors = doc.select("body main div.main-wrapper div.section-center.section-right div div div div div div div div.m-tr div span");
        ArrayList<String> importantMomentsTxt = new ArrayList<>();

        //scorecount
        ArrayList<String> importantGoalsTxt = new ArrayList<>();
        ArrayList<String> goalFlag = new ArrayList<>();
        int scoreHome = 0;
        int scoreAway = 0;
        for(Element z: importantMomentsType){

            if(z.hasClass("icon-top")){
                goalFlag.add("1");
            }
            else if(z.hasClass("card yellow")){
                goalFlag.add("0");
            }
            else if(z.hasClass("card red")){
                goalFlag.add("0");
            }
            else if(z.hasClass("icon-oyuncu-cikis")){
                goalFlag.add("0");
            }

        }
        System.out.println(goalFlag);

        //
        Elements tableColumns = doc.select("div.widget.main-match-detail div.inner.no-after div.m-table div.m-tr");
        System.out.println(tableColumns.text());
        int fh=-1;

        ArrayList<String> liveScore = new ArrayList<>();
        for (Element tableColumn:tableColumns) {
            fh++;
            if(goalFlag.get(fh)=="1"){
                System.out.println("çalıştı");
                if (tableColumn.select("div.left-team").text().isEmpty()){
                    scoreAway++;
                    liveScore.add(savedHomeTeamName+ ": "+scoreHome+" - "+savedAwayTeamName+ ": "+scoreAway);
                }
                else if(tableColumn.select("div.right-team").text().isEmpty()){
                    scoreHome++;
                    liveScore.add(savedHomeTeamName+ ": "+scoreHome+" - "+savedAwayTeamName+ ": "+scoreAway);

                }
            }
            else{
                liveScore.add(savedHomeTeamName+ ": "+scoreHome+" - "+savedAwayTeamName+ ": "+scoreAway);
            }
            System.out.println(tableColumn.text()+goalFlag.get(fh));
        }

        for(Element z: importantMomentsType){
            if(z.hasClass("icon-top")){
                importantMomentsTxt.add("asistiyle topu ağlarla buluşturdu. Gol oldu!");
            }
            else if(z.hasClass("card yellow")){
                importantMomentsTxt.add("sarı kart gördü.");
            }
            else if(z.hasClass("card red")){
                importantMomentsTxt.add("kırmızı kart gördü.");
            }
            else if(z.hasClass("icon-oyuncu-cikis")){
                importantMomentsTxt.add("oyuncu değişikliği yapıldı.");
            }

        }



        ArrayList<String> unmergedString = new ArrayList<>();
        for (Element y:importantMomentsAsistors) {
            if(y.hasClass("asist")){
                unmergedString.add(y.text());
            }
            else if(y.hasClass("player")){
                unmergedString.add(y.text());
            }
        }

        //to merge assist and main guy

        ArrayList<String> mergedString = new ArrayList<>();
        for (int i = 0; i < unmergedString.size()-1; i++) {
            if(!unmergedString.get(i+1).matches(("\\d.*"))){
                mergedString.add(unmergedString.get(i)+" - "+unmergedString.get(i+1));
            }
            else if(unmergedString.get(i).startsWith("(")){
                continue;
            }
            else{
                mergedString.add(unmergedString.get(i));
            }
        }



        mergedString.forEach(System.out::println);


        ArrayList<String> playerAllNames = new ArrayList();
        playerNamesHomeTeam.forEach((n)->playerAllNames.add(n));
        playerNamesAwayTeam.forEach((n)->playerAllNames.add(n));
        System.out.println(playerAllNames);
        ArrayList<String> playerAllSurnames = new ArrayList();
        playerSurnamesHomeTeam.forEach((n)->playerAllSurnames.add(n));
        playerSurnamesAwayTeam.forEach((n)->playerAllSurnames.add(n));
        System.out.println(playerAllSurnames);

        for (int i = 0; i < mergedString.size(); i++) {
            String[] words = mergedString.get(i).split(" ");




            for (int j = 0; j < playerAllSurnames.size(); j++) {
                try {
                    System.out.println(playerAllSurnames.get(j)+"baktım");
                    if(words[2].equalsIgnoreCase(playerAllSurnames.get(j))){
                        mergedString.set(i,mergedString.get(i).replaceAll(words[1],playerAllNames.get(j)));
                        System.out.println("çalıştı");
                    }
                    else if(words[words.length-1].substring(0, words.length-1).equalsIgnoreCase(playerAllSurnames.get(j))){
                        System.out.println(words[words.length-1].substring(0, words.length-1)+"karşılaştırıldı"+playerAllSurnames.get(j));
                        System.out.println(words[words.length-2]+"değiştirildi"+playerAllNames.get(j));
                        mergedString.set(i,mergedString.get(i).replace(words[words.length-2],("("+playerAllNames.get(j))));
                        System.out.println("denendi");
                    }
                    else if(words[words.length-1].substring(0).replace(")","").equalsIgnoreCase(playerAllSurnames.get(j))){
                        System.out.println(words[words.length-1].substring(0, words.length-1)+"karşılaştırıldı"+playerAllSurnames.get(j));
                        System.out.println(words[words.length-2]+"değiştirildi"+playerAllNames.get(j));
                        mergedString.set(i,mergedString.get(i).replace(words[words.length-2],("("+playerAllNames.get(j))));
                        System.out.println("denendi");
                    }
                }catch (StringIndexOutOfBoundsException e) {

                }catch (ArrayIndexOutOfBoundsException e) {

                }

                System.out.println(words[words.length-1].substring(0).replace(")","")+" bubububububu");
            }
        }

        for (int i = 0; i < mergedString.size(); i++) {
            if(i<liveScore.size() && goalFlag.get(i)=="1"){
                allFatherText.add(mergedString.get(i)+" " +importantMomentsTxt.get(i)+" ["+liveScore.get(i)+"]");
            }
            else{
                allFatherText.add(mergedString.get(i)+" " +importantMomentsTxt.get(i));
            }
        }
        allFatherText.add(" ");
        allFatherText.add(savedMatchLeagueName+" "+savedHomeTeamName+" - "+savedAwayTeamName+" Maç Sonucu");
        allFatherText.add(" ");
        allFatherText.add(savedHomeTeamName.toUpperCase(Locale.ROOT)+": "+savedHomeTeamScore+" - "+savedAwayTeamName.toUpperCase(Locale.ROOT)+": "+savedAwayTeamScore);

        //istatistikler
        allFatherText.add(" ");
        allFatherText.add(savedMatchLeagueName+" "+savedHomeTeamName+" - "+savedAwayTeamName+ " Maçı İstatistikleri: ");
        allFatherText.add(" ");
        Elements matchStatistics = doc.getElementById("istatistik").select("div ul li");
        for (Element matchStatistic:matchStatistics) {
            allFatherText.add(savedHomeTeamName+": " +matchStatistic.text()+" :"+savedAwayTeamName);
        }

        //puan durumu
        Elements latestScoreTableWinner = doc.getElementsByAttributeValue("style","background:#32557c!important;color: #fff;");
        Elements latestScoreTableLoser = doc.getElementsByAttributeValue("style","background:#f1b834!important;");
        System.out.println(latestScoreTableWinner.text());
        System.out.println(latestScoreTableLoser.text());
        String[] winnerStats = latestScoreTableWinner.text().split(" ");
        String[] loserStats = latestScoreTableLoser.text().split(" ");
        //0 order 1 name 2 matches 3 win 4 draw 5 lose 6 average 7 point
        try {
            if (!Character.isDigit(winnerStats[3].charAt(0)) && Character.isDigit(winnerStats[4].charAt(0)) && Character.isDigit(winnerStats[5].charAt(0))) {
                allFatherText.add(" ");
                allFatherText.add(winnerStats[1] + " " + winnerStats[2] + " " + winnerStats[3] + " Maç Sonucu Puan Durumu: ");
                allFatherText.add(" ");

                allFatherText.add(winnerStats[1] + " " + winnerStats[2] + " " + winnerStats[3] + " bu maçla beraber " + winnerStats[9] + " puana ulaştı. " + winnerStats[1] + " " + winnerStats[2] + " " + winnerStats[3] + " " + " bu sezon " + savedMatchLeagueName + "'de toplam " + winnerStats[5] + " maç oynadı. " + winnerStats[1] + " " + winnerStats[2] + " " + winnerStats[3] + " bu sezon oynadığı maçlardan " + winnerStats[5] + " galibiyet, " + winnerStats[6] + " mağlubiyet ve " + winnerStats[7] + " beraberlikle ayrıldı. " + winnerStats[1] + " " + winnerStats[2] + " " + winnerStats[3] + " " + " bu yılki maçlarda " + winnerStats[8] + " averaja sahip oldu ve bu maçın sonucunda " + savedMatchLeagueName + "'inde " + winnerStats[0] + ". sıraya oturdu. ");
                allFatherText.add(" ");
            } else if (!Character.isDigit(winnerStats[2].charAt(0)) && Character.isDigit(winnerStats[3].charAt(0)) && Character.isDigit(winnerStats[4].charAt(0))) {
                allFatherText.add(" ");
                allFatherText.add(winnerStats[1] + " " + winnerStats[2] + " Maç Sonucu Puan Durumu: ");
                allFatherText.add(" ");
                allFatherText.add(winnerStats[1] + " " + winnerStats[2] + " bu maçla beraber " + winnerStats[8] + " puana ulaştı. " + winnerStats[1] + " " + winnerStats[2] + " bu sezon " + savedMatchLeagueName + "'de toplam " + winnerStats[3] + " maç oynadı. " + winnerStats[1] + " " + winnerStats[2] + " bu sezon oynadığı maçlardan " + winnerStats[4] + " galibiyet, " + winnerStats[5] + " mağlubiyet ve " + winnerStats[6] + " beraberlikle ayrıldı. " + winnerStats[1] + " " + winnerStats[2] + " " + " bu yılki maçlarda " + winnerStats[7] + " averaja sahip oldu ve bu maçın sonucunda " + savedMatchLeagueName + "'inde " + winnerStats[0] + ". sıraya oturdu. ");
                allFatherText.add(" ");
            } else {
                allFatherText.add(" ");
                allFatherText.add(winnerStats[1] + " Maç Sonucu Puan Durumu: ");
                allFatherText.add(" ");
                allFatherText.add(winnerStats[1] + " bu maçla beraber " + winnerStats[7] + " puana ulaştı. " + winnerStats[1] + " bu sezon " + savedMatchLeagueName + "'de toplam " + winnerStats[2] + " maç oynadı. " + winnerStats[1] + " bu sezon oynadığı maçlardan " + winnerStats[3] + " galibiyet, " + winnerStats[4] + " mağlubiyet ve " + winnerStats[5] + " beraberlikle ayrıldı. " + winnerStats[1] + " bu yılki maçlarda " + winnerStats[6] + " averaja sahip oldu ve bu maçın sonucunda " + savedMatchLeagueName + "'inde " + winnerStats[0] + ". sıraya oturdu. ");
                allFatherText.add(" ");
            }

            if (!Character.isDigit(loserStats[3].charAt(0)) && Character.isDigit(loserStats[4].charAt(0)) && Character.isDigit(loserStats[5].charAt(0))) {
                allFatherText.add(" ");
                allFatherText.add(loserStats[1] + " " + loserStats[2] + " " + loserStats[3] + " Maç Sonucu Puan Durumu: ");
                allFatherText.add(" ");
                allFatherText.add(loserStats[1] + " " + loserStats[2] + " " + loserStats[3] + " bu maçla beraber " + loserStats[9] + " puana ulaştı. " + loserStats[1] + " " + loserStats[2] + " " + loserStats[3] + " " + " bu sezon " + savedMatchLeagueName + "'de toplam " + loserStats[4] + " maç oynadı." + loserStats[1] + " " + loserStats[2] + " " + loserStats[3] + " bu sezon oynadığı maçlardan " + loserStats[5] + " galibiyet, " + loserStats[6] + " mağlubiyet ve " + loserStats[7] + " beraberlikle ayrıldı. " + loserStats[1] + " " + loserStats[2] + " " + loserStats[3] + " " + " bu yılki maçlarda " + loserStats[8] + " averaja sahip oldu ve bu maçın sonucunda " + savedMatchLeagueName + "'inde " + loserStats[0] + ". sıraya oturdu. ");
                allFatherText.add(" ");
            } else if (!Character.isDigit(loserStats[2].charAt(0)) && Character.isDigit(loserStats[3].charAt(0)) && Character.isDigit(loserStats[4].charAt(0))) {
                allFatherText.add(" ");
                allFatherText.add(loserStats[1] + " " + loserStats[2] + " Maç Sonucu Puan Durumu: ");
                allFatherText.add(" ");
                allFatherText.add(loserStats[1] + " " + loserStats[2] + " bu maçla beraber " + loserStats[8] + " puana ulaştı. " + loserStats[1] + " " + loserStats[2] + " bu sezon " + savedMatchLeagueName + "'de toplam " + loserStats[3] + " maç oynadı." + loserStats[1] + " " + loserStats[2] + " bu sezon oynadığı maçlardan " + loserStats[4] + " galibiyet, " + loserStats[5] + " mağlubiyet ve " + loserStats[6] + " beraberlikle ayrıldı. " + loserStats[1] + " " + loserStats[2] + " " + " bu yılki maçlarda " + loserStats[7] + " averaja sahip oldu ve bu maçın sonucunda " + savedMatchLeagueName + "'inde " + loserStats[0] + ". sıraya oturdu. ");
                allFatherText.add(" ");
            } else {
                allFatherText.add(" ");
                allFatherText.add(loserStats[1] + " Maç Sonucu Puan Durumu: ");
                allFatherText.add(" ");
                allFatherText.add(loserStats[1] + " bu maçla beraber " + loserStats[7] + " puana ulaştı. " + loserStats[1] + " bu sezon " + savedMatchLeagueName + "'de toplam " + loserStats[2] + " maç oynadı. " + loserStats[1] + " bu sezon oynadığı maçlardan " + loserStats[3] + " galibiyet, " + loserStats[4] + " mağlubiyet ve " + loserStats[5] + " beraberlikle ayrıldı. " + loserStats[1] + " bu yılki maçlarda " + loserStats[6] + " averaja sahip oldu ve bu maçın sonucunda " + savedMatchLeagueName + "'inde " + loserStats[0] + ". sıraya oturdu. ");
                allFatherText.add(" ");
            }
        }
        catch (StringIndexOutOfBoundsException e) {

        }
        catch (ArrayIndexOutOfBoundsException e) {

        }

        //home latest matches
        Elements lastMatchesHome = doc.select("section.main-content.latest-matches div.home-team-matches div.table div.table-body div.m-table div.m-tr");
        allFatherText.add(" ");
        allFatherText.add(savedHomeTeamName + " Son Maçlardaki Performansı: ");
        allFatherText.add(" ");
        for (Element lastMatch:lastMatchesHome) {
            allFatherText.add(lastMatch.text());
        }

        //away latest matches
        Elements lastMatchesAway = doc.select("section.main-content.latest-matches div.away-team-matches div.table div.table-body div.m-table div.m-tr");
        allFatherText.add(" ");
        allFatherText.add(savedAwayTeamName + " Son Maçlardaki Performansı: ");
        allFatherText.add(" ");
        for (Element lastMatch:lastMatchesAway) {
            allFatherText.add(lastMatch.text());
        }

        //write our allfather arraylist into title.txt
        Path out = Paths.get(title+".txt");
        Files.write(out,allFatherText, Charset.defaultCharset());




    }
}