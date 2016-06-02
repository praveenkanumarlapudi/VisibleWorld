package com.vw;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileDeliveryDriver {
	Scanner in = new Scanner(System.in);

	public static void main(String[] args) {
		BufferedReader br = null;

		if(args.length == 0){
			System.err.println("Error :: Usage -> atleast one Argument <Absolute path of INPUT HOST LIST REFERENCE FILE.psv>");
			System.exit(0);
		}
		if(args.length > 1){
			System.err.println("Error :: Usage -> Only one Argument <Absolute path of INPUT HOST LIST REFERENCE FILE.psv>");
			System.exit(0);
		}

		File hostListFile = new File(args[0]);
		if(hostListFile.exists()){
			try {

				br = new BufferedReader(new FileReader(hostListFile));
				String inputRecord;
				String[] inputTokens;
				inputRecord = br.readLine();

				while(inputRecord != null){
					inputTokens = inputRecord.trim().split("[|]");		
					if(inputTokens.length == 3){
						FileDelivery.addLink(inputTokens[0], inputTokens[2], inputTokens[1]);
						
						
						inputRecord = br.readLine();
					}
				}	
				new FileDeliveryDriver().printOptions();
			} catch (FileNotFoundException e) {
				System.err.println("Host File Does not Exist in the mentioned path ::"+args[0]);
			}catch (IOException e) {
			}finally{
				if(br != null){
					try {
						br.close();
					} catch (IOException e) {
					}
				}
			}
		}else{
			System.err.println("Host File Does not Exist in the mentioned path ::"+args[0]);
			System.exit(0);

		}
	}

	public void printOptions(){
		boolean  stopExecution = Boolean.FALSE;
		while(!stopExecution){
			stopExecution = scanInput();
		}
		if(in != null){
			in.close();
		}
	}

	private boolean scanInput() {
		System.out.println("Please select one of the following: ");
		System.out.println("1 - List Hosts ");
		System.out.println("2 - List Links ");
		System.out.println("3 - Add a Link ");
		System.out.println("4 - Find a Path ");
		System.out.println("5 - Quit ");
		System.out.println("Please enter option number : ");

		try{
			int option = in.nextInt();
			if(option == 5){
				return Boolean.TRUE;
			}else{
				if(option == 1){
					FileDelivery.printHosts();
				}else if(option == 2){
					FileDelivery.printLinks();
				}else if(option == 3){
					System.out.println("please provide the following\n ");
					System.out.println("Source Host : ");
					String source = in.next();
					System.out.println("Transfer Protocol : ");
					String protocol = in.next();
					System.out.println("Target Host : ");
					String target = in.next();
					FileDelivery.addLink(source, target, protocol);

				}else if(option == 4){
					System.out.println("please provide the following\n ");
					System.out.println("Source Host : ");
					String source = in.next();
					System.out.println("Target Host : ");
					String target = in.next();
					FileDelivery.generatePath(source, target);;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return Boolean.FALSE;
	}

}
