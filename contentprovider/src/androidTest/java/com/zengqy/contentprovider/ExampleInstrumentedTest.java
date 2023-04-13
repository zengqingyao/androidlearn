package com.zengqy.contentprovider;

import android.content.Context;
import android.util.Log;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import com.zengqy.contentprovider.dao.UserDaoImpl;
import com.zengqy.contentprovider.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertNotEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    private static final String TAG = "ExampleInstrumentedTest";



    @Test
    public void testAddUser(){
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        UserDaoImpl userDao = new UserDaoImpl(appContext);

        User user = new User();
        user.setAge(66);
        user.setUserName("zensssgq");
        user.setUserPassowrd("cccc");

        long result = userDao.addUser(user);

        Log.e(TAG, "testAddUser: "+result );

        assertNotEquals(-1,result);

    }

    @Test
    public void testListAllUser(){
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        UserDaoImpl userDao = new UserDaoImpl(appContext);

        List<User> users = userDao.listAllUser();

        for (User user : users) {
            Log.e(TAG, "user--> "+user);
        }
    }

    @Test
    public void testUpdateUser() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        UserDaoImpl userDao = new UserDaoImpl(appContext);

        User user = new User();
        user.set_id(5);
//        user.setAge(29);
//        user.setUserName("zengqy");
        user.setUserPassowrd("shen135");

        int res = userDao.updateUser(user);
        Log.e(TAG, "testUpdateUser: "+res );
    }

    @Test
    public void testDelUserById(){
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        UserDaoImpl userDao = new UserDaoImpl(appContext);

        int res = userDao.delUserById(5);
        Log.e(TAG, "testDelUserById: "+res );
    }

    @Test
    public void testGetUserById(){
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        UserDaoImpl userDao = new UserDaoImpl(appContext);

        User userById = userDao.getUserById(5);
        Log.e(TAG, "testGetUserById: "+userById );
    }

}