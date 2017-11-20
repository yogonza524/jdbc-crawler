package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.cli.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.beust.jcommander.JCommander;
import com.soulgalore.crawler.core.PageURL;

import config.Config;
import crawler.JCrawler;
import hibernate.Repository;
import model.Content;
import util.Arguments;
import util.Arguments.TypeArg;

public class App {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//Process args
		Arguments.TypeArg params = new Arguments.TypeArg();
				
		try {
			JCommander.newBuilder()
		        .addObject(params)
			    .build()
			    .parse(args);
		} catch (com.beust.jcommander.ParameterException e) {
			System.out.println("Error of parameters. " + e.getMessage());
			System.exit(1);
		}
		
		checkParams(params);
		
		configParams(params);
		
		//Crawl process
		Set<PageURL> pages = crawlWeb(params);
		
		download(pages);
	}

	private static Set<PageURL> crawlWeb(Arguments.TypeArg params) {

		JCrawler cr = null;
		
		try {
			cr = new JCrawler(new String[] {
					"-u", params.url,
					"-l", params.level > 0 ? params.level + "" : "3"
			});
			
			Set<PageURL> pages = cr.crawl();
			
			if(pages == null) {
				System.out.println("URL not crawled! Not pages found");
				System.exit(1);
			}
			
			if (pages.isEmpty()) {
				System.out.println("Empty pages! Not pages found");
				System.exit(1);
			}
			
			return pages;
			
		} catch (ParseException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		return null;
	}

	private static void configParams(TypeArg params) {
		Config.get().put("dbadmin", "postgresql");
		Config.get().put("username", params.username);
		Config.get().put("password", params.password);
		Config.get().put("dialect", "PostgreSQLDialect");
		Config.get().put("driver", "org.postgresql.Driver");
		Config.get().put("host", params.host);
		Config.get().put("port", params.port);
		Config.get().put("database", params.db);
		Config.get().put("url", params.url);
		Config.get().put("tagContent", params.tagContainer);
	}

	private static void download(Set<PageURL> pages) {
		List<Content> contents = new ArrayList<Content>();
		
		for(PageURL page : pages) {
			Document d = get(page.getUrl());
			
			if (d != null) {
				persist(page.getUrl(), d);
			}
			else {
				System.out.println("URL " + page.getUrl() + " failed to get!");
			}
		}
	}
	
	private static void persist(String url,Document d) {
		Content c = new Content();
		c.setContent(d.select(Config.get().get("tagContent")).outerHtml());
		c.setUrl(url);
		
		Repository<Content> rc = new Repository<Content>(Content.class);
		
		if (rc.save(c)) {
			System.out.println("Content saved! ID: " + c.getId());
		}
		else {
			System.out.println("Content not saved! ID: " + c.getId());
		}
	}

	private static Document get(String url) {
		try {
			return Jsoup.connect(url)
				      .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
				      .referrer("http://www.google.com")
				      .validateTLSCertificates(false)
				      .get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private static void checkParams(TypeArg params) {
		String urlRegex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
		
		if (!params.url.matches(urlRegex)) {
			System.out.println("URL malformed!" + params.url);
			System.exit(1);
		}
		
		if (!params.type.equalsIgnoreCase("postgresql")) {
			System.out.println("Is not valid another type than 'postgresql'");
			System.exit(1);
		}
	}

}
