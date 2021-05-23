## nHentai4j
A java library for usage with the nHentai API focused mostly on ease-of-use written because other libraries were unsatisfactory<br>
Android Support is untested

### Required libraries:
- GSON
- Apache Commons Lang3

### Basic example usage:
#### Get comic by ID
```java
Comic comic = NHentai.getComic(341365);
if(comic != null) {
  for(Comic.Page page : comic.getPages()) {
    System.out.println(page.getUrl());
  }
}
```
#### Get random comic with query
```java
comic = NHentai.getRandomComic(NHentai.parseQuery(new NHentai.CustomTokenizer("yuri -ahegao")));
		
if(comic != null) {
  for(Comic.Page page : comic.getPages()) {
    System.out.println(page.getUrl());
  }
}
```
#### Get comic list for query
```java
for(Comic c : NHentai.getComicList(NHentai.parseQuery(new NHentai.CustomTokenizer("yuri -ahegao")), 0, false)) {
  System.out.println(c.getPretty());
}
```
