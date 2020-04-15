package demo.expresso.infosystest.network;

import demo.expresso.infosystest.model.ImageData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    String BASE_URL = "https://dl.dropboxusercontent.com/s/2iodh4vg0eortkl/facts.json";
    @GET("{path}")
    Call<ImageData> getImageData(@Path("path") String path);
}
