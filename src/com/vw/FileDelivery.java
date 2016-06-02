package com.vw;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class FileDelivery {
	public static Map<String,Host> hostMap = new HashMap<String,Host>();
	public static Map<String,Link> links = new HashMap<String, Link>(); 


	public static void addLink(String sourceHost, String targetHost, String protocol){
		if(sourceHost.isEmpty() || targetHost.isEmpty()){
			System.out.println("Hosts cannot be empty ");
			return;
		}
		if(sourceHost.equalsIgnoreCase(targetHost)){
			System.out.println(" Source and Target hosts should be different");
			return;
		}
		if(!hostMap.containsKey(sourceHost)){
			hostMap.put(sourceHost,new Host(sourceHost));
		}
		if(!hostMap.containsKey(targetHost)){
			hostMap.put(targetHost,new Host(targetHost));
		}
		Host source = hostMap.get(sourceHost);
		Host target =  hostMap.get(targetHost);
		Link flink = new Link(source, target, protocol);
		Link rlink = new Link(target, source, protocol);
		source.addLink(flink);
		target.addLink(rlink);

		links.put(source+"_"+target, flink);
	}


	public static void computePaths(Host source) {
		
		resetPaths();
		
		source.setPathLength(0);
		PriorityQueue<Host> hostQueue = new PriorityQueue<Host>();
		hostQueue.add(source);

		while (!hostQueue.isEmpty()) {
			Host currentHost = hostQueue.poll();
			for (Link l : currentHost.getLinks()) {
				Host target = l.target;
				double weight = l.pathWeight;
				double distanceThroughU = currentHost.getPathLength() + weight;
				if (distanceThroughU < target.getPathLength()) {
					hostQueue.remove(target);
					target.setPathLength(distanceThroughU) ;
					target.previous = currentHost;
					hostQueue.add(target);
				}
			}
		}
	}


	public static void resetPaths() {
		for(String host: hostMap.keySet()){
			hostMap.get(host).setPathLength(Double.POSITIVE_INFINITY);
			hostMap.get(host).setPrevious(null); 
		}
	}

	public static List<Host> getShortestPathTo(Host target) {
		List<Host> path = new ArrayList<Host>();
		for (Host host = target; host != null; host = host.previous) {
			path.add(host);
		}
		Collections.reverse(path);
		return path;
	}
	
	
	public static void generatePath(String source, String target){
		List<Host> path = null;
		if(hostMap.containsKey(source) && hostMap.containsKey(target)){
			computePaths(hostMap.get(source));
			path = getShortestPathTo(hostMap.get(target));
		}
		if(path != null && path.size() >1){
			printPath(path,links);
		}else{
			System.out.println("########### PATH ###########");
			System.out.println(source+" None "+target);
			System.out.println("############################");
			
		}
	}
	
	
	private static void printPath(List<Host> path, Map<String,Link> links) {
		Host previous = null;
		StringBuffer sb = new StringBuffer();
		for(Host host : path){
			if(previous != null ){
				Link l = links.get(previous.getName()+"_"+host.getName());
				if(l == null){
					l = links.get(host.getName()+"_"+previous.getName());
				}
				sb.append(" ").append(l.protocol);
				
			}
			
			sb.append(" ").append(host);
			previous = host;
		}
		System.out.println("########### PATH ###########");
		System.out.println(sb.toString().trim());
		System.out.println("############################");
	}

	public static void printHosts(){
		System.out.println(" **** LIST OF HOSTS ***");
		for(String host: hostMap.keySet()){
			System.out.println(host);
			
		}
		System.out.println(" **** END ***");
	}
	
	public static void printLinks(){
		System.out.println(" **** LIST OF LINKS ***");
		for(String key: links.keySet()){
			System.out.println(links.get(key));
			
		}
		System.out.println(" **** END ***");
	}
}

