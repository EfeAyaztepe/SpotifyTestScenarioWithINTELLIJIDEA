import org.junit.Test;
import org.testng.Assert;

import java.io.IOException;

public class AppTest extends BaseTest {

    @Test
    public void SpotifyTest() throws InterruptedException, IOException {
    FirstScenario firstScenario = new FirstScenario();
    firstScenario.Login();
    firstScenario.getCurrentUserAPI();
    firstScenario.CreatePlayListAPI();
    firstScenario.goToSongPage();
    firstScenario.clickMoreButton();
    firstScenario.addSongToPlayList();
    firstScenario.SearchAPI();
    firstScenario.addSongToPlayListAPI();
    firstScenario.playSong();
    firstScenario.getCurrentlyPlayingTrackAPI();
    Assert.assertEquals(FirstScenario.name,FirstScenario.name,"Song name Control Failed");
    firstScenario.stopSong();
    firstScenario.removePlayListItemsAPI();
    firstScenario.deletePlayList();
    firstScenario.isPlayListDeleted();
    }
}
