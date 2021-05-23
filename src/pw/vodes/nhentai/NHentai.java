package pw.vodes.nhentai;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import pw.vodes.nhentai.connection.Connection;
import pw.vodes.nhentai.connection.Response;
import pw.vodes.nhentai.types.BareComic;
import pw.vodes.nhentai.types.Comic;

public class NHentai {
	
	/**
	 * Get Comic List for given Query and specific page if chosen
	 * @param query
	 * @param page
	 * @param sorted_popular Gets most recent if false
	 * @return ArrayList<Comic>
	 */
	public static ArrayList<Comic> getComicList(String query, int page, boolean sorted_popular){
		ArrayList<Comic> comics = new ArrayList<Comic>();
        Gson gson = new Gson();
		String url = Finals.QUERY_PREFIX + query + (page > 1 ? "&page=" + page : "") + (sorted_popular ? "&sort=popular" : "");
		Response response = Connection.getResponse(url);
		if(response.getCode() == 200) {
			JsonArray result = new JsonParser().parse(response.getMessage()).getAsJsonObject().get("result").getAsJsonArray();
            for (JsonElement jsonElement : result) {
            	try {
                    Comic c = new Comic(gson.fromJson(jsonElement, BareComic.class).setJSONString(jsonElement.toString()));
                    comics.add(c);
            	} catch(Exception ex) {
            		ex.printStackTrace();
            	}

            }
		}
		return comics;
	}
	
	/**
	 * Gets a comic by ID
	 * @param ID
	 * @return Comic
	 */
	public static Comic getComic(int ID) {
        Gson gson = new Gson();
		try {
			String url = Finals.COMIC_PREFIX + ID;
			Response response = Connection.getResponse(url);
			if(response.getCode() == 200) {
                JsonObject json = new JsonParser().parse(response.getMessage()).getAsJsonObject();
                return new Comic(gson.fromJson(json, BareComic.class).setJSONString(response.getMessage()));
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Gets a random comic from the given query out of 5 random pages
	 * @param query
	 * @return Comic
	 */
	public static Comic getRandomComic(String query) {
		ArrayList<Comic> comics = new ArrayList<Comic>();
        Gson gson = new Gson();
		String url = Finals.QUERY_PREFIX + query;
		Response response = Connection.getResponse(url);
		Random rand = new Random();
		if(response.getCode() == 200) {
            JsonObject json = new JsonParser().parse(response.getMessage()).getAsJsonObject();
            int pages = json.get("num_pages").getAsInt();
            
    		if(pages > 5) {
    			for(int x = 1; x < 5; x++) {
    				int page = rand.nextInt(pages) + 1;
    				String pageJson = Connection.getResponse(url + "&page=" + page).getMessage();
    				JsonArray result = new JsonParser().parse(pageJson).getAsJsonObject().get("result").getAsJsonArray();
                    
                    for (JsonElement jsonElement : result) {
                        Comic c = new Comic(gson.fromJson(jsonElement, BareComic.class).setJSONString(jsonElement.toString()));
                        comics.add(c);
                    }
    			}
    		} else {
    			JsonArray startPage = new JsonParser().parse(response.getMessage()).getAsJsonObject().get("result").getAsJsonArray();
                for (JsonElement jsonElement : startPage) {
                    Comic c = new Comic(gson.fromJson(jsonElement, BareComic.class).setJSONString(jsonElement.toString()));
                    comics.add(c);
                }
                if(pages > 1) {
        			for(int x = 2; x < pages; x++) {
        				int page = x;
        				String pageJson = Connection.getResponse(url + "&page=" + page).getMessage();
        				JsonArray result = new JsonParser().parse(pageJson).getAsJsonObject().get("result").getAsJsonArray();
                        
                        for (JsonElement jsonElement : result) {
                            Comic c = new Comic(gson.fromJson(jsonElement, BareComic.class).setJSONString(jsonElement.toString()));
                            comics.add(c);
                        }
        			}
                }
    		}
    		return comics.get(rand.nextInt(comics.size()));
		}
		return null;
	}
	
	/**
	 * Parses a query from a message like:<p>
	 * lang:en yuri "-big breasts"<p>
	 * which would be language=english with the yuri tag and without the "big breasts" tag
	 * @param tokenizer
	 * @return correctly formatted String to insert for a search query
	 */
	public static String parseQuery(CustomTokenizer tokenizer) {
		String languageQuery = "";
		ArrayList<String> tags = new ArrayList<String>();
		String current = "";
		while(!(current = tokenizer.nextToken()).isEmpty()) {
			if(StringUtils.startsWithIgnoreCase(current, "l:") || StringUtils.startsWithIgnoreCase(current, "lang:") || StringUtils.startsWithIgnoreCase(current, "language:")) {
				String lang = current.split(":")[1];
				if(lang.equalsIgnoreCase("en") || lang.equalsIgnoreCase("eng") || lang.equalsIgnoreCase("english")) {
					languageQuery = "english";
				} else if(lang.equalsIgnoreCase("cn") || lang.equalsIgnoreCase("chinese")) {
					languageQuery = "chinese";
				} else if(lang.equalsIgnoreCase("jp") || lang.equalsIgnoreCase("jpn") || lang.equalsIgnoreCase("japanese")) {
					languageQuery = "japanese";
				}
			} else {
				if(!current.isEmpty()) {
					tags.add(current);
				}
			}
		}
		
		String finalQuery = languageQuery.isEmpty() ? "" : "language:" + languageQuery + (tags.isEmpty() ? "" : "+");
		for(String tag : tags) {
			finalQuery += tag.startsWith("-") ? ("-\"" + tag + "\"+") : ("\"" + tag + "\"+"); 
		}
		return StringUtils.removeEnd(finalQuery, "+");
	}
	
	/**
	 * Simple Tokenizer that creates tokens split by spaces but keeps quoted strings intact
	 */
	public static class CustomTokenizer {

	    private int current = 0;
	    private List<String> tokens = new ArrayList<String>();

	    public CustomTokenizer(String input){
	        Matcher m = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(input);
	        while (m.find()){
	            tokens.add(m.group(1).replace("\"", ""));
	        }
	    }

	    public String nextToken(){
	        current++;
	        try {
	            return tokens.get(current - 1);
	        } catch(Exception ex) {
	            return "";
	        }
	    }
	}

}
