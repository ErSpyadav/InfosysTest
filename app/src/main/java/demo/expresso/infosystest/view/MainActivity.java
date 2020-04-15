package demo.expresso.infosystest.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import demo.expresso.infosystest.R;
import demo.expresso.infosystest.adapter.ImageAdapter;
import demo.expresso.infosystest.model.ImageData;
import demo.expresso.infosystest.model.Row;
import demo.expresso.infosystest.network.ApiClient;
import demo.expresso.infosystest.network.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    String TAG;
    ApiService apiService;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TAG = MainActivity.class.getSimpleName();

        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView=findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        apiService = ApiClient.getClient(getApplicationContext()).create(ApiService.class);
        Call<ImageData> call = apiService.getImageData(ApiService.BASE_URL);
        call.enqueue(new Callback<ImageData>() {
            @Override
            public void onResponse(Call<ImageData> call, final Response<ImageData> response) {
                List<Row> list=response.body().getRows();
                recyclerView.setAdapter(new ImageAdapter(list,getApplicationContext()));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        MainActivity.this.getSupportActionBar().setTitle(response.body().getTitle());
                    }
                });

            }

            @Override
            public void onFailure(Call<ImageData> call, Throwable t) {

            }

        });

    }
}
