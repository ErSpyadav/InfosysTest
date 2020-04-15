package demo.expresso.infosystest.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import demo.expresso.infosystest.R;
import demo.expresso.infosystest.adapter.ImageAdapter;
import demo.expresso.infosystest.model.ImageData;
import demo.expresso.infosystest.network.ApiService;
import demo.expresso.infosystest.viewmodel.ImageViewModel;

public class MainActivity extends AppCompatActivity {

    String TAG;
    ApiService apiService;
    private RecyclerView recyclerView;
    public ImageViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TAG = MainActivity.class.getSimpleName();
        mViewModel = ViewModelProviders.of(this).get(ImageViewModel.class);


        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        refreshData();
        final SwipeRefreshLayout pullToRefresh = findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData(); // your code
                pullToRefresh.setRefreshing(false);
            }
        });
//        apiService = ApiClient.getClient(getApplicationContext()).create(ApiService.class);
//
//
//
//        Call<ImageData> call = apiService.getImageData(ApiService.BASE_URL);
//        call.enqueue(new Callback<ImageData>() {
//            @Override
//            public void onResponse(Call<ImageData> call, final Response<ImageData> response) {
//                List<Row> list=response.body().getRows();
//                recyclerView.setAdapter(new ImageAdapter(list,getApplicationContext()));
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        MainActivity.this.getSupportActionBar().setTitle(response.body().getTitle());
//                    }
//                });
//
//            }
//
//            @Override
//            public void onFailure(Call<ImageData> call, Throwable t) {
//
//            }
//
//        });



    }

    public void refreshData() {
        mViewModel.getImageData(getApplicationContext());
        mViewModel.imageDataMutableLiveData.observe(this, new Observer<ImageData>() {
            @Override
            public void onChanged(ImageData imageData) {
                MainActivity.this.getSupportActionBar().setTitle(imageData.getTitle());
                recyclerView.setAdapter(new ImageAdapter(imageData.getRows()));
            }
        });
    }
}
