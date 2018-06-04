package com.andy.a;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import java.util.List;

/**
 * @author wuhaibo
 * create date: 2018/6/2.
 */

@Route(path = "/module_a/test")
public class ModuleATest extends AppCompatActivity {
    @Autowired(name = "idList")
    List<Integer> idList;

    @Autowired(name = "id")
    int id = -1;

    @Autowired(name = "user")
    User mUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_test_module_a);

        TextView tvIdList =  findViewById(R.id.tv_id_list);
        TextView tvId =  findViewById(R.id.tv_id);

        tvIdList.setText("idList: " + idList);
        tvId.setText("id: " + id);

        TextView tvUserName = findViewById(R.id.tv_user_name);
        TextView tvUserId = findViewById(R.id.tv_user_id);

        tvUserName.setText("User.name: " + mUser.name);
        tvUserId.setText("User.id: " + +mUser.id);

        Log.i("Andy", "idList: " + idList);
        Log.i("Andy", "id: " + id);
        Log.i("Andy", "mUser: " + mUser);
    }
}
