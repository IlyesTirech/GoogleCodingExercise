package com.google;

import java.util.*;

public class VideoPlayer {

  private final VideoLibrary videoLibrary;
  private List<Playlist> playlists = new ArrayList<>();

  private Video currentVideo = null;
  private boolean currentVideoPlaying = false;

  public VideoPlayer() {
    this.videoLibrary = new VideoLibrary();
  }

  public void numberOfVideos() {
    System.out.printf("%s videos in the library%n", videoLibrary.getVideos().size());
  }

  public void showAllVideos() {
    System.out.println("Here's a list of all available videos:");

    List<String> names = new ArrayList<>();
    for(Video video: videoLibrary.getVideos()){
      names.add(video.getVideoId());
    }
    Collections.sort(names);

    for(String name: names){
      Video video = videoLibrary.getVideo(name);
      System.out.print(video.getTitle()
              + " (" +
              video.getVideoId()
              + ") ");
      System.out.print("[");
      int counter = 1;

      for (String tag: video.getTags()) {
        if(counter == video.getTags().size()){
          System.out.print(tag);
        }else{
          System.out.print(tag+" ");
          counter++;
        }
      }
      System.out.print("]\n");
    }
  }

  public void playVideo(String videoId) {

    Video video = videoLibrary.getVideo(videoId);
    if(!(video == null)){
      if(currentVideo != null){
        stopVideo();
      }
      currentVideo = video;
      currentVideoPlaying = true;
      System.out.println("Playing video: "+ currentVideo.getTitle());
    }else{
      System.out.println("Cannot play video: Video does not exist");
    }
  }

  public void stopVideo() {
    if(currentVideo != null){
      currentVideoPlaying = false;
      System.out.println("Stopping video: "+currentVideo.getTitle());
      currentVideo = null;
    }else{
      System.out.println("Cannot stop video: No video is currently playing");
    }
  }

  public void playRandomVideo() {
    if(currentVideoPlaying){stopVideo();}
    Random random = new Random();
    int randomIndex = random.nextInt(videoLibrary.getVideos().size() - 1);
    Video randomVideo = videoLibrary.getVideos().get(randomIndex);
    playVideo(randomVideo.getVideoId());
  }

  public void pauseVideo() {
    if(currentVideo != null){
      if(currentVideoPlaying == true){
        currentVideoPlaying = false;
        System.out.println("Pausing video: "+currentVideo.getTitle());
      }else{
        System.out.println("Video already paused: "+currentVideo.getTitle());
      }
    }else{
      System.out.println("Cannot pause video: No video is currently playing");
    }
  }

  public void continueVideo() {
    if(currentVideo == null){
      System.out.print("Cannot continue video: No video is currently playing");
    }else{
      if(currentVideoPlaying == true){
        System.out.print("Cannot continue video: Video is not paused");
      }else{
        currentVideoPlaying = true;
        System.out.print("Continuing video: "+currentVideo.getTitle());
      }

    }
  }

  public void showPlaying() {

    if(currentVideo != null){

      if(currentVideoPlaying){
        System.out.print("Currently playing: "+currentVideo.getTitle()
                + " (" +
                currentVideo.getVideoId()
                + ") ");
        System.out.print("[");
        int counter = 1;

        for (String tag: currentVideo.getTags()) {
          if(counter == currentVideo.getTags().size()){
            System.out.print(tag);
          }else{
            System.out.print(tag+" ");
            counter++;
          }
        }
        System.out.print("]");
      }else{
        System.out.print("Currently playing: "+currentVideo.getTitle()
                + " (" +
                currentVideo.getVideoId()
                + ") ");
        System.out.print("[");
        int counter = 1;

        for (String tag: currentVideo.getTags()) {
          if(counter == currentVideo.getTags().size()){
            System.out.print(tag);
          }else{
            System.out.print(tag+" ");
            counter++;
          }
        }
        System.out.print("] - PAUSED");
      }
    }else{
      System.out.println("No video is currently playing!");
    }
  }

