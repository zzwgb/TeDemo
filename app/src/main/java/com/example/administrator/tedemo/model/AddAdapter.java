package com.example.administrator.tedemo.model;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;

import com.example.administrator.tedemo.R;

import java.util.List;

/**
 * Created by fan on 2017/1/11.
 */
public class AddAdapter extends BaseAdapter {

    private Context context;
    private List<AddBean> mans;
    private LayoutInflater layinf;

    public AddAdapter(Context context, List<AddBean> mans) {
        this.context = context;
        this.mans = mans;
        layinf = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mans.size();
    }


    @Override
    public int getItemViewType(int position) {
        //这里是根据position返回指定的布局类型，比如男的返回0，女的返回1，会根据这个返回值加载不同布局
        return mans.get(position).id;
    }

    @Override
    public int getViewTypeCount() {
        //这里是adapter里有几种布局
        return 2;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final AddBean bean = mans.get(position);
        //第一个布局的holder
        ViewManHolder vhMan = null;
        //第二个布局的holder
        ViewWoManHolder vhWoMan = null;

        if(convertView == null){
            switch (bean.id){
                case 0:
                    convertView = layinf.inflate(R.layout.item_man ,null);
                    vhMan = new ViewManHolder(convertView);
                    convertView.setTag(vhMan);
                    break;
                case 1:
                    convertView = layinf.inflate(R.layout.item_woman ,null);
                    vhWoMan = new ViewWoManHolder(convertView);
                    convertView.setTag(vhWoMan);
                    break;
            }
        }else{
            switch (bean.id){
                case 0:
                    vhMan = (ViewManHolder) convertView.getTag();
                    break;
                case 1:
                    vhWoMan = (ViewWoManHolder) convertView.getTag();
                    break;
            }
        }
        //根据不同布局加载不同数据
        switch (bean.id){
            case 0:

                //清除焦点
                vhMan.name.clearFocus();
                vhMan.age.clearFocus();
                vhMan.sex.clearFocus();

                //先清除之前的文本改变监听
                if (vhMan.name.getTag() instanceof TextWatcher) {
                    vhMan.name.removeTextChangedListener((TextWatcher) (vhMan.name.getTag()));
                }
                if (vhMan.age.getTag() instanceof TextWatcher) {
                    vhMan.age.removeTextChangedListener((TextWatcher) (vhMan.age.getTag()));
                }
                if (vhMan.sex.getTag() instanceof TextWatcher) {
                    vhMan.sex.removeTextChangedListener((TextWatcher) (vhMan.sex.getTag()));
                }

                //设置数据
                vhMan.name.setText(TextUtils.isEmpty(bean.name)? "":bean.name);
                vhMan.age.setText(TextUtils.isEmpty(bean.age)? "":bean.age);
                vhMan.sex.setText(TextUtils.isEmpty(bean.sex)? "":bean.sex);

                //文本改变监听
                final TextWatcher oneNameWatcher = new SimpeTextWather() {
                    @Override
                    public void afterTextChanged(Editable s) {
                        if (TextUtils.isEmpty(s)) {
                            bean.setName(null);
                        } else {
                            bean.setName(String.valueOf(s));
                        }
                    }
                };
                final TextWatcher oneAgeWatcher = new SimpeTextWather() {
                    @Override
                    public void afterTextChanged(Editable s) {
                        if (TextUtils.isEmpty(s)) {
                            bean.setAge(null);
                        } else {
                            bean.setAge(String.valueOf(s));
                        }
                    }
                };
                final TextWatcher oneSexWatcher = new SimpeTextWather() {
                    @Override
                    public void afterTextChanged(Editable s) {
                        if (TextUtils.isEmpty(s)) {
                            bean.setSex(null);
                        } else {
                            bean.setSex(String.valueOf(s));
                        }
                    }
                };
                //吧监听设置到不同的EditText上
                vhMan.name.addTextChangedListener(oneNameWatcher);
                vhMan.name.setTag(oneNameWatcher);

                vhMan.age.addTextChangedListener(oneAgeWatcher);
                vhMan.age.setTag(oneAgeWatcher);

                vhMan.sex.addTextChangedListener(oneSexWatcher);
                vhMan.sex.setTag(oneSexWatcher);
                break;
            case 1:

                //清除焦点
                vhWoMan.name.clearFocus();
                vhWoMan.age.clearFocus();
                vhWoMan.sex.clearFocus();
                vhWoMan.test.clearFocus();

                if (vhWoMan.name.getTag() instanceof TextWatcher) {
                    vhWoMan.name.removeTextChangedListener((TextWatcher) (vhWoMan.name.getTag()));
                }
                if (vhWoMan.age.getTag() instanceof TextWatcher) {
                    vhWoMan.age.removeTextChangedListener((TextWatcher) (vhWoMan.age.getTag()));
                }
                if (vhWoMan.sex.getTag() instanceof TextWatcher) {
                    vhWoMan.sex.removeTextChangedListener((TextWatcher) (vhWoMan.sex.getTag()));
                }
                if (vhWoMan.test.getTag() instanceof TextWatcher) {
                    vhWoMan.test.removeTextChangedListener((TextWatcher) (vhWoMan.test.getTag()));
                }

                vhWoMan.name.setText(TextUtils.isEmpty(bean.name) ? "" : bean.name);
                vhWoMan.age.setText(TextUtils.isEmpty(bean.age) ? "" : bean.age);
                vhWoMan.sex.setText(TextUtils.isEmpty(bean.sex) ? "" : bean.sex);
                vhWoMan.test.setText(TextUtils.isEmpty(bean.test) ? "" : bean.test);

                //文本改变监听
                final TextWatcher twoNameWatcher = new SimpeTextWather() {
                    @Override
                    public void afterTextChanged(Editable s) {
                        if (TextUtils.isEmpty(s)) {
                            bean.setName(null);
                        } else {
                            bean.setName(String.valueOf(s));
                        }
                    }
                };
                final TextWatcher twoAgeWatcher = new SimpeTextWather() {
                    @Override
                    public void afterTextChanged(Editable s) {
                        if (TextUtils.isEmpty(s)) {
                            bean.setAge(null);
                        } else {
                            bean.setAge(String.valueOf(s));
                        }
                    }
                };
                final TextWatcher twoSexWatcher = new SimpeTextWather() {

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (TextUtils.isEmpty(s)) {
                            bean.setSex(null);
                        } else {
                            bean.setSex(String.valueOf(s));
                        }
                    }
                };
                final TextWatcher twoTestWatcher = new SimpeTextWather() {

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (TextUtils.isEmpty(s)) {
                            bean.setTest(null);
                        } else {
                            bean.setTest(String.valueOf(s));
                        }
                    }
                };

