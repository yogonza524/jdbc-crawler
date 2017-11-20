package util;

import com.beust.jcommander.Parameter;

public class Arguments {

	public static class TypeArg {

		  @Parameter(names = { "-t", "-type" }, description = "Type of persistence (postgresql or file", required = true)
		  public String type = "postgresql";
		  
		  @Parameter(names = { "-u", "-useraname"}, description = "Username for JDBC connection", required = true)
		  public String username;

		  @Parameter(names = {"-p", "-password"}, description = "Password for JDBC connection", required = true)
		  public String password;
		  
		  @Parameter(names = "-port", description = "Port for JDBC connection", required = true)
		  public String port;
		  
		  @Parameter(names = { "-h", "-host"}, description = "Host for JDBC connection", required = true)
		  public String host;
		  
		  @Parameter(names = { "-d", "-db", "-database"}, description = "Database name for JDBC connection", required = true)
		  public String db;
		  
		  @Parameter(names = { "-cr"}, description = "flag for crawl the website or not", required = false)
		  public boolean crawl;
		  
		  @Parameter(names = { "-f", "-folder", "-path"}, description = "Absolute path folder to persist the data", required = false)
		  public String folder;
		  
		  @Parameter(names = { "-l", "-lv", "-level"}, description = "Deph crawling process", required = false)
		  public int level;
		  
		  @Parameter(names = { "-w", "-web", "-url"}, description = "URL for crawl process", required = true)
		  public String url;
		  
		  @Parameter(names = { "-tag", "-tag-html"}, description = "URL for crawl process", required = true)
		  public String tagContainer;
	}
}
