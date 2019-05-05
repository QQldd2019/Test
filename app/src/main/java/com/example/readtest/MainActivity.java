package com.example.readtest;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    String onetxt;
    private Timer     timer;
    private TimerTask timerTask1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        timerTask1 = new TimerTask() {
            @Override
            public void run() {                                                            //新建定时任务显示信号实时数值
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        myHandler1.sendEmptyMessage(0x123);
                    }
                });
            }
        };
        timer = Tim.getTimer();
        timer.schedule(timerTask1, 10, 1000);
        setContentView(R.layout.activity_main);
        textView=(TextView)findViewById(R.id.tv1);
        textView.setText(Txt().get(0));
    }
    public Map<Integer, String> Txt() {
        //将读出来的一行行数据使用Map存储
        String filePath = "/storage/emulated/0/p1.txt";//手机上地址
        Map<Integer, String> map = new HashMap<Integer, String>();
        try {
            File file  = new File(filePath);
            int  count = 0;//初始化 key值
            if (file.isFile() && file.exists()) {  //文件存在的前提
                InputStreamReader isr     = new InputStreamReader(new FileInputStream(file));
                BufferedReader    br      = new BufferedReader(isr);
                String            lineTxt = null;
                while ((lineTxt = br.readLine()) != null) {  //
                    if (!"".equals(lineTxt)) {
                       String reds = lineTxt.split(";")[0];  //java 正则表达式
                      //  String reds = lineTxt;  //java 正则表达式
                        map.put(count, reds);//依次放到map 0，value0;1,value2
                        count++;
                    }
                }
                isr.close();
                br.close();

            }else {

                Toast.makeText(getApplicationContext(),"can not find file",Toast.LENGTH_SHORT).show();//找不到文件情况下
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    final Handler myHandler1 = new Handler() {
        @Override
        //重写handleMessage方法,根据msg中what的值判断是否执行后续操作
        public void handleMessage(Message msg) {                                              //新建线程显示信号实时数值

            if (msg.what == 0x123) {
                String S=Txt().get(0);
                textView.setText(Txt().get(0));
                Log.d("FFF","map="+ReadTxtFile("/storage/emulated/0/p1.txt"));

            }}};
    /*
     * 读取txt文本的工具
     * */
    public String ReadTxtFile(String strFilePath) {
        String path = String.valueOf(strFilePath);
        String content = ""; //文件内容字符串
        StringBuffer sb = new StringBuffer();
        //打开文件
        File file = new File(path);
        //如果path是传递过来的参数，可以做一个非目录的判断
        if (file.isDirectory()) {
        } else {
            try {
                InputStream instream = new FileInputStream(file);
                if (instream != null) {
                    String line=null;
                    BufferedReader buffreader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"GB2312"));
                    //分行读取
                    line = buffreader.readLine();
                    if(line != null)  onetxt = line+"\n";
                    while ((line = buffreader.readLine()) != null) {
                        content += line + "\n";
                    }
                    instream.close();
                }
            } catch (java.io.FileNotFoundException e) {
                Log.d("TestFile", "The File doesn't not exist.");
            } catch (IOException e) {
                Log.d("TestFile", e.getMessage());
            }
        }
        return content;
    }
}