                //吧监听设置到不同的EditText上
                vhWoMan.name.addTextChangedListener(twoNameWatcher);
                vhWoMan.name.setTag(twoNameWatcher);

                vhWoMan.age.addTextChangedListener(twoAgeWatcher);
                vhWoMan.age.setTag(twoAgeWatcher);

                vhWoMan.sex.addTextChangedListener(twoSexWatcher);
                vhWoMan.sex.setTag(twoSexWatcher);

                vhWoMan.test.addTextChangedListener(twoTestWatcher);
                vhWoMan.test.setTag(twoTestWatcher);

                break;
        }
        return convertView;
    }

    /**
     * 第一种布局的Holder
     */
    class ViewManHolder{
        EditText name;
        EditText age;
        EditText sex;
        public ViewManHolder(View convertView){
            name = (EditText) convertView.findViewById(R.id.name);
            age = (EditText) convertView.findViewById(R.id.age);
            sex = (EditText) convertView.findViewById(R.id.sex);
        }
    }

    /**
     * 第二种布局的Holder
     */
    class ViewWoManHolder{
        EditText name;
        EditText age;
        EditText sex;
        EditText test;
        public ViewWoManHolder(View convertView){
            name = (EditText) convertView.findViewById(R.id.name);
            age = (EditText) convertView.findViewById(R.id.age);
            sex = (EditText) convertView.findViewById(R.id.sex);
            test = (EditText) convertView.findViewById(R.id.test);
        }
    }

    public abstract class SimpeTextWather implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
    }

}
