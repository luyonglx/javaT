package com.luyong.java.likejava;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Test;

import com.luyong.java.likejava.pojo.Users;

public class CallableTest {
	/**
	 * Callable要采用ExecutorService的submit方法提交，返回的future对象可以取消任务
	 */
	@Test
	public void testOrg1(){
	  ExecutorService threadPool = Executors.newSingleThreadExecutor();  
      // submit是提交以便返回结果  
      Future<String> future = threadPool.submit(new Callable<String>() {  
          public String call() throws Exception {  
              Thread.sleep(2000);  
              return "hello";  
          };  
      });  
      System.out.println("等待结果"); 
      try {  
          System.out.println("拿到结果：" + future.get());  
      } catch (InterruptedException e) {  
          // TODO Auto-generated catch block  
          e.printStackTrace();  
      } catch (ExecutionException e) {  
          // TODO Auto-generated catch block  
          e.printStackTrace();  
      }  
	}
	@Test
	public void testMe1(){
	  ExecutorService threadPool = Executors.newSingleThreadExecutor();  
      // submit是提交以便返回结果 
      //返回对象
      Future<Users> future1=threadPool.submit(new Callable<Users>(){
    	  public Users call() {
    		  Users users=new Users();
    		  users.setUserId(1);
    		  users.setUserName("luyong1");
			  return users;
		}
      });   	  
      System.out.println("等待结果"); 
      try {  
          System.out.println("拿到结果：" + future1.get().getUserName());  
      } catch (InterruptedException e) {  
          // TODO Auto-generated catch block  
          e.printStackTrace();  
      } catch (ExecutionException e) {  
          // TODO Auto-generated catch block  
          e.printStackTrace();  
      }  
	}
	
	//执行多个带返回值的任务，并取得多个返回值
	@Test
	public void testOrg2(){
		//方式2 CompletionService用于提交一组Callable任务，其take方法返回已完成的一个Callable任务对应的Future对象  
		//好比我同时种了几块地的麦子，然后就等待收割。收割时，则是那块先成熟了，则先去收割哪块麦子。  
		ExecutorService threadPool2 = Executors.newFixedThreadPool(10);  
		CompletionService<Integer> compeletionService = new ExecutorCompletionService<Integer>(  
		        threadPool2);  
		for (int i = 0; i <= 10; i++) {  
		    final int seq = i;  
		    compeletionService.submit(new Callable<Integer>() {  
		        @Override  
		        public Integer call() throws Exception {  
		            Thread.sleep(new Random().nextInt(5000));  
		            return seq;  
		        }  
		    });  
		}  
		for (int i = 0; i < 10; i++) {  
		    try {  
		        //产生线程，返回结果再把结果拿回来  
		        System.out.println(compeletionService.take().get());  
		    } catch (InterruptedException e) {  
		        // TODO Auto-generated catch block  
		        e.printStackTrace();  
		    } catch (ExecutionException e) {  
		        // TODO Auto-generated catch block  
		        e.printStackTrace();  
		    }  
		}  
	}

}
