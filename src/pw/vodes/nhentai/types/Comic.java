package pw.vodes.nhentai.types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.annotations.JsonAdapter;

import pw.vodes.nhentai.Finals;

@SuppressWarnings("unused")
public class Comic {
	
	private String pretty, english, japanese;
	
	private int id, mediaID, numPages, favourites;
	
	private ArrayList<String> tags, authors, characters, parodies, languages;
	
	private ArrayList<Page> pages;
	
	public String json;
	
	/**
	 *  Parse a BareComic to a better-to-use Comic class
	 */
	public Comic(BareComic bc) {
		tags = new ArrayList<>();
		authors = new ArrayList<>();
		characters = new ArrayList<>();
		parodies = new ArrayList<>();
		languages = new ArrayList<>();
		pages = new ArrayList<Comic.Page>();
		this.id = bc.id;
		this.json = bc.json;
		this.mediaID = bc.media_id;
		this.pretty = bc.title.pretty;
		this.english = bc.title.english;
		this.japanese = bc.title.japanese;
		this.numPages = bc.pages;
		this.favourites = bc.favourites;
		// Add Tags to respective lists
		for(BareComic.Tag tag : bc.tags) {
    		if(tag.type.trim().equalsIgnoreCase("language")) {
    			if(tag.name.trim().equalsIgnoreCase("translated"))
    				continue;
    			
    			languages.add(StringUtils.capitalize(tag.name.trim()));
    		} else if(tag.type.equalsIgnoreCase("parody")) {
    			parodies.add(tag.name.trim());
    		} else if(tag.type.equalsIgnoreCase("character")) {
    			characters.add(tag.name.trim());
    		} else if(tag.type.equalsIgnoreCase("artist")) {
    			authors.add(tag.name.trim());
    		} else if(tag.type.equalsIgnoreCase("tag")){
    			tags.add(tag.name.trim());
    		}
		}
		
		// Parse Pages
		JsonElement element = new JsonParser().parse(bc.json).getAsJsonObject().get("images");
		JsonArray array = element.getAsJsonObject().get("pages").getAsJsonArray();
		int pageID = 1;
		for(JsonElement ele : array) {
			JsonObject obj = ele.getAsJsonObject();
			Page page = new Page();
			page.setUrl(String.format(Finals.MEDIA_PREFIX_URL + "%s/%s.%s", "" + mediaID, "" + pageID, obj.get("t").getAsString().trim().equalsIgnoreCase("j") ? "jpg" : "png"));
			page.setWidth(obj.get("w").getAsInt());
			page.setHeight(obj.get("h").getAsInt());
			pages.add(page);
			pageID++;
		}
	}
	
	public Page getPage(int page) {
		// Start from 1
		// Just for convenience
		try {
			return getPages().get(page - 1);
		} catch(Exception ex) {
			return getPages().get(0);
		}
	}
	
	public String getPretty() {
		return pretty;
	}

	public String getEnglish() {
		return english;
	}

	public String getJapanese() {
		return japanese;
	}

	public int getId() {
		return id;
	}

	public int getMediaID() {
		return mediaID;
	}

	public int getNumPages() {
		return numPages;
	}

	public int getFavourites() {
		return favourites;
	}

	public ArrayList<String> getTags() {
		return tags;
	}

	public ArrayList<String> getAuthors() {
		return authors;
	}

	public ArrayList<String> getCharacters() {
		return characters;
	}

	public ArrayList<String> getParodies() {
		return parodies;
	}

	public ArrayList<String> getLanguages() {
		return languages;
	}

	public ArrayList<Page> getPages() {
		return pages;
	}

	public class Page {
		private String url;
		private int width, height;

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public int getWidth() {
			return width;
		}

		public void setWidth(int width) {
			this.width = width;
		}

		public int getHeight() {
			return height;
		}

		public void setHeight(int height) {
			this.height = height;
		}
	}

}
