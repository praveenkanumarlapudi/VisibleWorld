package com.vw;

public class Link {
	public  Host target;
	public  double pathWeight;
	public  String protocol;
	public  Host source;

	public Link(Host source,Host target, String protocol){
		this.source = source;
		this.target = target;
		this.pathWeight = 1;
		this.protocol = protocol;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return source+" "+ protocol +" "+target;
	}
}