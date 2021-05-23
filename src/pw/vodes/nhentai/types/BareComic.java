package pw.vodes.nhentai.types;

import com.google.gson.annotations.SerializedName;

public class BareComic {
	
	@SerializedName("id")
	public int id;
	
	@SerializedName("media_id")
	public int media_id;
	
	@SerializedName("num_pages")
	public int pages;
	
	@SerializedName("num_favorties")
	public int favourites;
	
	/**
	 * UNIX epoche datetime
	 */
	@SerializedName("upload_date")
	public int upload_date;
	
	public String json;
	
	public Tag[] tags;
    
    public Title title;

	public class Title {
		
		String english, japanese, pretty;

		public Title(String english) {
			this.english = english;
		}

		@Override
		public String toString() {
			return pretty;
		}
	}
	
	public BareComic setJSONString(String JSON) {
		this.json = JSON;
		return this;
	}
    

    //Comic Tag
    public class Tag {
        int id, count;
        String type, name, url;

        public Tag(int id, int count, String type, String name, String url) {
            this.id = id;
            this.count = count;
            this.type = type;
            this.name = name;
            this.url = url;
        }
    }
}
