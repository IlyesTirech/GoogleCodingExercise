package com.google;

import java.util.ArrayList;
import java.util.List;

public class Playlist {

private  String name;
private  int id;
private  List<Video> videos = new ArrayList<>();

    public Playlist(String name, List<Playlist> playlists) {
        this.name = name;
        this.id = generateID(playlists);

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Video> getVideos() {
        return videos;
    }

    private int generateID(List<Playlist> playlists){
        int id = 0;
        for (Playlist playlist: playlists ) {
            if(playlist.getId() > id){ id = playlist.getId() + 1;}}
        return id;
    }


}
