package crawler;

import java.util.Set;

import org.apache.commons.cli.ParseException;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.soulgalore.crawler.core.Crawler;
import com.soulgalore.crawler.core.CrawlerResult;
import com.soulgalore.crawler.core.PageURL;
import com.soulgalore.crawler.guice.CrawlModule;
import com.soulgalore.crawler.run.AbstractCrawl;

public class JCrawler extends AbstractCrawl{

	public JCrawler(String[] args) throws ParseException {
		super(args);
	}
	
	public Set<PageURL> crawl() {
	    final Injector injector = Guice.createInjector(new CrawlModule());
	    final Crawler crawler = injector.getInstance(Crawler.class);
	    
	    final CrawlerResult result = crawler.getUrls(getConfiguration());
	    
	    return result.getUrls();
	}

}
