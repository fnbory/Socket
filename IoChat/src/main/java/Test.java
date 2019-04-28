import java.util.ArrayList;
import java.util.List;

public class Test implements Runnable{
	
	@Override
	public void run() {
		while(true){
			if(list.size()!=0) {
				System.out.println("haha");
			}
		}
	}
	
	public static int i=0;
	private static List<Integer> list;
	public Test() {
		list=new ArrayList<>();
	}
	public static void main(String[] args) throws InterruptedException {
		
		new Thread(new Test()).start();
		i++;
		list.add(i);
		i++;
		list.add(i);
		Thread.sleep(1000000);
	}
}
