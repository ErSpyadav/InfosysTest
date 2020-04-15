package demo.expresso.infosystest.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import demo.expresso.infosystest.R;
import demo.expresso.infosystest.adapter.ImageAdapter;
import demo.expresso.infosystest.model.ImageData;
import demo.expresso.infosystest.viewmodel.ImageViewModel;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    public ImageViewModel mViewModel;
    ProgressBar progressBar;
    TextView errorMsgTv;
    ImageAdapter imageAdapter;
    boolean isRefresh=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewModel = ViewModelProviders.of(this).get(ImageViewModel.class);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        errorMsgTv = (TextView) findViewById(R.id.errorMsg);
        errorMsgTv.setVisibility(View.GONE);
        progressBar = findViewById(R.id.progressBar_cyclic);
        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        refreshData();
        final SwipeRefreshLayout pullToRefresh = findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh=true;
                refreshData(); // your code
                progressBar.setVisibility(View.VISIBLE);
                pullToRefresh.setRefreshing(false);
            }
        });

        mViewModel.progressFlag.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (!aBoolean)
                    progressBar.setVisibility(View.GONE);

            }
        });

        mViewModel.errorMessage.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                errorMsgTv.setVisibility(View.VISIBLE);
                errorMsgTv.setText(s);
            }
        });

        mViewModel.imageDataMutableLiveData.observe(this, new Observer<ImageData>() {
            @Override
            public void onChanged(ImageData imageData) {
                MainActivity.this.getSupportActionBar().setTitle(imageData.getTitle());
                if(imageAdapter==null)
                    imageAdapter =new ImageAdapter(imageData.getRows());

                recyclerView.setAdapter(imageAdapter);
                progressBar.setVisibility(View.GONE);
            }
        });




    }

    public void refreshData() {
        progressBar.setVisibility(View.VISIBLE);
        mViewModel.getImageData(getApplicationContext());
    }
}
