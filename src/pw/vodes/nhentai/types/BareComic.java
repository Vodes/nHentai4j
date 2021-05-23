package pw.vodes.nhentai.types;

import com.google.gson.annotations.SerializedName;

public class BareComic {
	
	@SerializedName("id")
	public int id;
	
	@SerializedName("media_id")
	public int media_id;
	
	@SerializedName("num_pages")
	public int pages;
	
	@SerializedName("num_favorites")
	public int favourites;
	
	/**
	 * UNIX epoche datetime
	 */
	@SerializedName("upload_date")
	public int upload_date;
	
	public String json;
	
	public Tag[] tags;
    
    public Title title;
	
	public BareComic setJSONString(String JSON) {
		this.json = JSON;
		return this;
	}
    
    public class Tag {
        public int id, count;
        public String type, name, url;
        
		@Override
		public String toString() {
			return name;
		}
    }
    
	public class Title {
		public String english, japanese, pretty;
		
		@Override
		public String toString() {
			return pretty;
		}
	}
}
