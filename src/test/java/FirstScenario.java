
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FirstScenario extends BasePage{
    String username;
    String songid;
    String playlistid;
    public static String name;
    public static String CurrentlyPlayingSongName;
    String token = "BQCmX-mg3inXsojZ7LKWfYTI2Qlo_55QVSp0mjVhJELeG0h14-Oxpk24urMR0qCoKu1bHMsx5N2aFdUy6B_EqqG13O5MkPKL54TceVtSMBHWuCytg7Zl-kq4JZ3qLrp9SnXq4VlvEg7Wamp0ve_GrZFvq4FA49-7VmxQS0uGMuJR_LeydXSnTwDM0Ck1XjBRUExLf-AkKFXv_fadgDO-Uk12iIYqqALcUn7qaf9Gvwf0aBHYPpkh26VH25-Ctnlp3BO6HKtu599nKBte5Fcv7-b3f-fCKCDhlthjWjFlEJxAd9uPeMCEetFB3_EdnxTHjw";
    private Logger logger = LogManager.getLogger(FirstScenario.class);

    By oturumAcButton = By.cssSelector("button[data-testid='login-button']");
    By usernameBox = By.id("login-username");
    By passwordBox = By.id("login-password");
    By logginButton = By.id("login-button");
    By loginControlName = By.cssSelector("button[data-testid='user-widget-link'] span");
    By goSearchButton = By.cssSelector("a[href='/search']");
    By searchBox= By.cssSelector("input[data-testid='search-input']");
    By goSongButton = By.cssSelector("div[data-testid='herocard-click-handler']");
    By moreButton = By.cssSelector("button.T0anrkk_QA4IAQL29get.mYN_ST1TsDdC6q1k1_xs"); //Sonradan Hata Verebilir Anlamsız Class (testid'de listeme sorunu oldu)
    By hoverCalmaListesineEkle = By.xpath("//span[text()='Add to playlist']/parent::button");
    By addPlayListButton = By.xpath("//span[text()='##New Playlist##']/parent::button"); //*
    By goLibrary = By.cssSelector("a[href='/collection']");
    By goPlayList = By.cssSelector("div[data-testid='card-click-handler']");
    By playButtons= By.cssSelector("span+button.RfidWIoz8FON2WhFoItU"); //*
    By hoverSong = By.xpath("//span[text()='2']");
    By stopButton = By.cssSelector("div[data-testid='player-controls'] div button[data-testid='control-button-playpause']");
    By cerezKapat = By.cssSelector("div[id='onetrust-close-btn-container'] button");
    By moreButtonforDelete = By.cssSelector("div[data-testid='action-bar-row'] button[data-testid='more-button']");
    By playListDeleteButton= By.xpath("//span[text()='Delete']");
    By playListDeleteConfirmButton =By.cssSelector("div.ReactModalPortal div div div div button[aria-label*='New Playlist']");

    By playListName = By.cssSelector("div.E1N1ByPFWo4AJLHovIBQ a div"); //*
    public void Login() throws InterruptedException {
       click(oturumAcButton); logger.info("Clicked to OturumAcButton");
       sendKeys(usernameBox,"testerrr20@gmail.com"); logger.info("UsernameBox Filled");
       sendKeys(passwordBox,"testtest123"); logger.info("PasswordBox Filled");
       click(logginButton); logger.info("Clicked to loginButton");
       waitByMilliSeconds(250); logger.warn("Wait 250 milliSecond");
        Assert.assertEquals(getText(loginControlName),"Efe"); logger.info("LoginControlName Successful");

    }

  public void getCurrentUserAPI(){
      RestAssured.baseURI="https://api.spotify.com";
      RequestSpecification httpRequest = RestAssured.given().auth().oauth2(token);
      Response response = httpRequest.get("/v1/me");
      ResponseBody body = response.getBody();
      String bodyAsString = body.asString();
      Headers header = response.getHeaders();
      int StatusCode = response.getStatusCode();
      System.out.println("getCurrentUser Status Code is  "+StatusCode);
      //System.out.println(bodyAsString);
      username= response.jsonPath().getString("id");
      //System.out.println(username);
      logger.info("CurrentUser Informations Reached");
  }

  public void CreatePlayListAPI(){
      RestAssured.baseURI="https://api.spotify.com";
      String postData ="{\n" +
              "  \"name\": \"##New Playlist##\",\n" +
              "  \"description\": \"Test Playlist...\",\n" +
              "  \"public\": false\n" +
              "}";
      RequestSpecification httpRequest = RestAssured.given().auth().oauth2(token);
      Response response = httpRequest.body(postData).post("/v1/users/"+username+"/playlists");
      ResponseBody body = response.getBody();
      String bodyAsString = body.asString();
      Headers header = response.getHeaders();
      int StatusCode = response.getStatusCode();
      System.out.println("Create PlayList Status Code is  "+StatusCode);
      //System.out.println(bodyAsString);
      //System.out.println(username);
      playlistid= response.jsonPath().getString("id");
      logger.info("New Playlist Created");
  }

    public void goToSongPage() throws InterruptedException {
        waitByMilliSeconds(500); logger.warn("Wait 500 milliSeconds");
    click(goSearchButton); logger.info("Clicked to goSearchButton");
    sendKeys(searchBox,"Adagio Lara Fabian"); logger.info("searchBox Filled");
    click(goSongButton); logger.info("Clicked to goSongButton");
    doesUrlEqual("https://open.spotify.com/album/1T9WljVzTvELFapFVNLd7x?highlight=spotify:track:48f9zJZNKuFwC2wfTGwmfJ",20); logger.info("Song Page url Control");

    }

    public List<WebElement> getmoreButtons(){
        logger.info("moreButtons Listed");
        return findAll(moreButton);
    }

    public void clickMoreButton(){
        getmoreButtons().get(0).click(); logger.info("Clicked to moreButton");
    }

    public void addSongToPlayList() throws InterruptedException {
        hoverElement(hoverCalmaListesineEkle); logger.info("hover to hoverCalmaListesineEkle Done");
        click(addPlayListButton); logger.info("Clicked to addPLayListButton");
        waitByMilliSeconds(300); logger.warn("Wait 300 milliSeconds");
    }

    public void SearchAPI() throws IOException {
        RestAssured.baseURI = "https://api.spotify.com";
        RequestSpecification httpRequest = RestAssured.given().auth().oauth2(token);
        Response response = httpRequest.get("/v1/search?q=ShapeOfYou&type=track");
        ResponseBody body = response.getBody();
        String bodyAsString = body.asString();
        Headers header = response.getHeaders();
        int StatusCode = response.getStatusCode();
        System.out.println("Status Code is  "+StatusCode);
        //System.out.println(bodyAsString);

        List<Object> list = new ArrayList<>();
        SearchClass obj = new SearchClass();


        songid= response.jsonPath().getString("tracks.items.id[0]");
         name= response.jsonPath().getString("tracks.items.name[0]");
        String track_number= response.jsonPath().getString("tracks.items.track_number");
        String type= response.jsonPath().getString("tracks.items.type");

        obj.setId(songid);
        obj.setName(name);
        obj.setTrack_number(track_number);
        obj.setType(type);

        list.add(obj);

        Writer writer = new FileWriter("Search.json");

        try {
            Gson gson1 = (new GsonBuilder()).setPrettyPrinting().create();
            gson1.toJson(list, writer);
            //System.out.println("Bilgiler Json dosyasına başarıyla kaydedildi");
        } catch (Exception e) {
        }

        writer.close();

        Reader reader = new BufferedReader(new FileReader("Search.json"));
        List<SearchClass> jsonElements = (List)(new Gson()).fromJson(reader, TypeToken.getParameterized(List.class, new Type[]{SearchClass.class}).getType());
        Iterator var3 = jsonElements.iterator();

        while(var3.hasNext()) {
            SearchClass a = (SearchClass) var3.next();
            //System.out.println(a.getId());


        }

    }

    public void addSongToPlayListAPI(){
        RestAssured.baseURI ="https://api.spotify.com";
        String postData ="{\n" +
                "  \"uris\": [\n" +
                "    \"spotify:track:"+songid+"\"\n" +
                "  ]\n" +
                "}";

        RequestSpecification httpRequest = RestAssured.given().auth().oauth2(token);
        Response response = httpRequest.body(postData).post("https://api.spotify.com/v1/playlists/"+playlistid+"/tracks");
        ResponseBody body = response.getBody();
        String bodyAsString = body.asString();
        Headers header = response.getHeaders();
        int StatusCode = response.getStatusCode();
        System.out.println("addSongToPlayList Status Code is  "+StatusCode);
       // System.out.println(bodyAsString);
    }


    public void playSong() throws InterruptedException {
        click(goLibrary); logger.info("Clicked to goLibrary");
        waitByMilliSeconds(250); logger.warn("Wait 250 milliSeconds");
        Assert.assertTrue(isElementVisible(goPlayList,20),"goPlayList is not Visible");
        click(goPlayList); logger.info("Clicked to goPlayList");
        waitByMilliSeconds(250); logger.warn("Wait 250 milliSeconds");
        hoverElement(hoverSong); logger.info("hover to hoverSong Done");
        getPlayButtons().get(1).click(); logger.info("Clicked to PlaySongButton");
        waitByMilliSeconds(250); logger.warn("Wait 250 milliSeconds");
    }

    public void stopSong() throws InterruptedException {
        click(cerezKapat);
        Assert.assertTrue(isElementVisible(stopButton,10),"stopButton is not Visible");
        Assert.assertTrue(isElementClickable(stopButton,10),"stopButton is not Clickable");
        waitByMilliSeconds(300); logger.warn("Wait 300 milliSeconds");
        click(stopButton); logger.info("Clicked to StopButton");

    }

    public List<WebElement> getPlayButtons(){
        return findAll(playButtons);
    }

    public void getCurrentlyPlayingTrackAPI() throws IOException {
        RestAssured.baseURI = "https://api.spotify.com";
        RequestSpecification httpRequest = RestAssured.given().auth().oauth2(token);
        Response response = httpRequest.get("/v1/me/player/currently-playing");
        ResponseBody body = response.getBody();
        String bodyAsString = body.asString();
        Headers header = response.getHeaders();
        int StatusCode = response.getStatusCode();
        System.out.println("getCurrentlyPlayingTrack Status Code is  "+StatusCode);
        CurrentlyPlayingSongName= response.jsonPath().getString("item.name");
        CurrentlyPlayingSong song = new CurrentlyPlayingSong();
        song.setName(CurrentlyPlayingSongName);
       List<Object> list1 = new ArrayList<>();
       list1.add(song);
        Writer writer = new FileWriter("CurrentlySong.json");

        try {
            Gson gson1 = (new GsonBuilder()).setPrettyPrinting().create();
            gson1.toJson(list1, writer);
            //System.out.println("Bilgiler Json dosyasına başarıyla kaydedildi");
        } catch (Exception e) {
        }

        writer.close();

    }


    public void removePlayListItemsAPI(){
        RestAssured.baseURI="https://api.spotify.com";
        String postData ="{\n" +
                "  \"tracks\": [\n" +
                "    {\n" +
                "      \"uri\": \"spotify:track:"+songid+"\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"uri\": \"spotify:track:48f9zJZNKuFwC2wfTGwmfJ\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        RequestSpecification httpRequest = RestAssured.given().auth().oauth2(token);
        Response response = httpRequest.body(postData).delete("/v1/playlists/"+playlistid+"/tracks");
        ResponseBody body = response.getBody();
        String bodyAsString = body.asString();
        Headers header = response.getHeaders();
        int StatusCode = response.getStatusCode();
        System.out.println("removePlayListItems Status Code is  "+StatusCode);
    }

    public void deletePlayList() throws InterruptedException {
        Assert.assertTrue(isElementVisible(moreButtonforDelete,10),"moreButtonforDelete is not Visible");
        click(moreButtonforDelete); logger.info("Clicked to moreButtonforDelete");
        click(playListDeleteButton); logger.info("Clicked to playListDeleteButton");
        click(playListDeleteConfirmButton); logger.info("Clicked to playListDeleteConfirmButton");
        waitByMilliSeconds(300); logger.warn("Wait 300 milliSeconds");
    }




    public List<WebElement> getPlayListNames(){
            return findAll(playListName);

    }

    public void isPlayListDeleted(){
        if (isElementVisible(playListName,10)) {


            for (int i = 0; i < getPlayListNames().size(); i++) {
                String PlayListName = getPlayListNames().get(i).getText();
                Assert.assertFalse(PlayListName.contains("##New Playlist##"), "PlayList not Deleted");
                logger.info("PlayList Delete Control Done");

            }
        }

        else if(!isElementVisible(playListName,10)){
            System.out.println("PlayList Empty");
            BaseTest.driver.quit();
        }

    }


}
