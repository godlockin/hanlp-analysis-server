package com.model;

import com.hankcs.hanlp.seg.common.Term;
import lombok.Data;

@Data
public class Item {
    private String text;
    private Integer startIndex;
    private Integer endIndex;
    private String pos;

    public Item(Term term) {
        this.text = term.word;
        this.startIndex = term.offset;
        this.endIndex = term.offset + term.length();
        this.pos = term.nature.toString();
    }
}
