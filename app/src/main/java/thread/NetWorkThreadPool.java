package thread;

import com.fanqi.succulent.network.RetrofitExecutor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NetWorkThreadPool {


    private ExecutorService mThreadPool;
    private RetrofitExecutor mRequester;
    private Object[] mTasksParameter;


    public NetWorkThreadPool(RetrofitExecutor requester) {
        mThreadPool = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors());
        mRequester = requester;
    }

    public void addTask(Runnable runable) {
        mThreadPool.submit(runable);
    }

    //第1次进入时的网络请求，参数是将被调用的网络请求方法名
    public void addFirstEnterTasks(Object[] tasksParameter) {
        mTasksParameter = tasksParameter;
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mRequester.requestGets((String) mTasksParameter[0]);
            }
        };
        addTask(runnable);
    }

    //第1次进入时的网络请求，参数是将被调用的网络请求方法名
    public void addPullPageTasks(final Object[] tasksParameter) {
        mTasksParameter = tasksParameter;
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mRequester.requestGets((String) mTasksParameter[0],mTasksParameter[1]);
            }
        };
        addTask(runnable);
    }

    public ExecutorService getThreadPool() {
        return mThreadPool;
    }


}
