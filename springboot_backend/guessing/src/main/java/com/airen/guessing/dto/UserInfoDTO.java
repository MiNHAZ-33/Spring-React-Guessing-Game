package com.airen.guessing.dto;

public class UserInfoDTO {
    private String name;
    private Integer win;
    private Integer lost;

    public UserInfoDTO(String name, Integer win, Integer lost) {
        this.name = name;
        this.win = win;
        this.lost = lost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getWin() {
        return win;
    }

    public void setWin(Integer win) {
        this.win = win;
    }

    public Integer getLost() {
        return lost;
    }

    public void setLost(Integer lost) {
        this.lost = lost;
    }
}