  public void createPlaylist(String playlistName) {
    boolean nameExists = false;
    for (Playlist playlist: this.playlists ) {
      if(playlist.getName().equals(playlistName)){
        nameExists = true;
      }
    }
    if(nameExists){
      System.out.println("playlist called : "+playlistName+" already exists!");
    }else {
      Playlist newPlaylist = new Playlist(playlistName, this.playlists);
      playlists.add(newPlaylist);
      System.out.println("playlist called : "+newPlaylist.getName()+" has been created!");
    }
  }

  public void addVideoToPlaylist(String playlistName, String videoId) {
    Playlist playlist = findPlaylistByName(playlistName, this.playlists);
    if(!(playlist == null)){
      Video video = videoLibrary.getVideo(videoId);
      if(!(video == null)){
        playlist.getVideos().add(video);
        System.out.println("Video added successfully!");
        System.out.println("Videos now present in playlist : ");
        for (Video videoItem: playlist.getVideos()) {
          System.out.println(videoItem.getTitle());
        }
      }else{
        System.out.println("The playlist exists but the video does not!");
      }
    }else{
      System.out.println("The playlist does not exist!");
    }
  }

  public void showAllPlaylists() {
    System.out.println("The following playlists are available : ");
    for (Playlist playlist: this.playlists ) {
      System.out.println("Playlist : "+playlist.getName());
      System.out.println("  Contains the following videos: ");
      for (Video video: playlist.getVideos()) {
        System.out.println("   "+video.getTitle());
      }
    }
  }

  public void showPlaylist(String playlistName) {
    Playlist playlist = findPlaylistByName(playlistName, this.playlists);
    if(playlist != null){
      System.out.println("Playlist : "+playlist.getName());
      System.out.println("  Contains the following videos: ");
      for (Video video: playlist.getVideos()) {
        System.out.println("   "+video.getTitle());
      }
    }else{
      System.out.println("PLaylist could not be found!");
    }
  }

  public void removeFromPlaylist(String playlistName, String videoId) {
    Playlist playlist = findPlaylistByName(playlistName, this.playlists);
    if(!(playlist == null)){
      Video video = videoLibrary.getVideo(videoId);
      if(!(video == null)){
        playlist.getVideos().remove(video);
        System.out.println("Video removed successfully!");
        System.out.println("Videos now present in playlist : ");
        for (Video videoItem: playlist.getVideos()) {
          System.out.println(videoItem.getTitle());
        }
      }else{
        System.out.println("The playlist exists but the video does not!");
      }
    }else{
      System.out.println("The playlist does not exist!");
    }
  }

  public void clearPlaylist(String playlistName) {
    Playlist playlist = findPlaylistByName(playlistName, this.playlists);
    if(!(playlist == null)){
      for (Video video: playlist.getVideos()) {
        playlist.getVideos().remove(video);
      }
      System.out.println("All videos removed!");
    }else{
      System.out.println("The playlist does not exist!");
    }
  }

  public void deletePlaylist(String playlistName) {
    Playlist playlist = findPlaylistByName(playlistName, this.playlists);
    if (playlist != null) {
      this.playlists.remove(playlist);
      System.out.println("playlist removed");
    }else{
      System.out.println("Playlist could not be found");
    }
  }

  public void searchVideos(String searchTerm) {
    System.out.println("searchVideos needs implementation");
  }

  public void searchVideosWithTag(String videoTag) {
    System.out.println("searchVideosWithTag needs implementation");
  }

  public void flagVideo(String videoId) {
    System.out.println("flagVideo needs implementation");
  }

  public void flagVideo(String videoId, String reason) {
    System.out.println("flagVideo needs implementation");
  }

  public void allowVideo(String videoId) {
    System.out.println("allowVideo needs implementation");
  }

  private Playlist findPlaylistByName(String name, List<Playlist> playlists){
    for (Playlist playlist: playlists ) {
      if(playlist.getName().equalsIgnoreCase(name)){
        return playlist;
      }
    }
    return null;
  }


}