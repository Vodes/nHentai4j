package pw.vodes.nhentai;

import org.apache.commons.lang3.StringUtils;

import pw.vodes.nhentai.types.Comic;

public class MarkdownUtil {
	
    public static String getCharacterString(Comic comic) {
    	String s = "";
    	for(String chara : comic.getCharacters()) {
			s += String.format("[%s](%s), ", chara, Finals.CHARACTER_PREFIX + chara.trim().replaceAll("[^a-zA-Z0-9]", "-"));
    	}
		if(s.trim().endsWith(",")) {
			s = StringUtils.removeEnd(s.trim(), ",");
		}
    	return s;
    }
    
    public static String getParodyString(Comic comic) {
    	String s = "";
    	for(String parody : comic.getCharacters()) {
			s += String.format("[%s](%s), ", parody, Finals.PARODY_PREFIX + parody.trim().replaceAll("[^a-zA-Z0-9]", "-"));
    	}
		if(s.trim().endsWith(",")) {
			s = StringUtils.removeEnd(s.trim(), ",");
		}
    	return s;
    }
    
    public static String getArtistString(Comic comic) {
    	String s = "";
    	for(String artist : comic.getAuthors()) {
			s += String.format("[%s](%s), ", artist, Finals.ARTIST_PREFIX + artist.trim().replaceAll("[^a-zA-Z0-9]", "-"));
    	}
		if(s.trim().endsWith(",")) {
			s = StringUtils.removeEnd(s.trim(), ",");
		}
    	return s;
    }
    
    public static String getLanguage(Comic comic) {
    	String def = "Unknown";
    	String s = "";
    	for(String lang : comic.getLanguages()) {
    		s += StringUtils.capitalize(lang.trim()) + ", ";
    	}
		if(s.trim().endsWith(",")) {
			s = StringUtils.removeEnd(s.trim(), ",");
		}
    	return s.isEmpty() ? def : s;
    }

}
