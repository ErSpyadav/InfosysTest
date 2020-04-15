package demo.expresso.infosystest.viewmodel;


import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import demo.expresso.infosystest.model.ImageData;
import demo.expresso.infosystest.network.ApiClient;
import demo.expresso.infosystest.network.ApiService;
import demo.expresso.infosystest.network.ApiUrl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ImageViewModel extends AndroidViewModel {
    public MutableLiveData<String> errorMessage = new MutableLiveData<>();
    public MutableLiveData<Boolean> progressFlag = new MutableLiveData<>();
    public MutableLiveData<ImageData> imageDataMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<ImageData> refreshData =new MutableLiveData<>();
    private Activity activity;

    public ImageViewModel(@NonNull Application application) {
        super(application);
    }


    public void getImageData(Context context) {
        ApiService apiService = ApiClient.getClient(context).create(ApiService.class);
        Call<ImageData> call = apiService.getImageData(ApiUrl.BASE_URL+ApiUrl.Images);
        call.enqueue(new Callback<ImageData>() {
            @Override
            public void onResponse(Call<ImageData> call, final Response<ImageData> response) {
               System.out.print("Response:\n"+response.body());
                imageDataMutableLiveData.postValue(response.body());
            }

            @Override
            public void onFailure(Call<ImageData> call, Throwable t) {
                Log.d("Faild",t.getLocalizedMessage());
                errorMessage.postValue("Something wrong-\n"+t.getLocalizedMessage()+"\n\nPlease check Internet connection!!");
                progressFlag.postValue(false);
            }

        });
    }


}

