package com.stswalkoo;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.stswalkoo.StswalkooApplication;

@SpringBootApplication
public class StswalkooApplication {

	public static void main(String[] args) {
		SpringApplication.run(StswalkooApplication.class, args);
		InetAddress local;
		try {
			local = InetAddress.getLocalHost();
			String ip = local.getHostAddress();
			System.out.println("local ip : "+ip);
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
	}

}
