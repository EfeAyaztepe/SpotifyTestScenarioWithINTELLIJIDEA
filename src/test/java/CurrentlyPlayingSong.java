import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrentlyPlayingSong {
    @SerializedName("name")
    @Expose
    String name;

    public void setName(String name){
        this.name=name;
    }
    public String getName(){
        return this.name;
    }
}
