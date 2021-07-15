package com.exalt.reddit.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Link extends Auditable{

    @Id @GeneratedValue private Long id;

    @NonNull private String url;

    @NonNull private String title;

    @OneToMany(mappedBy = "link")
    private List<Comment> comments;

    public Link(@NonNull String url, @NonNull String title) {
        this.url = url;
        this.title = title;
    }

    public void addComment(Comment comment) {
        if (comments == null) {
            comments = new ArrayList<>();
        }
        comments.add(comment);
    }
}
