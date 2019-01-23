package com.article.demos.android.ui;

import android.Manifest;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.article.demos.android.R;
import com.article.demos.android.databinding.ActivityRxBinding;
import com.article.demos.common.base.BaseActivity;
import com.article.demos.common.constant.Constant;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxCompoundButton;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author a
 */
@Route(path = Constant.RX_ACTIVITY)
public class RxActivity extends AppCompatActivity {

    private ActivityRxBinding mBinding;
    private static final int TIME = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_rx);

        initEvents();
    }

    private void initEvents() {

        //优化搜索功能
        RxTextView.textChanges(mBinding.etSearch)
                // 跳过一开始et内容为空时的搜索
                .skip(1)
                //debounce 在一定的时间内没有操作就会发送事件
                .debounce(1000, TimeUnit.MILLISECONDS)
                //下面这两个都是数据转换
                //flatMap：当同时多个网络请求访问的时候，前面的网络数据会覆盖后面的网络数据
                //switchMap：当同时多个网络请求访问的时候，会以最后一个发送请求为准，前面网路数据会被最后一个覆盖
                .switchMap(new Function<CharSequence, ObservableSource<List<String>>>() {
                    @Override
                    public ObservableSource<List<String>> apply(CharSequence charSequence) throws Exception {
                        String searchKey = charSequence.toString();
                        System.out.println("binding=======搜索内容:" + searchKey);
                        //这里执行网络操作，获取数据
                        List<String> list = new ArrayList<String>();
                        list.add("小刘哥");
                        list.add("可爱多");

                        return Observable.just(list);
                    }
                })
                // .onErrorResumeNext()
                //网络操作，获取我们需要的数据
                .subscribeOn(Schedulers.io())
                //界面更新在主线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<String>>() {
                    @Override
                    public void accept(List<String> strings) throws Exception {
                        System.out.println("binding=======搜索到" + strings.size() + "条数据");
                    }
                });

        /**
         * 防止多次点击--2秒内执行一次点击
         */
        RxView.clicks(mBinding.btClick)
                .throttleFirst(TIME, TimeUnit.SECONDS)
                .subscribe(c -> System.out.println("binding=======点击了按钮"));

        /**
         * 长按事件
         */
        RxView.longClicks(mBinding.btClick)
                .subscribe(c -> System.out.println("binding=======长按了按钮"));

        /**
         * checkbox 选中就修改textview
         */
        RxCompoundButton.checkedChanges(mBinding.checkbox)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        mBinding.tvCb.setText(aBoolean ? "按钮选中了" : "按钮未选中");
                    }
                });

        /**
         * 注册登录等情况下，所有输入都合法再点亮登录按钮
         */
        Observable<CharSequence> name = RxTextView.textChanges(mBinding.etName).skip(1);
        Observable<CharSequence> age = RxTextView.textChanges(mBinding.etAge).skip(1);

        Observable.combineLatest(name, age, new BiFunction<CharSequence, CharSequence, Boolean>() {
            @Override
            public Boolean apply(CharSequence charSequence, CharSequence charSequence2) throws Exception {

                boolean isNameEmpty = TextUtils.isEmpty(mBinding.etName.getText());
                boolean isAgeEmpty = TextUtils.isEmpty(mBinding.etAge.getText());

                return !isNameEmpty && !isAgeEmpty;
            }
        })
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        System.out.println("bt======" + aBoolean);
                        mBinding.btSubmit.setEnabled(aBoolean);
                    }
                });


        /**
         * 统计点击次数
         */

        /**
         * 动态权限
         */

        /**
         * 统计点击次数
         */


    }


    /**
     * 倒计时操作
     */
    public void clickTimer(View view) {

        // 2 秒后发送数据
        Observable.timer(2, TimeUnit.SECONDS)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long value) {
                        System.out.println("binding=======value:" + value);//0
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        //倒计时操作
        final int count = 10;
        Observable.interval(0, 1, TimeUnit.SECONDS)//设置0延迟，每隔一秒发送一条数据
                .take(count + 1)//设置循环次数
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(Long aLong) throws Exception {

                        return count - aLong;
                    }
                })
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        //在发送数据的时候设置为不能点击
                        mBinding.btCutdown.setEnabled(false);

                        //背景色
                        mBinding.btCutdown.setBackgroundColor(Color.parseColor("#39c6c1"));
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long value) {
                        mBinding.btCutdown.setText("" + value);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        mBinding.btCutdown.setText("重新获取");
                        mBinding.btCutdown.setEnabled(true);
                        mBinding.btCutdown.setBackgroundColor(Color.parseColor("#d1d1d1"));
                    }
                });

    }

    /**
     * 每隔2秒 输出一次日志
     * 每隔2秒产生一个数字，0开始 递增
     */
    Disposable mDisposable;

    public void clickIntervar(View view) {

        Observable.interval(2, TimeUnit.SECONDS)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;

                    }

                    @Override
                    public void onNext(Long value) {
                        System.out.println("binding=======输出日志:" + value);
                        if (value == 5L) {
                            System.out.println("binding=======dispose");
                            mDisposable.dispose();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 使用schedulePeriodically做轮询请求
     */
    public void clickHttp(View view) {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(final ObservableEmitter<String> e) throws Exception {

                Schedulers.newThread().createWorker()
                        .schedulePeriodically(new Runnable() {
                            @Override
                            public void run() {
                                e.onNext("net work-----");
                            }
                        }, 0, 3, TimeUnit.SECONDS);

            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println("binding=======net work");
            }
        });

    }

    /**
     * 条件轮询
     */
    int count;

    public void clickRepeat(View view) {

        Observable.just(100)
                .repeatWhen(new Function<Observable<Object>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Observable<Object> objectObservable) throws Exception {

                        // 将原始 Observable 停止发送事件的标识（Complete（） /  Error（））转换成1个 Object 类型数据传递给1个新被观察者（Observable）
                        // 以此决定是否重新订阅 & 发送原来的 Observable，即轮询
                        return objectObservable.flatMap(new Function<Object, ObservableSource<?>>() {
                            @Override
                            public ObservableSource<?> apply(Object o) throws Exception {
                                if (count > 4) {
                                    return Observable.error(new Throwable("轮询完成"));
                                }

                                return Observable.just(1)
                                        .delay(2, TimeUnit.SECONDS);
                            }
                        });
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer value) {
                        System.out.println("repeat=====" + count);
                        count++;
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("repeat====err=" + e.toString());
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("repeat=====complete");
                    }
                });

    }

    /**
     * 网络错误重试
     * 这里just操作符 改为retrofit 网络请求返回的即可。
     */
    int mRetryCount;

    public void clickRetry(View view) {
        Observable.just("retry")
                .retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Observable<Throwable> throwableObservable) throws Exception {

                        // 参数Observable<Throwable>中的泛型 = 上游操作符抛出的异常，可通过该条件来判断异常的类型
                        return throwableObservable.flatMap(new Function<Throwable, ObservableSource<?>>() {
                            @Override
                            public ObservableSource<?> apply(Throwable throwable) throws Exception {

                                // 判断异常信息  根据异常信息判断是否需要重试
                                if (throwable instanceof IOException) {
                                    System.out.println("retry======y==");
                                    // 重试
                                    // 判断重试次数 这里设置最多重试5次
                                    if (mRetryCount < 5) {
                                        mRetryCount++;
                                        /**
                                         * 1、通过返回的Observable发送的事件 = Next事件，从而使得retryWhen（）重订阅，最终实现重试功能
                                         * 2、延迟1段时间再重试  采用delay操作符 = 延迟一段时间发送，以实现重试间隔设置
                                         * 3、在delay操作符的等待时间内设置 = 每重试1次，增多延迟重试时间1s
                                         */
                                        int time = 1000 + mRetryCount * 1000;
                                        return Observable.just(1).delay(time, TimeUnit.MILLISECONDS);
                                    } else {
                                        System.out.println("retry======5==");
                                        return Observable.error(new Throwable("已重试5次 放弃治疗"));
                                    }

                                } else {
                                    // 不重试
                                    System.out.println("retry======n==");
                                    return Observable.error(new Throwable("发生了非网络异常（非I/O异常）"));
                                }
                            }
                        });
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String value) {
                        System.out.println("retry======suc==" + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("retry======err==" + e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    /**
     * 优化网络嵌套请求问题
     * 一下为了方便演示 写的伪代码
     */
    public void clickRequest(View view) {
        Observable<String> requestLogin = Observable.just("requestLogin");
        final Observable<String> request2 = Observable.just("request2");

        requestLogin.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        System.out.println("flat=======loginsuccess");
                    }
                })
                .observeOn(Schedulers.io())
                .flatMap(new Function<String, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(String s) throws Exception {
                        // 将网络请求1转换成网络请求2，即发送网络请求2
                        return request2;
                    }
                })
                // （新被观察者，同时也是新观察者）切换到IO线程去发起登录请求
                //  特别注意：因为flatMap是对初始被观察者作变换，所以对于旧被观察者，它是新观察者，所以通过observeOn切换线程
                // 但对于初始观察者，它则是新的被观察者
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        System.out.println("flat=======第二次请求成功");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        System.out.println("flat=======loginerr");
                    }
                });
    }

    /**
     * 背压 Flowable g观察者使用
     * 解决发送和订阅事件 流速不一致的问题
     * <p>
     * 注意：同步订阅中，被观察者 & 观察者工作于同1线程，同步订阅关系中没有缓存区。
     * 被观察者在发送1个事件后，必须等待观察者接收后，才能继续发下1个事件.若Subscription.request没有设置，
     * 观察者接收不到事件，会抛出MissingBackpressureException异常。
     */
    Subscription mSubscription;

    public void clickFlow(View view) {

        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> e) throws Exception {

                /**
                 * 同步订阅：
                 * 同步订阅的情况下，调用e.requested()方法，获取当前观察者需要接收的事件数量.
                 * 根据当前观察者需要接收的事件数量来发送事件
                 *
                 * 异步订阅：
                 * 由于二者处于不同线程，所以被观察者 无法通过 FlowableEmitter.requested()知道观察者自身接收事件能力。
                 * 异步的反向控制：
                 */
                long count = e.requested();
                System.out.println("flowable======需要接收的事件数量=" + count);

                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onNext(4);
                e.onNext(5);
                e.onComplete();
            }
        }, BackpressureStrategy.ERROR)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {

                        // 作用：决定观察者能够接收多少个事件,多出的事件放入缓存区.若不设置，则不接收事件.
                        // 不过被观察者仍然在发送事件（存放在缓存区，大小为128），等观察者需要时 再取出被观察者事件（比如点击事件里）.
                        // 但是 当缓存区满时  就会溢出报错
                        // 官方默认推荐使用Long.MAX_VALUE，即s.request(Long.MAX_VALUE);
                        mSubscription = s;
                        s.request(2);
                        // s.request(1); // 同步订阅 观察者连续要求接收事件的话，被观察者e.requested() 返回3
                    }

                    @Override
                    public void onNext(Integer integer) {

                        System.out.println("flowable=======" + integer);
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 动态权限
     * 这里自己根据需求封装一下就行
     */
    public void clickPermission(View view) {
        RxPermissions permissions = new RxPermissions(this);
        RxView.clicks(mBinding.btPermission)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .compose(permissions.ensure(Manifest.permission.CAMERA))
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            System.out.println("binding=======允许");
                        } else {
                            System.out.println("binding=======拒绝");
                        }
                    }
                });
    }

}

