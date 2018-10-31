package com.example.administrator.tedemo.view;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.tedemo.R;
import com.example.administrator.tedemo.model.AddAdapter;
import com.example.administrator.tedemo.model.AddBean;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    private MyHandler myHandler;
    private android.widget.TextView add;
    private android.widget.ListView listview;
    private Context context;
    private List<AddBean> mans = new ArrayList<>();
    private AddAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.listview = (ListView) findViewById(R.id.listview);
        this.add = (TextView) findViewById(R.id.add);

        context = this;

        myHandler = new MyHandler(this);
        //设置适配器
        adapter = new AddAdapter(context, mans);
        listview.setAdapter(adapter);
        //添加
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddItem();
            }
        });
        //listview设置滑动监听
        listview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {

                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:    //当停止滚动时

                        break;

                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:    //滚动时

                        //没错，下面这一坨就是隐藏软键盘的代码
                        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(MainActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                        break;

                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING:   //手指抬起，但是屏幕还在滚动状态

                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
    }

    /**
     * 显示添加item
     */
    private void showAddItem(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        //设置要显示的条目
        final String[] str = new String[]{"添加男生","添加女生"};
        //第一个参数为数组，第二个参数为默认勾选，
        builder.setSingleChoiceItems(str, -1, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (which == 0) {   //男生
                    mans.add(new AddBean(0));
                } else {            //女生
                    mans.add(new AddBean(1));
                }

                myHandler.sendEmptyMessage(22);

                //点击后取消对话框
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {}
        });

        //最后一定要显示出来
        builder.show();
    }

    private class MyHandler extends Handler{

        WeakReference<AppCompatActivity> mAct;

        public MyHandler(AppCompatActivity act){
            mAct = new WeakReference<AppCompatActivity>(act);
        }

        @Override
        public void handleMessage(Message msg) {
            AppCompatActivity atc = mAct.get();
            if(atc == null)
                return;

            adapter.notifyDataSetChanged();

        }
    }

}
