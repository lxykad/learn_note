package git.lxy.com.wananzhuoapp.ui.flutter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.text.SimpleDateFormat;

import io.flutter.app.FlutterActivity;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugins.GeneratedPluginRegistrant;

/**
 * @author liuxinyu
 * @date 2019/1/2  10:35 AM
 * @Description
 */
public class FlutterSearchActivity extends FlutterActivity {

    private static final String ROUTE_ACTION = "android.intent.action.RUN";
    private static final String ROUTE = "route";
    private static final String CHANNEL = "flutter.plugin";

    public static void openActivity(Context context, String route) {
        Intent intent = new Intent(context, FlutterSearchActivity.class);
        intent.setAction(ROUTE_ACTION);
        intent.putExtra(ROUTE, route);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String route = getIntent().getStringExtra(ROUTE);
        getIntent().putExtra(ROUTE, route);

        new MethodChannel(getFlutterView(), CHANNEL)
                .setMethodCallHandler(new MethodChannel.MethodCallHandler() {
                    @Override
                    public void onMethodCall(MethodCall call, MethodChannel.Result result) {
                        // 和flutter页面交互
                        if (call.method.equals("toast")) {
                            Toast.makeText(FlutterSearchActivity.this, call.argument("msg").toString(), Toast.LENGTH_SHORT).show();

                        } else if (call.method.equals("getTime")) {
                            result.success(getCurrentTime());
                        } else if (call.method.equals("jump")){
                              String url = call.argument("url").toString();


                        }else {

                        }
                    }
                });

        GeneratedPluginRegistrant.registerWith(this);
    }

    private String getCurrentTime() {
        return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(System.currentTimeMillis());
    }
}
