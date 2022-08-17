import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchClass {
    @SerializedName("id")
    @Expose
    private Object id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("track_number")
    @Expose
    private String track_number;
    @SerializedName("type")
    @Expose
    private String type;

    public Object getId(){
        return this.id;
    }
    public void  setId(Object id){
        this.id = id;
    }

    public String getName(){
        return this.name;
    }
    public void  setName(String name){
        this.name = name;
    }

    public String getType(){
        return this.type;
    }
    public void  setType(String type){
        this.type = type;
    }

    public String getTrack_number(){
        return this.track_number;
    }
    public void  setTrack_number(String track_number){
        this.track_number = track_number;
    }


}
