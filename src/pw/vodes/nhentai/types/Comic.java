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
	 * Parse a BareComic to a better-to-use Comic class
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
		for (BareComic.Tag tag : bc.tags) {
			if (tag.type.trim().equalsIgnoreCase("language")) {
				if (tag.name.trim().equalsIgnoreCase("translated"))
					continue;

				languages.add(StringUtils.capitalize(tag.name.trim()));
			} else if (tag.type.equalsIgnoreCase("parody")) {
				parodies.add(tag.name.trim());
			} else if (tag.type.equalsIgnoreCase("character")) {
				characters.add(tag.name.trim());
			} else if (tag.type.equalsIgnoreCase("artist")) {
				authors.add(tag.name.trim());
			} else if (tag.type.equalsIgnoreCase("tag")) {
				tags.add(tag.name.trim());
			}
		}

		// Parse Pages
		JsonElement element = JsonParser.parseString(bc.json).getAsJsonObject().get("images");
		JsonArray array = element.getAsJsonObject().get("pages").getAsJsonArray();
		int pageID = 1;
		for (JsonElement ele : array) {
			JsonObject obj = ele.getAsJsonObject();
			Page page = new Page();
			String imgType = obj.get("t").getAsString().trim();
			String extension = "png";
			if (imgType.equalsIgnoreCase("j")) {
				extension = "jpg";
			} else if (imgType.equalsIgnoreCase("g")) {
				extension = "gif";
			}
			page.setUrl(String.format(Finals.MEDIA_PREFIX_URL + "%s/%s.%s", "" + mediaID, "" + pageID, extension));
			page.setWidth(obj.get("w").getAsInt());
			page.setHeight(obj.get("h").getAsInt());
			pages.add(page);
			pageID++;
		}
	}

	/**
	 * Gets page starting with 1
	 * 
	 * @param page
	 * @return
	 */
	public Page getPage(int page) {
		try {
			return getPages().get(page - 1);
		} catch (Exception ex) {
			return getPages().get(0);
		}
	}

	/**
	 * "Pretty" Name of the comic (with all the bracket stuff removed)
	 * 
	 * @return String
	 */
	public String getPretty() {
		return pretty;
	}

	/**
	 * English Name of the comic
	 * 
	 * @return String
	 */
	public String getEnglish() {
		return english;
	}

	/**
	 * Japanese Name of the comic
	 * 
	 * @return String
	 */
	public String getJapanese() {
		return japanese;
	}

	/**
	 * Comic ID thats also used in the URL
	 * 
	 * @return int
	 */
	public int getId() {
		return id;
	}

	/**
	 * Media ID used in the URL for the images
	 * 
	 * @return int
	 */
	public int getMediaID() {
		return mediaID;
	}

	/**
	 * Number of pages in the comic
	 * 
	 * @return int
	 */
	public int getNumPages() {
		return numPages;
	}

	/**
	 * How often the comic has been favourized by users
	 * 
	 * @return int
	 */
	public int getFavourites() {
		return favourites;
	}

	/**
	 * Get normal tags in String form
	 * 
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getTags() {
		return tags;
	}

	/**
	 * Get Authors in String form
	 * 
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getAuthors() {
		return authors;
	}

	/**
	 * Get Characters in String form
	 * 
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getCharacters() {
		return characters;
	}

	/**
	 * Get Parodies in String form
	 * 
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getParodies() {
		return parodies;
	}

	/**
	 * Get Languages in String form
	 * 
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getLanguages() {
		return languages;
	}

	/**
	 * Get the list of pages
	 * 
	 * @return ArrayList<Page>
	 */
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
