package com.example.recyclerviewcheckboxdemo;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_over;
    private RecyclerView recyView;
    private Button commit;
    private List<String> list;//保存数据源
    private RecyViewAdapter adapter;
    private boolean isSelect = false;//全选按钮的状态
    private List<String>listdatas=new ArrayList<>();//记录选择的数据
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initData() {
        list=new ArrayList<>();
        for (int i = 0; i <20 ; i++) {
            list.add("测试数据----"+i);
        }
        adapter = new RecyViewAdapter(MainActivity.this,list);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(MainActivity.this, 3);
        recyView.setLayoutManager(layoutManager);
        recyView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        recyView.setLongClickable(true);
        recyView.addItemDecoration(new GridSpacingItemDecoration(3, 80, true));
    }

    private void initView() {
        tv_over = (TextView) findViewById(R.id.tv_over);
        recyView = (RecyclerView) findViewById(R.id.recyView);
        commit = (Button) findViewById(R.id.commit);

        tv_over.setOnClickListener(this);
        commit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_over:
                if (!isSelect){
                   isSelect=true;//全选
                   adapter.All();
                   tv_over.setText("取消全选");
                }else{
                    isSelect=false;//取消全选
                    adapter.neverall();
                    tv_over.setText("全选");
                }
                break;
            case R.id.commit:
           String  content="";
                 listdatas.clear();
                Map<Integer, Boolean> map = adapter.getMap();
                for (int i = 0; i < list.size(); i++) {
                    if (map.get(i)){
                     listdatas.add(list.get(i));
                    }
                }
                for (int j = 0; j <listdatas.size() ; j++) {
                    content+=listdatas.get(j)+",";
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                if (content.length() == 0) {
                    builder.setMessage("请选择数据");
                } else {
                    builder.setMessage(content.substring(0, content.length() - 1));
                }
                builder.create().show();

                break;

                default:
                    break;
        }
    }
}
