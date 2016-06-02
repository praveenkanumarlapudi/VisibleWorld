package com.vw;

import java.util.ArrayList;
import java.util.List;

public class Host implements Comparable<Host>
{
    public  String name;
    public List<Link> links = new ArrayList<Link>();
    public List<Link> getLinks() {
		return links;
	}
	public void setLinks(List<Link> links) {
		this.links = links;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public void addLink(Link link) {
		links.add(link);
	}
	
	public Host getPrevious() {
		return previous;
	}
	public void setPrevious(Host previous) {
		this.previous = previous;
	}
	public double pathLength = Double.POSITIVE_INFINITY;
    public double getPathLength() {
		return pathLength;
	}
	public void setPathLength(double pathLength) {
		this.pathLength = pathLength;
	}
	public Host previous;
    public Host(String name) {
    	this.name = name; 
    }
    public String toString() { 
    	return name; 
    }
    
    public int compareTo(Host other) {
        return Double.compare(pathLength, other.pathLength);
    }
}
