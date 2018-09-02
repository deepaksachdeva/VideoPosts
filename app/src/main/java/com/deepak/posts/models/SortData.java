package com.deepak.posts.models;

import java.util.Comparator;

public class SortData implements Comparator<Post> {
    private int pos;
    public SortData(int pos){
        this.pos = pos;
    }

    @Override
    public int compare(Post p1, Post p2) {
        if(pos == 1) {
            return p1.getEventDate().intValue() - p2.getEventDate().intValue();
        }else if(pos == 2){
            return p1.getLikes() - p2.getLikes();
        }else if(pos == 3){
            return p1.getViews() - p2.getViews();
        }else{
            return p1.getShares() - p2.getShares();
        }
    }
}
