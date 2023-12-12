package szu.vo;

import lombok.Data;

@Data
public class BarrageVo {
    private String text;
    private int time;
    private String color;
    private boolean border = false;
    private int mode;
}
