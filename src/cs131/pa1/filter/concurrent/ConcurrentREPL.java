package cs131.pa1.filter.concurrent;

import cs131.pa1.filter.Message;
import java.util.Scanner;

public class ConcurrentREPL {

	static String currentWorkingDirectory;
	
	public static void main(String[] args){
		currentWorkingDirectory = System.getProperty("user.dir");
		Scanner s = new Scanner(System.in);
		System.out.print(Message.WELCOME);
		String command;
		Thread threader= null;
		while(true) {
			//obtaining the command from the user
			System.out.print(Message.NEWCOMMAND);
			command = s.nextLine();
			if(command.equals("exit")) {
				break;
			} else if(!command.trim().equals("")) {
				//building the filters list from the command
				ConcurrentFilter filterlist = ConcurrentCommandBuilder.createFiltersFromCommand(command);
				while(filterlist != null) {
					threader = new Thread (filterlist);
					if(filterlist.getNext()==(null)) {
						try {
							threader.start();
							threader.join();
							filterlist = (ConcurrentFilter) filterlist.getNext();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}else {
						threader.start();
						filterlist = (ConcurrentFilter) filterlist.getNext();
					}
					
				}
			}
		}
		s.close();
		System.out.print(Message.GOODBYE);
	}

}
